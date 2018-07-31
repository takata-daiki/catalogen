package PropertyModel;

import java.util.Stack;

/**
 * The StringWrapper class is used to pass String values by reference when used
 * as a parameter the internal value will be manipulated by the method!
 *
 * @author phreax
 */
final class StringWrapper
{
  private String string;

  public StringWrapper()
  {
    string = null;
  }

  public StringWrapper(final String s)
  {
    string = s;
  }

  public void setString(final String s)
  {
    string = s;
  }

  public String getString()
  {
    return string;
  }

  @Override
  public String toString()
  {
    return this.string;
  }

}

/**
 * This class represents a whole tree of properties<br /> the propertytree has
 * the following structure:<br />
 * <code>
 * [rootproperty]<br />
 * [subproperty]it's string value[/subproperty]<br />
 * [/rootproperty]<br />
 * </code>where properties that have suproperties are not allowed to have values
 * themselves.<br /> Creating a tree would already be possible using propeties
 * alone, but this class adds the functionality to acces any value in the tree
 * by a path. The above example would be create by the following code:<br />
 * <code>PropertyTree tree = new PropertyTree(rootproperty);<br />
 * tree.setValueByPath("rootproperty/subproperty","it's string value",false)</code>
 *
 * @author phreax
 */
public final class PropertyTree
{
  private final Property rootProperty;

  /**
   * creates a new property tree
   *
   * @param name the name for the root property
   * @throws IllegalPropOperation if name is does not match propety name pattern
   */
  public PropertyTree(final String name)
          throws IllegalPropOperation
  {
    rootProperty = new Property(name);
  }

  /**
   * creates anew property tree
   *
   * @param root the root proerty of thsi tree
   */
  public PropertyTree(final Property root)
  {
    rootProperty = root;
  }

  private static boolean isOpenToken(final String request_part)
  {
    return request_part.matches("\\[[a-zA-Z0-9]+\\].*");
  }

  private static boolean isCloseToken(final String request_part)
  {
    return request_part.matches("\\[/[a-zA-Z0-9]+\\].*");
  }

  private static String getOpenTokenName(final String request_part)
  {
    return request_part.substring(1, request_part.indexOf("]"));
  }

  private static String getCloseTokenName(final String request_part)
  {
    return request_part.substring(2, request_part.indexOf("]"));
  }

  private static String getValue(final String request_part)
  {
    String result = "";
    String current_part;
    for (int idx = 0; idx < request_part.length(); idx++)
    {
      current_part = request_part.substring(idx);
      if (!isCloseToken(current_part) && !isOpenToken(current_part))
      {
        result += request_part.charAt(idx);
      } else
      {
        break;
      }
    }
    return result;
  }

  private static String stripToken(final String request_part)
  {
    return request_part.substring(request_part.indexOf("]") + 1);
  }

  private static String stripValue(final String request_part)
  {
    final int startIdx = request_part.indexOf("[");
    if (startIdx == -1)
    {
      return "";
    }
    return request_part.substring(startIdx);
  }

  private String iterateSubPropertySerializer(final Property prop, final String serialized)
          throws IllegalPropOperation
  {
    String result = serialized;
    Stack<String> opentags = new Stack<>();

    result += "[" + prop.getName() + "]";
    opentags.push(prop.getName());

    if (prop.hasValue())
    {
      result += prop.getValue();
    }

    for (final Property subprop : prop)
    {
      result = iterateSubPropertySerializer(subprop, result);
    }
    while (!opentags.empty()) //close opened tags
    {
      result += "[/" + opentags.pop() + "]";
    }
    return result;
  }

  public boolean validate()
          throws IllegalPropOperation
  {
    return rootProperty.checkConsistency();
  }

  /**
   * returns the string representation of this object
   *
   * @return the serialized string version of this tree
   */
  @Override
  public String toString()
  {
    String result = "An error occured!";
    try
    {
      result = iterateSubPropertySerializer(rootProperty, "");
    } catch (IllegalPropOperation ex)
    {
      throw new RuntimeException(ex);
    }
    return result;
  }

  private static void parseOpenToken(final StringWrapper source_part, final StringWrapper current_path)
  {
    current_path.
            setString(current_path.getString() + "/" + getOpenTokenName(source_part.
            getString()));
    source_part.setString(stripToken(source_part.getString()));
  }

  private static void parseCloseToken(final StringWrapper source_part, final StringWrapper current_path)
          throws IllegalPropOperation, PropParseException
  {
    final String token = getCloseTokenName(source_part.getString());
    if (!token.
            equals(PropertyTree.getLastPropFromPath(current_path.getString())))
    {
      throw new PropParseException(
              "\"" + token + "\" is not the current open Token!");
    }
    current_path.setString(PropertyTree.stripLastPropFromPath(current_path.
            getString()));
    source_part.setString(stripToken(source_part.getString()));
  }

  private static void parseValue(final PropertyTree tree, final StringWrapper source_part, final StringWrapper current_path)
          throws IllegalPropOperation, PropParseException
  {
    final String value = getValue(source_part.getString());
    String oldvalue = "";
    try
    {
      oldvalue = tree.getValueFromPath(current_path.getString());
    } catch (IllegalPropOperation e)
    {
    }
    if (!oldvalue.isEmpty() && !value.isEmpty())
    {
      throw new PropParseException(
              "Double Value definition! Check the consistence of the supplied string!");
    }

    if (oldvalue.isEmpty())
    {
      tree.setValueByPath(current_path.getString(), value, true);
      source_part.setString(stripValue(source_part.getString()));
    }
  }

  /**
   * create a new property tree from its serialized version in a string
   *
   * @param source the string from which this proeprty tree should be created
   * @return the new property tree
   * @throws PropParseException if source string contains errors
   * @throws IllegalPropOperation if source string contains invalid properties
   */
  public static PropertyTree createFromString(final String source)
          throws PropParseException, IllegalPropOperation
  {
    if (!isOpenToken(source))
    {
      throw new PropParseException("Request must start with Token");
    }
    final StringWrapper currentPath = new StringWrapper(getOpenTokenName(source));
    final PropertyTree result = new PropertyTree(currentPath.getString());
    final StringWrapper source_part = new StringWrapper(source);
    source_part.setString(stripToken(source_part.getString()));
    while (!source_part.getString().isEmpty())
    {
      while (isOpenToken(source_part.getString()))
      {
        parseOpenToken(source_part, currentPath);
        parseValue(result, source_part, currentPath);
      }
      while (isCloseToken(source_part.getString()))
      {
        parseCloseToken(source_part, currentPath);
        parseValue(result, source_part, currentPath);
      }
    }
    return result;

  }

  /**
   * get the last property form a propertytree path
   *
   * @param path the properrty tree path
   * @return the name of the trailing property from the gieb path
   */
  public static String getLastPropFromPath(final String path)
  {
    int slashpos = 0;
    String result = "/" + stripSlashes(path);
    for (int i = result.length() - 1; i >= 0; i--)
    {
      if (result.charAt(i) == '/')
      {
        slashpos = i;
        break;
      }
    }
    result = result.substring(slashpos + 1);

    return result;
  }

  /**
   * remove the last property form a propertytree path
   *
   * @param path the property tree path
   * @return the path without the trailing property
   */
  public static String stripLastPropFromPath(final String path)
  {
    int slashpos = 0;
    String result = "/" + stripSlashes(path);
    for (int i = result.length() - 1; i >= 0; i--)
    {
      if (result.charAt(i) == '/')
      {
        slashpos = i;
        break;
      }
    }
    result = result.substring(0, slashpos);

    return result;
  }

  /**
   * get the first property form a propertytree path
   *
   * @param path the property tree path
   * @return the name of the leading property from the given path
   */
  public static String getFirstPropFromPath(final String path)
  {
    final String s = stripSlashes(path) + "/";
    return s.substring(0, s.indexOf('/'));
  }

  private static String stripSlashes(final String path)
  {
    String result = path;
    if ("/".equals(result))
    {
      return "";
    }
    if (result.endsWith("/"))
    {
      result = result.substring(0, result.length() - 2);
    }
    if (result.startsWith("/"))
    {
      result = result.substring(1);
    }
    return result;
  }

  /**
   * remove the first property form a propertytree path
   *
   * @param path the property tree path
   * @return the path without the leading property
   */
  public static String stripFirstPropFromPath(final String path)
  {
    String result = stripSlashes(path);

    while (result.length() > 0 && result.charAt(0) != '/')
    {
      result = result.substring(1);
    }
    return result;
  }

  /**
   * gets the value from the porperty at the given path
   *
   * @param path property tree path
   * @return the value at teh given path
   * @throws IllegalPropOperation if the value deos not exist
   */
  public String getValueFromPath(final String path)
          throws IllegalPropOperation
  {
    return this.getPropertyFromPath(path).getValue();
  }

  /**
   * get a property from a property tree path
   *
   * @param path the path to the proeprty
   * @return the property at the givene path
   * @throws IllegalPropOperation if the property does not exist
   */
  public Property getPropertyFromPath(final String path)
          throws IllegalPropOperation
  {
    if (path.isEmpty() || path.equals(rootProperty.getName()))
    {
      return rootProperty;
    }
    String currentPath = stripRootPropFrompath(path);
    String currentPropName = getFirstPropFromPath(currentPath);
    Property currentProperty = rootProperty;
    while (!currentPropName.isEmpty())
    {
      currentProperty = currentProperty.getsubProperty(currentPropName);
      currentPath = stripFirstPropFromPath(currentPath);
      currentPropName = getFirstPropFromPath(currentPath);
    }
    return currentProperty;
  }

  private String getRootName()
  {
    return rootProperty.getName();
  }

  private String stripRootPropFrompath(final String path)
  {
    if (getFirstPropFromPath(path).equals(getRootName()))
    {
      return stripFirstPropFromPath(path);
    }
    return path;
  }

  //this function is private because it hides a lot of the inner functionalitity of the tree
  //since a Property already contains its name AND value, you have the same result as addValueByPath
  //BUT you don't see the full path, because the last path name is hidden in the Property
  //in a nutshell: you will obfuscate your code when using this function!
  private void addPropertyByPath(final String path, final Property prop, final boolean overwrite)
          throws IllegalPropOperation
  {
    String currentPath = stripRootPropFrompath(path);
    String currentPropName = getFirstPropFromPath(currentPath);

    Property currentProperty = rootProperty;

    while (!currentPropName.isEmpty())
    {
      if (currentProperty.doesSubPropertyExist(currentPropName))
      {
        currentProperty = currentProperty.getsubProperty(currentPropName);
      } else
      {
        final Property newprop = new Property(currentPropName);
        currentProperty.addSubProperty(newprop);
        currentProperty = newprop;
      }

      currentPath = stripFirstPropFromPath(currentPath);
      currentPropName = getFirstPropFromPath(currentPath);
    }
    if (currentProperty.doesSubPropertyExist(prop.getName()))
    {
      if (!overwrite)
      {
        throw new IllegalPropOperation(path + " contains already a subproperty named " + prop.
                getName());
      }
      for (int i = 0; i < currentProperty.countSubProperties(); i++)
      {
        if (currentProperty.getName().equals(prop.getName()))
        {
          currentProperty.removeSubProperty(i);
        }
      }
    }
    currentProperty.addSubProperty(prop);
  }

  /**
   * set a value in the tree
   *
   * @param path the path to the new value
   * @param value the new value
   * @param overwrite true if you want to overwrite existing values
   * @throws IllegalPropOperation if you try to overwrite an existing avlue with
   * <code>overwrite == false</code>
   */
  public void setValueByPath(final String path, final String value, final boolean overwrite)
          throws IllegalPropOperation
  {
    if (this.doesPropertyExist(path))
    {
      if (!overwrite)
      {
        throw new IllegalPropOperation(
                path + " contains already a value (overwrite is disabled)!");
      }
      this.getPropertyFromPath(path).setValue(value);
    } else
    {
      String newPath = path;
      if (newPath.isEmpty() || "/".equals(newPath))
      {
        newPath = "/" + getRootName();
      }
      final Property newProp = new Property(getLastPropFromPath(newPath), value);
      newPath = stripLastPropFromPath(newPath);
      this.addPropertyByPath(newPath, newProp, overwrite);
    }
  }

  /**
   * check if a proeprty exists in the tree
   *
   * @param path the path to the property
   * @return true if the property exists
   * @throws IllegalPropOperation if something goes wrong :)
   */
  public boolean doesPropertyExist(final String path)
          throws IllegalPropOperation
  {
    String currentPath = stripRootPropFrompath(path);
    String currentPropName = getFirstPropFromPath(currentPath);

    Property currentProperty = this.rootProperty;

    boolean result = true;

    while (!currentPath.isEmpty())
    {
      result = currentProperty.doesSubPropertyExist(currentPropName);
      if (!result)
      {
        return result;
      }
      currentProperty = currentProperty.getsubProperty(currentPropName);
      currentPath = stripFirstPropFromPath(currentPath);
      currentPropName = getFirstPropFromPath(currentPath);
    }
    return result;
  }

}

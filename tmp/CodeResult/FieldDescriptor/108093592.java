package fm.last.mucene.schema;

public class FieldDescriptor {
  private String name;
  private FieldType type;
  private boolean stored;
  private boolean indexed;

  public FieldDescriptor(String name, FieldType type) {
    super();
    this.name = name;
    this.type = type;
  }

  public FieldDescriptor(String name, FieldType type, boolean stored, boolean indexed) {
    super();
    this.name = name;
    this.type = type;
    this.stored = stored;
    this.indexed = indexed;
  }

  public String getName() {
    return name;
  }

  public FieldType getFieldType() {
    return type;
  }

  public boolean isStored() {
    return stored;
  }

  public boolean isIndexed() {
    return indexed;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (indexed ? 1231 : 1237);
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + (stored ? 1231 : 1237);
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FieldDescriptor other = (FieldDescriptor) obj;
    if (indexed != other.indexed)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (stored != other.stored)
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }

}

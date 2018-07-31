package org.charter_project.graph.analysis;

import org.charter_project.graph.analysis.definition.RDTDefinition;
import org.charter_project.graph.analysis.definition.RDTOperation;
import org.charter_project.graph.client.AnnotationException;
import org.charter_project.graph.client.ExpressionType;
import org.charter_project.graph.client.GraphException;
import org.charter_project.graph.client.Support;

/**
 * An <code>InvalidOperationException</code> is a {@link AnnotationException}
 * that is thrown when a {@link RDTOperation} is encountered with an invalid
 * type.
 *
 * @author Maarten de Mol
 * @version 1.0
 */
public class InvalidOperationException extends AnnotationException
{
  // Generated automatically by Eclipse.
  private static final long serialVersionUID = 6080171826880809390L;

  /** Explicitly inherited constructor of the supertype. */
  public InvalidOperationException(String message,
                                   RDTDefinition<?, ?>... defs)
  {
    super(message, defs);
  }

  /**
   * Throws an <code>InvalidOperationException</code> for a
   * {@link RDTOperation} that has an illegal argument type. The
   * <code>context</code> argument indicates that the error only occurs for a
   * special use of the operation. This argument is ignored when it is
   * <code>null</code>.
   */
  public static InvalidOperationException disallowed(ExpressionType type,
                                                     RDTOperation<?, ?> op,
                                                     String context)
  {
    return new InvalidOperationException("The argument type " + type +
                                         " is illegal for the @" +
                                         op.getAnnotationType() +
                                         " operation" +
                                         ((context == null) ?
					  "" :
					  " " + context) + ".",
					 op);
  }

  /**
   * Throws an <code>InvalidOperationException</code> for a
   * {@link RDTOperation} with a duplicate argument.
   */
  public static InvalidOperationException duplicate(ExpressionType type,
                                                    RDTOperation<?, ?> op)
  {
    return new InvalidOperationException("The @" + op.getAnnotationType() +
					 " operation may not have more than" +
					 " one argument of type " +
					 type + ".",
					 op);
  }

  /**
   * Throws an <code>InvalidOperationException</code> for a
   * {@link RDTOperation} with more than one {@link Support @Support}
   * argument.
   */
  public static
    InvalidOperationException duplicateSupport(RDTOperation<?, ?> op)
  {
    return new InvalidOperationException("The @" + op.getAnnotationType() +
					 " operation may not have more than" +
					 " one @Support argument.",
                                         op);
  }

  /**
   * Throws an <code>InvalidOperationException</code> for a
   * {@link RDTOperation} that throws an exception other than a
   * {@link GraphException} or a {@link RuntimeException}.
   */
  public static InvalidOperationException exception(RDTOperation<?, ?> op)
  {
    return new InvalidOperationException("A RDT operation may only throw" +
					 " exceptions that are a subtype" +
					 " of either GraphException or" +
					 " RuntimeException.",
                                         op);
  }

  /**
   * Throws an <code>InvalidOperationException</code> for a
   * {@link RDTOperation} that does not have an expected argument type. The
   * <code>context</code> argument indicates that the error only occurs for a
   * special use of the operation. This argument is ignored when it is
   * <code>null</code>.
   */
  public static InvalidOperationException missing(ExpressionType expected,
                                                  RDTOperation<?, ?> op,
                                                  String context)
  {
    if (op.getAnnotationType() == AnnotationType.EdgeReplace)
      {
        return new InvalidOperationException("The @EdgeReplace operation" +
					     " must have exactly two" +
					     " arguments of type " +
					     expected + ".",
					     op);
      }
    else
      {
        return new InvalidOperationException("The @" + op.getAnnotationType() +
					     " operation" +
					     ((context == null) ?
					      "" :
					      " " + context) +
					     " must have an argument of type " +
					     expected + ".",
					     op);
      }
  }

  /**
   * Throws an <code>InvalidOperationException</code> for a
   * {@link RDTOperation} that is defined on the factory class, but for which
   * the corresponding node type cannot be inferred.
   */
  public static InvalidOperationException noNode(RDTOperation<?, ?> op)
  {
    return new InvalidOperationException("Unable to recognize node type" +
					 " to which the factory" +
					 " operation belongs.",
					 op);
  }

  /**
   * Throws an <code>InvalidOperationException</code> for a
   * {@link RDTOperation} with an invalid result type.
   */
  public static InvalidOperationException result(ExpressionType expected,
                                                 ExpressionType got,
                                                 RDTOperation<?, ?> op)
  {
    return new InvalidOperationException("The @" + op.getAnnotationType() +
                                         " operation must return " +
                                         expected + " instead of " + got +
                                         ".",
					 op);
  }

  /**
   * Throws an <code>InvalidOperationException</code> for a
   * {@link RDTOperation} with an argument type that is not part of the
   * expected type signature.
   */
  public static InvalidOperationException superfluous(int index,
                                                      ExpressionType type,
                                                      RDTOperation<?, ?> op)
  {
    return new InvalidOperationException("The " + type + " argument(#" +
					 Integer.toString(index + 1) +
					 ") of the @" +
					 op.getAnnotationType() +
					 " operation is not part of the" +
					 " expected type signature.",
                                         op);
  }

  /**
   * Throws an <code>InvalidOperationException</code> for a node
   * {@link RDTOperation} that has a different node as argument than the node
   * class that it appears in. The <code>isResult</code> indicates whether the
   * node is a result type or an argument type.
   */
  public static InvalidOperationException wrongNode(RDTOperation<?, ?> op,
                                                    boolean isResult)
  {
    return new InvalidOperationException("The node " +
                                         (isResult ? "result" : "argument") +
                                         " of the @" +
                                         op.getAnnotationType() +
                                         " operation must be of type " +
                                         op.getRepresentation()
					   .getDeclaringClass()
					   .getSimpleName() +
					 ".",
					 op);
  }
}

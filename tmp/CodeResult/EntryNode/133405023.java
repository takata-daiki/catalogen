package bubble.code;

import org.charter_project.graph.client.*;
import org.charter_project.graph.client.GraphVisitor.CONTINUE;

@Node
public class EntryNode implements NextEdge
{
  @Override
  public EntryNode getNextEdge()
  {
    return next;
  }

  @Override
  public void setNextEdge(EntryNode next)
  {
    this.next = next;
  }

  @AttributeGet
  public int getValue()
  {
    return value;
  }

  @AttributeSet
  public void setValue(int value)
  {
    this.value = value;
  }

  @NodeVisit
  public static CONTINUE
  visitEntries(Graph graph, GraphVisitor<EntryNode> visitor)
  throws GraphException
  {
    EntryNode entry = graph.getRoot();
    while (entry != null)
      {
        if (visitor.apply(entry) == CONTINUE.NO)
          {
            return CONTINUE.NO;
          }
        else
          {
            entry = entry.getNextEdge();
          }
      }
    return CONTINUE.YES;
  }

  private EntryNode next = null;

  private int value;
}

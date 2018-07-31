/*
 * Module.java
 * 
 * last update: 16.01.2010 by Stefan Saru
 * 
 * author:	Alec(panovici@elcom.pub.ro)
 * 
 * Obs:
 */

package engine;

import java.util.*;
import middle.ScopeNode;

/**
 * A module instance. It acts much like some lnked .obj file into an executable
 * image
 */
class Module extends NameSpace {

  ModuleInstanceDescription id;
  boolean hasIndex;
  int index;

  Module (DefaultNameSpaceDescription desc, NameSpace parent,
          ModuleInstanceDescription id,
          ScopeNode thisScope) throws ParseException {
    super(parent, desc);
    this.id = id;
    downwardAllowed = false;
    hasIndex = false;
    desc.instantiateAll(this, thisScope);
  }
  
  Module (DefaultNameSpaceDescription desc, NameSpace parent,
          ModuleInstanceDescription id,
          int index,
          ScopeNode thisScope) throws ParseException {
    super(parent, desc);
    this.id = id;
    downwardAllowed = false;
    this.index = index;
    hasIndex = true;
    desc.instantiateAll(this, thisScope);
  }

  

  public String toString() {
    return (parent == null ? "" : parent + ".") + id.name +
      (hasIndex ? "[" +  index + "]" : "");
  }

}






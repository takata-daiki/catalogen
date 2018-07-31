package edu.stanford.bunnyworld.script;

import java.util.HashMap;
import java.util.Map;

/**
 * Operator class, wraps the available operators. Also holds information on each
 * of the operators statically. 
 * It is expected that the LHS of binary operators be in[0] and the RHS be in[1].
 * If the operator does assignment then the callback will evaluate the RHS only
 * of that assignment, ie for a += b the callback will evaluate a+b.
 * @author Steven
 *
 */
public class BOperator extends BCallable {
	
	private static Map<String, BOpInfo> opMap;
	
	public static class BOpInfo {
		public String op;
		public int precedence;
		public int numArgs;
		public boolean leftAssoc;
		public boolean doesAssign;
		public BRefFunction fn;
		public BOpInfo(String op, int precedence, int numArgs, boolean leftAssoc, BRefFunction fn) {
			this.op = op;
			this.precedence = precedence;
			this.numArgs = numArgs;
			this.leftAssoc = leftAssoc;
			this.fn = fn;
		}
	}
	
	static {
		opMap = new HashMap<String, BOpInfo>();
		
		//get object field
		// add definition for . operator (should be easy with refactored operators)
		
		//postfix increment/decrement - these autocast to integers
		opMap.put("++", new BOpInfo("++",8,1,false,new BRefFunction() {
			public void refEval(BExpression[] args, Object[] vals, BVarContext varContext, BFnContext fnContext) {
				if (!(args[0] instanceof BExprVariable))
					;//TODO: catch illegal assignments
				((BExprVariable) args[0]).setValue((Integer)vals[0]+1, varContext);
			}
			public Object getReturn(Object[] args) {
				return (Integer)args[0]+1;
			}
		}));
		opMap.put("--", new BOpInfo("--",8,1,false,new BRefFunction() {
			public void refEval(BExpression[] args, Object[] vals, BVarContext varContext, BFnContext fnContext) {
				if (!(args[0] instanceof BExprVariable))
					;//TODO: catch illegal assignments
				((BExprVariable) args[0]).setValue((Integer)vals[0]-1, varContext);
			}
			public Object getReturn(Object[] args) {
				return (Integer)args[0]-1;
			}
		}));
		
		/*
		//unary plus/minus, negation
		opMap.put("+", new BOpInfo("+",7,1,true, new BCallback() {
			public Object call(Object[] in, String varName, BVarContext varContext) {
				return in[0];
			}
		}));
		opMap.put("-", new BOpInfo("-",7,1,true));
		opMap.put("!", new BOpInfo("!",7,1,true));
		*/
		
		//infix arithmetic
		opMap.put("*", new BOpInfo("*",6,2,false,new BRefFunction() {
			public Object getReturn(Object[] in) {
				try {
					if (in[0] instanceof Integer || in[1] instanceof Integer)
						return (Integer)in[0] * (Integer)in[1];
					else
						return (Double)in[0] * (Double)in[1];
				} catch (ClassCastException e) {
					return null; //TODO: catch this fail
				}
			}
		}));
		opMap.put("/", new BOpInfo("/",6,2,false,new BRefFunction() {
			public Object getReturn(Object[] in) {
				try {
					if (in[0] instanceof Integer || in[1] instanceof Integer)
						return (Integer)in[0] / (Integer)in[1];
					else
						return (Double)in[0] / (Double)in[1];
				} catch (ClassCastException e) {
					return null; //TODO: catch this fail
				}
			}	
		}));
		opMap.put("+", new BOpInfo("+",5,2,false,new BRefFunction() {
			public Object getReturn(Object[] in) {
				try {
					if (in[0] instanceof Integer || in[1] instanceof Integer)
						return (Integer)in[0] + (Integer)in[1];
					else
						return (Double)in[0] + (Double)in[1];
				} catch (ClassCastException e) {
					return null; //TODO: catch this fail
				}
			}	
		}));
		opMap.put("-", new BOpInfo("-",5,2,false,new BRefFunction() {
			public Object getReturn(Object[] in) {
				try {
					if (in[0] instanceof Integer || in[1] instanceof Integer)
						return (Integer)in[0] - (Integer)in[1];
					else
						return (Double)in[0] - (Double)in[1];
				} catch (ClassCastException e) {
					return null; //TODO: catch this fail
				}
			}	
		}));
		
		/*
		//relational comparisons
		opMap.put("<", new BOpInfo("<",4,false));
		opMap.put(">", new BOpInfo(">",4,false));
		opMap.put("<=", new BOpInfo("<=",4,false));
		opMap.put(">=", new BOpInfo(">=",4,false));
		
		//relational equality
		opMap.put("==", new BOpInfo("==",3,false));
		opMap.put("!=", new BOpInfo("!=",3,false));
	
		//logical connectors
		opMap.put("&&", new BOpInfo("&&",2,false));
		opMap.put("||", new BOpInfo("||",1,false));
		*/

		opMap.put(">", new BOpInfo(">",4,2,false,new BRefFunction() {
			public Object getReturn(Object[] in) {
				try {
					if (in[0] instanceof Integer || in[1] instanceof Integer)
						return (Integer)in[0] > (Integer)in[1];
					else
						return (Double)in[0] > (Double)in[1];
				} catch (ClassCastException e) {
					return null; //TODO: catch this fail
				}
			}	
		}));
		
		opMap.put("<", new BOpInfo("<",4,2,false,new BRefFunction() {
			public Object getReturn(Object[] in) {
				try {
					if (in[0] instanceof Integer || in[1] instanceof Integer)
						return (Integer)in[0] < (Integer)in[1];
					else
						return (Double)in[0] < (Double)in[1];
				} catch (ClassCastException e) {
					return null; //TODO: catch this fail
				}
			}	
		}));
		
		opMap.put("!=", new BOpInfo("!=",3,2,false,new BRefFunction() {
			public Object getReturn(Object[] in) {
				return !in[0].equals(in[1]);
			}	
		}));
		opMap.put("==", new BOpInfo("==",3,2,false,new BRefFunction() {
			public Object getReturn(Object[] in) {
				return in[0].equals(in[1]);
			}	
		}));
		
		//assignment
		opMap.put("=", new BOpInfo("=",0,2,false,new BRefFunction() {
			public void refEval(BExpression[] args, Object[] vals, BVarContext varContext, BFnContext fnContext) {
				if (!(args[0] instanceof BExprVariable))
					;//TODO: catch illegal assignments
				((BExprVariable) args[0]).setValue(vals[1], varContext);
			}
			public Object getReturn(Object[] in) {
				return in[1];
			}	
		}));
		

		
	}
	
	public static BOpInfo getInfo(String op) {
		return opMap.get(op);
	}
	
	public static int getPrecedence(String op) {
		 return opMap.get(op).precedence;
	}
	
	public static int getNumArgs(String op) {
		 return opMap.get(op).numArgs;
	}
	
	public static boolean isLeftAssoc(String op) {
		 return opMap.get(op).leftAssoc;
	}
	
	public static boolean isOperator(String token) {
		return opMap.containsKey(token);
	}

	
	public Object call(Object[] args, BVarContext varContext, BFnContext fnContext) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int numArgs() {
		// TODO Auto-generated method stub
		return 0;
	}
}

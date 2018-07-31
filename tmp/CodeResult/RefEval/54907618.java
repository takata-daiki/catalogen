package edu.stanford.bunnyworld.script;

/**
 * "Reference function" abstract class - currently only used for operator callbacks (might
 * rename to reflect that). Allows manipulation of calling function's context.
 * @author Steven
 *
 */
public class BRefFunction extends BCallable {

	public void refEval(BExpression[] args, Object[] vals, BVarContext varContext, BFnContext fnContext) {
		//by default does nothing
	}
	
	public Object getReturn(Object[] args) {
		return null;
	}
	
	public Object call(Object[] args, BVarContext varContext,
			BFnContext fnContext) {

		Object[] evalArgs = new Object[args.length];
		for (int i=0; i<args.length; i++)
			evalArgs[i] =  ((BExpression) args[i]).eval(varContext, fnContext);
		
		refEval((BExpression[])args, evalArgs, varContext, fnContext);
		
		return getReturn(evalArgs);
	}

	public int numArgs() {
		return 0;
	}
}

package org.xmlcml.mathml;

import java.util.List;

import nu.xom.Element;
import nu.xom.Elements;

public abstract class SumProductElement extends AbstractMathElement implements Operator {


	protected APPLYElement parent;
	protected APPLYElement apply;
	protected CONDITIONElement condition;
	protected LOWLIMITElement lowlimit;
	protected LimitElement uplimit;
	BVARElement bvar;
	private Integer uplimitI;
	private Integer lowlimitI;
	private Number result;
	private List<?> objectList;

	protected SumProductElement(String tag) {
		super(tag);
	}
	
	@Override
	public Object eval() {
		throw new RuntimeException("NYI");
	}

	@Override
	protected void validate() {
		assertNoElementChildren();
		assertNoStringContent();
	}

	protected void getLimits(Elements elements) {
		for (int i = 2; i < elements.size()-1; i++) {
			Element element = elements.get(i);
			if (element instanceof CONDITIONElement) {
				if (condition != null) {
					throw new RuntimeException("multiple <condition/> not allowed");
				}
				condition = (CONDITIONElement) element;
				Object result = condition.eval();
				if (!(result instanceof List<?>)) {
					throw new RuntimeException("Cannot create iterator from <condition/>");
				}
				objectList = (List<?>) result;
			} else if (element instanceof LOWLIMITElement) {
				if (lowlimit != null) {
					throw new RuntimeException("multiple <lowlimit/> not allowed");
				}
				lowlimit = (LOWLIMITElement) element;
			} else if (element instanceof UPLIMITElement) {
				if (uplimit != null) {
					throw new RuntimeException("multiple <uplimit/> not allowed");
				}
				uplimit = (UPLIMITElement) element;
			}
		}
	}

	protected void getApplyBvar(Elements elements) {
		parent = (APPLYElement) this.getParent();
		if (!(elements.get(1) instanceof BVARElement)) {
			throw new RuntimeException("first sibling of <sum/> must be <bvar/>");
		}
		bvar = (BVARElement) elements.get(1);
		Element last = elements.get(elements.size()-1);
		if (!(last instanceof APPLYElement)) {
			throw new RuntimeException("last element must be <apply/>");
		}
		apply = (APPLYElement) last;
	}

	/**
	 <apply xmlns:m='http://www.w3.org/1998/Math/MathML'>
	   <sum/>
	   <bvar>
	     <ci> x </ci>
	   </bvar>
	   <lowlimit>
	     <ci> a </ci>
	   </lowlimit>
	   <uplimit>
	     <ci> b </ci>
	   </uplimit>
	   <apply>
	     <ci type='fn'> f </ci>
	     <ci> x </ci>
	   </apply>
	 </apply> 
	    
		<apply xmlns:m='http://www.w3.org/1998/Math/MathML'>
	      <sum/>
	      <bvar>
	        <ci> x </ci>
	      </bvar>
	      <condition>
	        <apply>
	          <in/>
	          <ci> x </ci>
	          <ci type='set'> B </ci>
	        </apply>
	      </condition>
	      <apply>
	        <ci type='fn'> f </ci>
	        <ci> x </ci>
	      </apply>
	    </apply> 
	 * 
	 */
	public Object apply(Elements elements, String tag) {
		if (elements.size() < 2) {
			throw new RuntimeException("not enough siblings of <"+tag+"/>");
		}
		getApplyBvar(elements);
		getLimits(elements);
		if (condition != null) {
			iterateOverCondition();
		} else if (uplimit != null && lowlimit != null) {
			iterateThroughIntegers();
		} else {
			throw new RuntimeException("limit(s) not given, cannot eval sum");
		}
		return result;
	}

	/**
	    "  <condition>" +
	    "    <apply>" +
	    "      <in/>" +
	    "      <ci> x </ci>" +
	    "      <ci type='set'> B </ci>" +
	    "    </apply>" +
	    "  </condition>" +
	 */
	private void iterateOverCondition() {
		result = 0;
		objectList = condition.getObjectList();
		for (Object object : objectList) {
			// not sure which to use yet
			this.setValueOfSymbol(bvar.getValue(), object);
			parent.setValueOfSymbol(bvar.getValue(), object);
			result = iterateResult(result, object, this);
		}
//		throw new RuntimeException("NYI");
	}

	private void iterateThroughIntegers() {
		try {
			uplimitI = uplimit.getInteger(); 
			lowlimitI = lowlimit.getInteger(); 
		} catch (Exception e) {
			throw new RuntimeException("Cannot get integer up- and low-limits "+uplimit+"/"+lowlimit);
		}
		if (uplimitI != null && lowlimitI != null && lowlimitI < uplimitI) {
			result = 0;
			for (Integer i = lowlimitI; i <= uplimitI; i++) {
				parent.setValueOfSymbol(bvar.getValue(), i);
				result = iterateResult(result, i, this);
			}
		}
	}

//	protected abstract Integer iterateResult(Integer result, Integer i, SumProductElement sumProduct);
	protected abstract Number iterateResult(Number result, Integer i, SumProductElement sumProduct);
	protected abstract Number iterateResult(Number result, Object object, SumProductElement sumProduct);

}

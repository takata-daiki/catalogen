package org.xmlcml.mathml;

import nu.xom.Elements;

public class SUMElement extends SumProductElement {

	public static String TAG = "sum";
	public SUMElement() {
		super(TAG);
	}

	public SUMElement(AbstractMathElement elem) {
		this();
		throw new RuntimeException("NYI");
	}
	
	public Object apply(Elements elements) {
		return apply(elements, TAG);
	}
	public AbstractMathElement differentiate() {
		throw new RuntimeException("NYI");
	}
	
//	protected Integer iterateResult(Integer result, Integer i, SumProductElement sumProduct) {
//		Integer value = (Integer) apply.eval();
//		return result + value;
//	}
	
	protected Number iterateResult(Number result, Integer i, SumProductElement sumProduct) {
		Number value = (Number) apply.eval();
		return add(result,  value);
	}
	
	private Number add(Number a, Number b) {
		if (a instanceof Integer && b instanceof Integer) {
			return (Integer) a + (Integer) b;
		} else if (a instanceof Integer && b instanceof Double) {
			return (Integer) a + (Double) b;
		} else if (a instanceof Double && b instanceof Integer) {
			return (Double) a + (Integer) b;
		} else {
			return (Double) a + (Double) b;
		}
	}
	
	protected Number iterateResult(Number result, Object object, SumProductElement sumProduct) {
		Object obj = apply.eval();
		Number value = (Number) obj;
		return add(result,  value);
	}
	
}

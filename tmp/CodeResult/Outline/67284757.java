package shapes.impl.composite;

import shapes.common.Shape;
import shapes.common.Visitor;

class Outline implements shapes.common.Outline {

	protected final Shape shape;
	
	public Outline(Shape shape) {
		this.shape = shape;
	}
	
	@Override
	public Shape getChild() {
		return shape;
	}
	
	@Override
	public <Result> Result accept(final Visitor<Result> v) {
		// TODO your job
		return null;
	}
}

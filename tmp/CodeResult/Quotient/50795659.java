public class Quotient extends BinaryOperation
{
	public Quotient(Expression e1, Expression e2)
	{
		super(e1, e2, '/');
	}
	
	protected double myOperation(double d1, double d2)
	{
		return d1 / d2;
	}
}

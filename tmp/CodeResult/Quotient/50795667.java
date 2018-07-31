public class Quotient extends Expression
{
	public Quotient(Expression e1, Expression e2)
	{
		lhs = e1;
		rhs = e2;
	}
	
	private Expression lhs;
    private Expression rhs;
    private char binaryOperation = '/';

    public String toString()
    {
        return "(" + lhs.toString() + " " + binaryOperation + " " + rhs.toString() + ")";
    };

    public double evaluateMe()
    {
        return lhs.evaluateMe() / rhs.evaluateMe();
    };
}

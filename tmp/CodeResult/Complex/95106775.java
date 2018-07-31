package it.uniroma3.signals.domain;

public class Complex {
	private double reale;
	private double immaginaria;
	
	public Complex() {
		this.reale = 0D;
		this.immaginaria = 0D;
	}
	
	public Complex(double reale) {
		this.reale = reale;
		this.immaginaria = 0D;
	}
	
	public Complex(double reale, double immaginaria) {
		this.reale = reale;
		this.immaginaria = immaginaria;
	}
	
	public double getReale() {
		return reale;
	}
	public void setReale(double reale) {
		this.reale = reale;
	}
	public double getImmaginaria() {
		return immaginaria;
	}
	public void setImmaginaria(double immaginaria) {
		this.immaginaria = immaginaria;
	}
	
	public double abs(){
		return Math.hypot(this.reale, this.immaginaria);
	}
	
	public Complex coniugato(){
		return new Complex(this.reale, - this.immaginaria);
	}
	
	public Complex moduloQuadro() {
		return this.coniugato().prodotto(this);
	}
	
	public Complex somma(Complex b){
		Complex result = new Complex(
			this.reale + b.getReale(),
			this.immaginaria + b.getImmaginaria()
		);
		return result;
	}
	
	public Complex somma(double b) {
		return this.somma(new Complex(b));
	}
	
	public Complex differenza(Complex b) {
		Complex result = new Complex(
			this.reale -  b.getReale(), 
			this.immaginaria - b.getImmaginaria()
		);
		return result;
	}
	
	public Complex differenza(double b) {
		return this.differenza(new Complex(b));
	}
	
	
	public Complex prodotto(Complex b) {
		Complex result = new Complex(
				this.reale * b.getReale() - this.immaginaria * b.getImmaginaria(),
				this.reale * b.getImmaginaria() + b.getReale() * this.immaginaria
		);
		return result;
	}
	
	public Complex prodotto(double b){
		Complex result = new Complex(
			this.reale * b,
			this.immaginaria * b
		);
		return result;
	}
	
	public Complex reciproco(){
		double scalare = Math.pow(this.reale,2) + Math.pow(this.immaginaria,2);
		Complex result = new Complex(this.reale/scalare , this.immaginaria/scalare);
		return result;
	}
	
	public Complex rapporto(Complex b){
		return this.prodotto(b.reciproco());
	}
	
	public Complex rapporto(double b) {
		return this.prodotto(1.0/b);
	}
	
	// e^{this}
	public Complex exp() {
		return new Complex(
			Math.exp(this.reale) * Math.cos(this.immaginaria),
			Math.exp(this.reale) * Math.sin(this.immaginaria)
		);
	}
	
	public Complex sin() {
	    return new Complex(
    		Math.cosh(this.immaginaria) * Math.sin(this.reale),
    		Math.sinh(this.immaginaria) * Math.cos(this.reale)
		);
	}

    public Complex cos() {
        return new Complex(
    		Math.cosh(this.immaginaria) * Math.cos(this.reale),
    		-1 * Math.sinh(this.immaginaria) * Math.sin(this.reale)
		);
    }
    
    public Complex sinh() {
        return new Complex(
    		Math.sinh(this.reale) * Math.cos(this.immaginaria),
    		Math.cosh(this.reale) * Math.sin(this.immaginaria)
		);
    }
    
    public Complex cosh() {
        return new Complex(
    		Math.cosh(this.reale) * Math.cos(this.immaginaria),
    		Math.sinh(this.reale)*Math.sin(this.immaginaria)
		);
    }
	
	@Override
	public int hashCode() {
		return (int) (this.reale + this.immaginaria);
	}

	@Override
	public boolean equals(Object o) {
		Complex c = (Complex) o;

		return this.reale == c.getReale() &&
			   this.immaginaria == c.getImmaginaria();
	}
	
	@Override
	public String toString(){
		String complex;

		if(this.immaginaria == 0)
			complex = "" + this.reale;
		else if(this.immaginaria < 0 && this.reale!=0) 
			complex = this.reale +" "+ this.immaginaria+" j";
		else if(this.reale == 0)
			complex = this.immaginaria+" j";
		else
			complex = this.reale+" + "+ this.immaginaria+" j";

		return complex;
	}
}
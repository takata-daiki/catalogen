package org.bitbucket.artbugorski.brainfuj.compiler;

import org.bitbucket.artbugorski.brainfuj.interpreter.Instruction;
import org.bitbucket.artbugorski.brainfuj.interpreter.ProcessState;

import java.io.IOException;

/**
 * This class is used to represent instructions such as + or -, but instead of being fixed to a
 * given increment value (i.e. 1 or -1) it can be scaled to any value. It can thus be used to build
 * a more optimized model of the program.
 */
public class AdjustValue implements Instruction{



    //_ **FIELDS** _//


    private final int relativeValueChange;



    //_ **CONSTRUCTOR** _//


    private AdjustValue( final int aRelativeValueChange ){
        this.relativeValueChange = aRelativeValueChange;
    }



	//_ **FACTORY METHOD** _//


	/**
	 * Using a factory method means that we can one day move towards
	 * becoming an instance controlled class.
	 */
	public static Instruction by( final int aRelativeChange ){
		return new AdjustValue( aRelativeChange );
	}



    //_ **METHODS** _//


    public int getRelativeValueChange(){
        return relativeValueChange;
    }


	@Override
	public void perform( final ProcessState proc ) throws IOException{
		proc.changeCurrentCellValueBy( relativeValueChange );
		proc.proceedToNextInstruction();
	}


	@Override
	public boolean equals( final Object o ){
		if( this == o ) return true;
		if( o == null || getClass() != o.getClass() ) return false;

		final AdjustValue that = ( AdjustValue ) o;

		if( relativeValueChange != that.relativeValueChange ) return false;

		return true;
	}


	@Override
	public int hashCode(){
		return relativeValueChange;
	}


	@Override
	public String toString() {
		return ( relativeValueChange > 0 ?  "+"  :  "" ) + relativeValueChange;
	}

}

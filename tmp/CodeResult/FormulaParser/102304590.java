/*	Copyright 2012 Luke Misenheimer

    This file is part of Grenade, a program to help teach strategy
    for natural deduction proofs.

    Grenade is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of
    the License, or (at your option) any later version.

    Grenade is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public
    License along with Grenade.  If not, see
    http://www.gnu.org/licenses/. */

package com.lukemisenheimer.grenade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

import com.lukemisenheimer.grenade.errors.FormulaParsingException;

public class FormulaParser implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1506095142197408719L;
	private static String onePlaceConnectives = "~";
    private static String twoPlaceConnectives = "→↔&∨";
    private static String quantifiers = "∀∃";
    private static String propsAndPreds = "ABCDEFGHIJKLMNOPQRSTUVWXYZ⊥";
    private static String infixPreds = "=";
    private static String constants = "abcdefghijklmnopqrs";
    private static String variables = "tuvwxyz";
    private static String schematicWFFs = "ϕψω";
    private static Set<Character> restrictors;
    // eventually change to use some kind of data structure to store (& eventually read) info about symbols
    // e.g. type (connective, quantifier, predicate/proposition, ...),
    // number of arguments, which characters go with which
    
    public FormulaParser() {
    	restrictors = new HashSet<Character>();
    	restrictors.add(SchematicFormula.NOT_IN_DEPENDENCIES);
    	restrictors.add(SchematicFormula.NOT_IN_OTHER_LINES);
    	restrictors.add(SchematicFormula.REPLACE_EVERY);
    }
    
    public Formula formulaFromString(String stringFormula) throws RecognitionException, FormulaParsingException {
    	String formulaForParsing = withOptionalOuterParens(stringFormula);
    	CharStream cs = new ANTLRStringStream(formulaForParsing);
    	LFOL_ForbesLexer lexer = new LFOL_ForbesLexer(cs);
    	CommonTokenStream tokens = new CommonTokenStream();
    	tokens.setTokenSource(lexer);
    	LFOL_ForbesParser parser = new LFOL_ForbesParser(tokens);
    	RuleReturnScope result = parser.input_string();
    	CommonTree tree = (CommonTree)result.getTree();
    	Formula form = formulaFromTree(tree);
    	if (wellFormed(form))
    		return form;
		throw new FormulaParsingException("Couldn't parse purported formula: " + stringFormula);
    }
	
    private Formula formulaFromTree(CommonTree tree) throws FormulaParsingException {
	    List<Formula> subformulae = new ArrayList<Formula>();
	    if (tree.getChildren() != null)
		    for(Object child : tree.getChildren())
		    	subformulae.add(formulaFromTree((CommonTree)child));
	    if (tree.getText().length() != 1)
	    	throw new FormulaParsingException("Tried to parse " + tree.getText() + " as a single symbol");
	    Formula form = new Formula(subformulae, null, tree.getText().charAt(0));
	    return new Formula(subformulae, updatedString(form), tree.getText().charAt(0));
	}

	private String withOptionalOuterParens(String stringFormula) {
		int parenDepth = 0;
		for(int i=0; i<stringFormula.length()-1; i++) {
			if (stringFormula.charAt(i) == ')')
				parenDepth--;
			else if (stringFormula.charAt(i) == '(')
					parenDepth++;
			if (parenDepth == 0 && twoPlaceConnective(stringFormula.charAt(i))) {
				return ("(" + stringFormula + ")");
			}
		}
		return stringFormula;
	}

	public boolean connectiveOrQuantifier(char symbol) {
		return (twoPlaceConnectives.indexOf(symbol) > -1 || onePlaceConnectives.indexOf(symbol) > -1 || quantifiers.indexOf(symbol) > -1);
	}

	private boolean canBeSchematized(char c) {
		if (c == '⊥')
			return false;
		return (propOrPred(c) || constant(c) || variable(c) || schematicWFF(c));
	}
	
	boolean schematicWFF(char c) {
		return (schematicWFFs.indexOf(c) > -1);
	}

	public boolean variable(char c) {
		return (variables.indexOf(c) > -1);
	}

	public boolean constant(char c) {
		return (constants.indexOf(c) > -1);
	}

	private boolean propOrPred(char c) {
		return (propsAndPreds.indexOf(c) > -1);
	}
	
	private boolean infixPred(char c) {
		return (infixPreds.indexOf(c) > -1);
	}
	
	private boolean onePlaceConnective(char c) {
		return (onePlaceConnectives.indexOf(c) > -1);
	}
	
	private boolean quantifier(char c) {
		return (quantifiers.indexOf(c) > -1);
	}
	
	private boolean twoPlaceConnective(char c) {
		return (twoPlaceConnectives.indexOf(c) > -1);
	}
	
	private boolean restrictor(char c) {
		return (restrictors.contains((Character)c));
	}
	
	boolean varOrConst(char c) {
		return (variable(c) || constant(c));
	}
	
	private boolean varOrConstOrRest(char c) {
		return (variable(c) || constant(c) || restrictor(c));
	}

	public SchematicFormula schematizeFormula(Formula form) {
		List<SchematicFormula> subSchemata = new ArrayList<SchematicFormula>();
		for(int i=0;i<form.getSubformulae().size();i++) {
			if (varOrConst(form.getSubformulae().get(i).getMainSymbol())
				&& i + 1 < form.getSubformulae().size()
				&& restrictor(form.getSubformulae().get(i + 1).getMainSymbol())) {
					List<SchematicFormula> subSubSchemata = new ArrayList<SchematicFormula>();
				    Formula variableFormula = form.getSubformulae().get(i);
					List<Character> restrictions = new ArrayList<Character>();
					// if there are any restrictions on a variable, add them to the SchematicFormula as restrictions
					while (restrictor(form.getSubformulae().get(i + 1).getMainSymbol())) {
						restrictions.add(form.getSubformulae().get(i + 1).getMainSymbol());
						// if the restriction is "replace every", the symbol that should be replaced will come directly after
						// and should be added as a subformula of the variable
						if (form.getSubformulae().get(i + 1).getMainSymbol() == SchematicFormula.REPLACE_EVERY
							&& i + 2 < form.getSubformulae().size()
							&& varOrConst(form.getSubformulae().get(i + 2).getMainSymbol())) {
							subSubSchemata.add(new SchematicFormula(new ArrayList<SchematicFormula>(),
									                                form.getSubformulae().get(i + 1).toString(),
									                                form.getSubformulae().get(i + 1).getMainSymbol(),
									                                true));
							i++;
						}
						i++;
					}
					subSchemata.add(new SchematicFormula(subSubSchemata,
							                           variableFormula.toString(),
							                           variableFormula.getMainSymbol(),
							                           true,
							                           restrictions));
				}
			else
				subSchemata.add(schematizeFormula(form.getSubformulae().get(i)));
		}
		return new SchematicFormula(subSchemata,
									form.toString(),
									form.getMainSymbol(),
									canBeSchematized(form.getMainSymbol()));
	}
	
	public String updatedString(Formula form) {
		StringBuilder builder = new StringBuilder();
		if (wellFormed(form) || hasSchematicSymbols(form)) {
			if (twoPlaceConnectives.indexOf(form.getMainSymbol()) > -1 || infixPreds.indexOf(form.getMainSymbol()) > -1) {
				if (twoPlaceConnectives.indexOf(form.getSubformulae().get(0).getMainSymbol()) > -1)
					builder.append("(" + updatedString(form.getSubformulae().get(0)) + ")");
				else
					builder.append(updatedString(form.getSubformulae().get(0)));
				builder.append(form.getMainSymbol());
				if (twoPlaceConnectives.indexOf(form.getSubformulae().get(1).getMainSymbol()) > -1)
					builder.append("(" + updatedString(form.getSubformulae().get(1)) + ")");
				else
					builder.append(updatedString(form.getSubformulae().get(1)));
			}
			else {
				builder.append(form.getMainSymbol());
				for(Formula sf : form.getSubformulae()) {
					if (twoPlaceConnectives.indexOf(sf.getMainSymbol()) > -1)
						builder.append("(" + updatedString(sf) + ")");
					else
						builder.append(updatedString(sf));
				}
			}
		}
		return builder.toString();
	}
	
	public String updatedString(SchematicFormula form) {
		StringBuilder builder = new StringBuilder();
		if (wellFormed(form)) {
			if (twoPlaceConnective(form.getMainSymbol()) || infixPred(form.getMainSymbol())) {
				if (twoPlaceConnective(form.getSubformulae().get(0).getMainSymbol()))
					builder.append("(" + updatedString(form.getSubformulae().get(0)) + ")");
				else
					builder.append(updatedString(form.getSubformulae().get(0)));
				builder.append(form.getMainSymbol());
				if (twoPlaceConnective(form.getSubformulae().get(1).getMainSymbol()))
					builder.append("(" + updatedString(form.getSubformulae().get(1)) + ")");
				else
					builder.append(updatedString(form.getSubformulae().get(1)));
			}
			else if (quantifier(form.getMainSymbol()) && form.getSubformulae().size() == 2)
				builder.append("(" + form.getMainSymbol() + updatedString(form.getSubformulae().get(0)) + ")"
						+ updatedString(form.getSubformulae().get(1)));
			else {
				builder.append(form.getMainSymbol());
				for(SchematicFormula ssf : form.getSubformulae()) {
					if (twoPlaceConnective(ssf.getMainSymbol()))
						builder.append("(" + updatedString(ssf) + ")");
					else
						builder.append(updatedString(ssf));
				}
			}
		}
		return builder.toString();
	}

	private boolean wellFormed(SchematicFormula form) {
		if (form == null || form.getSubformulae() == null)
			return false;
		if (onePlaceConnective(form.getMainSymbol()))
			return (form.getSubformulae().size() == 1
					&& wellFormed(form.getSubformulae().get(0)));
		else if (twoPlaceConnective(form.getMainSymbol()))
			return (form.getSubformulae().size() == 2
			        && (wellFormed(form.getSubformulae().get(0))
			        	&& wellFormed(form.getSubformulae().get(1))));
		else if (quantifier(form.getMainSymbol()))
			return (form.getSubformulae().size() == 2
	        		&& (variable(form.getSubformulae().get(0).getMainSymbol())
        				&& wellFormed(form.getSubformulae().get(1))));
		else if (propOrPred(form.getMainSymbol())) {
			for(SchematicFormula subform : form.getSubformulae())
				if (!variable(subform.getMainSymbol())
					&& !constant(subform.getMainSymbol()))
					return false;
			return true;
		}
		else if (infixPred(form.getMainSymbol()))
			return (form.getSubformulae().size() == 2
    				&& ((variable(form.getSubformulae().get(0).getMainSymbol())
    					 || constant(form.getSubformulae().get(0).getMainSymbol()))
						&& (variable(form.getSubformulae().get(1).getMainSymbol())
	    					|| constant(form.getSubformulae().get(1).getMainSymbol()))));
		else if (constant(form.getMainSymbol())
				 || variable(form.getMainSymbol()))
			return (form.getSubformulae().size() == 0);
		else if (schematicWFF(form.getMainSymbol()))
			for(SchematicFormula subform : form.getSubformulae())
				if (!variable(subform.getMainSymbol())
					&& !constant(subform.getMainSymbol()))
					return false;
		return false;
	}

	private boolean wellFormed(Formula form) {
		if (form == null || form.getSubformulae() == null)
			return false;
		if (onePlaceConnective(form.getMainSymbol()))
			return (form.getSubformulae().size() == 1
					&& wellFormed(form.getSubformulae().get(0)));
		else if (twoPlaceConnective(form.getMainSymbol()))
			return (form.getSubformulae().size() == 2
			        && (wellFormed(form.getSubformulae().get(0))
			        	&& wellFormed(form.getSubformulae().get(1))));
		else if (quantifier(form.getMainSymbol()))
			return (form.getSubformulae().size() == 2
	        		&& (variable(form.getSubformulae().get(0).getMainSymbol())
        				&& wellFormed(form.getSubformulae().get(1))));
		else if (propOrPred(form.getMainSymbol())) {
			for(Formula subform : form.getSubformulae())
				if (!variable(subform.getMainSymbol())
					&& !constant(subform.getMainSymbol()))
					return false;
			return true;
		}
		else if (infixPred(form.getMainSymbol()))
			return (form.getSubformulae().size() == 2
    				&& ((variable(form.getSubformulae().get(0).getMainSymbol())
    					 || constant(form.getSubformulae().get(0).getMainSymbol()))
						&& (variable(form.getSubformulae().get(1).getMainSymbol())
	    					|| constant(form.getSubformulae().get(1).getMainSymbol()))));
		else if (constant(form.getMainSymbol())
				 || variable(form.getMainSymbol()))
			return (form.getSubformulae().size() == 0);
		else return false;
	}
	
	private boolean hasSchematicSymbols(Formula form) {
		if (form == null)
			return false;
		if (restrictor(form.getMainSymbol()) || schematicWFF(form.getMainSymbol()))
			return true;
		for(Formula sf : form.getSubformulae())
			if (hasSchematicSymbols(sf))
				return true;
		return false;
	}

	public List<String> formattedForParsing(List<String> strings) {
		List<String> formattedStrings = new ArrayList<String>();
		for(String sentence : strings)
			formattedStrings.add(sentence.replaceAll("[¬-]", "~")
					                 .replaceAll("<>", "↔")
					                 .replaceAll(">", "→")
					                 .replaceAll("v", "∨")
//					                 .replaceAll("V", "∀")
//					                 .replaceAll("E", "∃")
					                 .replaceAll("!", "⊥"));
		return formattedStrings;
	}

}

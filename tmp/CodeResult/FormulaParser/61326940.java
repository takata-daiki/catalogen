// $ANTLR 2.7.5 (20050128): "../src/csheets/core/formula/compiler/FormulaCompilerPT.g" -> "FormulaParser.java"$
package csheets.core.formula.compiler;
import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;
/**
 * A parser that generates expressions from lists of lexical tokens.
 * @author Einar Pehrson
 */
public class FormulaParser extends antlr.LLkParser       implements FormulaParserTokenTypes
 {

protected FormulaParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public FormulaParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected FormulaParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public FormulaParser(TokenStream lexer) {
  this(lexer,1);
}

public FormulaParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

/**
 * The start rule for formula expressions.
 */
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		match(EQ);
		{
		if ((LA(1)==CELL_REF||LA(1)==VARIAVEL)) {
			anothercell();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else if ((LA(1)==LPARRE)) {
			match(LPARRE);
			list();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPARRE);
		}
		else if ((_tokenSet_0.member(LA(1)))) {
			comparison();
			astFactory.addASTChild(currentAST, returnAST);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		match(Token.EOF_TYPE);
		expression_AST = (AST)currentAST.root;
		returnAST = expression_AST;
	}
	
	public final void anothercell() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST anothercell_AST = null;
		
		{
		switch ( LA(1)) {
		case CELL_REF:
		{
			AST tmp5_AST = null;
			tmp5_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp5_AST);
			match(CELL_REF);
			break;
		}
		case VARIAVEL:
		{
			AST tmp6_AST = null;
			tmp6_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp6_AST);
			match(VARIAVEL);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case EQEQ:
		{
			AST tmp7_AST = null;
			tmp7_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp7_AST);
			match(EQEQ);
			comparison();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case RPARRE:
		case SEMI:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		anothercell_AST = (AST)currentAST.root;
		returnAST = anothercell_AST;
	}
	
	public final void list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST list_AST = null;
		
		anothercell();
		astFactory.addASTChild(currentAST, returnAST);
		other();
		astFactory.addASTChild(currentAST, returnAST);
		list_AST = (AST)currentAST.root;
		returnAST = list_AST;
	}
	
	public final void comparison() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST comparison_AST = null;
		
		concatenation();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case EQ:
		case NEQ:
		case GT:
		case LT:
		case LTEQ:
		case GTEQ:
		{
			{
			switch ( LA(1)) {
			case EQ:
			{
				AST tmp8_AST = null;
				tmp8_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp8_AST);
				match(EQ);
				break;
			}
			case NEQ:
			{
				AST tmp9_AST = null;
				tmp9_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp9_AST);
				match(NEQ);
				break;
			}
			case GT:
			{
				AST tmp10_AST = null;
				tmp10_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp10_AST);
				match(GT);
				break;
			}
			case LT:
			{
				AST tmp11_AST = null;
				tmp11_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp11_AST);
				match(LT);
				break;
			}
			case LTEQ:
			{
				AST tmp12_AST = null;
				tmp12_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp12_AST);
				match(LTEQ);
				break;
			}
			case GTEQ:
			{
				AST tmp13_AST = null;
				tmp13_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp13_AST);
				match(GTEQ);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			concatenation();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case RPARRE:
		case SEMI:
		case RPAR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		comparison_AST = (AST)currentAST.root;
		returnAST = comparison_AST;
	}
	
	public final void other() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST other_AST = null;
		
		switch ( LA(1)) {
		case SEMI:
		{
			match(SEMI);
			list();
			astFactory.addASTChild(currentAST, returnAST);
			other_AST = (AST)currentAST.root;
			break;
		}
		case RPARRE:
		{
			other_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = other_AST;
	}
	
	public final void concatenation() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concatenation_AST = null;
		
		arithmetic_lowest();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop13:
		do {
			if ((LA(1)==AMP)) {
				AST tmp15_AST = null;
				tmp15_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp15_AST);
				match(AMP);
				arithmetic_lowest();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop13;
			}
			
		} while (true);
		}
		concatenation_AST = (AST)currentAST.root;
		returnAST = concatenation_AST;
	}
	
	public final void arithmetic_lowest() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmetic_lowest_AST = null;
		
		arithmetic_low();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop17:
		do {
			if ((LA(1)==PLUS||LA(1)==MINUS)) {
				{
				switch ( LA(1)) {
				case PLUS:
				{
					AST tmp16_AST = null;
					tmp16_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp16_AST);
					match(PLUS);
					break;
				}
				case MINUS:
				{
					AST tmp17_AST = null;
					tmp17_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp17_AST);
					match(MINUS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				arithmetic_low();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop17;
			}
			
		} while (true);
		}
		arithmetic_lowest_AST = (AST)currentAST.root;
		returnAST = arithmetic_lowest_AST;
	}
	
	public final void arithmetic_low() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmetic_low_AST = null;
		
		arithmetic_medium();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop21:
		do {
			if ((LA(1)==MULTI||LA(1)==DIV)) {
				{
				switch ( LA(1)) {
				case MULTI:
				{
					AST tmp18_AST = null;
					tmp18_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp18_AST);
					match(MULTI);
					break;
				}
				case DIV:
				{
					AST tmp19_AST = null;
					tmp19_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp19_AST);
					match(DIV);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				arithmetic_medium();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop21;
			}
			
		} while (true);
		}
		arithmetic_low_AST = (AST)currentAST.root;
		returnAST = arithmetic_low_AST;
	}
	
	public final void arithmetic_medium() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmetic_medium_AST = null;
		
		arithmetic_high();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case POWER:
		{
			AST tmp20_AST = null;
			tmp20_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp20_AST);
			match(POWER);
			arithmetic_high();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case EQ:
		case RPARRE:
		case SEMI:
		case NEQ:
		case GT:
		case LT:
		case LTEQ:
		case GTEQ:
		case AMP:
		case PLUS:
		case MINUS:
		case MULTI:
		case DIV:
		case RPAR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		arithmetic_medium_AST = (AST)currentAST.root;
		returnAST = arithmetic_medium_AST;
	}
	
	public final void arithmetic_high() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmetic_high_AST = null;
		
		arithmetic_highest();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case PERCENT:
		{
			AST tmp21_AST = null;
			tmp21_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp21_AST);
			match(PERCENT);
			break;
		}
		case EOF:
		case EQ:
		case RPARRE:
		case SEMI:
		case NEQ:
		case GT:
		case LT:
		case LTEQ:
		case GTEQ:
		case AMP:
		case PLUS:
		case MINUS:
		case MULTI:
		case DIV:
		case POWER:
		case RPAR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		arithmetic_high_AST = (AST)currentAST.root;
		returnAST = arithmetic_high_AST;
	}
	
	public final void arithmetic_highest() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmetic_highest_AST = null;
		
		{
		switch ( LA(1)) {
		case MINUS:
		{
			AST tmp22_AST = null;
			tmp22_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp22_AST);
			match(MINUS);
			break;
		}
		case CELL_REF:
		case VARIAVEL:
		case LPAR:
		case FUNCTION:
		case NAME:
		case NUMBER:
		case STRING:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		atom();
		astFactory.addASTChild(currentAST, returnAST);
		arithmetic_highest_AST = (AST)currentAST.root;
		returnAST = arithmetic_highest_AST;
	}
	
	public final void atom() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST atom_AST = null;
		
		switch ( LA(1)) {
		case FUNCTION:
		{
			function_call();
			astFactory.addASTChild(currentAST, returnAST);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case CELL_REF:
		case NAME:
		{
			reference();
			astFactory.addASTChild(currentAST, returnAST);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case NUMBER:
		case STRING:
		{
			literal();
			astFactory.addASTChild(currentAST, returnAST);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case VARIAVEL:
		{
			AST tmp23_AST = null;
			tmp23_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp23_AST);
			match(VARIAVEL);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case LPAR:
		{
			match(LPAR);
			comparison();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAR);
			atom_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = atom_AST;
	}
	
	public final void function_call() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_call_AST = null;
		
		AST tmp26_AST = null;
		tmp26_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp26_AST);
		match(FUNCTION);
		{
		switch ( LA(1)) {
		case CELL_REF:
		case VARIAVEL:
		case MINUS:
		case LPAR:
		case FUNCTION:
		case NAME:
		case NUMBER:
		case STRING:
		{
			comparison();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop32:
			do {
				if ((LA(1)==SEMI)) {
					match(SEMI);
					comparison();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop32;
				}
				
			} while (true);
			}
			break;
		}
		case RPAR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RPAR);
		function_call_AST = (AST)currentAST.root;
		returnAST = function_call_AST;
	}
	
	public final void reference() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST reference_AST = null;
		
		switch ( LA(1)) {
		case CELL_REF:
		{
			AST tmp29_AST = null;
			tmp29_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp29_AST);
			match(CELL_REF);
			{
			switch ( LA(1)) {
			case COLON:
			{
				{
				AST tmp30_AST = null;
				tmp30_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp30_AST);
				match(COLON);
				}
				AST tmp31_AST = null;
				tmp31_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp31_AST);
				match(CELL_REF);
				break;
			}
			case EOF:
			case EQ:
			case RPARRE:
			case SEMI:
			case NEQ:
			case GT:
			case LT:
			case LTEQ:
			case GTEQ:
			case AMP:
			case PLUS:
			case MINUS:
			case MULTI:
			case DIV:
			case POWER:
			case PERCENT:
			case RPAR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			reference_AST = (AST)currentAST.root;
			break;
		}
		case NAME:
		{
			AST tmp32_AST = null;
			tmp32_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp32_AST);
			match(NAME);
			reference_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = reference_AST;
	}
	
	public final void literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST literal_AST = null;
		
		switch ( LA(1)) {
		case NUMBER:
		{
			AST tmp33_AST = null;
			tmp33_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp33_AST);
			match(NUMBER);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case STRING:
		{
			AST tmp34_AST = null;
			tmp34_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp34_AST);
			match(STRING);
			literal_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = literal_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"EQ",
		"LPARRE",
		"RPARRE",
		"SEMI",
		"CELL_REF",
		"VARIAVEL",
		"EQEQ",
		"NEQ",
		"GT",
		"LT",
		"LTEQ",
		"GTEQ",
		"AMP",
		"PLUS",
		"MINUS",
		"MULTI",
		"DIV",
		"POWER",
		"PERCENT",
		"LPAR",
		"RPAR",
		"FUNCTION",
		"COLON",
		"NAME",
		"NUMBER",
		"STRING",
		"LETTER",
		"ALPHABETICAL",
		"QUOT",
		"DIGIT",
		"ABS",
		"EXCL",
		"COMMA",
		"WS"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 981730048L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	
	}

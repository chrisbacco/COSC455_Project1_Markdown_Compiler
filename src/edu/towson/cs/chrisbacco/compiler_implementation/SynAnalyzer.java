package edu.towson.cs.chrisbacco.compiler_implementation;

/*
 * @chris bacco
 * @version 1.1
 * @11/11/15
 * 
 */



import java.awt.color.CMMException;

import edu.towson.cs.chrisbacco.compiler_implementation.Compiler;
import edu.towson.cs.chrisbacco.tokens.*;
import edu.towson.cosc.cosc455.interfaces.*;

public class SynAnalyzer implements SyntaxAnalyzer {
	
	/*where the magic happens */
	/* follow the methods in the interface */
	
	@Override
	public void markdown() throws CMMException {
		try{
			if (!(new Begin()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : Expected Document BEGIN tag when'" + Compiler.currentToken + "' is what was given.");
			}
			
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			
			if((new VariableDefine()).testLegal(Compiler.currentToken)){
				variableDefine();
			}
			
			if((new Carrot()).testLegal(Compiler.currentToken)){
				head();
			}
			body();
			
			if (!(new End()).testLegal(Compiler.currentToken)){
				throw new CompilerException("SYNTAX ERROR : Expected document END tag when '" + Compiler.currentToken + "' is found.");
			}
			
			/* add to parsetree */
			
			else{
				addParseTree();
				Compiler.SemanticAnalyzer.createMarkdownLang();
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	
	/* check for the correct MD tags, if not present throw syntax error and list the given token */
	
	@Override
	public void head() throws CompilerException {		
		try{
			if (!(new Carrot()).testLegal(Compiler.currentToken)){
				throw new CompilerException("SYNTAX ERROR : A head begin tag was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			title();
			if (!(new Carrot()).testLegal(Compiler.currentToken)){
				throw new CompilerException("SYNTAX ERROR : A head end tag was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	
	/* check for the correct MD tags, if not present throw syntax error and list the given token */
	
	@Override
	public void title() throws CompilerException {
		try{
			if (!(new AngleOpen()).testLegal(Compiler.currentToken)){
				throw new CompilerException("SYNTAX ERROR : Expected title open tag when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new TextGeneric()).testLegal(Compiler.currentToken)){
				throw new CompilerException("SYNTAX ERROR : Expected valid text when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new AngleClose()).testLegal(Compiler.currentToken)){
				throw new CompilerException("SYNTAX ERROR : expected a title close tag when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	
	
	/* check for the correct MD tags, if not present throw syntax error and list the given token */
	@Override
	public void body() throws CompilerException {
		try{
			
			if((new CurlyBraceOpen()).testLegal(Compiler.currentToken)){
				paragraph();
				body();
			}
			
			else if((new VariableUse()).testLegal(Compiler.currentToken) ||
					(new Bold()).testLegal(Compiler.currentToken) || 
					(new Italics()).testLegal(Compiler.currentToken) || 
					(new ListOpen()).testLegal(Compiler.currentToken) || 
					(new Audio()).testLegal(Compiler.currentToken) || 
					(new Video()).testLegal(Compiler.currentToken) || 
					(new BracketOpen()).testLegal(Compiler.currentToken) || 
					(new Break()).testLegal(Compiler.currentToken) ||
					(new TextGeneric()).testLegal(Compiler.currentToken))
			{
				innerText();
				body();
			}
			else if((new Break()).testLegal(Compiler.currentToken)){
				newline();
				body();
			}
			else if((new End()).testLegal(Compiler.currentToken));
			else{
				throw new CompilerException("syntax error : illegal syntax found '" + Compiler.currentToken + "' is what was given.");
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	
	
	/* check for the correct MD tags, if not present throw syntax error and list the given token */
	
	@Override
	public void paragraph() throws CompilerException {
		try{
			if (!(new CurlyBraceOpen()).testLegal(Compiler.currentToken)){
				throw new CompilerException("SYNTAX ERROR : expected a paragraph begin '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			
			if((new VariableDefine()).testLegal(Compiler.currentToken)){
				variableDefine();
			}
			innerText();
			
			if (!(new CurlyBraceClose()).testLegal(Compiler.currentToken)){
				throw new CompilerException("SYNTAX ERROR :expected a paragraph end '" + Compiler.currentToken + "' is what was given.");
			}
			
			else{
			
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
		} 
		
		catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	/* inner text method  throws CompilerException */
	
	
	@Override
	public void innerText() throws CompilerException {
		try{
			if((new VariableUse()).testLegal(Compiler.currentToken)){
				variableUse();
				innerText();
			}
			
			else if((new Bold()).testLegal(Compiler.currentToken)){
				bold();
				innerText();
			}
			
			else if((new Italics()).testLegal(Compiler.currentToken)){
				italics();
				innerText();
			}
			
			else if((new ListOpen()).testLegal(Compiler.currentToken)){
				listitem();
				innerText();
			}
			
			else if((new Audio()).testLegal(Compiler.currentToken)){
				audio();
				innerText();
			}
			
			else if((new Video()).testLegal(Compiler.currentToken)){
				video();
				innerText();
			}
			
			else if((new BracketOpen()).testLegal(Compiler.currentToken)){
				link();
				innerText();
			}
			
			else if((new Break()).testLegal(Compiler.currentToken)){
				newline();
				innerText();
			}
			
			else if((new TextGeneric()).testLegal(Compiler.currentToken)){
				addParseTree();
				Compiler.Lexeme.getNextToken();
				innerText();
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	
	/* Variable testing */ 
	
	@Override
	public void variableDefine() throws CompilerException {		
		
		try{		
			if ((new VariableDefine()).testLegal(Compiler.currentToken)){
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			else{
				throw new CompilerException("SYNTAX ERROR : A variable definition tag was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			if ((new TextGeneric()).testLegal(Compiler.currentToken)){
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			else{
				throw new CompilerException("syntax error : Valid text was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			if ((new EqualsSign()).testLegal(Compiler.currentToken)){
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			else{
				throw new CompilerException("syntax error : An equals sign was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			if ((new TextGeneric()).testLegal(Compiler.currentToken)){
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			else{
				throw new CompilerException("syntax error : Valid text was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			if ((new VariableEnd()).testLegal(Compiler.currentToken)){
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			else{
				throw new CompilerException("syntax error : A variable end tag was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			if ((new VariableDefine()).testLegal(Compiler.currentToken)){
				variableDefine();
			}
		}catch(CompilerException e){
			e.printStackTrace();
		}
	}

	
	/* variable use testing */ 
	
	@Override
	public void variableUse() throws CompilerException {
		try{
			if (!(new VariableUse()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : A variable use tag was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new TextGeneric()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : Valid Text was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new VariableEnd()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : A variable end tag was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	
	
	@Override
	public void bold() throws CompilerException {
		try{
			if (!(new Bold()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : A bold tag was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new TextGeneric()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : Valid Text was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new Bold()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : A bold tag was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	
	
	@Override
	public void italics() throws CompilerException {
		try{
			if (!(new Italics()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : An italic tag was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new TextGeneric()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : Valid Text was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new Italics()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : An italic tag was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void listitem() throws CompilerException {
		try{
			if (!(new ListOpen()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : A list begin token was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			innerItem();
			if (!(new ListClose()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : An list end token was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if((new ListOpen()).testLegal(Compiler.currentToken)){
				listitem();
			}
		}catch(CompilerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void innerItem() throws CMMException {
		try{
			if((new BracketOpen()).testLegal(Compiler.currentToken)){
				link();
				innerItem();
			}
			else if((new Bold()).testLegal(Compiler.currentToken)){
				bold();
				innerItem();
			}
			else if((new Italics()).testLegal(Compiler.currentToken)){
				italics();
				innerItem();
			}
			else if((new TextGeneric()).testLegal(Compiler.currentToken)){
				addParseTree();
				Compiler.Lexeme.getNextToken();
				innerItem();
			}
			else if((new VariableUse()).testLegal(Compiler.currentToken)){
				variableUse();
				innerItem();
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void link() throws CompilerException {
		try{
			if (!(new BracketOpen()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : A link begin token was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new TextGeneric()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : Valid Text was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new BracketClose()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : A link end token was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new AddressOpen()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : An address begin token was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new TextGeneric()).testLegal(Compiler.currentToken)){
				throw new CompilerException("Syntax error : Expected valid text. '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			if (!(new AddressClose()).testLegal(Compiler.currentToken)){
				throw new CompilerException("Syntax error : expected address end token. '" + Compiler.currentToken + "' is what was given.");
			}
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void audio() throws CompilerException {
		try{
			if (!(new Audio()).testLegal(Compiler.currentToken)){
				throw new CompilerException("Syntax error : expected audio token. '" + Compiler.currentToken + "' is what was given.");
			}
			
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			
			if (!(new AddressOpen()).testLegal(Compiler.currentToken)){
				throw new CompilerException("Syntax error : Expected address begin token  '" + Compiler.currentToken + "' is what was given.");
			}
			
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			
			if (!(new TextGeneric()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : Expected valid text was expected when '" + Compiler.currentToken + "' is what was given.");
			}
			
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			
			if (!(new AddressClose()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error: Expected address end token '" + Compiler.currentToken + "' is what was given.");
			}
			
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
		} 
		catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void video() throws CompilerException {
		try{
			if (!(new Video()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : expected a video begins tag '" + Compiler.currentToken + "' is what was given.");
			}
			
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			
			if (!(new AddressOpen()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : expected an address begins token '" + Compiler.currentToken + "' is what was given.");
			}
			
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			
			if (!(new TextGeneric()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : expected valid text '" + Compiler.currentToken + "' is what was given.");
			}
			
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
			
			if (!(new AddressClose()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : expected the address begin tag '" + Compiler.currentToken + "' is what was given.");
			}
			
			else{
				addParseTree();
				Compiler.Lexeme.getNextToken();
			}
		} catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void newline() throws CMMException {
		try{
			if (!(new Break()).testLegal(Compiler.currentToken)){
				throw new CompilerException("syntax error : expected a  newline token '" + Compiler.currentToken + "' is what was given.");
			}
			
			else
				addParseTree();
				Compiler.Lexeme.getNextToken();
		} 
		
		catch(CompilerException e) {
			e.printStackTrace();
		}
	}

	//adds legal tokens to the parse tree in the semantic analyzer
	public void addParseTree(){
		Compiler.SemanticAnalyzer.addToStack(Compiler.currentToken);
	}
}

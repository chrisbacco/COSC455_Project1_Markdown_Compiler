package edu.towson.cs.chrisbacco.compiler_implementation;

/*
 * @chris bacco
 * @version 1.1
 * @11/11/15
 * 
 */



import edu.towson.cosc.cosc455.interfaces.LexicalAnalyzer;
import edu.towson.cs.chrisbacco.tokens.TextGeneric;

public class LexAnalyzer implements LexicalAnalyzer {
	
	/* position tracking */
	private static int currentPosition = 0;
	private int lexLength = 0;
	/*keep private */
	private String stuff;
	private char[] lexemes = new char [100];
	private char getChar;
	
	
	/*getCharacter and getNextToken supplied by interface */
	
	public void initialize(String strMD){
		stuff = strMD;
		getCharacter();
		getNextToken();
	}
	
	
	
	/* compiler calls the syntax analyzer for the next legal token */
	/* 
	/* implement interface methods with @override */ 
	
	@Override
	public void getNextToken() {
		
		if(stuff.length() < currentPosition){
			Compiler.currentToken = "";
		}
		
		/* testing the chars */
		/* add character
		 * get character
		 * all from interface
		 */
		else{
			try{
				if(getChar == '$')
				{
					addCharacter();
					getCharacter();
					getVariable();
				}
				
				else if(getChar == '#')
				{
					addCharacter();
					getCharacter();
					getBegin();
				}
				
				else if(getChar == '*')
				{
					addCharacter();
					char temp = stuff.charAt(currentPosition + 1);
					if(temp == '*'){
						getCharacter();
						addCharacter();
					}
				}
				
				
				else if(lookupToken()){
					addCharacter();
				}
				else
				{
					if((new TextGeneric()).testLegal(String.valueOf(getChar))){
						addCharacter();
						findLegalText();
					}
					
					else
					{
						throw new CompilerException("*** ERROR: No legal lexemes found ***");
					}
				}
			} catch(CompilerException e){
				e.printStackTrace();
				System.exit(0);
			}
			String currentT = "";
			for(int i = 0; i < lexLength; i++){
				currentT = currentT + lexemes[i];
			}
			Compiler.currentToken = currentT;
			for(int i = 0; i < 100; i++){
				lexemes[i] = ' ';
			}
			lexLength = 0;
			getCharacter();
		}
	}

	
	
	
	
	@Override
	public void getCharacter() {
		
		if(stuff.length() > currentPosition){
			
			getChar = stuff.charAt(currentPosition);
			
				if(isSpace(String.valueOf(getChar))){
					currentPosition++;
					getCharacter();
			}
		}
	}

	
	/*add the found legal character to the array for lexemes */
	
	@Override
	public void addCharacter() {
		lexemes[lexLength] = getChar;
		lexLength++;
		currentPosition++;
	}

	
	@Override
	public boolean isSpace(String c) {
		if(c.equals(" ")){
			return true;
		}
		return false;
	}

	/* need to check for the following: */
	/* {}[] () <> @ ^ = ; + * ~ % */ 
	
	
	@Override
	public boolean lookupToken() {
		String currentLexeme = "" + getChar;
		
		      if(currentLexeme.equalsIgnoreCase("{")|| 
				currentLexeme.equalsIgnoreCase("}") || 
				currentLexeme.equalsIgnoreCase("[") ||
				currentLexeme.equalsIgnoreCase("]") || 
				currentLexeme.equalsIgnoreCase("(") || 
				currentLexeme.equalsIgnoreCase(")") ||
				currentLexeme.equalsIgnoreCase("<") ||
				currentLexeme.equalsIgnoreCase(">") || 
				currentLexeme.equalsIgnoreCase("@") ||
				currentLexeme.equalsIgnoreCase("^") ||
				currentLexeme.equalsIgnoreCase("=") || 
				currentLexeme.equalsIgnoreCase(";") ||
				currentLexeme.equalsIgnoreCase("+") ||
				currentLexeme.equalsIgnoreCase("*") || 
				currentLexeme.equalsIgnoreCase("~") ||
				currentLexeme.equalsIgnoreCase("%"))
		      {
			
		    	  
		   return true;
		}
		
	return false;
	}
	

	
	/* Below is used to find the legal text */
	
	public void findLegalText(){
		
		char text = stuff.charAt(currentPosition);
		
		while((new TextGeneric()).testLegal(String.valueOf(text))){
			
			getChar = text;
			addCharacter();
			text = stuff.charAt(currentPosition);
			
			while(text == ' '){
				getChar = text;
				addCharacter();
				text = stuff.charAt(currentPosition);
			}
		}
	}
	
	/* $DEF USE END */
	/* compare, add, get compare */
	
	public void getVariable(){
		
		/* Error checking */ 
		
		try{
			if(getChar == 'D' || getChar == 'd'){
				addCharacter();
				getCharacter();
				
					if(getChar == 'E' || getChar == 'e'){
					addCharacter();
					getCharacter();
					
						if(getChar == 'F' || getChar == 'f'){
						addCharacter();
					}
				}
			}
			
			else if(getChar == 'U' || getChar == 'u'){
				addCharacter();
				getCharacter();
				
					if(getChar == 'S' || getChar == 's'){
					addCharacter();
					getCharacter();
					
						if(getChar == 'E' || getChar == 'e'){
						addCharacter();
					}
				}
			}
			
			else if(getChar == 'E' || getChar == 'e'){
				addCharacter();
				getCharacter();
				
					if(getChar == 'N' || getChar == 'n'){
					addCharacter();
					getCharacter();
					
							if(getChar == 'D' || getChar == 'd'){
						addCharacter();
					}
				}
			}
			
			
			else{
				throw new CompilerException("** LEXICAL ERROR - nothing legal found ** ");
			}
		} catch(CompilerException e){
			e.printStackTrace();
		}
	}
	

/* vertical rows of text so I can read this easier */	
	
public void getBegin(){
	try{
	if(getChar == 'B' || getChar == 'b'){
				addCharacter();
				getCharacter();
				
	if(getChar == 'E' || getChar == 'e'){
				addCharacter();
				getCharacter();
					
	if(getChar == 'G' || getChar == 'g'){
				addCharacter();
				getCharacter();
						
	if(getChar == 'I' || getChar == 'i'){
				addCharacter();
				getCharacter();
							
	if(getChar == 'N' || getChar == 'n'){
				addCharacter();
						}
					}
				}
			}
		}
			
			
			
			
	else if(getChar == 'E' || getChar == 'e'){
			addCharacter();
			getCharacter();
				
	if(getChar == 'N' || getChar == 'n'){
			addCharacter();
			getCharacter();
					
	if(getChar == 'D' || getChar == 'd'){
			addCharacter();
					}
				}
			}
			
			
			
			else
			{
				throw new CompilerException("LEXICAL ERROR - nothing legal found");
			}
		} 
		
		
		
		catch(CompilerException e){
			e.printStackTrace();
		}
	}
}

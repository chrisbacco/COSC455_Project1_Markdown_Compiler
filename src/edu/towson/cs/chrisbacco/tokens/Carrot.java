package edu.towson.cs.chrisbacco.tokens;
/*
 * @chris bacco
 * @version 1.1
 * @11/11/15
 * 
 */


import edu.towson.cosc.cosc455.interfaces.Tokens;
import edu.towson.cs.chrisbacco.compiler_implementation.Token;

public class Carrot implements Tokens {
	static Token c = new Token("^");

	@Override
	public boolean testLegal(String strMD){
		
		if(strMD.equalsIgnoreCase(c.getToken())){
			return true;
		}
		
		else{
			return false;
		}
	}
	
	@Override
	
	public String getMD(boolean mdTag) {
		
		if(mdTag == true){
			return "<head>";
		}
		
		else{
			return "</head>";
		}
	}
}

package edu.towson.cs.chrisbacco.tokens;

/*
 * @chris bacco
 * @version 1.1
 * @11/11/15
 * 
 */

import edu.towson.cosc.cosc455.interfaces.Tokens;
import edu.towson.cs.chrisbacco.compiler_implementation.Token;

public class ListOpen implements Tokens {
	static Token plus = new Token("+");
	
	@Override
	public boolean testLegal(String strMD){
		
		if(strMD.equalsIgnoreCase(plus.getToken())){
			return true;
		}
		
		
		else{
			return false;
		}
	}

	@Override
	public String getMD(boolean mdTag) {
		return "<li>";
	}
}

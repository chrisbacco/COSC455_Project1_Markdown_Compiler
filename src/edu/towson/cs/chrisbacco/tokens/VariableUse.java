package edu.towson.cs.chrisbacco.tokens;

/*
 * @chris bacco
 * @version 1.1
 * @11/11/15
 * 
 */


import edu.towson.cosc.cosc455.interfaces.Tokens;
import edu.towson.cs.chrisbacco.compiler_implementation.Token;

public class VariableUse implements Tokens {
	static Token var = new Token("$USE");
	
	@Override
	public boolean testLegal(String strMD){
		
		if(strMD.equalsIgnoreCase(var.getToken())){
			return true;
		}
		
		else{
			return false;
		}
	}

	@Override
	public String getMD(boolean mdTag) {
		return "";
	}
}

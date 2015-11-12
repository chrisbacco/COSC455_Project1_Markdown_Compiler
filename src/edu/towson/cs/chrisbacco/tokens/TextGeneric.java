package edu.towson.cs.chrisbacco.tokens;


/*
 * @chris bacco
 * @version 1.1
 * @11/11/15
 * 
 */

import edu.towson.cosc.cosc455.interfaces.Tokens;
import edu.towson.cs.chrisbacco.compiler_implementation.Token;

public class TextGeneric implements Tokens {
	
	
	
	static Token text = new Token("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,. ':?_!/");
	
	@Override
	public boolean testLegal(String strMD){
		
		int k; 
		boolean test = false;
		String temp = text.getToken();
	
		for(k = 0; k < strMD.length(); k++){
			test = false;
			
			
			for(int j = 0; j < temp.length(); j++){
				if((String.valueOf(temp.charAt(j))).equalsIgnoreCase(strMD.substring(k, k + 1)) == true){
					test = true;
				}
			}
			
			
			if(test == false){
				return false;
			}
		}
		return true;
	}

	
	
	
	@Override
	public String getMD(boolean mdTag) {
		return "";
	}
}

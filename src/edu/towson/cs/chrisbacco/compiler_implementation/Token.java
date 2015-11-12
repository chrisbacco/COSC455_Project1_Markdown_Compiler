package edu.towson.cs.chrisbacco.compiler_implementation;

/*
 * @chris bacco
 * @version 1.1
 * @11/11/15
 * 
 */



public class Token {
	public String token;
	
	public Token(String strMD){
		token = strMD;
	}
	
	public String getToken(){
		return token;
	}
}

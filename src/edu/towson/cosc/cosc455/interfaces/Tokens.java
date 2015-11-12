package edu.towson.cosc.cosc455.interfaces;

import edu.towson.cs.chrisbacco.compiler_implementation.Token;

public interface Tokens {
	static Token L = new Token("");
	public boolean testLegal(String strMD);
	public String getMD(boolean openTag);
}

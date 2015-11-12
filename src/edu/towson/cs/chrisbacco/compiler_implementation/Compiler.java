package edu.towson.cs.chrisbacco.compiler_implementation;

import java.io.*;

/*
 * @chris bacco
 * @version 1.1
 * @11/11/15
 * 
 */



public class Compiler {
	/* file path stuff */
	public static String file = "";
	public static String filePath = "";
	
	/* currentToken, Lexeme, Parser and Semantic Analyzer */ 
	public static String currentToken;
	
	/* LexAnalyzer, SynAnalyzer and SemAnalyzer */
	public static LexAnalyzer Lexeme;
	public static SynAnalyzer Parser;
	public static SemAnalyzer SemanticAnalyzer;
	

	
	public static void main(String[] args) throws IOException{
		
		/*Objects for lex, syn and sem */
		
		Lexeme = new LexAnalyzer();
		Parser = new SynAnalyzer();
		SemanticAnalyzer = new SemAnalyzer();
		
		String ReadFile = args[0];
		String ext = "";
		
		int init = ReadFile.lastIndexOf('.');
		
		if (init > 0) 
		{
		    ext = ReadFile.substring(init+1);
		    file = ReadFile.substring(0, init);
		}
		
		/* verifies the correct file ext: mkd is present*/
		/* incorrect file type yields an error */
		/* lexeme retrieves the first token */
		/* the syntax analyzer keep it all moving along */ 
		/*use equalsIgnoreCase*/
		//if(ext.equalsIgnoreCase(".mkd")
		
		if(ext.equalsIgnoreCase("mkd"))
		{
			
			/*FileReader and Buffered Reader Objects */
			
			FileReader fileReader = new FileReader(ReadFile);
			BufferedReader bufferReader = new BufferedReader(fileReader);
			
			File source = new File(ReadFile);
			source.mkdirs();		
			
				/*string*/
				String sourceLine = null;
				String blank = "";
			
			
			
			while((sourceLine = bufferReader.readLine()) != null)
			{
				blank = blank + " " + sourceLine;
			}
			
			Lexeme.initialize(blank);
			Parser.markdown();
			
			fileReader.close();
		}
		
		/* failure to find the correct file ext results in error */
		else
		{
			System.err.print("INVALID FILE TYPE - .mkd expected");
		}
	}
}

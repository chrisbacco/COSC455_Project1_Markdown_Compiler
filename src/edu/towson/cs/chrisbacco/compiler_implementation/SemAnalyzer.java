package edu.towson.cs.chrisbacco.compiler_implementation;

/*
 * @chris bacco
 * @version 1.1
 * @11/11/15
 * 
 */



import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import edu.towson.cs.chrisbacco.tokens.*;

public class SemAnalyzer {
	
	/* create stacks */
	private static Stack<String> variableStack;
	private static Stack<String> mdStack = new Stack<String>();
	public static Stack<String> firstStack = new Stack<String>();
	
	
	public void createMarkdownLang(){
		
		buildMDStack();
		
		String test = "";
		
		/*pop the stack */
		test = mdStack.pop();
		
		while(!mdStack.isEmpty()){
			String strMD = mdStack.pop();
			test = test + " " + strMD;
		}
		
		newFile(test);		
	}
	
	
	/* add to the stack w/ string parameter */
	public void addToStack(String str){
		firstStack.push(str);
	}
	
	/* string testing with a stack for tokens */
	
	private void buildMDStack(){
		
		while(!firstStack.isEmpty()){
			String strTemp= firstStack.pop();
			
		
			if(strTemp.equalsIgnoreCase("<")){
				mdStack.push((new AngleOpen()).getMD(true));
			}
			
			else if(strTemp.equalsIgnoreCase(">")){
				mdStack.push((new AngleClose()).getMD(true));
			}
			
			else if(strTemp.equalsIgnoreCase("^")){
				variableStack = new Stack<String>();
				boolean ifExists = false;
				
				while(!mdStack.isEmpty()){
					String str2 = mdStack.pop();
					variableStack.push(str2);
					if(str2.equalsIgnoreCase((new Carrot()).getMD(false))){
						ifExists = true;
						break;
					}
				}
				while(!variableStack.isEmpty()){
					mdStack.push(variableStack.pop());
				}
				if(ifExists == false){
					mdStack.push((new Carrot()).getMD(false));
				}
				else{
					mdStack.push((new Carrot()).getMD(true));
				}
			}
			
			else if(strTemp.equalsIgnoreCase("{")){
				mdStack.push((new CurlyBraceOpen()).getMD(true));
			}
			
			else if(strTemp.equalsIgnoreCase("}")){
				mdStack.push((new CurlyBraceClose()).getMD(true));
			}
			
			else if(strTemp.equalsIgnoreCase("#begin")){
				mdStack.push((new Begin()).getMD(true));
			}
			
			else if(strTemp.equalsIgnoreCase("#end")){
				mdStack.push((new End()).getMD(true));
			}
			
			else if(strTemp.equalsIgnoreCase("*")){
				variableStack = new Stack<String>();
				boolean here = false;
				while(!mdStack.isEmpty()){
					String element = mdStack.pop();
					variableStack.push(element);
					if(element.equalsIgnoreCase((new Italics()).getMD(false))){
						here = true;
						break;
					}
				}
				while(!variableStack.isEmpty()){
					mdStack.push(variableStack.pop());
				}
				if(here == false){
					mdStack.push((new Italics()).getMD(false));
				}
				else{
					mdStack.push((new Italics()).getMD(true));
				}
			}
			else if(strTemp.equalsIgnoreCase("**")){
				variableStack = new Stack<String>();
				boolean here = false;
				
				
				while(!mdStack.isEmpty()){
					String element = mdStack.pop();
					variableStack.push(element);
					
					if(element.equalsIgnoreCase((new Bold()).getMD(false))){
						here = true;
						break;
					}
				}
				while(!variableStack.isEmpty()){
					mdStack.push(variableStack.pop());
				}
				if(here == false){
					mdStack.push((new Bold()).getMD(false));
				}
				else{
					mdStack.push((new Bold()).getMD(true));
				}
			}
			
			else if(strTemp.equalsIgnoreCase("+")){
				mdStack.push((new ListOpen()).getMD(true));
			}
			
			
			else if(strTemp.equalsIgnoreCase("~")){
				mdStack.push((new Break()).getMD(true));
			}
			
			
			else if(strTemp.equalsIgnoreCase(";")){
				mdStack.push((new ListClose()).getMD(true));
			}
			
			else if((new TextGeneric()).testLegal(strTemp)){
				mdStack.push(strTemp);
			}
			
			else if((new VariableEnd()).testLegal(strTemp)){
				String var_name = removeSpacesString(firstStack.pop());
				replaceVariables(var_name);
			}
			
			else if(strTemp.equalsIgnoreCase(")")){
				
				/* test for links, audio, iframe */
				
				
				
				String address = firstStack.pop();
				String Link = "";
				
				firstStack.pop();
				strTemp = firstStack.pop();
				
				if(strTemp.equalsIgnoreCase("%")){
					Link = "<iframe src=\"" + address + "\"/> "; 
				}
				
				else if(strTemp.equalsIgnoreCase("@")){
					Link = "<audio controls>  <source src=\"" + address + "\">    </audio> "; 
				}
				
				
				
				else{
					firstStack.pop();
					Link = "<a href = \"" + address + "\">" + firstStack.pop() + "</a>"; 
					firstStack.pop();
				}
				mdStack.push(Link);
			}
		}
	}
	
	
	/* let's do something w/ variables */
	
	
	private void replaceVariables(String variableName){
		String defineVar = "";
		
		
		variableStack = new Stack<String>();
		
		while(!firstStack.isEmpty()){
			String stringTest = firstStack.pop();
			
			if(!(new TextGeneric()).testLegal(stringTest)){
				stringTest = removeSpacesString(stringTest);
			}
			
			if(stringTest.equalsIgnoreCase("$end")){
				String varDefineTemp = removeSpacesString(firstStack.pop());
				stringTest = firstStack.pop();
				
				if(stringTest.equalsIgnoreCase("=")){
					String sName = removeSpacesString(firstStack.pop());
					
					if(sName.equalsIgnoreCase(variableName)){
						defineVar = varDefineTemp;
						firstStack.pop();
						break;
					}
					
					else
					{
						variableStack.push("$end");
						variableStack.push(varDefineTemp);
						variableStack.push("=");
						variableStack.push(sName);
						variableStack.push(firstStack.pop());
					}
				}
				
				
				
				else if(stringTest.equalsIgnoreCase("$use")){
					
					if(!varDefineTemp.equalsIgnoreCase(variableName)){
						variableStack.push("$end");
					}
						variableStack.push(varDefineTemp);
						variableStack.push("$use");
				}
				
				else{
						variableStack.push(stringTest);
				}
			}
			
			else if(stringTest.equalsIgnoreCase("$use"));
			
			
			
			else{
				variableStack.push(stringTest);
			}
		}
		/* error checking */
		
		try{
			if(firstStack.isEmpty()){
				throw new CompilerException("STATIC SEMANTIC ERROR: variable not found \"" + variableName + "\" !");
			}
		} 
		
		
		catch(CompilerException e){
			e.printStackTrace();
			System.exit(0);
		}
		
		
		
		while(!variableStack.isEmpty()){
			String temp = variableStack.pop();
			
			
			if(temp.equalsIgnoreCase("$def")){
				firstStack.push(temp);
				firstStack.push(variableStack.pop());
				firstStack.push(variableStack.pop());
				firstStack.push(variableStack.pop());
				firstStack.push(variableStack.pop());
			}
			
			else if(temp.equalsIgnoreCase("$use"))
			{
				String varname1 = variableStack.pop();
				
				if(varname1.equalsIgnoreCase(variableName))
					{
						firstStack.push(defineVar);
					}
				
				else
					{
						firstStack.push("$use");
						firstStack.push(varname1);
						firstStack.push(variableStack.pop());
					}
			}
			
			else
			{
						firstStack.push(temp);
			}
		}
	}
	

	
	public void newFile(String sourceMKD){
		try {
			
			String str1 = Compiler.file + ".html";
			File file = new File(str1);

			if (!file.exists()) 
			{
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter buffer = new BufferedWriter(fileWriter);
			
			
			
			buffer.write(sourceMKD);
			buffer.close();

			openHTMLFileInBrowser(str1);
		} 
		
		
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/*removes spaces in strings */
	private String removeSpacesString(String str1){
		String reduced = "";
		for(int i = 0; i < str1.length(); i++){
			if(!(str1.charAt(i) == ' ')){
				reduced = reduced + str1.charAt(i);
			}
		}
		return reduced;
	}
	
	
	
	/* displays the html file made in the google chrome browser */
	
	
		public void openHTMLFileInBrowser(String htmlFileStr){
			File file= new File(htmlFileStr.trim());
			if(!file.exists()){
				System.err.println("File "+ htmlFileStr +" does not exist.");
				return;
			}
			try{
				Desktop.getDesktop().browse(file.toURI());
			} catch(IOException ioe){
				System.err.println("Failed to open file");
				ioe.printStackTrace();
			}
			return ;
	}
}

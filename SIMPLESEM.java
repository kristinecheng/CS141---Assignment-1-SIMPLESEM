// SIMPLESEM.java

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;

import javax.lang.model.util.ElementScanner6;

public class SIMPLESEM {
	
	public static class tokenType {
		static String SET = "SET";
		static String JUMP = "JUMP";
		static String JUMPT = "JUMPT";
		static String HALT = "HALT";
	}

	static String studentName = "YOUR NAME";
	static String studentID = "YOUR 8-DIGIT ID";
	static String uciNetID = "YOUR UCI-NET ID";
	private static int nextChar; //contains the character(or -1==EOF)
	private static final int EOF = -1; //int value for End of File
	private int number;

	private static LineNumberReader in;
	public static FileOutputStream outFile;
	private static PrintStream fileData;

	public static void main(String[] args)
	{
		SIMPLESEM s = new SIMPLESEM(args[0]);
		s.parseProgram();
	}

	private void printRule(String rule)
	{
		fileData.println(rule);
	}

	
	public SIMPLESEM(String sourceFile)
	{
		try 
		{
			SIMPLESEM.in = new LineNumberReader(new FileReader(sourceFile));
			SIMPLESEM.in.setLineNumber(1);
			nextChar = SIMPLESEM.in.read(); // read in an single character
			outFile = new FileOutputStream(sourceFile + ".out");
			fileData = new PrintStream(outFile);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.err.println("init: Errors accessing source file "
					+ sourceFile);
			System.exit(-2);
		}
	}
	
	public void parseProgram() 
	{
        printRule("Program");
		parseStatement();
		while (nextChar != EOF)
		{
			parseStatement();			 
		}		
	}

	private void parseStatement() 
	{
		printRule("Statement");
		
		String token;
		String line;
		line = in.readLine();

		// TODO: 
		// Read each line
		// Tokenize the line (Use regex)
		// Keep all the tokens in a list inside each parseStatement function
		// Parse each token respectively

		if (token == tokenType.SET)
		{
			parseSet(token, line);	
		}
		else if (token == tokenType.JUMPT)
		{
			parseJumpt(token, line);
		}
		else if (token == tokenType.JUMP)
		{
			parseJump(token, line);
		}
		else if (token == tokenType.HALT)
		{
			parseHalt();
		}
		else 
		{
			System.out.println("Expression Fail\n");
		}
	}

	private void parseSet(String token, String currentLine)
	{
		// display "Set"
		// check if READ or WRITE is the end of the line 
		// go to expression function 

		if (token.indexOf("read") == EOF)
			parseExpression(token, currentLine);

		if (token.indexOf("write") == EOF)
			parseExpression(token, currentLine);
	}

	private void parseJumpt(String token, String currentLine)
	{
		// display "Jumpt"
		// check three expression 
		// ex. jumpt 8, D[0] == D[1]
		// --> get the grammar of 8, 0, 1 
	}

	private void parseJump(String token, String currentLine)
	{
		// display "Jump"
		// check the expression 
	}

	private void parseHalt()
	{
		// return
	}

	private void parseExpression(String token, String currentLine)
	{
		// display "Expression"
		// find the delimiter: + - 
		// check the left and right teram of delimiter 
	}

	private void parseTerm(String token, String currentLine)
	{
		// display "Term"
		// find the delimiter: * / %
		// check the left and right factor of delimiter 
	}

	private void parseFactor(String token, String currentLine)
	{
		// display "Factor"
		// check if is number -> if yes, then end 
		// if find symbol "D", go back to expression and check the chracter D[#]
		// # might be token[2]
	}

	private void parseNumber(String token, String currentLine)
	{
		// display number 
	}
}


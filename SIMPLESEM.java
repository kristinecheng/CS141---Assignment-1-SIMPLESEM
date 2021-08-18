// SIMPLESEM.java

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;

import javax.lang.model.util.ElementScanner6;

public class SIMPLESEM {
	
	public static class tokenType {
		static String SET = "set";
		static String JUMP = "jump";
		static String JUMPT = "jumpt";
		static String HALT = "halt";
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

	public static void main(String[] args) throws IOException
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
	
	public void parseProgram() throws IOException 
	{
        printRule("Program");
		parseStatement();
		while (nextChar != EOF)
		{
			parseStatement();			 
		}		
	}

	private void parseStatement() throws IOException 
	{
		printRule("Statement");

		String token = "";
		String content = "";

		while (nextChar != 32 && nextChar != -1) {
			token += Character.toString(nextChar);
			nextChar = SIMPLESEM.in.read();
		}

		if (nextChar != EOF) {
			nextChar = SIMPLESEM.in.read();

			while (nextChar != 10 && nextChar != 13) {
				content += Character.toString(nextChar);
				nextChar = SIMPLESEM.in.read();
			}
		}

		// System.out.println(token);
		// System.out.println(content);

		token = removeCRLF(token);

		// TODO: 
		// Read each line
		// Tokenize the line (Use regex)
		// Keep all the tokens in a list inside each parseStatement function
		// Parse each token respectively

		if (token.equals(tokenType.SET)) {
			parseSet(token, content);
		} else if (token.equals(tokenType.JUMPT)) {
			parseJumpt(token, content);
		} else if (token.equals(tokenType.JUMP)) {
			parseJump(token, content);
		} else if (token.equals(tokenType.HALT)) {
			parseHalt();
		} else {
			System.out.println("Expression Fail");
		}
	}
	
	private String removeCRLF(String str) {

		str = str.replace("\r", "");
		str = str.replace("\n", "");
		str = str.replace("\r\n", "");

		return str;
	}

	private void parseSet(String token, String currentLine)
	{
		// display "Set"
		// check if READ or WRITE is the end of the line 
		// go to expression function 

		// if (token.indexOf("read") == EOF)
		// 	parseExpression(token, currentLine);

		// if (token.indexOf("write") == EOF)
		// 	parseExpression(token, currentLine);

		System.out.println("Parsing set");
	}

	private void parseJumpt(String token, String currentLine)
	{
		// display "Jumpt"
		// check three expression 
		// ex. jumpt 8, D[0] == D[1]
		// --> get the grammar of 8, 0, 1 

		System.out.println("Parsing jumpt");
	}

	private void parseJump(String token, String currentLine)
	{
		// display "Jump"
		// check the expression 

		System.out.println("Parsing jump");
	}

	private void parseHalt()
	{
		// return

		System.out.println("Parsing halt");
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


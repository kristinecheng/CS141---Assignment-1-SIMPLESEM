// SIMPLESEM.java

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;

import javax.lang.model.util.ElementScanner6;

public class SIMPLESEM{

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
		// TODO Auto-generated method stub
		
		String token = nextChar;
		String line = in.readLine(); // read in a line of text 

		if (token == "set")
		{
			set(token, line);	
		}
		else if (token == "jumpt")
		{
			jumpt(token, line);
		}
		else if (token == "jump")
		{
			jump(token, line);
		}
		else if (token == "halt")
		{
			halt();
		}
		else 
		{
			System.out.println("Expression Fail\n");
		}
	}

	private void set(String token, String currentLine)
	{
		// display "Set"
		// check if READ or WRITE is the end of the line 
		// go to expression function 

		if (token.indexOf("read") == EOF)
			expression(token, currentLine);

		if (token.indexOf("write") == EOF)
			expression(token, currentLine);
	}

	private void jumpt(String token, String currentLine)
	{
		// display "Jumpt"
		// check three expression 
		// ex. jumpt 8, D[0] == D[1]
		// --> get the grammar of 8, 0, 1 
	}

	private void jump(String token, String currentLine)
	{
		// display "Jump"
		// chekc the expression 
	}

	private void halt()
	{
		// return
	}

	private void expression(String token, String currentLine)
	{
		// display "Expression"
		// find the delimiter: + - 
		// check the left and right teram of delimiter 
	}

	private void term(String token, String currentLine)
	{
		// display "Term"
		// find the delimiter: * / %
		// check the left and right factor of delimiter 
	}

	private void factor(String token, String currentLine)
	{
		// display "Factor"
		// check if is number -> if yes, then end 
		// if find symbol "D", go back to expression and check the chracter D[#]
		// # might be token[2]
	}

	private void number(String token, String currentLine)
	{
		// display number 
	}
}


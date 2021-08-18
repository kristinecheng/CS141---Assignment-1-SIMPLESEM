// SIMPLESEM.java

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;

import javax.lang.model.util.ElementScanner6;

public class SIMPLESEM {

	private static class tokenType {
		static String SET = "set";
		static String JUMP = "jump";
		static String JUMPT = "jumpt";
		static String HALT = "halt";
	}

	private static class terminalType {
		static String READ = "read";
		static String WRITE = "write";
	}

	static String studentName = "YOUR NAME";
	static String studentID = "YOUR 8-DIGIT ID";
	static String uciNetID = "YOUR UCI-NET ID";
	private static int nextChar; // contains the character(or -1==EOF)
	private static final int EOF = -1; // int value for End of File
	private int number;

	private static LineNumberReader in;
	public static FileOutputStream outFile;
	private static PrintStream fileData;

	public static void main(String[] args) throws IOException {
		SIMPLESEM s = new SIMPLESEM(args[0]);
		s.parseProgram();
	}

	private void printRule(String rule) {
		fileData.println(rule);
	}

	public SIMPLESEM(String sourceFile) {
		try {
			SIMPLESEM.in = new LineNumberReader(new FileReader(sourceFile));
			SIMPLESEM.in.setLineNumber(1);
			nextChar = SIMPLESEM.in.read(); // read in an single character
			outFile = new FileOutputStream(sourceFile + ".out");
			fileData = new PrintStream(outFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("init: Errors accessing source file " + sourceFile);
			System.exit(-2);
		}
	}

	public void parseProgram() throws IOException {
		printRule("Program");
		parseStatement();
		while (nextChar != EOF) {
			parseStatement();
		}
	}

	private void parseStatement() throws IOException {
		printRule("Statement");

		String token = "";
		String content = "";

		while (nextChar != 32 && nextChar != EOF) {
			token += Character.toString(nextChar);
			nextChar = SIMPLESEM.in.read();
		}

		if (nextChar != EOF) {
			nextChar = SIMPLESEM.in.read();

			while (nextChar != 10 && nextChar != 13 && nextChar != EOF) {
				content += Character.toString(nextChar);
				nextChar = SIMPLESEM.in.read();
			}
		}

		token = removeCRLF(token);

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

	private String removeSpaces(String str) {
		str = str.replace(" ", "");

		return str;
	}

	private void parseSet(String token, String content) {
		printRule("Set");
		System.out.println("Parsing set");

		String[] tokens = content.split(",");
		String left = removeSpaces(tokens[0]);
		String right = removeSpaces(tokens[1]);

		if (!left.equals(terminalType.WRITE)) {
			parseExpression(left);
		}

		if (!right.equals(terminalType.READ)) {
			parseExpression(right);
		}
	}

	private void parseJumpt(String token, String content) {
		printRule("Jumpt");
		// display "Jumpt"
		// check three expression
		// ex. jumpt 8, D[0] == D[1]
		// --> get the grammar of 8, 0, 1

		System.out.println("Parsing jumpt");
	}

	private void parseJump(String token, String content) {
		printRule("Jump");
		// display "Jump"
		// check the expression

		System.out.println("Parsing jump");
	}

	private void parseHalt() {
		System.out.println("Parsing halt");

		return;
	}

	private void parseExpression(String content) {
		printRule("Expression");
		// display "Expression"
		// find the delimiter: + -
		// check the left and right teram of delimiter
	}

	private void parseTerm(String token, String content) {
		printRule("Term");
		// display "Term"
		// find the delimiter: * / %
		// check the left and right factor of delimiter
	}

	private void parseFactor(String token, String content) {
		printRule("Factor");
		// display "Factor"
		// check if is number -> if yes, then end
		// if find symbol "D", go back to expression and check the chracter D[#]
		// # might be token[2]
	}

	private void parseNumber(String token, String content) {
		printRule("Number");
		// display number
	}
}

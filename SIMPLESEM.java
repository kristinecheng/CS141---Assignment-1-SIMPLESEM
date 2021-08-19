// SIMPLESEM.java

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private boolean debug = false;

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
		if (debug)
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
		if (debug)
			System.out.println("Parsing jumpt");
		// display "Jumpt"
		// check three expression
		// ex. jumpt 8, D[0] == D[1]
		// --> get the grammar of 8, 0, 1

		String[] tokens = content.split(",");
		String left = removeSpaces(tokens[0]);
		String right = removeSpaces(tokens[1]);

		String[] rest = right.split("!=|==|>|<|>=|<=");

		parseExpression(left);

		for (int i = 0; i < rest.length; i++) {
			parseExpression(rest[i]);
		}
	}

	private void parseJump(String token, String content) {
		printRule("Jump");
		if (debug)
			System.out.println("Parsing jump");
		// display "Jump"
		// check the expression

		token = removeSpaces(content);
		parseExpression(token);
	}

	private void parseHalt() {
		if (debug)
			System.out.println("Parsing halt");

		return;
	}

	private void parseExpression(String content) {
		printRule("Expr");
		if (debug)
			System.out.println("Parsing expression");
		// display "Expression"
		// find the delimiter: + -
		// check the left and right teram of delimiter

		String term = "";
		int paren_count = 0;

		for (int i = 0; i < content.length(); i++) {
			char chr = content.charAt(i);

			if (chr == '[' || chr == '(')
				paren_count++;

			if (chr == ']' || chr == ')')
				paren_count--;

			term += chr;

			if (paren_count == 0 && (chr == '+' || chr == '-')) {
				term = term.substring(0, term.length() - 1);
				
				parseTerm(term);
				term = "";
			}
		}

		parseTerm(term);
	}

	private void parseTerm(String content) {
		printRule("Term");
		if (debug)
			System.out.println("Parsing term");

		
		String factor = "";
		int paren_count = 0;

		for (int i = 0; i < content.length(); i++) {
			char chr = content.charAt(i);

			if (chr == '[' || chr == '(')
				paren_count++;

			if (chr == ']' || chr == ')')
				paren_count--;

			factor += chr;

			if (paren_count == 0 && (chr == '*' || chr == '/' || chr == '%')) {
				factor = factor.substring(0, factor.length() - 1);


				// System.out.println(factor);
				parseFactor(factor);
				factor = "";
			}
		}

		parseFactor(factor);
		// System.out.println(factor);

	}

	private void parseFactor(String content) {
		printRule("Factor");
		if (debug)
			System.out.println("Parsing factor");
		// display "Factor"
		// check if is number -> if yes, then end
		// if find symbol "D", go back to expression and check the chracter D[#]
		// # might be token[2]

		System.out.println(content);

		// content = content.replace("(", "");
		// content = content.replace(")", "");

		Pattern p = Pattern.compile("0|[1-9]+");
		Matcher re = p.matcher(content);

		if (re.matches()) {
			parseNumber(content);
		}

		else if (content.substring(0, 1).equals("D")) {
			String exp = content.substring(2, content.length() - 1);
			// System.out.println(exp);
			parseExpression(exp);

		}
		
		else if (content.substring(0, 1).equals("(")) {
			String exp = content.substring(1, content.length() - 1);
			// System.out.println(exp);
			parseExpression(exp);

		}
		
		else {
			// parseExpression(content);

		}
	}

	private void parseNumber(String content) {
		printRule("Number");
		// if (debug)
			System.out.println("Parsing number");
		// display number
	}
}

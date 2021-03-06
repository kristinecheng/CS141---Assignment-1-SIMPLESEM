// SIMPLESEM.java

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	static String studentName = "Eric Ko / Kristine Cheng";
	static String studentID = "Eric: 88335453 / Kristine: 62637032";
	static String uciNetID = "Eric: sangyuk1 / Kristine: cycheng5";
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

		token = removeSpaces(content);
		parseExpression(token);
	}

	private void parseHalt() {
		return;
	}

	private void parseExpression(String content) {
		printRule("Expr");

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

				parseFactor(factor);
				factor = "";
			}
		}

		parseFactor(factor);
	}

	private void parseFactor(String content) {
		printRule("Factor");

		Pattern p = Pattern.compile("0|[1-9]+");
		Matcher re = p.matcher(content);

		if (re.matches()) {
			parseNumber(content);
		}

		else if (content.substring(0, 1).equals("D")) {
			String exp = content.substring(2, content.length() - 1);
			parseExpression(exp);

		}
		
		else if (content.substring(0, 1).equals("(")) {
			String exp = content.substring(1, content.length() - 1);
			parseExpression(exp);

		}
		
		else {
			parseNumber(content);

		}
	}

	private void parseNumber(String content) {
		printRule("Number");
	}
}

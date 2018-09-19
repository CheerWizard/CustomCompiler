package lexer;
import java.util.ArrayList;
import java.util.List;

import constants.Operations;
import jdk.nashorn.api.tree.ConditionalExpressionTree;
import tokens.Token;
import tokens.TokenType;

public final class Lexer {

	private final List<Token> tokens;
	
	private final int length;
	private final String input;
	
	private int pos;
	
	public Lexer(String input) {
		this.input = input;
		length = input.length();
		tokens = new ArrayList<>();
		pos = 0;
	}
	
	public List<Token> tokenize() {
		while (pos < length) {
			if (Character.isDigit(peek(0))) tokenizeNumber(peek(0));
			else if (Operations.OPERATOR_CHARS.indexOf(peek(0)) != -1) tokenizeOperator(peek(0));
			else if (Character.isLetter(peek(0))) tokenizeWord(peek(0));
			else if (peek(0) == '#') {
				next();
				tokenizeHexNumber(peek(0));
			}
			else if (peek(0) == '"') tokenizeText(peek(0));
			else next();
		}
		return tokens;
	}
	
	private void tokenizeText(char current) {
		next(); // skip "
		final StringBuilder builder = new StringBuilder();
		while (true) {
			if (current == '\\') {
				current = next();
				switch (current) {
				case '"': current = next(); builder.append('"'); continue;
				case 'n': current = next(); builder.append('\n'); continue;
				case 't': current = next(); builder.append('\t'); continue;
				}
				builder.append('\\');
				continue;
			}
			if (current == '"') break; 
			builder.append(current);
			current = next();
		}
		next(); // skip closing "
		addToken(TokenType.TEXT , builder.toString());
	}

	private void tokenizeWord(char current) {
		final StringBuilder builder = new StringBuilder();
		while (true) {
			if (!Character.isLetterOrDigit(current) && (current != '_') && (current!= '$')) break; 
			builder.append(current);
			current = next();
		}
		switch(builder.toString()) {
		case "print" : addToken(TokenType.PRINT); break;
		case "if" : addToken(TokenType.IF); break;
		case "else" : addToken(TokenType.ELSE); break;
		}
	}

	private void tokenizeHexNumber(char current) {
		final StringBuilder builder = new StringBuilder();
		while (Character.isDigit(current) || isHexNumber(current)) {
			builder.append(current);
			current = next();
		}
		addToken(TokenType.HEX_NUMBER, builder.toString());
	}
	
	private static boolean isHexNumber(char current) {
		return "abcdef".indexOf(Character.toLowerCase(current)) != -1;
	}

	private void tokenizeOperator(char current) {
		// tokenize comments
		if (current == '/') {
			if (peek(1) == '/') {
				for (int i = 0 ; i < 2 ; i++) next(); // double call of next() , to skip slashes
				tokenizeComment(current);
				return;
			}
			else if (peek(1) == '*') {
				for (int i = 0 ; i < 2 ; i++) next(); // double call of next() , to skip slash and star
				tokenizeMultilineComment(current);
				return;
			}
		}
		// tokenize logic operators
		final StringBuilder builder = new StringBuilder();
		while (true) {
			final String text = builder.toString();
			if (!Operations.OPERATORS.containsKey(text + current) && !text.isEmpty()) {
				addToken(Operations.OPERATORS.get(text)); 
				return;
			}
			builder.append(current);
			current = next();
		}
	}

	private void tokenizeMultilineComment(char current) {
		while (true) {
			if (current == '*' && peek(1) == '/') break; 
			if(current == '\0') throw new RuntimeException("Multiline tag is not closed");
			current = next();
		}
		for (int i = 0 ; i < 2 ; i++) next(); // double call of next() , to skip slash and star
	}

	private void tokenizeComment(char current) {
		while ("\r\n\0".indexOf(current) == -1) current = next();		
	}

	private void tokenizeNumber(char current) {
		final StringBuilder builder = new StringBuilder();
		while (true) {
			if (current == '.') {
				if (builder.indexOf(".") != -1) throw new RuntimeException("Invalid float number!");
			}
			else if (!Character.isDigit(current)) break;
			builder.append(current);
			current = next();
		}
		addToken(TokenType.NUMBER, builder.toString());
	}
	
	//peek the char element of inputed string
	private char peek(int relativePosition) {
		if (pos + relativePosition >= length) return '\0';
		return input.charAt(pos + relativePosition);
	}

	//return next char element of inputed string
	private char next() {
		pos++;
		return peek(0);
	}
	
	//add token to list 
	private void addToken(TokenType tokenType , String input) {
		tokens.add(new Token(tokenType , input));
	}
	
	private void addToken(TokenType tokenType) {
		addToken(tokenType , "");
	}
	
}

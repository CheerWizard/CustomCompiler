package constants;
import java.util.HashMap;
import java.util.Map;

import tokens.TokenType;

public final class Operations {
	public final static String OPERATOR_CHARS = "+-*/()=<>!&|";
	public static final Map<String , TokenType> OPERATORS;
	static {
		OPERATORS = new HashMap<>();
		OPERATORS.put("+", TokenType.PLUS);
		OPERATORS.put("-", TokenType.MINUS);
		OPERATORS.put("*", TokenType.STAR);
		OPERATORS.put("/", TokenType.SLASH);
		OPERATORS.put(">", TokenType.GT);
		OPERATORS.put("<", TokenType.LT);
		OPERATORS.put("=", TokenType.EQ);
		OPERATORS.put("!", TokenType.EXCL);
		OPERATORS.put("&", TokenType.AMP);
		OPERATORS.put("|", TokenType.BAR);
		OPERATORS.put("(", TokenType.LPAREN);
		OPERATORS.put(")", TokenType.RPAREN);

		OPERATORS.put("==", TokenType.EQEQ);
		OPERATORS.put(">=", TokenType.GTEQ);
		OPERATORS.put("<=", TokenType.LTEQ);
		OPERATORS.put("!=", TokenType.EXCLEQ);

		OPERATORS.put("&&", TokenType.AMPAMP);
		OPERATORS.put("||", TokenType.BARBAR);
	}
}

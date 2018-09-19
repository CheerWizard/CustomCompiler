package parser;
import java.util.ArrayList;
import java.util.List;

import expressions.BinaryExpression;
import expressions.ConditionalExpression;
import expressions.ConstantExpression;
import expressions.IExpression;
import expressions.UnaryExpression;
import expressions.ValueExpression;
import statements.AssignmentStatement;
import statements.IStatement;
import statements.IfStatement;
import statements.PrintStatement;
import tokens.Token;
import tokens.TokenType;

public class Parser implements IParser {

	private static final Token EOF = new Token(TokenType.EOF , ""); 	
	private List<Token> tokens;
	private final int size;
	private int pos;
	
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
		size = tokens.size();
		pos = 0;
	}
	
	public List<IStatement> parse() {
		final List<IStatement> result = new ArrayList<>();
		while (!checkToken(TokenType.EOF)) result.add(statement());
		return result;
	}
	
	private IStatement statement() {
		if (checkToken(TokenType.PRINT)) return new PrintStatement(expression());
		if (checkToken(TokenType.IF)) return ifElse();
		return assignmentStatement();
	}
	
	private IStatement assignmentStatement() {
		final Token current = getToken(0);
		if (checkToken(TokenType.WORD) && getToken(0).getTokenType() == TokenType.EQ) {
			consumeToken(TokenType.EQ);
			return new AssignmentStatement(current.getText(), expression());
		}
		throw new RuntimeException("Unknown statement");
	}

	private Token getToken(int relativePosition) {
		if (pos + relativePosition >= size) return EOF;
		return tokens.get(pos + relativePosition);
	}
	
	private boolean checkToken(TokenType tokenType) {
		if (tokenType != getToken(0).getTokenType()) return false;
		pos++;
		return true;
	}
	
	private Token consumeToken(TokenType tokenType) {
		final Token current = getToken(0);
		if (tokenType != current.getTokenType()) throw new RuntimeException("Token " + current + "doesn't match" + tokenType);
		pos++;
		return current;
	}
	
	private IExpression primaryExpressions() {
		if (checkToken(TokenType.NUMBER)) return new ValueExpression(Double.parseDouble(getToken(0).getText()));
		if (checkToken(TokenType.HEX_NUMBER)) return new ValueExpression(Long.parseLong(getToken(0).getText() , 16));
		if (checkToken(TokenType.LPAREN)) {
			IExpression result = primaryExpressions();
			checkToken(TokenType.RPAREN); // closing parenthesis )
			return result;
		}
		if (checkToken(TokenType.WORD)) return new ConstantExpression(getToken(0).getText());
		if (checkToken(TokenType.TEXT)) return new ValueExpression(getToken(0).getText());
		throw new RuntimeException("Unknown expression");
	}
	
	private IExpression unaryExpressions() {
		if (checkToken(TokenType.MINUS)) return new UnaryExpression('-', primaryExpressions());
		if (checkToken(TokenType.PLUS)) return primaryExpressions();
		return primaryExpressions();
	}
	
	private IExpression multiplicativeExpressions() {
		IExpression result = unaryExpressions();
		while (true) {
			if(checkToken(TokenType.STAR)) {
				result = new BinaryExpression('*', result, unaryExpressions());
				continue;
			}
			if (checkToken(TokenType.SLASH)) {
				result = new BinaryExpression('/', result, unaryExpressions());
				continue;
			}
			break;
		}
		return result;
	}
	
	private IExpression additiveExpressions() {
		IExpression result = multiplicativeExpressions();
		while (true) {
			if(checkToken(TokenType.PLUS)) {
				result = new BinaryExpression('+', result, multiplicativeExpressions());
				continue;
			}
			if (checkToken(TokenType.MINUS)) {
				result = new BinaryExpression('-', result, multiplicativeExpressions());
				continue;
			}
			break;
		}
		return result;
	}
	
	private IExpression expression() {
		return logicalOr();
	}
	
	private IExpression conditional() {
		IExpression result = additiveExpressions();
		while (true) {
			if (checkToken(TokenType.LT)) {
				result = new ConditionalExpression(ConditionalExpression.Operator.LT, result, additiveExpressions());
				continue;
			}
			if (checkToken(TokenType.GT)) {
				result = new ConditionalExpression(ConditionalExpression.Operator.GT, result, additiveExpressions());
				continue;
			}
			if (checkToken(TokenType.LTEQ)) {
				result = new ConditionalExpression(ConditionalExpression.Operator.LTEQ, result, additiveExpressions());
				continue;
			}
			if (checkToken(TokenType.GTEQ)) {
				result = new ConditionalExpression(ConditionalExpression.Operator.GTEQ, result, additiveExpressions());
				continue;
			}
			break;
		}
		return result;
	}
	
	private IExpression equality() {
		IExpression result = conditional();
		if (checkToken(TokenType.EQEQ)) return new ConditionalExpression(ConditionalExpression.Operator.EQUALS, result, conditional());
		if (checkToken(TokenType.EXCLEQ)) return new ConditionalExpression(ConditionalExpression.Operator.NOT_EQUALS, result, conditional());
		return result;
	}
	
	private IExpression logicalOr() {
		IExpression result = logicalAnd();
		while (true) {
			if (checkToken(TokenType.BARBAR)) { 
				return new ConditionalExpression(ConditionalExpression.Operator.OR, result, logicalAnd());
			}
			break;
		}
		return result;
	}
	
	private IExpression logicalAnd() {
		IExpression result = equality();
		while (true) {
			if (checkToken(TokenType.AMPAMP)) { 
				return new ConditionalExpression(ConditionalExpression.Operator.AND, result, equality());
			}
			break;
		}
		return result;
	}
	
	private IStatement ifElse() {
		final IExpression condition = expression();
		final IStatement ifStatement = statement();
		final IStatement elseStatement;
		
		if (checkToken(TokenType.ELSE)) elseStatement = statement();
		else elseStatement = null;
		
		return new IfStatement(condition , ifStatement , elseStatement);
	}
	
}

package expressions;

import values.IValue;
import values.NumberValue;
import values.StringValue;

public final class BinaryExpression implements IExpression {

	private final char operation;
	private final IExpression expr1 , expr2;
	
	public BinaryExpression(char operation , IExpression expr1 , IExpression expr2) {
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.operation = operation;
	}

	@Override
	public IValue compute() {
		IValue result = null;
		//string operations
		if (expr1.compute() instanceof StringValue) result = stringExpressions();
		//number operations (math)
		if (expr1.compute() instanceof NumberValue) result = numberExpressions();
		return result;
	}
	
	private StringValue stringExpressions() {
		StringValue result = null;
		final String str1 = expr1.compute().asString();
		final String str2 = expr2.compute().asString();
		switch (operation) {
			case '*': 
				final int iterations = (int) expr2.compute().asDouble();
				final StringBuilder builder = new StringBuilder();
				for (int i = 0 ; i < iterations ; i++) builder.append(str1);
				result = new StringValue(builder.toString());
			case '+': default : result = new StringValue(str1 + str2);
		}
		return result;
	}
	
	private NumberValue numberExpressions() {
		final double number1 = expr1.compute().asDouble();
		final double number2 = expr2.compute().asDouble();
		switch (operation) {
			case '-': return new NumberValue(number1 - number2);
			case '*': return new NumberValue(number1 * number2);
			case '/': return new NumberValue(number1 / number2);
			case '+': default : return new NumberValue(number1 + number2);
		}
	}
	
	public IExpression getExpr1() {
		return expr1;
	}
	
	public IExpression getExpr2() {
		return expr2;
	}
	
	@Override
	public String toString() {
		return String.format("%s %c %s", expr1 , operation , expr2);
	}
	
}

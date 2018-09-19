package expressions;

import values.IValue;
import values.NumberValue;
import values.StringValue;

public class ConditionalExpression implements IExpression {

	public static enum Operator {
		PLUS("+"),
		MINUS("-"),
		MULTIPLY("*"),
		DIVIDE("/"),
		
		EQUALS("=="),
		NOT_EQUALS("!="),
		
		LT("<"),
		LTEQ("<="),
		GT(">"),
		GTEQ(">="),
		
		AND("&&"),
		OR("||");
		
		private final String name;

		private Operator(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	private final Operator operation;
	private final IExpression expr1 , expr2;
	
	public ConditionalExpression(Operator operation , IExpression expr1 , IExpression expr2) {
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
		else if (expr1.compute() instanceof NumberValue) result = numberExpressions();
		return result;
	}
	
	private NumberValue stringExpressions() {
		boolean result = false;
		final String str1 = expr1.compute().asString();
		final String str2 = expr2.compute().asString();
		switch (operation) {
			case GT: result = (str1.compareTo(str2) > 0); break;
			case LT: result = (str1.compareTo(str2) < 0); break;
			case EQUALS: default : result = (str1.equals(str2)); break;
		}
		return new NumberValue(result);
	}
	
	private NumberValue numberExpressions() {
		final double number1 = expr1.compute().asDouble();
		final double number2 = expr2.compute().asDouble();
		boolean result = false;
		switch (operation) {
			case GT: result = number1 > number2; break;
			case LT: result = number1 < number2; break;
			case LTEQ: result = number1 <= number2; break;
			case GTEQ: result = number1 >= number2; break;
			
			case AND: result = (number1 != 0) && (number2 != 0); break;
			case OR: result = (number1 != 0) || (number2 != 0); break;
				
			case NOT_EQUALS: result = number1 != number2; break;	
			case EQUALS: default : result = number1 == number2; break;
		}
		return new NumberValue(result);
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s", expr1 , operation.getName() , expr2);
	}
}

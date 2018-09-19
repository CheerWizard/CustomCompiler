package expressions;

import values.IValue;
import values.NumberValue;

public class UnaryExpression implements IExpression {

	private final IExpression expr;
	private final char operation;
	
	public UnaryExpression(char operation , IExpression expr) {
		this.expr = expr;
		this.operation = operation;
	}
	
	@Override
	public IValue compute() {
		switch (operation) {
			case '-' : return new NumberValue(-expr.compute().asDouble());
			case '+' : default : return new NumberValue(expr.compute().asDouble());
		}
	}
	
	@Override
	public String toString() {
		return String.format("%c %s", operation , expr);
	}

}

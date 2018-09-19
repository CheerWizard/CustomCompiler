package expressions;

import values.IValue;
import values.NumberValue;
import values.StringValue;

public final class ValueExpression implements IExpression {

	private final IValue value;
	
	public ValueExpression(double value) {
		this.value = new NumberValue(value);
	}
	
	public ValueExpression(String value) {
		this.value = new StringValue(value);
	}
	
	@Override
	public IValue compute() {
		return value;
	}

	@Override
	public String toString() {
		return value.asString();
	}
}

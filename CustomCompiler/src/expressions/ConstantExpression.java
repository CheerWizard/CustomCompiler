package expressions;

import constants.Variables;
import values.IValue;

public class ConstantExpression implements IExpression {

	private final String name;
	
	public ConstantExpression(String name) {
		this.name = name;
	}

	@Override
	public IValue compute() {
		if (!Variables.isExists(name)) throw new RuntimeException("Constant does not exists");
		return Variables.get(name);
	}
	
	@Override
	public String toString() {
		return String.format("%s", name);
	}
	
}

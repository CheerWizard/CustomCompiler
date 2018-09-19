package statements;

import constants.Variables;
import expressions.IExpression;

public class AssignmentStatement implements IStatement {

	private final String variable;
	private final IExpression expression;
	
	public AssignmentStatement(String variable , IExpression expression) {
		this.expression = expression;
		this.variable = variable;
	}
	
	@Override
	public void execute() {
		Variables.set(variable , expression.compute());
	}

	@Override
	public String toString() {
		return String.format("%s = %s", variable , expression);
	}
}

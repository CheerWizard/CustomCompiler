package statements;
import expressions.IExpression;

public class IfStatement implements IStatement {

	private final IStatement ifStatement;
	private final IStatement elseStatement;
	private final IExpression expression;
	
	public IfStatement(IExpression expression , IStatement ifStatement , IStatement elseStatement) {
		this.ifStatement = ifStatement;
		this.elseStatement = elseStatement;
		this.expression = expression;
	}
	@Override
	public void execute() {
		final double result = expression.compute().asDouble();
		if (result != 0) ifStatement.execute();;
		if (elseStatement != null) elseStatement.execute();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("if ").append(expression).append(' ').append(ifStatement);
		if (elseStatement != null) builder.append("/nelse").append(expression).append(elseStatement);
		return builder.toString();
	}

}

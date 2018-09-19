package statements;
import expressions.IExpression;

public class PrintStatement implements IStatement {

	private final IExpression expression;
	
	public PrintStatement(IExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public void execute() {
		System.out.println(expression.compute());
	}
	
	@Override
	public String toString() {
		return "print" + expression.compute();
	}

}

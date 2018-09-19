package parser;

import java.util.List;

import statements.IStatement;

public interface IParser {
	List<IStatement> parse();
}

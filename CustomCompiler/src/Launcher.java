import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import lexer.Lexer;
import parser.Parser;
import statements.IStatement;
import tokens.Token;

public class Launcher {

	private String input;
	private List<Token> tokens;
	private List<IStatement> statements;
	
	
	public Launcher(String fileName) {
		initFile(fileName);
	}
	
	//run all compiler structure with .txt file
	public void runCompiler() {
		printTokens();
		printStatements();
	}
	
	private void initFile(String fileName) {
		try {
			input = new String(Files.readAllBytes(Paths.get(fileName)) , "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException("File not found!");
		}
	}
	
	private void printTokens() {
		tokens = new Lexer(input).tokenize();
		for (Token token : tokens) System.out.println(token);
	}
	
	private void printStatements() {
		statements = new Parser(tokens).parse();
		for (IStatement statement : statements) System.out.println(statement);
		for (IStatement statement : statements) statement.execute();
	}
}

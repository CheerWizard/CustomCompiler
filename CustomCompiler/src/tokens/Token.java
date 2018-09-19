package tokens;

public final class Token {

	private TokenType tokenType;
	private String text;
	
	public Token() {
		//empty constructor
	}
	
	public Token(TokenType tokenType, String text) {
		this.text = text;
		this.tokenType = tokenType;
	}
	
	public TokenType getTokenType() {
		return tokenType;
	}
	
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}

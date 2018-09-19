package tokens;
public enum TokenType {

	//variable
	NUMBER,
	HEX_NUMBER,
	WORD,
	TEXT,
	
	//math operations
	PLUS, //+
	MINUS, //-
	STAR, // *
	EQ, // =
	LT, // <
	GT, // >
	LTEQ, // <=
	GTEQ, // >=
	
	//print text
	PRINT, // print
	SLASH, // /
	
	//logic operations
	IF, // if()
	ELSE, // else()
	AMP, // &
	AMPAMP, // &&
	BAR, // |
	BARBAR, // ||
	EQEQ, // ==
	EXCLEQ, // !=
	EXCL, // !
	
	//brackets
	LPAREN, // (
	RPAREN, // )
	
	//end of file
	EOF
}

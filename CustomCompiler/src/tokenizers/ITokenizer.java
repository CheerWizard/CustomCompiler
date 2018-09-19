package tokenizers;
import java.util.List;

import tokens.Token;

public interface ITokenizer {
	List<Token> tokenize();
}

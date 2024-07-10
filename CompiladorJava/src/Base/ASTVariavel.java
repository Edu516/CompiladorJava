package Base;

import Base.Token;

/**
 *
 * @author Usuario
 */
public class ASTVariavel extends ASTNode{
    private Token token;

    public ASTVariavel(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}

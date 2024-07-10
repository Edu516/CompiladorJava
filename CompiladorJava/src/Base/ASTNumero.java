package Base;

import Base.Token;

/**
 *
 * @author Usuario
 */
public class ASTNumero extends ASTNode{
    private final Token numero;

    public ASTNumero(Token numero) {
        this.numero = numero;
    }

    public Token getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "ASTNumero{" +
               "numero=" + numero +
               '}';
    }
}

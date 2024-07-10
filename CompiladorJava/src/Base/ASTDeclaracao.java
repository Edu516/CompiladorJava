package Base;

import Base.Token;

/**
 *
 * @author Usuario
 */
public class ASTDeclaracao extends ASTNode{
    private Token tipo;
    private Token identificador;
    private ASTNode expressao;

    public ASTDeclaracao(Token tipo, Token identificador, ASTNode expressao) {
        this.tipo = tipo;
        this.identificador = identificador;
        this.expressao = expressao;
    }

    public Token getTipo() {
        return tipo;
    }

    public Token getIdentificador() {
        return identificador;
    }

    public ASTNode getExpressao() {
        return expressao;
    }
}

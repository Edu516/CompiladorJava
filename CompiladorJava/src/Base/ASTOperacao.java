package Base;

import Base.Token;

/**
 *
 * @author Usuario
 */
public class ASTOperacao extends ASTNode{
    private ASTNode esquerda;
    private Token operador;
    private ASTNode direita;

    public ASTOperacao(ASTNode esquerda, Token operador, ASTNode direita) {
        this.esquerda = esquerda;
        this.operador = operador;
        this.direita = direita;
    }

    public ASTNode getEsquerda() {
        return esquerda;
    }

    public Token getOperador() {
        return operador;
    }

    public ASTNode getDireita() {
        return direita;
    }
}

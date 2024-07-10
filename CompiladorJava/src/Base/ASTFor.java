package Base;


/**
 *
 * @author Usuario
 */
public class ASTFor extends ASTNode{
    private ASTNode inicializacao;
    private ASTNode condicao;
    private ASTNode incremento;
    private ASTNode bloco;

    public ASTFor(ASTNode inicializacao, ASTNode condicao, ASTNode incremento, ASTNode bloco) {
        this.inicializacao = inicializacao;
        this.condicao = condicao;
        this.incremento = incremento;
        this.bloco = bloco;
    }

    public ASTNode getInicializacao() {
        return inicializacao;
    }

    public ASTNode getCondicao() {
        return condicao;
    }

    public ASTNode getIncremento() {
        return incremento;
    }

    public ASTNode getBloco() {
        return bloco;
    }
}

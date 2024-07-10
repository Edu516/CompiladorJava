package Base;


/**
 *
 * @author Usuario
 */
public class ASTIf extends ASTNode{
    private ASTNode condicao;
    private ASTNode blocoIf;
    private ASTNode blocoElse;

    public ASTIf(ASTNode condicao, ASTNode blocoIf, ASTNode blocoElse) {
        this.condicao = condicao;
        this.blocoIf = blocoIf;
        this.blocoElse = blocoElse;
    }

    public ASTNode getCondicao() {
        return condicao;
    }

    public ASTNode getBlocoIf() {
        return blocoIf;
    }

    public ASTNode getBlocoElse() {
        return blocoElse;
    }
}

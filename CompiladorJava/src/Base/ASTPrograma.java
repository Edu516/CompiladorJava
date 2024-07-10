package Base;

import java.util.List;


/**
 *
 * @author Usuario
 */
public class ASTPrograma extends ASTNode{
    private List<ASTNode> declaracoes;

    public ASTPrograma(List<ASTNode> declaracoes) {
        this.declaracoes = declaracoes;
    }

    public List<ASTNode> getDeclaracoes() {
        return declaracoes;
    }
}

package Base;

import java.util.List;

/**
 *
 * @author Usuario
 */
public class ASTBloco extends ASTNode{
    private List<ASTNode> conteudo;

    public ASTBloco(List<ASTNode> conteudo) {
        this.conteudo = conteudo;
    }

    public List<ASTNode> getConteudo() {
        return conteudo;
    }
}

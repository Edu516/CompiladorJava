package Base;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Token {

    private String lexema;
    private TokenType tipo;
    private int linhaDefinicao;
    private Escopo escopo;
    private int posInicial;
    private int posFinal;
    private List<Integer> linhasDeUtilizacao;

    public Token(String lexema, TokenType tipo, int linhaDefinicao, Escopo escopo, int posInicial, int posFinal) {
        this.lexema = lexema;
        this.tipo = tipo;
        this.linhaDefinicao = linhaDefinicao;
        this.escopo = escopo;
        this.posInicial = posInicial;
        this.posFinal = posFinal;
        this.linhasDeUtilizacao = new ArrayList<>();
    }

    public String getLexema() {
        return lexema;
    }

    public TokenType getTipo() {
        return tipo;
    }

    public int getLinhaDefinicao() {
        return linhaDefinicao;
    }

    public Escopo getEscopo() {
        return escopo;
    }

    public int getPosInicial() {
        return posInicial;
    }

    public int getPosFinal() {
        return posFinal;
    }

    public List<Integer> getLinhasDeUtilizacao() {
        return linhasDeUtilizacao;
    }

    public void addLinhaDeUtilizacao(int linha) {
        linhasDeUtilizacao.add(linha);
    }

    @Override
    public String toString() {
        return "Token{"
                + "lexema='" + lexema + '\''
                + ", tipo=" + tipo
                + ", linhaDefinicao=" + linhaDefinicao
                + ", escopo=" + escopo
                + ", posInicial=" + posInicial
                + ", posFinal=" + posFinal
                + ", linhasDeUtilizacao=" + linhasDeUtilizacao
                + '}';
    }
}

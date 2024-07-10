package Base;

import java.util.HashMap;
import java.util.Map;

public class AnalisadorSemantico {
    private Map<String, String> variaveis; // Armazena as variáveis e seus tipos

    // Construtor que inicializa o mapa de variáveis
    public AnalisadorSemantico() {
        this.variaveis = new HashMap<>();
    }

    // Método principal de análise semântica
    public void analisar(ASTNode root) {
        if (root instanceof ASTPrograma) {
            ASTPrograma programa = (ASTPrograma) root;
            for (ASTNode node : programa.getDeclaracoes()) {
                analisar(node);
            }
        } else if (root instanceof ASTDeclaracao) {
            ASTDeclaracao declaracao = (ASTDeclaracao) root;
            String tipo = declaracao.getTipo().getLexema();
            String nome = declaracao.getIdentificador().getLexema();
            if (variaveis.containsKey(nome)) {
                System.out.println("Variável já declarada: " + nome);
                // Tratar o erro de variável já declarada aqui, se necessário
            }
            variaveis.put(nome, tipo);
            analisar(declaracao.getExpressao());
        } else if (root instanceof ASTVariavel) {
            ASTVariavel variavel = (ASTVariavel) root;
            String nome = variavel.getToken().getLexema();
            if (!variaveis.containsKey(nome)) {
                System.out.println("Variável não declarada: " + nome);
                // Tratar o erro de variável não declarada aqui, se necessário
            }
        } else if (root instanceof ASTOperacao) {
            ASTOperacao operacao = (ASTOperacao) root;
            analisar(operacao.getEsquerda());
            analisar(operacao.getDireita());
        } else if (root instanceof ASTFor) {
            ASTFor forLoop = (ASTFor) root;
            analisar(forLoop.getInicializacao());
            analisar(forLoop.getCondicao());
            analisar(forLoop.getIncremento());
            analisar(forLoop.getBloco());
        } else if (root instanceof ASTIf) {
            ASTIf ifNode = (ASTIf) root;
            analisar(ifNode.getCondicao());
            analisar(ifNode.getBlocoIf());
            if (ifNode.getBlocoElse() != null) {
                analisar(ifNode.getBlocoElse());
            }
        } else if (root instanceof ASTBloco) {
            ASTBloco bloco = (ASTBloco) root;
            for (ASTNode node : bloco.getConteudo()) {
                analisar(node);
            }
        } else {
            System.out.println("Tipo de nó desconhecido: " + root.getClass());
            // Tratar outros tipos de nó desconhecidos aqui, se necessário
        }
    }
}

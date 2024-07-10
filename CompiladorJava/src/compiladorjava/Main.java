package compiladorjava;

import Base.ASTNode;
import java.util.List;
import Base.AnalisadorLexico;
import Base.AnalisadorSemantico;
import Base.AnalisadorSintatico;
import Base.ParserException;
import Base.Token;

/**
 * Classe principal para execução do compilador Autor: Usuario
 */
public class Main {

    public static void main(String[] args) {
        String codigo = "FOR (int i = 0; i < 10; i++) {\n"
                + "    String text = \"Hello\";\n"
                + "    if (i == 5) {\n"
                + "        break;\n"
                + "    }\n"
                + "    double valor = 3.14;\n"
                + "}";

        String codigo2 = "int i = 1;";
        String codigo3 = " int i ";

        AnalisadorLexico lexer = new AnalisadorLexico();
        List<Token> tokens = lexer.analisar(codigo2);

        // Imprime os tokens gerados pelo analisador léxico
        for (Token token : tokens) {
            System.out.println(token);
        }

        try {
            AnalisadorSintatico parser = new AnalisadorSintatico(tokens);
            ASTNode root = parser.Analisar();

            if (root == null) {
                System.err.println("Erro durante a analise sintática: Arvore sintatica e nula.");
                return;
            }

            // Imprime a árvore sintática
            System.out.println(root.toString());

            // Se não houver erros, prossegue com a análise semântica
            AnalisadorSemantico semanticAnalyzer = new AnalisadorSemantico();
            semanticAnalyzer.analisar(root);

        } catch (ParserException e) {
            System.err.println("Erro durante a análise sintática: " + e.getMessage());
        }
    }
}

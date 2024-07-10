package Base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnalisadorSintatico {

    private final List<Token> tokens; // Lista de tokens a serem analisados
    private Iterator<Token> tokenIterator; // Iterador para percorrer os tokens
    private Token atual; // Token atual sendo analisado
    private String erro; // Mensagem de erro encontrada durante a análise

    // Construtor que inicializa com a lista de tokens
    public AnalisadorSintatico(List<Token> tokens) {
        this.tokens = tokens;
        this.tokenIterator = tokens.iterator();
        this.erro = null;
        avancar(); // Avança para o primeiro token
    }

    // Método para avançar para o próximo token
    private void avancar() {
        if (tokenIterator.hasNext()) {
            atual = tokenIterator.next();
        } else {
            atual = null;
        }
    }

    // Método principal de análise que retorna a árvore sintática abstrata (AST)
    public ASTNode Analisar() {
        List<ASTNode> declarations = new ArrayList<>();
        while (atual != null) {
            if (atual.getTipo() == TokenType.TIPO_PRIMITIVO) {
                ASTNode decl = parseDeclaracao();
                if (decl == null) {
                    // Se ocorreu um erro, adiciona uma mensagem de erro
                    System.err.println("Erro: " + erro);
                    return null;
                }
                declarations.add(decl);
            } else if (atual.getTipo() == TokenType.PALAVRA_RESERVADA) {
                if (atual.getLexema().equals("FOR")) {
                    ASTNode forLoop = parseFor();
                    if (forLoop == null) {
                        // Se ocorreu um erro, adiciona uma mensagem de erro
                        System.err.println("Erro: " + erro);
                        return null;
                    }
                    declarations.add(forLoop);
                } else if (atual.getLexema().equals("IF")) {
                    ASTNode ifStmt = parseIf();
                    if (ifStmt == null) {
                        // Se ocorreu um erro, adiciona uma mensagem de erro
                        System.err.println("Erro: " + erro);
                        return null;
                    }
                    declarations.add(ifStmt);
                } else {
                    erro = "Palavra reservada desconhecida: " + atual.getLexema();
                    System.err.println("Erro: " + erro);
                    return null;
                }
            } else {
                erro = "Token inesperado: " + atual;
                System.err.println("Erro: " + erro);
                return null;
            }
        }
        return new ASTPrograma(declarations);
    }

    // Método para analisar declarações de variáveis
    private ASTDeclaracao parseDeclaracao() {
        Token tipo = atual;
        avancar();
        if (atual == null || atual.getTipo() != TokenType.VARIAVEL) {
            erro = "Esperava identificador, mas encontrou: " + atual;
            return null;
        }
        Token identificador = atual;
        avancar();
        if (atual == null || !atual.getLexema().equals("=")) {
            erro = "Esperava '=', mas encontrou: " + atual;
            return null;
        }
        avancar();
        ASTNode expressao = parseExpressao();
        if (expressao == null) {
            return null; // Se ocorreu um erro na expressão, retorna null
        }
        if (atual == null || !atual.getLexema().equals(";")) {
            erro = "Esperava ';', mas encontrou: " + atual;
            return null;
        }
        avancar();
        return new ASTDeclaracao(tipo, identificador, expressao);
    }

    // Método para analisar expressões
    private ASTNode parseExpressao() {
        ASTNode termo = parseTermo();
        if (termo == null) {
            return null; // Se ocorreu um erro no termo, retorna null
        }
        while (atual != null && atual.getTipo() == TokenType.OPERADOR) {
            Token operador = atual;
            avancar();
            ASTNode proximoTermo = parseTermo();
            if (proximoTermo == null) {
                return null; // Se ocorreu um erro no próximo termo, retorna null
            }
            termo = new ASTOperacao(termo, operador, proximoTermo);
        }
        return termo;
    }

    // Método para analisar termos em expressões (números, variáveis, etc.)
    private ASTNode parseTermo() {
        if (atual == null) {
            erro = "Token inesperado: null";
            return null;
        }

        if (atual.getTipo() == TokenType.NUMERO_INTEIRO || atual.getTipo() == TokenType.NUMERO_REAL) {
            Token numero = atual;
            avancar();
            return new ASTNumero(numero);
        } else if (atual.getTipo() == TokenType.VARIAVEL) {
            Token variavel = atual;
            avancar();
            return new ASTVariavel(variavel);
        } else if (atual.getLexema().equals("(")) {
            avancar();
            ASTNode expressao = parseExpressao();
            if (expressao == null) {
                return null; // Se ocorreu um erro na expressão, retorna null
            }
            if (atual == null || !atual.getLexema().equals(")")) {
                erro = "Esperava ')', mas encontrou: " + atual;
                return null;
            }
            avancar();
            return expressao;
        } else {
            erro = "Termo inesperado: " + atual;
            return null;
        }
    }

    // Método para analisar laços "for"
    private ASTFor parseFor() {
        avancar();
        if (atual == null || !atual.getLexema().equals("(")) {
            erro = "Esperava '(', mas encontrou: " + atual;
            return null;
        }
        avancar();

        // A inicialização pode ser uma declaração ou uma expressão simples
        ASTNode inicializacao;
        if (atual.getTipo() == TokenType.TIPO_PRIMITIVO) {
            inicializacao = parseDeclaracao();
        } else {
            inicializacao = parseExpressao();
        }

        if (inicializacao == null) {
            return null; // Se ocorreu um erro na inicialização, retorna null
        }
        ASTNode condicao = parseExpressao();
        if (condicao == null) {
            return null; // Se ocorreu um erro na condição, retorna null
        }
        if (atual == null || !atual.getLexema().equals(";")) {
            erro = "Esperava ';', mas encontrou: " + atual;
            return null;
        }
        avancar();

        ASTNode incremento = parseExpressao();
        if (incremento == null) {
            return null; // Se ocorreu um erro no incremento, retorna null
        }
        if (atual == null || !atual.getLexema().equals(")")) {
            erro = "Esperava ')', mas encontrou: " + atual;
            return null;
        }
        avancar();

        ASTNode bloco = parseBloco();
        if (bloco == null) {
            return null; // Se ocorreu um erro no bloco, retorna null
        }
        return new ASTFor(inicializacao, condicao, incremento, bloco);
    }

    // Método para analisar estruturas condicionais "if"
    private ASTIf parseIf() {
        avancar();
        if (atual == null || !atual.getLexema().equals("(")) {
            erro = "Esperava '(', mas encontrou: " + atual;
            return null;
        }
        avancar();
        ASTNode condicao = parseExpressao();
        if (condicao == null) {
            return null; // Se ocorreu um erro na condição, retorna null
        }
        if (atual == null || !atual.getLexema().equals(")")) {
            erro = "Esperava ')', mas encontrou: " + atual;
            return null;
        }
        avancar();
        ASTNode blocoIf = parseBloco();
        if (blocoIf == null) {
            return null; // Se ocorreu um erro no bloco if, retorna null
        }
        ASTNode blocoElse = null;
        if (atual != null && atual.getLexema().equals("else")) {
            avancar();
            blocoElse = parseBloco();
            if (blocoElse == null) {
                return null; // Se ocorreu um erro no bloco else, retorna null
            }
        }
        return new ASTIf(condicao, blocoIf, blocoElse);
    }

    // Método para analisar blocos de código
    private ASTBloco parseBloco() {
        if (atual == null || !atual.getLexema().equals("{")) {
            erro = "Esperava '{', mas encontrou: " + atual;
            return null;
        }
        avancar();
        List<ASTNode> conteudo = new ArrayList<>();
        while (atual != null && !atual.getLexema().equals("}")) {
            if (atual.getTipo() == TokenType.TIPO_PRIMITIVO) {
                ASTNode decl = parseDeclaracao();
                if (decl == null) {
                    return null; // Se ocorreu um erro na declaração, retorna null
                }
                conteudo.add(decl);
            } else if (atual.getTipo() == TokenType.PALAVRA_RESERVADA) {
                if (atual.getLexema().equals("IF")) {
                    ASTNode ifStmt = parseIf();
                    if (ifStmt == null) {
                        return null; // Se ocorreu um erro no if, retorna null
                    }
                    conteudo.add(ifStmt);
                } else if (atual.getLexema().equals("FOR")) {
                    ASTNode forLoop = parseFor();
                    if (forLoop == null) {
                        return null; // Se ocorreu um erro no for, retorna null
                    }
                    conteudo.add(forLoop);
                } else {
                    erro = "Palavra reservada desconhecida: " + atual.getLexema();
                    return null;
                }
            } else {
                ASTNode expr = parseExpressao();
                if (expr == null) {
                    return null; // Se ocorreu um erro na expressão, retorna null
                }
                if (atual == null || !atual.getLexema().equals(";")) {
                    erro = "Esperava ';', mas encontrou: " + atual;
                    return null;
                }
                avancar();
                conteudo.add(expr);
            }
        }
        if (atual == null || !atual.getLexema().equals("}")) {
            erro = "Esperava '}', mas encontrou: " + atual;
            return null;
        }
        avancar();
        return new ASTBloco(conteudo);
    }

    // Método para obter a mensagem de erro
    public String getErro() {
        return erro;
    }
}

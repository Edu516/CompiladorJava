package Base;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Eduardo
 */
public class AnalisadorLexico {
    private static final String[] PALAVRAS_RESERVADAS = {
        "FOR", "WHILE", "IF", "ELSE", "DO", "PRIVATE", "PUBLIC", 
        "CLASS", "SWITCH", "CASE", "BREAK", "CONTINUE"
    };
    
    private static final String[] SEPARADORES = {
        "(", ")", "{", "}", "[", "]", "'", "\"", ";", ".", ",", "<", ">"
    };
    
    private static final String[] OPERADORES = {
        "+", "/", "*", "-", "%", "++", "--", "=", "=="
    };
    
    private static final String[] TIPOS_PRIMITIVOS = {
        "int", "String", "double", "float", "char"
    };

    private static final Pattern NUMERO_INTEIRO = Pattern.compile("\\d+");
    private static final Pattern NUMERO_REAL = Pattern.compile("\\d+\\.\\d+");

    public List<Token> analisar(String codigo) {
        List<Token> tokens = new ArrayList<>();
        String[] linhas = codigo.split("\n");
        Escopo escopoAtual = Escopo.GLOBAL;

        for (int linhaIndex = 0; linhaIndex < linhas.length; linhaIndex++) {
            String linha = linhas[linhaIndex];
            int posicaoAtual = 0;

            while (posicaoAtual < linha.length()) {
                if (Character.isWhitespace(linha.charAt(posicaoAtual))) {
                    posicaoAtual++;
                    continue;
                }

                String lexema = extrairProximoLexema(linha, posicaoAtual);
                if (lexema.isEmpty()) {
                    posicaoAtual++;
                    continue;
                }

                Token token;
                escopoAtual = escopoParaPalavraReservada(lexema);
                if (isPalavraReservada(lexema)) {
                    Escopo escopo = escopoParaPalavraReservada(lexema);
                    token = new Token(lexema, TokenType.PALAVRA_RESERVADA, linhaIndex + 1, escopo, posicaoAtual, posicaoAtual + lexema.length() - 1);
                    escopoAtual = escopo;
                } else if (isSeparador(lexema)) {
                    token = new Token(lexema, TokenType.SEPARADOR, linhaIndex + 1, escopoAtual, posicaoAtual, posicaoAtual + lexema.length() - 1);
                } else if (isOperador(lexema)) {
                    token = new Token(lexema, TokenType.OPERADOR, linhaIndex + 1, escopoAtual, posicaoAtual, posicaoAtual + lexema.length() - 1);
                } else if (isTipoPrimitivo(lexema)) {
                    token = new Token(lexema, TokenType.TIPO_PRIMITIVO, linhaIndex + 1, escopoAtual, posicaoAtual, posicaoAtual + lexema.length() - 1);
                } else if (isNumeroReal(lexema)) {
                    token = new Token(lexema, TokenType.NUMERO_REAL, linhaIndex + 1, escopoAtual, posicaoAtual, posicaoAtual + lexema.length() - 1);
                } else if (isNumeroInteiro(lexema)) {
                    token = new Token(lexema, TokenType.NUMERO_INTEIRO, linhaIndex + 1, escopoAtual, posicaoAtual, posicaoAtual + lexema.length() - 1);
                } else {
                    token = new Token(lexema, TokenType.VARIAVEL, linhaIndex + 1, escopoAtual, posicaoAtual, posicaoAtual + lexema.length() - 1);
                }

                tokens.add(token);
                posicaoAtual += lexema.length();
            }
        }
        
        return tokens;
    }

    private String extrairProximoLexema(String linha, int posicaoAtual) {
        StringBuilder lexema = new StringBuilder();
        boolean pontoEncontrado = false;

        for (int i = posicaoAtual; i < linha.length(); i++) {
            char c = linha.charAt(i);

            if (Character.isWhitespace(c) && lexema.length() > 0) {
                break;
            }

            if (Character.isDigit(c) || (c == '.' && !pontoEncontrado)) {
                if (c == '.') {
                    pontoEncontrado = true;
                }
                lexema.append(c);
            } else if (isSeparador(Character.toString(c)) || isOperador(Character.toString(c))) {
                if (lexema.length() == 0) {
                    lexema.append(c);
                    // Verificar se o próximo caractere forma um operador composto, como '=='
                    if (i + 1 < linha.length()) {
                        String possivelComposto = c + "" + linha.charAt(i + 1);
                        if (isOperador(possivelComposto)) {
                            lexema.append(linha.charAt(i + 1));
                            i++; // Avançar o índice para não reprocessar o próximo caractere
                        }
                    }
                }
                break;
            } else if (Character.isLetter(c) || lexema.length() > 0) {
                lexema.append(c);
            } else {
                break;
            }
        }
        
        return lexema.toString();
    }
    
    private boolean isPalavraReservada(String lexema) {
        for (String palavra : PALAVRAS_RESERVADAS) {
            if (palavra.equals(lexema)) {
                return true;
            }
        }
        return false;
    }

    private Escopo escopoParaPalavraReservada(String lexema) {
        switch (lexema) {
            case "FOR": return Escopo.FOR;
            case "WHILE": return Escopo.WHILE;
            case "IF": return Escopo.IF;
            case "ELSE": return Escopo.ELSE;
            case "SWITCH": return Escopo.SWITCH;
            case "CASE": return Escopo.CASE;
            case "BREAK": return Escopo.BREAK;
            default: return Escopo.GLOBAL;
        }
    }
    
    private boolean isSeparador(String lexema) {
        for (String sep : SEPARADORES) {
            if (sep.equals(lexema)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isOperador(String lexema) {
        for (String op : OPERADORES) {
            if (op.equals(lexema)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isTipoPrimitivo(String lexema) {
        for (String tipo : TIPOS_PRIMITIVOS) {
            if (tipo.equals(lexema)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isNumeroInteiro(String lexema) {
        return NUMERO_INTEIRO.matcher(lexema).matches();
    }
    
    private boolean isNumeroReal(String lexema) {
        return NUMERO_REAL.matcher(lexema).matches();
    }
}

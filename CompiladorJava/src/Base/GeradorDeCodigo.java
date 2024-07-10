package Base;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author eduardo
 */
public class GeradorDeCodigo {
    public boolean GerarCodigo(String codigo, String caminho) {
        // Cabeçalho padrão do arquivo Java
        String cabecalho = "public class Main {\n" +
                           "    public static void main(String[] args) {\n";
        
        // Rodapé padrão do arquivo Java
        String rodape = "    }\n" +
                        "}";

        // Montando o conteúdo final do arquivo
        StringBuilder conteudoFinal = new StringBuilder();
        conteudoFinal.append(cabecalho);
        conteudoFinal.append(adicionarIndentacao(codigo));
        conteudoFinal.append(rodape);

        // Tentando escrever o arquivo no caminho especificado
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write(conteudoFinal.toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para adicionar indentação ao código fornecido
    private String adicionarIndentacao(String codigo) {
        String[] linhas = codigo.split("\n");
        StringBuilder codigoIndentado = new StringBuilder();

        for (String linha : linhas) {
            codigoIndentado.append("        ").append(linha).append("\n");
        }

        return codigoIndentado.toString();
    }
}

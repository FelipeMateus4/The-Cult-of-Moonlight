package ppoo.seuJogo;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class TesteCarregamentoImagem {
    public static void main(String[] args) {
        // Caminho ajustado para o local onde o arquivo foi carregado
        String caminhoImagem = "imagens\\avb.jpeg";

        try {
            File arquivoImagem = new File(caminhoImagem);
            System.out.println("Verificando se o arquivo existe: " + arquivoImagem.exists());
            System.out.println("Verificando se o arquivo é um arquivo: " + arquivoImagem.isFile());

            BufferedImage imagem = ImageIO.read(arquivoImagem);
            if (imagem != null) {
                System.out.println("Imagem carregada com sucesso.");
            } else {
                System.out.println("A imagem não pôde ser carregada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao tentar carregar a imagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
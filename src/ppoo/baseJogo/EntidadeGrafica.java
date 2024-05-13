package ppoo.baseJogo;

import java.awt.image.*;
import javax.imageio.*;
import java.io.File;

/**
 * Classe abstrata que define uma entidade (ambiente ou item) que possui uma
 * imagem.
 * 
 * @author Julio César Alves
 * @version 2024-04-24
 */
public abstract class EntidadeGrafica {
    // Atributo que guarda a imagem da entidade
    private BufferedImage imagem;

    /**
     * Construtor da classe recebe o caminho do arquivo que contém a imagem.
     * 
     * Por exemplo, se você tiver uma pasta chamada 'imagens' na pasta do seu
     * projeto (ou seja, ao lado da pasta 'br'), e dentro desta pasta tiver um
     * arquivo 'sala.jpg', o caminho para essa imagem será "imagens/casa.jpg".
     * 
     * @param caminhoImagem Caminho do arquivo que contém a imagem.
     */
    public EntidadeGrafica(String caminhoImagem) {
        // Se o caminho da imagem não é vazio
        if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
            try {
                // tenta carregar a imagem do caminho passado
                imagem = ImageIO.read(new File(caminhoImagem));
            }
            catch (Exception e) {
                // Se houver algum problema no carregamento da imagem (por exemplo, ela não for
                // encontrada)
                // esta mensagem será exibida
                System.out.println("ATENÇÃO: Erro ao tentar abrir imagem " + caminhoImagem + "!");
            }
        }
    }

    /**
     * Retorna a imagem que representa a entidade (ambiente ou item)
     */
    public BufferedImage getImagem() {
        return imagem;
    }

    /**
     * Método abstrato que retorna o nome da entidade (ambiente ou item)
     */
    public abstract String getNome();
}

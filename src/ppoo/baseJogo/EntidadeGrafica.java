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
     * @param caminhoImagem Caminho do arquivo que contém a imagem.
     */
    public EntidadeGrafica(String caminhoImagem) {
        // Se o caminho da imagem não é vazio
        if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
            try {
                // tenta carregar a imagem do caminho passado
                imagem = ImageIO.read(new File(caminhoImagem));
                if (imagem == null) {
                    System.out.println("ATENÇÃO: Imagem não encontrada ou não pode ser carregada: " + caminhoImagem);
                }
            }
            catch (Exception e) {
                // Se houver algum problema no carregamento da imagem (por exemplo, ela não for
                // encontrada)
                System.out.println("ATENÇÃO: Erro ao tentar abrir imagem " + caminhoImagem + "! Detalhes: " + e.getMessage());
            }
        } else {
            System.out.println("ATENÇÃO: Caminho da imagem é nulo ou vazio!");
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

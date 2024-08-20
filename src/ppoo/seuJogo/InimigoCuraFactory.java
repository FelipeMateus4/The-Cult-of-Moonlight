package ppoo.seuJogo;

import java.util.List;

public class InimigoCuraFactory extends InimigoFactory {
    @Override
    public Inimigo criarInimigo(String tipo, String nome, String descricao, double vida, double dano, int pontos, List<Item> itensDrop) {
        switch (tipo) {
            case "Boss":
                return new InimigoBossCura(nome, descricao, vida, dano, pontos, itensDrop);
            case "Lobisomem":
                return new InimigoLobisomemCura(nome, descricao, vida, dano, pontos, itensDrop);
            case "Comum":
                return new InimigoComumCura(nome, descricao, vida, dano, pontos, itensDrop);
            default:
                throw new IllegalArgumentException("Tipo de inimigo desconhecido: " + tipo);
        }
    }
}

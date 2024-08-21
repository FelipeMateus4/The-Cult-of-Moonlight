package ppoo.seuJogo.Inimigo;

import java.util.List;

import ppoo.seuJogo.Equipaveis.Item;

public class InimigoNaoCuraFactory extends InimigoFactory {
    @Override
    public Inimigo criarInimigo(String tipo, String nome, String descricao, double vida, double dano, int pontos, List<Item> itensDrop) {
        switch (tipo) {
            case "Boss":
                return new InimigoBoss(nome, descricao, vida, dano, pontos, itensDrop);
            case "Lobisomem":
                return new InimigoLobisomem(nome, descricao, vida, dano, pontos, itensDrop);
            case "Comum":
                return new InimigoComum(nome, descricao, vida, dano, pontos, itensDrop);
            default:
                throw new IllegalArgumentException("Tipo de inimigo desconhecido: " + tipo);
        }
    }
}

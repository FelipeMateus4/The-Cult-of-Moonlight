package ppoo.seuJogo;

import java.util.List;

public abstract class InimigoFactory {
    public abstract Inimigo criarInimigo(String tipo, String nome, String descricao, double vida, double dano, int pontos, List<Item> itensDrop);
}

package ppoo.seuJogo.Inimigo;

import java.util.List;

import ppoo.seuJogo.Equipaveis.Item;

public abstract class InimigoFactory {
    public abstract Inimigo criarInimigo(String tipo, String nome, String descricao, double vida, double dano, int pontos, List<Item> itensDrop);
}

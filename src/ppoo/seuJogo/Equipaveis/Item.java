package ppoo.seuJogo.Equipaveis;

import ppoo.baseJogo.EntidadeGrafica;

public class Item extends EntidadeGrafica {
    private String nome;
    private String descricao;

    public Item(String nome, String descricao, String caminhoImagem) {
        super(caminhoImagem);
        this.nome = nome;
        this.descricao = descricao;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}

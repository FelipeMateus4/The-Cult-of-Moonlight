package ppoo.seuJogo;

public class Item {
    private String nome;
    private String descricao;
    private int durabilidade;

    public Item(String nome, String descricao, int durabilidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.durabilidade =  durabilidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public int getDurabilidade() {
        return durabilidade;
    }
}
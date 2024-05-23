package ppoo.seuJogo;

public class Arma extends Item {
    private int durabilidade;

    public Arma(String nome, String descricao, int durabilidade) {
        super(nome, descricao);
        this.durabilidade = durabilidade;
    }

    public int getDurabilidade() {
        return durabilidade;
    }

}

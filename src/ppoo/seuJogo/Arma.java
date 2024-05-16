package ppoo.seuJogo;

public class Arma extends Item {
    private double durabilidade;

    public Arma(String nome, String descricao, double durabilidade) {
        super(nome, descricao);
        this.durabilidade = durabilidade;
    }

    public double getDurabilidade() {
        return durabilidade;
    }
}

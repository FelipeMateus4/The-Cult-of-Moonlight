package ppoo.seuJogo;

public class Arma extends Item {
    private int durabilidade;
    private String classe;

    public Arma(String nome, String descricao, int durabilidade, String classe) {
        super(nome, descricao);
        this.durabilidade = durabilidade;
        this.classe = classe;
    }

    public int getDurabilidade() {
        return durabilidade;
    }

    public String getClasse() {
        return classe;
    }

}

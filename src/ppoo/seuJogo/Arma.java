package ppoo.seuJogo;

public abstract class Arma extends Item {
    private int durabilidade;
    private String classe;
    protected DanoDeArmaStrategy danoStrategy;

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

    public double calcularDano(double baseDamage, Inimigo inimigo) {
        return danoStrategy.calcularDano(baseDamage, inimigo);
    }
}

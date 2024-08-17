package ppoo.seuJogo;

public abstract class Arma extends Item {
    private int durabilidade;
    private double danoBase;
    private String classe;
    protected DanoDeArmaStrategy danoStrategy;

    public Arma(String nome, double danoBase, String descricao, int durabilidade, String classe, String caminhoImagem) {
        super(nome, descricao, caminhoImagem);
        this.danoBase = danoBase;
        this.durabilidade = durabilidade;
        this.classe = classe;
    }

    public int getDurabilidade() {
        return durabilidade;
    }

    public double getDanoBase() {
        return danoBase;
    }

    public String getClasse() {
        return classe;
    }

    public DanoDeArmaStrategy getDanoStrategy() {
        return danoStrategy;
    }

    public double calcularDano(double danoBase, Inimigo inimigo) {
        if (danoStrategy != null) {
            return danoStrategy.calcularDano(danoBase, inimigo);
        } else {
            return danoBase;
        }
    }
}

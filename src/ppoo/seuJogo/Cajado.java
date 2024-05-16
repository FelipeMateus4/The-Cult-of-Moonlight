package ppoo.seuJogo;

public class Cajado extends Arma {
    private double danoMagico;

    public Cajado(String nome, String descricao, int durabilidade, double danoMagico) {
        super(nome, descricao, durabilidade);
        this.danoMagico = danoMagico;
    }
    
    public double getDanoPerfurante() {
        return danoMagico;
    }
}

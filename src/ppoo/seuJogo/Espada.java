package ppoo.seuJogo;

public class Espada extends Item {
    private double danoCorte;
    
    public Espada(String nome, String descricao, int durabilidade, double danoCorte) {
        super(nome, descricao, durabilidade);
        this.danoCorte = danoCorte;
    }

    public double getDanoCorte() {
        return danoCorte;
    }
}

package ppoo.seuJogo;

public class Armadura extends Item{
    private double defesaFisica;
    private double defesaMagica;

    public Armadura(String nome, String descricao, double defesaFisica, double defesaMagica) {
        super(nome, descricao);
        this.defesaFisica = defesaFisica;
        this.defesaMagica = defesaMagica;
    }

    public double getDefesaFisica() {
        return defesaFisica;
    }

    public double getDefesaMagica() {
        return defesaMagica;
    }
}

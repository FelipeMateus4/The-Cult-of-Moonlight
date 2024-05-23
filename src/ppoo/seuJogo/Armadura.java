package ppoo.seuJogo;

public class Armadura extends Item implements Equipavel{
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

    @Override
    public void equipado(Jogador jogador) {
        String nome = getNome();
        double defesaFisica = getDefesaFisica();
        double defesaMagica = getDefesaMagica();
        System.out.println("Armadura:");
        System.out.println("Você está equipado com a armadura: " + nome);
        System.out.println("Defesa Física: " + defesaFisica);
        System.out.println("Defesa Mágica: " + defesaMagica);
    }
}

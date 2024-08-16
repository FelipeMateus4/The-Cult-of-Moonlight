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
    public String equipado(Jogador jogador) {
        String nome = getNome();
        double defesaFisica = getDefesaFisica();
        double defesaMagica = getDefesaMagica();
        String mensagem = "Armadura:\n";
        mensagem += "Você está equipado com a armadura: " + nome + "\n";
        mensagem += "Defesa Física: " + defesaFisica + "\n";
        mensagem += "Defesa Mágica: " + defesaMagica + "\n";
        
        return mensagem;
    }
    

    @Override
    public boolean equipar(Jogador jogador) {
        jogador.setArmaduraAtual(this);
        return true;
    }
}

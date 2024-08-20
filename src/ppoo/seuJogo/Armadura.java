package ppoo.seuJogo;

public class Armadura extends Item implements Equipavel{
    private double defesa;

    public Armadura(String nome, String descricao, double defesa, String caminhoImagem) {
        super(nome, descricao, caminhoImagem);
        this.defesa = defesa;
    }

    public double getDefesa() {
        return defesa;
    }

    @Override
    public String equipado(Jogador jogador) {
        String nome = getNome();
        double defesa = getDefesa();
        String mensagem = "Armadura:\n";
        mensagem += "Você está equipado com a armadura: " + nome + "\n";
        mensagem += "Defesa: " + defesa + "\n";
        
        return mensagem;
    }
    

    @Override
    public boolean equipar(Jogador jogador) {
        jogador.setArmaduraAtual(this);
        return true;
    }
}

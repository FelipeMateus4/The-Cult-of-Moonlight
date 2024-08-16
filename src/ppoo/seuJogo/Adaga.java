package ppoo.seuJogo;

public class Adaga extends Arma implements Equipavel{

    public Adaga(String nome, double danoBase, String descricao, Integer durabilidade) {
        super(nome, danoBase, descricao, durabilidade, "Ladrao");
        this.danoStrategy = new  DanoDeAdagaStrategy();
    }

    @Override
    public String equipado(Jogador jogador) {
        String nome = getNome();
        int durabilidade = getDurabilidade();
        String mensagem = "Adaga:\n";
        mensagem += "Você está equipado com a adaga: " + nome + "\n";
        mensagem += "Durabilidade: " + durabilidade + "\n";
        mensagem += "Dano de corte: " + getDanoBase() + "\n";
        
        return mensagem;
    }
    

    @Override
    public boolean equipar(Jogador jogador) {
        if (!jogador.getClasseJogador().equals("Ladrão")) {
            System.out.println("Você não pode equipar uma adaga, pois não é um ladrao.");
            return false;
        }
        jogador.setArmaAtual(this);
        return true;
    }
}

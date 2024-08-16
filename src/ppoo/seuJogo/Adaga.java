package ppoo.seuJogo;

public class Adaga extends Arma implements Equipavel{

    public Adaga(String nome, double danoBase, String descricao, Integer durabilidade) {
        super(nome, danoBase, descricao, durabilidade, "Ladrao");
        this.danoStrategy = new  DanoDeAdagaStrategy();
    }

    @Override
    public void equipado(Jogador jogador) {
        String nome = getNome();
        int durabilidade = getDurabilidade();
        System.out.println("Adaga:");
        System.out.println("Você está equipado com a adaga: " + nome);
        System.out.println("Durabilidade: " + durabilidade);
        System.out.println("Dano de corte: " + getDanoBase());
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

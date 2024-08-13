package ppoo.seuJogo;

public class Adaga extends Arma implements Equipavel{
    private Double danoPerfurante;

    public Adaga(String nome, String descricao, Integer durabilidade, Double danoPerfurante) {
        super(nome, descricao, durabilidade, "Ladrao");
        this.danoPerfurante = danoPerfurante;
        this.danoStrategy = new  DanoDeAdagaStrategy();
    }
    
    public Double getDanoPerfurante() {
        return danoPerfurante;
    }

    @Override
    public void equipado(Jogador jogador) {
        String nome = getNome();
        int durabilidade = getDurabilidade();
        System.out.println("Adaga:");
        System.out.println("Você está equipado com a adaga: " + nome);
        System.out.println("Durabilidade: " + durabilidade);
        System.out.println("Dano de corte: " + danoPerfurante);
    }

    @Override
    public boolean equipar(Jogador jogador) {
        if (jogador.getClasseJogador() != "Ladrao") {
            System.out.println("Você não pode equipar uma adaga, pois não é um ladrao.");
            return false;
        }
        jogador.setArmaAtual(this);
        return true;
    }
}

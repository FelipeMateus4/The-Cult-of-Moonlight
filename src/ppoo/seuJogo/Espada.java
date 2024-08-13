package ppoo.seuJogo;

public class Espada extends Arma implements Equipavel {


    public Espada(String nome, double danoBase, String descricao, int durabilidade) {
        super(nome, danoBase, descricao, durabilidade, "Guerreiro");
        this.danoStrategy = new DanoDeEspadaStrategy();
    }

    @Override
    public void equipado(Jogador jogador) {
        String nome = getNome();
        int durabilidade = getDurabilidade();
        System.out.println("Espada:");
        System.out.println("Você está equipado com a espada: " + nome);
        System.out.println("Durabilidade: " + durabilidade);
        System.out.println("Dano de corte: " + getDanoBase());
    }

    @Override
    public boolean equipar(Jogador jogador) {
        if (!jogador.getClasseJogador().equals("Guerreiro")) {
            System.out.println("Você não pode equipar uma espada, pois não é um guerreiro.");
            return false;
        }
        jogador.setArmaAtual(this);
        return true;
    }
}

// Classes Adaga e Cajado seguem a mesma estrutura, com estratégias de dano apropriadas.

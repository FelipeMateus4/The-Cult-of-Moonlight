package ppoo.seuJogo;

public class Espada extends Arma implements Equipavel {


    public Espada(String nome, double danoBase, String descricao, int durabilidade) {
        super(nome, danoBase, descricao, durabilidade, "Guerreiro");
        this.danoStrategy = new DanoDeEspadaStrategy();
    }

    @Override
    public String equipado(Jogador jogador) {
        String nome = getNome();
        int durabilidade = getDurabilidade();
        String mensagem = "Espada:\n";
        mensagem += "Você está equipado com a espada: " + nome + "\n";
        mensagem += "Durabilidade: " + durabilidade + "\n";
        mensagem += "Dano de corte: " + getDanoBase() + "\n";
        
        return mensagem;
    }
    

    @Override
    public boolean equipar(Jogador jogador) {
        if (!jogador.getClasseJogador().equals("Guerreiro")) {
            return false;
        }
        jogador.setArmaAtual(this);
        return true;
    }
}

// Classes Adaga e Cajado seguem a mesma estrutura, com estratégias de dano apropriadas.

package ppoo.seuJogo;

public class Cajado extends Arma implements Equipavel{

    public Cajado(String nome, double danoBase, String descricao, int durabilidade) {
        super(nome, danoBase, descricao, durabilidade, "Mago");
        this.danoStrategy = new  DanoDeCajadoStrategy();
    }
    

    @Override
    public void equipado(Jogador jogador) {
        String nome = getNome();
        int durabilidade = getDurabilidade();
        System.out.println("Cajado:");
        System.out.println("Você está equipado com a cajado: " + nome);
        System.out.println("Durabilidade: " + durabilidade);
        System.out.println("Dano de corte: " + getDanoBase());
    }

    @Override
    public boolean equipar(Jogador jogador) {
        if (jogador.getClasseJogador() != "Mago") {
            System.out.println("Você não pode equipar um cajado, pois não é um mago.");
            return false;
        }
        jogador.setArmaAtual(this);
        return true;
    }
}

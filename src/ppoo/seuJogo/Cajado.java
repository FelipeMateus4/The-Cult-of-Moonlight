package ppoo.seuJogo;

public class Cajado extends Arma implements Equipavel{
    private double danoMagico;

    public Cajado(String nome, String descricao, int durabilidade, double danoMagico) {
        super(nome, descricao, durabilidade, "Mago");
        this.danoMagico = danoMagico;
        this.danoStrategy = new  DanoDeCajadoStrategy();
    }
    
    public double getDanoPerfurante() {
        return danoMagico;
    }

    @Override
    public void equipado(Jogador jogador) {
        String nome = getNome();
        int durabilidade = getDurabilidade();
        System.out.println("Cajado:");
        System.out.println("Você está equipado com a cajado: " + nome);
        System.out.println("Durabilidade: " + durabilidade);
        System.out.println("Dano de corte: " + danoMagico);
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

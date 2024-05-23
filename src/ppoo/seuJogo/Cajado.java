package ppoo.seuJogo;

public class Cajado extends Arma implements Equipavel{
    private double danoMagico;

    public Cajado(String nome, String descricao, int durabilidade, double danoMagico) {
        super(nome, descricao, durabilidade);
        this.danoMagico = danoMagico;
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
}

package ppoo.seuJogo;

public class Mao extends Arma implements Equipavel{
    private double danoSoco = 1.0;

    public Mao(String nome, double baseDano, String descricao, int durabilidade) {
        super(nome, baseDano, descricao, durabilidade, "Nenhum");
    }

    @Override
    public void equipado(Jogador jogador) {
        System.out.println("Mão:");
        System.out.println("Você está de mãos vazias.");
        System.out.println("Dano do soco: " + danoSoco);
    }

    @Override
    public boolean equipar(Jogador jogador) {
        jogador.setArmaAtual(this);
        return true;
    }
}

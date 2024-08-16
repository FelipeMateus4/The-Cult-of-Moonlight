package ppoo.seuJogo;

public class Mao extends Arma implements Equipavel{
    private double danoSoco = 1.0;

    public Mao(String nome, double baseDano, String descricao, int durabilidade) {
        super(nome, baseDano, descricao, durabilidade, "Nenhum");
    }

    @Override
    public String equipado(Jogador jogador) {
        String mensagem = "Mão:\n";
        mensagem += "Você está de mãos vazias.\n";
        mensagem += "Dano do soco: " + danoSoco + "\n";
        
        return mensagem;
    }
    

    @Override
    public boolean equipar(Jogador jogador) {
        jogador.setArmaAtual(this);
        return true;
    }
}

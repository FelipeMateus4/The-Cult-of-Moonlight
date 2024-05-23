package ppoo.seuJogo;

public class Adaga extends Arma implements Equipavel{
    private Double danoPerfurante;

    public Adaga(String nome, String descricao, Integer durabilidade, Double danoPerfurante) {
        super(nome, descricao, durabilidade);
        this.danoPerfurante = danoPerfurante;
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
}

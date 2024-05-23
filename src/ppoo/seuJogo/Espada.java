package ppoo.seuJogo;

public class Espada extends Arma implements Equipavel{
    private double danoCorte;
    
    public Espada(String nome, String descricao, int durabilidade, double danoCorte) {
        super(nome, descricao, durabilidade);
        this.danoCorte = danoCorte;
    }

    public double getDanoCorte() {
        return danoCorte;
    }

    @Override
    public void equipado(Jogador jogador) {
        String nome = getNome();
        int durabilidade = getDurabilidade();
        System.out.println("Espada:");
        System.out.println("Você está equipado com a espada: " + nome);
        System.out.println("Durabilidade: " + durabilidade);
        System.out.println("Dano de corte: " + danoCorte);
    }
}

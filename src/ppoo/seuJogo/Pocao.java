package ppoo.seuJogo;

public class Pocao extends Consumivel implements Bebivel {
    private double vidaDada;

    public Pocao(String nome, String descricao, double vidaDada, int usos, String caminhoImagem) {
        super(nome, descricao, usos, caminhoImagem);
        this.vidaDada = vidaDada;
    }

    public double getVidaDada() {
        return vidaDada;
    }

    @Override
    public boolean beberPocao(Jogador jogador) {
        if (this.getUsos() >= 1) {
            jogador.beber(this);
            this.setUsosDiminuir();
            if (this.getUsos() == 0) {
                jogador.removerItem(this.getNome());
                System.out.println("Seus usos acabaram. Você não tem mais " + this.getNome() + " na mochila.");
            }
            return true;
        } 
        else { 
            return false;
        }
    }
}

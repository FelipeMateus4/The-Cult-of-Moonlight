package ppoo.seuJogo.Pegaveis;

import ppoo.seuJogo.Jogador;
import ppoo.seuJogo.interfaces.Bebivel;

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
                return true;
            }
            return false;
        } 
        else { 
            return false;
        }
    }
}

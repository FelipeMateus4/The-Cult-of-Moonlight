package ppoo.seuJogo.Pegaveis;

import ppoo.seuJogo.Equipaveis.Item;

public class Consumivel extends Item {
    private int usos;

    public Consumivel(String nome, String descricao, int usos, String caminhoImagem) {
        super(nome, descricao, caminhoImagem);
        this.usos = usos;
    }

    public int getUsos() {
        return usos;
    }

    public int setUsosDiminuir() {
        usos = usos - 1;
        return usos;
    }

    public int setUsosAumentar() {
        usos = usos + 1;
        return usos;
    }
}

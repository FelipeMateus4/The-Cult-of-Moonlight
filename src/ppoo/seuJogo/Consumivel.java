package ppoo.seuJogo;

public class Consumivel extends Item {
    private int usos;

    public Consumivel(String nome, String descricao, int usos) {
        super(nome, descricao);
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

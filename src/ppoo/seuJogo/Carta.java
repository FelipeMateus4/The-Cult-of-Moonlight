package ppoo.seuJogo;

public class Carta extends Item {
    private String texto;

    public Carta(String nome, String descricao, String texto) {
        super(nome, descricao);
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
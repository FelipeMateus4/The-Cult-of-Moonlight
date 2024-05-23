package ppoo.seuJogo;

public class Carta extends Item implements Legivel{
    private String texto;

    public Carta(String nome, String descricao, String texto) {
        super(nome, descricao);
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public void lerCarta(Jogador jogador) {
        System.out.println("Você leu a carta: " + getTexto());
    }
}

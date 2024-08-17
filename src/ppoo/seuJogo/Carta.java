package ppoo.seuJogo;

public class Carta extends Item implements Legivel{
    private String texto;

    public Carta(String nome, String descricao, String texto, String caminhoImagem) {
        super(nome, descricao, caminhoImagem);
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public String lerCarta(Jogador jogador) {
        return "Você leu a carta: " + getTexto();
    }
}

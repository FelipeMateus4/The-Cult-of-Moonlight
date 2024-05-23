package ppoo.seuJogo;

public class Pocao extends Consumivel implements Bebivel{
    private double vidaDada;

    public Pocao(String nome, String descricao, double vidaDada, int usos) {
        super(nome, descricao, usos);
        this.vidaDada = vidaDada;
    }

    public double getVidaDada() {
        return vidaDada;
    }

    public void setvidaDada() {
        vidaDada = 0.0;
    }

    @Override
    public void beberPocao(Jogador jogador) {
        if(this.getUsos() >= 1) {
            jogador.beber(this);
            System.out.println("Você bebeu a poção e recuperou " + getVidaDada() + " de vida");
            this.setvidaDada();
            this.setUsosDiminuir();

        } 
        else { 
            System.out.println(" não pode ser bebido pois está vazio");
        }
    }

}

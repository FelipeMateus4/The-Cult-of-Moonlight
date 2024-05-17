package ppoo.seuJogo;

// Classe que representa a saída dos ambientes.
public class Saida {
    private Ambiente destino;
    private boolean trancado;
    private String chave;

    public Saida(Ambiente destino, boolean trancado, String chave) {
        this.destino = destino;
        this.trancado = trancado;
        this.chave = chave;
    }

    public Saida(Ambiente destino) {
        this(destino, false, null);
    }

    public Ambiente getDestino() {
        return destino;
    }

    public boolean estaTrancado() {
        return trancado;
    }

    public String getChave() {
        return chave;
    }

    public void desbloquear() {
        trancado = false;
    }
}

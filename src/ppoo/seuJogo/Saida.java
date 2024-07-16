package ppoo.seuJogo;

// Classe que representa a saída dos ambientes.
public class Saida {
    private Ambiente destino;
    private boolean trancado;
    private String chave;
    private String motivoBloqueio;

    public Saida(Ambiente destino, boolean trancado, String chave, String motivoBloqueio) {
        this.destino = destino;
        this.trancado = trancado;
        this.chave = chave;
        this.motivoBloqueio = motivoBloqueio;
    }

    public Saida(Ambiente destino) {
        this(destino, false, null, null);
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

    public String getMotivoBloqueio() {
        return motivoBloqueio;
    }
}

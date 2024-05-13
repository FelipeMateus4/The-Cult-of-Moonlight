package ppoo.seuJogo;

public class Adaga extends Item {
    private Double danoPerfurante;

    public Adaga(String nome, String descricao, Integer durabilidade, Double danoPerfurante) {
        super(nome, descricao, durabilidade);
        this.danoPerfurante = danoPerfurante;
    }
    
    public Double getDanoPerfurante() {
        return danoPerfurante;
    }
}

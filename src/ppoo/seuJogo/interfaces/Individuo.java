package ppoo.seuJogo.interfaces;

public abstract class Individuo {
    private String nome;
    private String descricao;


public Individuo(String nome, String descricao) {
    this.nome = nome;
    this.descricao = descricao;
}

public String getDescricao() {
    return descricao;
}

public String getNome() {
    return nome;
}
}
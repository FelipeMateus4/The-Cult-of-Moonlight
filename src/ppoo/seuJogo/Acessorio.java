package ppoo.seuJogo;

public class Acessorio extends Item implements Equipavel{
    private String efeito;

    Acessorio (String nome, String descricao, String efeito) {
        super(nome, descricao);
        this.efeito =  efeito;
    }
    public String getEfeito() {
        return efeito;
    }

        @Override
    public void equipado(Jogador jogador) {
        String nome = getNome();
        System.out.println("Acessorio:");
        System.out.println("Você esta equipado com o acessorio: " + nome + " que tem o efeito " + getEfeito());

        
    }

    @Override
    public boolean equipar(Jogador jogador) {
        jogador.setAcessorioAtual(this);
        return true;
    }
}

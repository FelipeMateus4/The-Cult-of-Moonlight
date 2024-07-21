package ppoo.seuJogo;

public class Lampiao extends Acessorio implements Equipavel {

    Lampiao(String nome, String descricao, String efeito) {
        super(nome, descricao, efeito);
    }

    @Override
    public void equipado(Jogador jogador) {
        String nome = getNome();
        System.out.println("Acessorio:");
        System.out.println("Você esta equipado com o acessorio: " + nome + "que tem o efeito " + getEfeito());

        
    }

    @Override
    public boolean equipar(Jogador jogador) {
        jogador.setAcessorioAtual(this);
        return true;
    }
}

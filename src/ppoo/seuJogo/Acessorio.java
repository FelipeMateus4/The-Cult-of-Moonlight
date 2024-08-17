package ppoo.seuJogo;

public class Acessorio extends Item implements Equipavel{
    private String efeito;

    Acessorio (String nome, String descricao, String efeito, String caminhoImagem) {
        super(nome, descricao, caminhoImagem);
        this.efeito =  efeito;
    }
    public String getEfeito() {
        return efeito;
    }

    @Override
    public String equipado(Jogador jogador) {
        String nome = getNome();
        String efeito = getEfeito();  // Presumindo que getEfeito() retorna o efeito do acessório
        String mensagem = "Acessório:\n";
        mensagem += "Você está equipado com o acessório: " + nome + " que tem o efeito " + efeito + "\n";
        
        return mensagem;
    }
    

    @Override
    public boolean equipar(Jogador jogador) {
        jogador.setAcessorioAtual(this);
        return true;
    }
}

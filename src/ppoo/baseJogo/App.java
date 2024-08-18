package ppoo.baseJogo;

import ppoo.seuJogo.Jogo;

public class App {
    public static void main(String[] args) throws Exception {
              try {
            // Defina o caminho para o arquivo de configuração
            String caminhoConfiguracao = "src\\ppoo\\seuJogo\\configuracoes.txt";
            
            // Crie uma instância da Tela, que é a interface de usuário para o modo gráfico
            Tela tela = new Tela("Cult of Moonlight");
            
            // Crie uma instância do jogo passando a Tela e o caminho do arquivo de configuração
            Jogo jogo = new Jogo(tela, caminhoConfiguracao);
            
            // Inicia o jogo
            jogo.jogar();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao iniciar o jogo gráfico: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


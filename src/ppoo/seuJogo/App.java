package ppoo.seuJogo;

public class App {
    public static void main(String[] args) {
        try {
            // Defina o caminho para o arquivo de configuração
            String caminhoConfiguracao = "C:\\Users\\ramon\\Desktop\\trabalho-pratico-cult-of-moonlight\\src\\ppoo\\seuJogo\\configuracoes.txt";
            
            // Crie uma instância do jogo passando o caminho do arquivo de configuração
            Jogo jogo = new Jogo(caminhoConfiguracao);
            
            // Inicia o jogo
            jogo.jogar();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao iniciar o jogo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
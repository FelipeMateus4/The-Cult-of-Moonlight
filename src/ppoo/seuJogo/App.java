package ppoo.seuJogo;

public class App {
    public static void main(String[] args) {
        try {
            // Defina o caminho para o arquivo de configuração
            String caminhoConfiguracao = "src\\ppoo\\seuJogo\\configuracoes.txt";
            
            // Crie uma instância do Terminal, que é a interface de usuário para o modo terminal
            Terminal terminal = new Terminal();
            
            // Crie uma instância do jogo passando o Terminal e o caminho do arquivo de configuração
            Jogo jogo = new Jogo(terminal, caminhoConfiguracao);
            
            // Inicia o jogo
            jogo.jogar();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao iniciar o jogo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

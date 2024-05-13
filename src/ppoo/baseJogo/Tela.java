package ppoo.baseJogo;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.ActionEvent;
import com.formdev.flatlaf.FlatDarkLaf;

/* Melhorias possíveis:
 *   - Tratamento para comando sair (término do jogo em geral).
 *   - Criar botões para as direções possíveis (atalho para comando "ir")
 *   - Tratar itens como botões (atalho para comando "usar")
 *   - Tratar todos os comandos válidos como botões
 */

/**
 * Classe que cria e exibe uma janela gráfica para Interface com o Usuário em um
 * jogo do tipo Adventure.
 * 
 * @author Julio César Alves
 * @version 2024-04-24
 */
public class Tela implements InterfaceUsuario {
    // janela da aplicacao
    private JFrame janela;
    // area de mensagens do jogo
    private JTextArea areaMensagens;
    // painel que mostra a imagem do ambiente
    private JPanel painelAmbiente;
    // rótulo para a imagem do ambiente
    private JLabel rotuloImagemAmbiente;
    // campo de entrada de dados do usuario
    private JTextField campoEntrada;
    // area de itens da mochila
    private JPanel painelItens;
    // Margem usada nos componentes de texto
    private Insets margem;

    // guardam a altura e a largura da janela
    private int larguraJanela, alturaJanela;

    // guarda a largura do painel central
    private int larguraPainelCentral;

    // comando enviado pelo usuario
    private String comandoEnviado;

    // HashMap para guardar uma referência para os componentes de cada item
    private HashMap<String, JComponent> componentesDosItens;

    /**
     * Constrói a tela do jogo.
     * 
     * @param nomeJogo nome do jogo a ser exibido na tela.
     */
    public Tela(String nomeJogo) {
        // define a altura e a largura da janela
        larguraJanela = 800;
        alturaJanela = 700;

        // define a largura do painel central (usado para mostrar a imagem do ambiente)
        larguraPainelCentral = 650;

        // cria a margem que será usada nos componentes de texto
        margem = new Insets(10, 10, 10, 10);

        // criando a janela principal do jogo
        criarJanela();

        // criando o painel superior onde é exibido o nome do jogo
        criarPainelSuperior(nomeJogo);

        // criando o painel central que mostra a imagem do ambiente e os itens que o
        // jogador coletou
        criarPainelCentral();

        // criando painel inferior onde são exibidas as mensagens para o jogador e
        // obtidos os comandos digitados por ele
        criarPainelInferior();

        // empacota a janela (informa que ela está toda montada)
        janela.pack();

        // indica que a janela deve ser exibida no centro da tela
        janela.setLocationRelativeTo(null);

        // exibe a janela do jogo
        janela.setVisible(true);
    }

    /**
     * Cria a janela do jogo (a aplicação em si).
     */
    private void criarJanela() {
        // cria e define as caracteristas da janela da aplicação
        janela = new JFrame();
        // Usa o FlatLaf com tema escuro para deixar o interface mais chamativa
        FlatDarkLaf.setup();

        // define o tamanho da janela
        janela.setPreferredSize(new Dimension(larguraJanela, alturaJanela));
        // define o título da janela
        janela.setTitle("Trabalho Prático de GAC106-PPOO - DAC-ICET-UFLA)");
        // define o tipo de layout da janela
        janela.setLayout(new BorderLayout());
        // impede o usuário de alterar o tamanho da janela (descomente se preferir)
        // janela.setResizable(false);
        // indica que o programa deve ser finalizado ao fechar a janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Cria o painel superior que mostra o nome do jogo.
     * 
     * @param nomeJogo Nome do jogo a ser exibido
     */
    private void criarPainelSuperior(String nomeJogo) {
        // cria o painel do superior e define seu tamanho e layout
        JPanel painelSuperior = new JPanel();
        painelSuperior.setPreferredSize(new Dimension(larguraJanela, 50));

        // Cria e adiciona um rótulo com a identificação do jogo
        JTextArea tituloJogo = new JTextArea(nomeJogo);
        tituloJogo.setFont(new Font("Consolas", Font.BOLD, 32));
        tituloJogo.setMargin(margem);
        tituloJogo.setEditable(false);
        painelSuperior.add(tituloJogo);

        // adiciona o painel superior à janela
        janela.add(painelSuperior, BorderLayout.NORTH);
    }

    /**
     * Cria o painel central que mostra a imagem do ambiente e os itens que o
     * jogador pegou
     */
    private void criarPainelCentral() {
        // Cria o painel onde serão exibidos o ambiente e os itens
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BorderLayout());

        // criando o painel onde é exibida a imagem do ambiente
        criarPainelAmbiente(painelCentral);

        // criando o painel onde são exibidos os itens obtidos pelo jogador
        criarPainelItens(painelCentral);

        janela.add(painelCentral);
    }

    /**
     * Cria o painel que exibe a imagem do ambiente atual
     */
    private void criarPainelAmbiente(JPanel painelPai) {
        // cria o painel do ambiente
        painelAmbiente = new JPanel();
        painelAmbiente.setPreferredSize(new Dimension(larguraPainelCentral, 600));
        painelAmbiente.setBorder(BorderFactory.createTitledBorder("Ambiente atual"));

        // cria o rótulo que é usado para mostrar a imagem do ambiente e o adiciona no
        // painel.
        // Nesse momento ainda não há nenhuma imagem.
        rotuloImagemAmbiente = new JLabel();
        painelAmbiente.add(rotuloImagemAmbiente);

        // adiciona o painel do ambiente à janela
        painelPai.add(painelAmbiente, BorderLayout.CENTER);
    }

    /**
     * Cria o painel que exibe os itens que o jogador possui
     */
    private void criarPainelItens(JPanel painelPai) {
        // cria o painel de itens do jogador e o adiciona à janela
        painelItens = new JPanel();
        painelItens.setPreferredSize(new Dimension(larguraJanela - larguraPainelCentral, 500));
        painelItens.setBorder(BorderFactory.createTitledBorder("Jogador possui"));
        painelItens.setLayout(new BoxLayout(painelItens, BoxLayout.Y_AXIS));
        painelPai.add(painelItens, BorderLayout.EAST);

        // cria o hashMap usado para guardar os componentes de itens no painel
        componentesDosItens = new HashMap<>();
    }

    /**
     * Cria o painel inferior usado para exibir mensagens para o jogador e para
     * obter seus comandos
     */
    private void criarPainelInferior() {
        // cria o painel inferior
        JPanel painelInferior = new JPanel();
        painelInferior.setLayout(new BorderLayout());
        painelInferior.setPreferredSize(new Dimension(larguraJanela, 250));

        // cria o subpainel de mensagens e o adiciona ao painel inferior
        criarSubPainelMensagens(painelInferior);

        // cria o subpainel de entrada (comandos) e o adiciona ao painel inferior
        criarSubPainelEntrada(painelInferior);

        // adiciona o painel inferior à janela do jogo
        janela.add(painelInferior, BorderLayout.SOUTH);
    }

    /**
     * Cria o subpainel usado para exibir as mensagens do jogo
     * 
     * @param painelPai Painel onde o subpainel vai ser colocado.
     */
    private void criarSubPainelMensagens(JPanel painelPai) {
        // cria o subpainel de mensagens e define seu tamanho
        JPanel painelMensagens = new JPanel();
        painelMensagens.setLayout(new BorderLayout());

        // cria uma área de texto onde são exibidas as mensagens do jogo e define suas
        // características
        areaMensagens = new JTextArea();
        areaMensagens.setLineWrap(true); // tem quebra de linha
        areaMensagens.setWrapStyleWord(true); // quebra por palavra
        areaMensagens.setEditable(false); // impede edição do texto pelo usuário
        areaMensagens.setMargin(margem); // define uma margem ao redor do texto
        areaMensagens.setFont(new Font("Consolas", Font.BOLD, 16)); // define fonte e tamanho

        // Cria uma área de rolagem para que a área de texto tenha barra de rolagem, se
        // precisar
        JScrollPane areaMensagensScrollPane = new JScrollPane(areaMensagens);
        areaMensagensScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // adiciona a área de rolagem ao subpainel de mensagens
        painelMensagens.add(areaMensagensScrollPane, BorderLayout.CENTER);

        // adiciona o subpainel de mensagem ao painel passado por parâmetro
        painelPai.add(painelMensagens, BorderLayout.CENTER);
    }

    /**
     * Cria o subpainel usado para obter os comandos do jogador
     * 
     * @param painelPai Painel onde o subpainel vai ser colocado.
     */
    private void criarSubPainelEntrada(JPanel painelPai) {
        // cria o painel de entrada do jogador e define seu tamanho
        JPanel painelEntrada = new JPanel();
        painelEntrada.setPreferredSize(new Dimension(larguraJanela, 50));

        // cria um rótulo com o texto "Comando: " e o adiciona ao painel
        painelEntrada.add(new JLabel("Comando:"));

        // cria um campo de texto onde o usuário digitará os comandos
        campoEntrada = new JTextField();
        campoEntrada.setPreferredSize(new Dimension(600, 30));
        campoEntrada.setFont(new Font("Consolas", Font.BOLD, 16)); // define fonte e tamanho
        // adiciona o campo de texto ao painel
        painelEntrada.add(campoEntrada);

        // Cria um botão para enviar os comandos e o adiciona ao painel
        JButton botaoComando = new JButton("Enviar");
        painelEntrada.add(botaoComando);

        // adiciona o painel de entrada ao painel passado por parâmetro
        painelPai.add(painelEntrada, BorderLayout.SOUTH);

        // cria um objeto para tratar a ação do campo de texto e do botão,
        // ou seja, define o código que é executado quando o usuário clica no botão
        // 'Enviar"
        // ou quando aperta ENTER dentro da caixa de texto dos comandos.
        Action acaoEnviarComando = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comandoEnviado = campoEntrada.getText();
                campoEntrada.setText("");
            }
        };

        // liga a ação à caixa de texto de comandos
        campoEntrada.addActionListener(acaoEnviarComando);
        // liga a ação ao clique no botão "Enviar"
        botaoComando.addActionListener(acaoEnviarComando);
    }

    /**
     * Acrescenta uma nova mensagem para o jogador
     * 
     * @param mensagem Mensagem a ser exibida para o usuário.
     */
    @Override
    public void exibirMensagem(String mensagem) {
        // acrescenta a informação passada ao texto da área de mensagens
        areaMensagens.append(mensagem + "\n");

        // trata o redesenho da área de mensagens na janela
        repaintAreaMensagens();
    }

    /**
     * Limpa as mensagens exibidas para o jogador (não precisa ser usada no
     * terminal)
     */
    @Override
    public void limparMensagens() {
        // limpa a área de mensagens
        areaMensagens.setText("");

        // trata o redesenho da área de mensagens na janela
        repaintAreaMensagens();
    }

    /**
     * Redesenha a área de mensagens e posiciona o cursor ao final do texto
     */
    private void repaintAreaMensagens() {
        // força o redesenho da área de mensagens na janela
        areaMensagens.repaint();
        // posiciona o cursor ao final do texto
        areaMensagens.setCaretPosition(areaMensagens.getDocument().getLength());
    }

    /**
     * Obtém um comando do jogador.
     * 
     * O código abaixo tem o efeito da tela ficar aguardando o jogador digitar um
     * comando e apertar ENTER ou clicar em enviar.
     */
    @Override
    public String obterComando() {
        // enquanto o usuário não tiver digitado algum comando
        while (comandoEnviado == null) {
            try {
                // aguarda 250 milissegundos
                Thread.sleep(250);
            }
            catch (Exception e) {
            }
        }

        // Essa linha só é executada depois que o usuário digitou um comando.
        // Ele é então guardado em uma variável auxiliar para ser retornado.
        String comando = comandoEnviado;

        // E o atributo da classe volta para null para depois tratar o próximo comando.
        comandoEnviado = null;

        // O método retorna o comando digitado pelo usuário
        return comando;
    }

    /**
     * Obtém uma informação do usuário como String.
     * 
     * Obs.: não deve ser usado para comandos. No caso de comandos use
     * 'obterComando'.
     * 
     * @param instrucao Mensagem de instrução para o usuário (diz qual informação
     *                  está sendo solicitada)
     */
    @Override
    public String obterInformacao(String instrucao) {
        return JOptionPane.showInputDialog(instrucao);
    }

    /**
     * Informa a Interface do Usuário que o jogador mudou de ambiente.
     * 
     * Tem o efeito de mostrar na tela a imagem do novo ambiente atual.
     * 
     * @param ambiente Objeto do novo ambiente atual.
     */
    @Override
    public void ambienteAtualMudou(EntidadeGrafica ambiente) {
        // obtém a imagem do ambiente
        BufferedImage imagem = ambiente.getImagem();

        // trata o rótulo que exibe a imagem do item
        if (imagem != null) {
            // muda a imagem do ambiente atual (redimensionando para o tamanho padrão de
            // imagens de ambiente)
            rotuloImagemAmbiente.setIcon(new ImageIcon(redimensionarImagem(ambiente.getImagem(), true)));
            rotuloImagemAmbiente.setText(null);
        }
        else {
            // se a imagem não foi carregada informa isso na tela
            rotuloImagemAmbiente.setIcon(null);
            rotuloImagemAmbiente.setText("Imagem não foi carregada! Ambiente: " + ambiente.getNome());
        }

        // força o redesenho do painel que tem a imagem do ambiente
        painelAmbiente.revalidate();
        painelAmbiente.repaint();
    }

    /**
     * Informa a Interface do Usuário que o jogador pegou um item (ou similar).
     * 
     * Tem o efeito de mostrar o item na barra lateral do jogo.
     * 
     * @param item Objeto do item que o usuário pegou.
     */
    @Override
    public void jogadorPegouItem(EntidadeGrafica item) {
        // Obtém a quantidade desse item já existente no painel
        int quantidade = quantidadeDoItemNoPainel(item);

        // Se o item ainda não existia no painel de itens
        if (quantidade == 0) {
            // cria um rótulo com o nome do item
            JLabel rotuloNomeItem = new JLabel(item.getNome());
            // adiciona o rótulo no painel de itens
            adicionarComponenteDeItem(rotuloNomeItem, item.getNome());

            // obtém a imagem do item
            BufferedImage imagem = item.getImagem();

            // trata o rótulo que exibe a imagem do item
            JLabel rotuloImagemItem;
            if (imagem != null) {
                // cria um rótulo com a imagem do item (redimensionando para o tamanho padrão de
                // imagens de itens)
                rotuloImagemItem = new JLabel(new ImageIcon(redimensionarImagem(imagem, false)));
            }
            else {
                // se a imagem não foi carregada informa isso na tela
                rotuloImagemItem = new JLabel("Imagem não carregada!");
            }
            // adiciona o rótulo da imagem no painel de itens
            adicionarComponenteDeItem(rotuloImagemItem, item.getNome() + "_img");
        }
        else { // se o item já existia no painel de itens
            JLabel rotuloNomeItem = (JLabel) componentesDosItens.get(item.getNome());

            // se tinha apenas um item
            if (quantidade == 1) {
                rotuloNomeItem.setText(rotuloNomeItem.getText() + " (x2)");
            }
            else { // se tinha mais de um, então é necessário atualizar o número
                rotuloNomeItem.setText(rotuloNomeItem.getText().substring(0, rotuloNomeItem.getText().lastIndexOf("("))
                        + "(x" + (quantidade + 1) + ")");
            }
        }

        // força o redesenho do painel que tem a imagem do item
        painelItens.revalidate();
        painelItens.repaint();
    }

    /*
     * Retorna a quantidade de um determinado item já existente no painel de itens
     * 
     * @param item Objeto do item a ser verificado
     */
    private int quantidadeDoItemNoPainel(EntidadeGrafica item) {
        // Tenta obter o rótulo do item (existirá se o item já existir no painel de
        // itens)
        JComponent rotuloItem = componentesDosItens.get(item.getNome());

        // Se o item ainda não existia no painel de itens
        if (rotuloItem == null) {
            return 0;
        }
        else { // se o item já existia no painel de itens
            JLabel rotuloNomeItem = (JLabel) rotuloItem;
            // se o rótulo tem o mesmo nome do item é porque tem apenas um
            if (rotuloNomeItem.getText().equals(item.getNome())) {
                return 1;
            }
            else { // se o rótulo tem nome diferente, é porque existe mais de um
                int indiceAbreParenteses = rotuloNomeItem.getText().lastIndexOf("(");
                int indiceFechaParenteses = rotuloNomeItem.getText().lastIndexOf(")");
                return Integer
                        .parseInt(rotuloNomeItem.getText().substring(indiceAbreParenteses + 2, indiceFechaParenteses));
            }
        }
    }

    /**
     * Informa a Interface do Usuário que o jogador não tem mais um item (ou
     * similar).
     * 
     * Tem o efeito de não mostrar mais o item na barra lateral do jogo.
     * 
     * @param item Objeto do item que o usuário não tem mais.
     */
    @Override
    public void jogadorDescartouItem(EntidadeGrafica item) {
        // Obtém a quantidade desse item já existente no painel
        int quantidade = quantidadeDoItemNoPainel(item);

        // Se existia apenas 1 desse item no painel
        if (quantidade == 1) {
            // remove os componentes do item do painel de itens
            removerComponenteDeItem(item.getNome());
            removerComponenteDeItem(item.getNome() + "_img");
        }
        else { // Se existiam mais de 1 desse item

            JLabel rotuloNomeItem = (JLabel) componentesDosItens.get(item.getNome());
            // Se existiam 2 desse item no painel
            if (quantidade == 2) {
                rotuloNomeItem.setText(item.getNome());
            }
            else { // se existiam mais desse item
                rotuloNomeItem.setText(rotuloNomeItem.getText().substring(0, rotuloNomeItem.getText().lastIndexOf("("))
                        + "(x" + (quantidade - 1) + ")");
            }
        }

        // força o redesenho do painel que tem a imagem do item
        painelItens.revalidate();
        painelItens.repaint();
    }

    /**
     * Adiciona um componente de um item no painel de itens. Adiciona também em um
     * HashMap para que depois seja possível obter o item pelo nome.
     * 
     * @param componente Componente do item.
     * @param nome       Nome a ser utilizado para o componente.
     */
    private void adicionarComponenteDeItem(JComponent componente, String nome) {
        componente.setName(nome);
        componente.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        componentesDosItens.put(nome, componente);
        painelItens.add(componente);
    }

    /**
     * Remove um componente de um item do painel de itens. Remove também de um
     * HashMap usado para encontrar o componente.
     * 
     * @param nome Nome utilizado ao adicionar o componente.
     */
    private void removerComponenteDeItem(String nome) {
        painelItens.remove(componentesDosItens.get(nome));
        componentesDosItens.remove(nome);
    }

    /**
     * Redimensiona uma imagem passada em um tamanho padrão (grande ou pequeno).
     * 
     * Tamanho grande (600 x 450 pixels) é usado para ambientes e tamanho pequeno
     * (100 x 100 pixels) é usado para itens.
     * 
     * @param imagem Imagem que terá seu tamanho redimensionado.
     * @param grande Indica se é para redimensionar para tamanho grande (true) ou
     *               tamanho pequeno (false)
     */
    private Image redimensionarImagem(BufferedImage imagem, boolean grande) {
        // define o tamanho da imagem de acordo com o parâmetro 'grande'
        int largura = 600;
        int altura = 450;
        if (!grande) {
            largura = 50;
            altura = 50;
        }

        // redimenciona a imagem e a retorna
        return imagem.getScaledInstance(largura, altura, Image.SCALE_DEFAULT);
    }
}

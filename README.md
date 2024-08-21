[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/KbwUgE91)
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-718a45dd9cf7e7f842a935f5ebbe5719a5e09af4491e668f4dbf3b35d5cca122.svg)](https://classroom.github.com/online_ide?assignment_repo_id=15053635&assignment_repo_type=AssignmentRepo)

# Trabalho de GAC106-PPOO - Jogo Adventure

Veja as instruções do trabalho no documento disponibilizado no Campus Virtual.

As informações abaixo devem ser preenchidas na primeira entrega e depois devem ser alteradas (no que for necessário) na entrega final.

## Informações sobre o jogo

Nome do jogo:

> Cult of Moonlight

Integrantes do grupo:

> Felipe Mateus Maximiniano e Silva Ribeiro <br>
> Gabriel Silva Duarte <br>
> Harisson Alvarenga <br>
> Ramon Damasceno Nascimento <br>

Descrição breve sobre o tema jogo:

> O jogo é sobre um mercenário que foi contratado para descobrir o que ou quem está causando a onda de assassinatos na cidade Moonlight. Ele envolve batalhas e enigmas, possuindo armas, classes e itens diferentes. <br>

Quem é o jogador/personagem principal do jogo e qual é seu objetivo?

> O jogador (que foi criado inicialmente com o nome Radahn), é um mercenário cujo objetivo é coletar informações em diferentes locais, para que desvende o mistério e derrote o culpado. <br>

Quais são os tipos de itens e para que cada tipo é usado?

> Arma (Cajado, Adaga, Espada) : Usados para causar dano aos inimigos. <br>
> -> Cajado: Causa dano mágico. <br>
> -> Adaga: Causa dano perfurante. <br>
> -> Espada: Causa dano cortante. <br>
> Consumível (Chave, Poção ) : Itens que vão ser consumidos. Ex: Chave. <br>
> -> Poção: classe com atributos de recuperação de vida. <br>
> -> Chave: um consumível simples de uso único. <br>
> Armadura: Usados para defender o jogador. Possui defesa física e mágica. <br>
> Mao: "Arma" default usada na criação do personagem e quando desequipa a arma atual. <br>
> Carta: Usado para transmitir informações pertinentes ao jogador. <br>

Quais são os tipos de ambientes e quais são suas funções específicas?

> Ambiente: Locais para acesso do jogador, podendo ou não ter saídas bloqueadas. <br>
> AmbienteEscuto: Herda de Ambiente, com adição da funcionalidade que itens não podem ser vistos ou pegos sem a utilização de um Acessório com efeito de iluminar. <br>
> AmbienteToxico: Herda de Ambiente, com adição da funcionalidade que o jogador perde vida ao decorrer do tempo, quando não está utilizando um Acessório com efeito de proteger. <br>

(Na entrega final) Quais são os personagens (NPCs) do jogo e o que eles fazem?

> digite aqui

(Na entrega final) Quas são os inimigos do jogo e o que eles fazem?

> digite aqui

## Checklist dos Requisitos de jogabilidade

Para cada história abaixo escreva sim ou não, indincando se ela foi implementada **de acordo com os critérios de aceitação**.

- História 1:
- História 2:
- História 3:
- História 4:
- História 5:
- História 6:
- História 7:
- História 8:
- História 9:
- História 10:
- História 11:
- História 12:
- História 13:
- História 14:

## Checklist dos Requisitos de OO

Para cada requisito de OO abaixo escreva sim ou não, indicando se ele foi utilizado adequadamente:

- Modelagem de classes:
- Encapsulamento:
- Relacionamentos de composição, agregação e associação:
- Design de classes (acoplamento, coesão, design baseado em responsabilidade, etc.):
- Divisão de camadas:
- Polimorfismo:
- Tratamento de exceções:

Foi implementado algum Design Pattern?

- Em caso afirmativo, especifique qual padrão foi utilizado e explique como a utilização do padrão facilita a evolução do jogo.

> Nós utilizamos o padrão de projeto Abstract Factory. Este Padrão nos ajudou em criar os inimigos, pois temos fabrica de inimigos gerar inimigos que curam quando batem e que não curam. Esse padrão facilita caso seja necessário criar outrop tipos de inimigos pois criaremos uma nova fabrica para eles.

## Autoavaliação sobre a implementação realizada

Qual nota vocês dariam para o trabalho entregue? Justifique.

> Nós Dariamos a nota 30/35, pois nos esforçamos muito durante o percurso e evoluímos muito nossas habilidades como programadores. No entanto, entendemos que nosso código não está perfeito. Nosso strategy ficou meio forçado, pois por mais que ele funcione, utilizamos um canhão para matar um mosquito, e outro ponto é que não conseguimos adaptar o padrão de projeto Command com a leitura do arquivo, pois instaciar os objetos na classe jogo ficava um pouco mais abstrato, nesse sentido, utilizamos uma interface para fazer um instaceof, o que funcionou bem, porém o padrão Command era muito mais interessante e seguro. Por estes fatores e ainda por que compreendemos que podem ter algum pequeno erro que não percebemos, acreditamos que 30 é uma nota válida.

Qual foi a contribuição de cada integrante do grupo?

> Durante todo o projeto, trabalhos sempre juntos nos comunicando e realizando as tarefas em chamadas, não foi dividido o que exatamente um faria e sim todos trabalharam em todas as partes, seja fazendo código, revisando, comentando, ou auxiliando durante a programação.

# LPOO_Project
LPOO final project

O UML deste projecto pode ser encontrado dentro da pasta UML.

Quando se corre o programa será apresentado aos jogadores um menu principal, com 2 botões, um com o Start Game e outro com o Quit.

(Em fullscreen os botões não estão alinhados com a zona que recebe o input do rato. Clicar no botão com a janela em tamanho original e depois aumentar a janela)

<img src=https://github.com/Batosta/LPOO_Project/blob/finalRelease/mainMenu.png>

Carregando na opção de Start Game, os jogadores serão levados para o jogo em si. Neste encontrarão 2 personagens, o Fire Boy e a Water Girl. São controlados pelas teclas A, W, S, D e ainda pelas setas.

<img src=https://github.com/Batosta/LPOO_Project/blob/finalRelease/game.png>

O objectivo do jogo é cada personagem colecionar todos os diamantes correspondentes, e quando esta condição tiver sido cumprida poderão deslocar-se para as portas por onde passarão para o nível seguinte. Há 3 níveis.
Se durante o jogo, a tecla R for pressionada, os jogadores serão levados para um menu idêntico ao menu de quando perdem, com a diferença que quando perdem, a opção de Resume está bloqueada.

<img src=https://github.com/Batosta/LPOO_Project/blob/finalRelease/pauseMenu.png>


	Design Patterns
Utilizamos o design pattern STATE para o movimento das plataformas e para as suas sprites.
Também utilizamos este design pattern na classe ScreenManager, classe que gere os ecrãs (GameScreen or MainMenuScreen).

Também usamos o SINGLETON na ScreenManager pois só precisamos de um objeto desta classe e chamamo-la em várias partes do programa.

	TESTES
	
Devido a falta de organização não conseguimos implementar testes. Não usamos MVC logo foi difícil implementar testes sem alterar grande parte do código o que não foi possível por falta de tempo.

	NÍVEIS

Estruturamos o código de forma a que fosse possvel adicionar níveis com mapas novos apenas adicionando uma função addLevel(String mappath) que recebe como argumento o nome do ficheiro tmx (Tile Map XML) com o mapa. Usamos a aplicação TILED para os criar.
Cada mapa deverá ter as layers na sequência certa e pelo menos a porta azul, a porta vermelha (layer 6) e um objeto com o nome fireBoy e  outro com o nome waterGirl (layer 13).
 
 
Ao todo, em conjunto, terão sido gastas mais de 100 horas no desenvolvimento do jogo, e a participação foi igual.

Uma das conclusões que pudemos tirar deste trabalho foi que a implementação dos testes unitários é algo que deve, de facto, ser realizado durante a criação do jogo e não deixada para o fim. Consideramos também que foi bastante importante a realização do projecto, visto que foi a primeira vez que ambos trabalhámos com fsicas num jogo.
A principal dificuldade foi o início da criação do jogo, pois não sabíamos a forma mais correta de estruturar o código, o que levou ainda a alguns retrocessos a meio da escrita do código. No entanto conseguimos cumprir o objetivo que tinhamos previsto enquanto jogo.


Trabalho 1:
Implementa��o da Simula��o da Ordena��o de Eventos atrav�s de
Rel�gios L�gicos de Lamport usando Comunica��o Inter-Processos
por Troca de Mensagens
Prof. Adriano Fiorese
------------------


1. Caracteriza��o do Servi�o
O servi�o a ser desenvolvido trata-se de uma simula��o de ordena��o de eventos usando rel�gio l�gico de
Lamport [GC05]. Cada equipe (de no maximo 3) de estudantes devera simular a comunicac~ao entre n processos
onde n � o numero de caracteres de seu nome. Por exemplo: equipe Jos�, Jo�o, Maria = 13 processos, pois s�o
13 caracteres o somat�rio de caracteres do primeiro nome de todos os integrantes da equipe.
Ao final de 500 itera��es (for) cada processo deveria apresentar a sequ�ncia de mensagens recebidas de forma
ordenada.
Os eventos em quest�o que devem ser ordenados atrav�s de rel�gios l�gicos de Lamport s�o o envio e
recep��o de mensagens trocadas entre processos. Cada mensagem enviada deve enviar ao menos a identica��o 
do processo que a emitiu. O envio das mensagens em cada processo deve seguir uma caracter�stica aleat�ria
(nem toda intera��o deve enviar mensagem). Toda mensagem produzida deve ser enviada a todos os outros
processos pertencentes ao grupo.

2. Implementa��o
A implementa��o de tal servi�o deveria ser realizada utilizando-se comunica��o Inter-Processos por meio da
abstra��o de passagem de mensagem UDP. O modelo arquitetural a ser utilizado deveria ser o cliente/servidor.
Cada processo atuaria tanto como cliente quanto como servidor; ou seja, deveria estar apto a enviar e receber
novas mensagens. Como dica, caso desenvolvam os processos para executarem em apenas uma m�quina (caso
de teste) utilizem scripts para inicializar todos os processos. Por exemplo, um arquivo .bat que inicialize os
n processos com os argumentos adequados de forma que o trabalho de programa��o consista em desenvolver
apenas um processo.

3. Prazos
Entrega (via e-mail)dos execut�veis, do c�digo fonte do cliente e servidor devera ser dia 27/03/2012.


----------------------------------------------------
- Utilizado JAVA 7

Selecione todos os arquivos .bat dentro da pasta bat(consegui rodar apenas 15 simultaneos) e aperte enter.
Ir�o aparecer um arquivo pro[X].txt para cada processo com uma lista de todas as mensagens recebidas.
logo em seguida ir� aparecer um arquivo txt para cada processo com cada mensagem recebida

-obs: foram diminuido as iteracoes para 50.

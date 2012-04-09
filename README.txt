Trabalho 1:
Implementação da Simulação da Ordenação de Eventos através de
Relógios Lógicos de Lamport usando Comunicação Inter-Processos
por Troca de Mensagens
Prof. Adriano Fiorese
------------------


1. Caracterização do Serviço
O serviço a ser desenvolvido trata-se de uma simulação de ordenação de eventos usando relógio lógico de
Lamport [GC05]. Cada equipe (de no máximo 3) de estudantes deverá simular a comunicação entre n processos
onde n é o numero de caracteres de seu nome. Por exemplo: equipe José, João, Maria = 13 processos, pois são
13 caracteres o somatório de caracteres do primeiro nome de todos os integrantes da equipe.
Ao final de 500 iterações (for) cada processo deveria apresentar a sequência de mensagens recebidas de forma
ordenada.
Os eventos em questão que devem ser ordenados através de relógios lógicos de Lamport são o envio e
recepção de mensagens trocadas entre processos. Cada mensagem enviada deve enviar ao menos a identicação 
do processo que a emitiu. O envio das mensagens em cada processo deve seguir uma característica aleatória
(nem toda interação deve enviar mensagem). Toda mensagem produzida deve ser enviada a todos os outros
processos pertencentes ao grupo.

2. Implementação
A implementação de tal serviço deveria ser realizada utilizando-se comunicação Inter-Processos por meio da
abstração de passagem de mensagem UDP. O modelo arquitetural a ser utilizado deveria ser o cliente/servidor.
Cada processo atuaria tanto como cliente quanto como servidor; ou seja, deveria estar apto a enviar e receber
novas mensagens. Como dica, caso desenvolvam os processos para executarem em apenas uma máquina (caso
de teste) utilizem scripts para inicializar todos os processos. Por exemplo, um arquivo .bat que inicialize os
n processos com os argumentos adequados de forma que o trabalho de programação consista em desenvolver
apenas um processo.

3. Prazos
Entrega (via e-mail)dos executáveis, do código fonte do cliente e servidor deverá ser dia 27/03/2012.


----------------------------------------------------
- Utilizado JAVA 7

Selecione todos os arquivos .bat dentro da pasta bat(consegui rodar apenas 15 simultaneos) e aperte enter.
Irão aparecer um arquivo pro[X].txt para cada processo com uma lista de todas as mensagens recebidas.
logo em seguida irá aparecer um arquivo txt para cada processo com cada mensagem recebida

-obs: foram diminuido as iteracoes para 50.

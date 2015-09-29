# DecisionTree
Algoritmo de Indução de Árvore de Decisão Multi-Caminhos para Atributos de Valores Discretos

1. Entrada: DataSet no formato TXT com valores separados por vírgula (contém 06 exemplos no pacote "recursos").

2. Processamento: Divisão de Atributos Nominais, Binários e Contínuos utilizando o cálculo do Índice Gini.

3. Saída: Árvore de Decisão plotada no console.

Executa divisão binária para atributos, o que pode tornar desnecessário o cálculo do ganho de informação (Gain Ratio) (Tan, pg. 164, parágrafo 01).


TODO: 

1. Poda da Árvore.

2. Comparação de divisão binária com divisão multi-caminhos e seleção da melhor alternativa.


TODO #2:

1. Aplicação dos cálculos de Entropia e Ganho de Informação. Desnecessário, já que está sendo usado a técnica de divisão binária para atributos contínuos e discretos.


Considerações:

Utilizar Java 1.8 ou superior.

Construído no IntelliJ Idea 14.1.4.


Fonte:

"Introduction To Data Mining" Pang-Ning Tan, Michael Steinbach, Vipin Kumar {2006 - páginas 151 à 165}
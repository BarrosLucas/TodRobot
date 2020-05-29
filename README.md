# TodRobot
Tod

## Resumo
É um aplicativo simples desenvolvido para o projeto "Desenvolvimento de uma ferramenta computacional para apoio ao ensino de introdução aos algoritmos e programação".

## Uso
O aplicativo é capaz de analisar o código do usuário e, baseado nisso, gerar uma estatística precisa dos principais erros do usuário. Existem as seguintes funções no aplicativo:

- **Modo livre**<br>
O código implementado visa fazer o robô fazer qualquer coisa, sem nenhum objetivo estipulado pelo aplicativo e sim pelo próprio usuário.
- **Modo missão**<br>
O código implementado visa fazer o robô cumprir a missão estipulada pelo aplicativo.
- **Socializar**<br>
Compartilha o código por redes sociais.
- **Estatísticas**<br>
Mostra os principais erros do usuário durante o desenvolvimento de código para o robô.

## Comandos
O ambiente de desenvolvimento pode ser utilizado utilizando os seguintes comandos:

### Comandos de Ação
#### andarFrente(numeroDePassos)
---
Utilizado para fazer o robô andar alguns passos para frente.
<br>**Parâmetros**:<br>
**numeroDePassos:** a quantidade de passos que o robô dará para frente, sendo esse numero um valor inteiro. Cada passo possui aproximadamente 30 centímetros.

#### girarEsquerda(numeroDeGiro)
---
Utilizado para fazer o robô girar para a esquerda.
<br>**Parâmetros**:<br>
**numeroDeGiro:** o quanto o robô deve girar para a esquerda, sendo esse valor um inteiro. Cada unidade equivale a um giro de aproximadamente 45°.

#### girarDireita(numeroDeGiro)
---
Utilizado para fazer o robô girar para a direita.
<br>**Parâmetros**:<br>
**numeroDeGiro:** o quanto o robô deve girar para a direita, sendo esse valor um inteiro. Cada unidade equivale a um giro de aproximadamente 45°.

#### abrirMao()
---
Utilizado para fazer o robô abrir a mão.

#### fecharMao()
---
Utilizado para fazer o robô abrir a mão.

#### olharFrente()
---
Utilizado para fazer o robô olhar para frente.
<br>**Retorno**:<br>
Um valor equivalente à distancia do obstáculo em centímetros.

### Comandos de Condição
#### se(condicao){bloco}
---
Utilizado para executar o bloco de código mediante uma condição.

#### senao{bloco}
---
Utilizado para executar o bloco de código caso um bloco da condição "se" anterior não tenha sido executada.

#### enquanto(condicao){bloco}
---
Utilizado para executar continuamente o bloco de código enquanto uma condição prévia for atendida.

### Comandos Booleanos
#### vazioFrente()
---
Utilizado para saber se a frente está vazia (considerando uma frente de aproximadamente 30 centímetros).
<br>**Retorno**:<br>
Um valor booleano que informa se a frente está vazia.

#### obstaculoFrente()
---
Utilizado para saber se há algum obstáculo a frente (considerando uma frente de aproximadamente 30 centímetros).
<br>**Retorno**:<br>
Um valor booleano que informa se tem um obstáculo a frente.

#### maoAberta()
---
Utilizado para saber se a mão do robô está aberta.
<br>**Retorno**:<br>
Um valor booleano que informa se a mão está aberta.

#### maoFechada()
---
Utilizado para saber se a mão está fechada.
<br>**Retorno**:<br>
Um valor booleano que informa se a frente está fechada.

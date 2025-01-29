test-backEnd-Java (Sobre o UOL HOST)
===============
O UOL HOST oferece soluções em hospedagem de sites, loja virtual, loja de aplicativos, revenda de hospedagem, registro de domí­nios, e-mail marketing, cloud computing, entre outros, contando com a confiabilidade e a escalabilidade de uma moderna infraestrutura de TI, composta por uma das maiores redes de Data Centers da América Latina.

## Teste para BackEnd para UOL HOST
Montamos este teste para conhecer seus conhecimentos e habilidades em linguagem Java, programação orientada a objetos e boas práticas de programação.

O teste consiste em montar uma aplicação Java capaz de recuperar informações de um arquivo XML e de um arquivo JSON, persistir um cadastro em um banco de dados em memória ou em arquivo e listar os cadastros em uma interface simples.

## Proposta

O 'novo' sistema de cadastro de jogadores do UOL precisa de uma nova cara! Isso porque a área de lazer da empresa definiu que todo jogador deverá ter um codinome. A proposta foi um sucesso e muitos candidatos se inscreveram, por isso a área de lazer acabou restringindo os codinomes em duas listas distintas: "Os Vingadores" e "A Liga da Justiça".

Seu desafio é elaborar um sistema em Java capaz de:

1. Permitir o cadastro de jogadores de acordo com os codinomes contidos nos links de referência vingadores.json e liga_da_justica.xml
2. Apresentar um cadastro contendo nome, e-mail e telefone do jogador (sendo que nome e e-mail são obrigatórios)
3. Persistir a informação cadastrada em um banco de dados em memória, como HSQLDB ou arquivo
4. Obter, a qualquer momento, a lista de todos os jogadores cadastrados com seus respectivos codinomes e também a informação de qual lista o codinome foi extraído
5. Impedir a utilização de um mesmo codinome para diferentes usuários (a menos que o codinome seja para listas diferentes)
6. Incluir o codinome escolhido dentro das listas Os Vingadores ou A Liga da Justiça
7. Obrigatoriamente, ler a informação do codinome em arquivos na internet (links abaixo). Atenção: não vale guardar a informação do codinome localmente (em um arquivo, em uma classe, em um banco de dados etc.)

## Arquitetura de referência

![alt text](https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/arquitetura.png)

## Links dos arquivos de referência
https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml
https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json

### Casos de uso

+ Cadastro com sucesso:
	1. O usuário 'Felipe' cadastra seu nome, e-mail e telefone, e escolhe a lista vingadores.json
	2. O sistema recebe o cadastro e verifica se há codinomes disponíveis na lista vingadores.json
	3. O sistema encontra um codinome livre e o escolhe
	4. O sistema persiste nome, e-mail, telefone, codinome e arquivo de referência em um banco de dados em memória ou em um arquivo
	5. O sistema informa que o usuário foi cadastrado corretamente e mostra uma imagem de sucesso

+ Lista escolhida não tem codinomes disponíveis:
	1. O usuário 'João' cadastra seu nome, e-mail e telefone, e escolhe a lista liga_da_justica.xml
	2. O sistema recebe o cadastro e verifica se há codinomes disponíveis na lista liga_da_justica.xml
	3. O sistema não encontra um codinome livre
	4. O sistema informa que aquela lista não possui mais usuários disponíveis

+ Relatório de usuários cadastrados:
	1. O usuário 'Luís' clica em “Visualizar relatório de jogadores”
	2. O sistema consulta o banco de dados em memória ou em arquivo
	3. O sistema apresenta todos os usuários cadastrados. Cada linha tem as informações: nome, e-mail, telefone, codinome e arquivo referência

## Instruções

Não há certo ou errado. Queremos apenas saber mais sobre seus conhecimentos na linguagem Java, como uso de bibliotecas públicas, e também seu cuidado com o código fonte, levando em consideração clareza de ideias, organização de código, documentação e testes.

**Faça um clone deste projeto, crie um novo projeto no seu próprio GitHub e siga os seguintes passos:**

1. Faça um clone do projeto https://github.com/uolhost/test-backEnd-Java.git
2. Crie um novo projeto dentro do seu GitHub (https://github.com)
3. Desenvolva um sistema que atenda os casos de uso apresentados
4. Para montar seu sistema, leve em consideração a arquitetura de referência dentro da pasta referência
5. Criar uma interface em HTML que contenha um formulário para receber nome, e-mail e telefone
6. Criar uma interface em HTML que liste os jogadores cadastrados por nome, e-mail, telefone, codinome e lista de referência
7. Criar uma ou mais classes que faça(m) uma requisição HTTP para o arquivo referência “Liga da Justiça” em: https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml
8. Criar uma ou mais classes que faça(m) uma requisição HTTP para o arquivo referência “Os Vingadores” em: https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json
9. Criar uma ou mais classes que contenha(m) as regras para persistir e recuperar cadastros de jogadores
10. Documente como o projeto deve ser iniciado para que possamos rodar sua aplicação
11. Suba a sua proposta para o projeto que você criou no GitHub. Exemplo: https://github.com/seuNome
12. Envie-nos o link do GitHub do seu projeto para podermos realizar o download. Exemplo: https://github.com/seuNome/test-backEnd-Java.git
13. Aguarde o RH entrar em contato.

## Regras
1. Você poderá utilizar o Java em qualquer versão :)
2. Você poderá utilizar quaisquer frameworks da linguagem Java :)
3. Para persistir as informações, você poderá utilizar um banco de dados em memória gerenciado por você ou utilizar um banco, como HSQLDB.
4. Você também pode optar por gravar em arquivo.
5. Não vale utilizar o codinome de um mesmo arquivo mais de uma vez.
6. Detalhes como criação de testes unitários, ordenação da lista de cadastrados ou filtro da lista são opcionais. Mas, se você fizer iremos apreciar! =)

## O que apreciamos
* Organização;
* Simplicidade;
* Objetividade;
* Reúso de código;
* Testes unitários;
* Padronização de código;
* Padrões de projeto;

## Quem buscamos
Queremos uma pessoa que goste do que faz, trabalhe em equipe e tenha vontade de inovar, buscando sempre atualização e soluções inovadoras.

Se você se identificou, venha fazer parte do nosso time!

## Resolução

Conforme proposto pelo desafio, utilizei o Banco de Dados em memória H2 para persistir as informações dos jogadores e criei uma API para listagem e cadastro de jogadores, temos dois endpoint's para utilizar.

## Como Executar o Projeto

### Pré-requisitos
- **Java 21 ou superior**
- Maven

### Passos
1. Clone o repositório:

   ```bash
   git clone https://github.com/FelipeFranke5/test-backEnd-Java.git
   ```
2. Navegue até o diretório do projeto:

   ```bash
   # Linux
   cd test-backEnd-Java/test-backEnd-Java/test-backend-java
   ```

    ```bash
   # Windows
   cd test-backEnd-Java\test-backEnd-Java\test-backend-java
   ```
3. Compile e execute o projeto com Maven:

   ```bash
   mvn spring-boot:run
   ```
4. O front-end estará disponível para consumo através do arquivo **index.html**.

## Endpoint's

### Listagem de Jogadores ###

Requisição:

```HTTP
GET /api/jogador HTTP/1.1
```

Resposta:

```json
[
  {
    "nome": "Jogador 1",
    "email": "email16@gmail.com",
    "telefone": "N/A",
    "codinome": "Thor",
    "grupo": "Vingadores"
  },
  {
    "nome": "Jogador 2",
    "email": "email17@gmail.com",
    "telefone": "N/A",
    "codinome": "Mulher Maravilha",
    "grupo": "Liga da Justiça"
  },
  {
    "nome": "Jogador 3",
    "email": "email20@gmail.com",
    "telefone": "N/A",
    "codinome": "Aquaman",
    "grupo": "Liga da Justiça"
  },
  {
    "nome": "Jogador 4",
    "email": "email22@gmail.com",
    "telefone": "N/A",
    "codinome": "Batman",
    "grupo": "Liga da Justiça"
  },
  {
    "nome": "Jogador 5",
    "email": "email25@gmail.com",
    "telefone": "N/A",
    "codinome": "Superman",
    "grupo": "Liga da Justiça"
  },
  {
    "nome": "Jogador 6",
    "email": "email222@gmail.com",
    "telefone": "N/A",
    "codinome": "Flash",
    "grupo": "Liga da Justiça"
  },
  {
    "nome": "Jogador 7",
    "email": "email226@gmail.com",
    "telefone": "N/A",
    "codinome": "Lanterna Verde",
    "grupo": "Liga da Justiça"
  },
  {
    "nome": "Jogador 15",
    "email": "emailqualquer28@gmail.com",
    "telefone": "N/A",
    "codinome": "Visão",
    "grupo": "Vingadores"
  },
  {
    "nome": "Jogador 25",
    "email": "emailqualquer35@gmail.com",
    "telefone": "N/A",
    "codinome": "Feiticeira Escarlate",
    "grupo": "Vingadores"
  },
  {
    "nome": "Jogador 67",
    "email": "emailqualquer45@gmail.com",
    "telefone": "N/A",
    "codinome": "Capitão América",
    "grupo": "Vingadores"
  },
  {
    "nome": "Jogador 99",
    "email": "emailqualquer456@gmail.com",
    "telefone": "N/A",
    "codinome": "Pantera Negra",
    "grupo": "Vingadores"
  },
  {
    "nome": "Jogador 201",
    "email": "emailqualquer4567@gmail.com",
    "telefone": "N/A",
    "codinome": "Hulk",
    "grupo": "Vingadores"
  },
  {
    "nome": "Jogador 2011",
    "email": "emailqualquer45678@gmail.com",
    "telefone": "N/A",
    "codinome": "Homem de Ferro",
    "grupo": "Vingadores"
  }
]
```

### Criação de um jogador ###

Requisição:

Campos obrigatórios: nome (nome do jogador), email (email do jogador) e grupo (grupo 1 = Vingadores; grupo 2 = Liga da Justiça).

```HTTP
POST /api/jogador HTTP/1.1
Content-Type: application/json

{
    "nome": "Jogador 20112",
    "email": "emailqualquer456781@gmail.com",
    "grupo": 1
}
```

A resposta será um código HTTP 201 (Created) sem conteúdo, caso o cadastro tenha sido feito com sucesso.

### Alguns possíveis erros ###

Caso tente cadastrar um jogador e o grupo não tiver mais codinomes disponíveis, um erro será exibido e a resposta vai trazer um código HTTP 400 (Bad Request), conforme exemplo abaixo:

```json
{
  "status": 400,
  "data": "2025-01-27T00:49:27.8663556",
  "mensagem": "Todos os codinomes dos Vingadores estão em uso"
}
```

Se o grupo (1 ou 2) estiver ausente na requisição:

```json
{
  "status": 400,
  "data": "2025-01-27T01:10:05.3289192",
  "mensagem": "O grupo deve ser 1 (Liga da Justiça) ou 2 (Vingadores)"
}
```

Se o nome ou email estiverem ausentes na requisição:

```json
{
  "status": 400,
  "data": "2025-01-27T01:12:39.2904268",
  "mensagem": "O nome e e-mail são campos obrigatórios"
}
```

Se o E-mail já existir na base de dados:

```json
{
  "status": 400,
  "data": "2025-01-27T01:14:27.5142995",
  "mensagem": "Jogador com este E-mail já existe"
}
```

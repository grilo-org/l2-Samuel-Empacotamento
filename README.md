# 🧩 Desafio - Empacotamento

## 🛒 Contexto

Seu Manoel é dono de uma loja de jogos online e está buscando automatizar o processo de embalagem dos pedidos.  
Ele precisa de uma API que, ao receber uma lista de pedidos contendo produtos com suas respectivas dimensões, determine:

- Quais tamanhos de caixas devem ser utilizadas para cada pedido.
- Quais produtos devem ser colocados em cada caixa.

---

## 📦 Descrição do Problema

Desenvolver uma **API Web em Java com Spring Boot** capaz de receber, via JSON, múltiplos pedidos.  
Cada pedido conterá uma lista de produtos com dimensões em centímetros (altura, largura e comprimento).
A API deve processar os pedidos determinar a melhor forma de embalar os produtos.
**Tipos de caixa:**
- Caixa 1: 30 x 40 x 80
- Caixa 2: 80 x 50 x 40
- Caixa 3: 50 x 80 x 60

## 📥 Entrada e 📤 Saída de Dados

A entrada e saída de dados devem seguir os exemplos abaixo:

- [entrada.json](ioexamples/entrada.json)
- [saida.json](ioexamples/saida.json)


## ✅ Requisitos do Desafio
1. Criar microsserviço em Java + SpringBoot
2. Documentação Swagger
3. A aplicação deve ser facilmente executada com o Docker

### ✨ Opcionais
1. Autenticação de chamadas na API
2. Testes unitários

---

# 💻 Solução/Implementação
## 🤔 Considerações inciais
1. Tomei a liberdade de fazer pequenas alterações no formato de entrada e saída de dados. Geralmente não é bom que IDs de objetos sejam nomes/descrições, então fiz com que caixas e produtos tivessem tanto id quanto nome.
2. Fiz o processamento do empacotamento da forma como eu imagino ser uma boa escolha. Mas a melhor forma de empacotar **quem define é o cliente**, que tem suas próprias necessidades. Minha escolha é, para cada pedido, fazer o empacotamento do maior produto para o menor, e da menor caixa para a maior. E se possível, reaproveitar caixas que já estão sendo usadas.
3. Ainda sobre o empacotamento, é importante destacar que num projeto mais crítico, onde é necessária alta otimização do uso de caixas, seriam necessários processamentos e algoritmos muito mais complexos. Já no cenário desse projeto, que é mais simples, eu defino que um produto cabe numa caixa, desde que suas 3 dimensões caibam nas 3 dimensões da caixa, e desde que o volume do produto seja menor ou igual ao volume restante de uma caixa.
4. Sobre a autenticação. Geralmente a ideia seria disponibilizar um endpoint para realização de login, onde o usuário receberia um Token JWT e utilizaria este pra enviar as requests. Mas minha ideia pra esse projeto foi fazer algo apenas demonstrativo, então ao invés de um token, o usuário só precisa passar uma "senha" que seria **"secret"**. No caso seria "Bearer secret", como se secret fosse o token.

## 📈 Considerações de adições/mudanças/melhorias, para um projeto real de uma empresa
1. Adicionaria validação nos campos da request.
2. Faria uma autenticação mais robusta.
3. Implementaria tratamento de exceções, com uma estrutura que facilita adição e alteração de erros personalizados e mensagens intuitivas.
4. Adicionaria mais logs na aplicação, em pontos considerados importantes pela a equipe.
5. Utilizaria possível processamento paralelo em alguns dos passos do empacotamento, já que existem muitos loops.
6. Colocaria os tipos de caixas num arquivo ou banco de dados, para que seja possível adicionar ou remover tipos de caixas, e a aplicação lidaria com isso de forma dinâmica.
7. Faria 100% de cobertura de testes unitários.

---

# 🔥 Como rodar o projeto
1. Instale o Docker na sua máquina. Em PCs Windows e Mac, ao instar o Docker Desktop, já vem todo o necessário. Caso queira instalar apenas o Docker CLI no Linux ou Mac, tenha certeza de instalar o docker-compose.
2. Baixe o projeto.
3. Vá com o console/terminal até a pasta raiz do projeto.
4. Execute o comando **docker-compose up**. Isso deve fazer todos os downloads e instalação e rodar a aplicação imediatamente após.

## 🖱️ Como utilizar a aplicação
1. Após ter rodado o projeto, procure no console/terminal onde a aplicação está rodando uma mensagem "Swagger UI disponível em: http://localhost:8888/swagger-ui.html".
2. Clique no link exibido e isso irá abrir o Swagger, uma página de documentação da aplicação. Essa página também serve para utilizar os serviços da aplicação. Use o Swagger, ou Postman, ou outra ferramenta de sua preferência para utilizar a aplicação. Vou descrever como utilizar pelo Swagger, já que a ideia é depender de menos ferramentas o possível.
3. No Swagger você verá a aba **/packages**. Ela provavelmente está encolhida. Abra essa aba e você verá exemplos de solicitação e resposta da aplicação.
4. Ao clicar no botão **"Try it out"** você poderá editar o exemplo de solicitação da forma que preferir, desde que mantenha o padrão JSON. Adicione pedidos, produtos, de diferentes tamanhos.
5. Você pode clicar no botão **"Execute"**, mas nesse momento sua resposta será um erro. Agora, para que tudo dê certo, procure pelo botão **"Authorize"**, informe a senha, que é **"secret"**. Agora pode executar novamente a solicitação. Você deve receber uma resposta que representa os pedidos, com seus produtos representados dentro de caixas.

---

# 📺 YouTube

Ficou confuso com algo? Quer me ver rodando a aplicação e explicando o código? Então eu te convido a assistir meu vídeo no YouTube.

**Video estará disponível em breve**

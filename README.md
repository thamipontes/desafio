# Desafio Unicred


Para realizar esse desafio e ele poder ser executado em qualquer máquina, foi utilizado o Docker.
Caso você não possua o docker e nem docker-compose instalado em sua máquina, instale-os através do link abaixo:

- Windows:
    - https://docs.docker.com/docker-for-windows/install/
- Linux:
  - https://docs.docker.com/engine/install/ubuntu/
- Mac:
  - https://docs.docker.com/docker-for-mac/install/

Docker-compose:
- https://docs.docker.com/compose/install/

## Como rodar a aplicação

Após ter o Docker e o Docker-compose instalados, basta rodar o comando abaixo na raiz do projeto:

```bash
docker-compose up -d
```

Após isso, as aplicações estarão disponíveis
- Associado Swagger : http://localhost:8081/swagger-ui/index.html
- Boleto Swagger : http://localhost:8082/swagger-ui/index.html
- Pgadmin : http://localhost:5050/
- Sonarqube : http://localhost:9000/
- Rabbitmq : http://localhost:15672/

Obsevação: As aplicações podem demorar um pouco a subir, pois o docker precisa baixar as imagens e subir os containers.
E para o bom funcionamento das aplicações é necessário que todos estejam rodando.

## Como acessar as aplicações

# Pgadmin
Com o Pgadmin você poderá ver os dados que estão sendo salvos/deletados no banco de dados.
- Acesse o link http://localhost:5050/
- Faça o login com o usuário e senha abaixo:
  - Usuário: pgadmin4@pgadmin.org
  - Senha: admin
- Clique com o botão direito em Servers e selecione a opção Create > Server
- Na aba General, coloque o nome do servidor como "Desafio Unicred"
- Na aba Connection, coloque os dados abaixo:
  - Para o banco ASSOCIADO
    - Host name/address: host.docker.internal
    - Port: 5433
    - Maintenance database: associado
    - Username: postgres
    - Password: root
  - Para o banco BOLETO
    - Host name/address: host.docker.internal
    - Port: 5434
    - Maintenance database: boleto
    - Username: postgres
    - Password: root

# Sonarqube
Com o Sonarqube você poderá ver a qualidade do código e as métricas de cada projeto.
- Rode na raiz do projeto cada comando abaixo por vez:
```bash
./gradlew test
```

```bash
./gradlew jacocoTestReport
```

```bash
./gradlew sonar
```

- Acesse o link http://localhost:9000/
- Faça o login com o usuário e senha abaixo:
  - Usuário: admin
  - Senha: admin


# Rabbitmq
Com o Rabbitmq você poderá ver as mensagens que estão sendo enviadas para as filas.
- Acesse o link http://localhost:15672/
- Faça o login com o usuário e senha abaixo:
  - Usuário: root
  - Senha: rabbitmq

# Associado
Para testar o microsserviço de associado, basta acessar o link abaixo:
- http://localhost:8081/swagger-ui/index.html
- Clique no endpoint /associados
- Clique no botão Try it out
- Preencha os dados e clique em Execute
- Após isso você verá o resultado da requisição

Observações do associado:
- O associado só poderá ser cadastrado se o documnento CPF/CPNJ for válido
- O associado só poderá ser cadastrado se não houver outro associado com o mesmo CPF/CPNJ
- O campo tipoPessoa é um ENUM da qual aceita apenas os valores PF e PJ
- O associado só poderá ser deletado caso exista boleto em seu nome e não esteja pedente de pagamento 
- Exemplo de Body da requisição:
  - {
    "documento": "91468816039",
    "tipoPessoa": "PF",
    "nome":"Teste"
    }
  
# Boleto
Para testar o microsserviço de boleto, basta acessar o link abaixo:
- http://localhost:8082/swagger-ui/index.html
- Clique no endpoint /boletos
- Clique no botão Try it out
- Preencha os dados e clique em Execute
- Após isso você verá o resultado da requisição

Observações do boleto:
- O boleto só poderá ser cadastrado se o associado já existir, portanto crie um associado primeiro antes de testar a criação do boleto
- O vencimento não pode ser menor que a data atual
- O documentoPagador deve ser válido
- Ao fazer o pagamento do boleto, esse boleto vai para uma fila onde será processado e criado um arquivo com os dados do boleto. Para ver o arquivo, veja o tópico Arquivo abaixo.
- Exemplo de Body da requisição para criação do boleto:
  - {
    "valor": 1010.04,
    "vencimento": "15-09-2023",
    "documentoPagador": "91468816039",
    "nomePagador": "Teste Boleto 2"
    }
- Exemplo de Body da requisição para pagamento do boleto:
  - {
    "valor": 1010.04,
    "documentoPagador": "91468816039",
    "id": "ihouWhXkSKCUuo"
    }

# Arquivo
Para ver o arquivo gerado após um pagamento do boleto, você precisará fazer alguns passo, pois o arquivo será criado dentro do container do Docker.
Você precisará descobrir o ID do container do arquivo, para isso, rode o comando abaixo:
```bash
docker ps
```
Após isso, copie o ID do container que terá a imagem desafio-spring-boot-arquivo e rode o comando abaixo:
```bash
docker cp <id_container>:/arquivos/arquivosgerados ~/Downloads
```
Após isso, o arquivo será copiado para a pasta Downloads do seu computador.
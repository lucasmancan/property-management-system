# Sistema de gestão de patrimônio (PSM)

O PSM (Property Management System) tem a intuíto de ajudar as pessoas na gerência de seus patrimõnios, sejam eles de qualquer fonte ou forma.


```
Give examples
```

### Arquitetura da Solução

O PSM se baseia em um serviço construído segundo o modelo arquitetural REST que possui recursos para gerenciar patrimônios, 
marcas/categorias e usuários de acesso. Sendo que essas funcionalidades no momento estão unidas em uma única aplicação, 
onde o acesso aos recursos são controlados rigorosamente através de autenticação e compartilhamento de chaves entre 
o cliente (usuário) e o servidor (aplicação).

Apesar de ser sum sistema único, cada usuário tem sua conta e acesso exclusivo as informações que insere na plataforma, sendo assim, 
as informações de marcas ou propriedades são sigilosas para os demais usuários.

## Fluxo de aplicação

A API recebe as solicitações dos clientes e se comunica com o banco de dados PostgreSQL, realizando tanto operações de CRUD (create, read, update, delete) como
 comunicação com o usuário através do envio de e-mail. 
 
Essas requisições dos cliente são autenticadas através da passagem de um Bearer 
 token que por sua vez possui data de expiração e solidos padrões de criptografia.
 
O compartilhamento de informações dentro e fora da aplicação é devidamente segmentado e 
filtrado para informações desnecessárias ou até sigilosas acabem chegando até clientes. 
Para isso, foi utilizado o padrão DTO para criação das entidades de resposta das solicitações dos clientes, 
conferindo segurança e escalabilidade para a solução através do isolamento da camada de API da camada de persistência.

### Tecnologias

** Java 11
** Spring Boot v 2.2.6
*** Spring Data
*** Spring Security
*** Spring Mail
** PostgreSQL
** Swagger
** Mockito
** Docker/Docker-Compose
** JWT

### Testes

Para garantir o funcionamento da aplicação foi realizado no primeiro momento um fluxo de testes manuais, 
mas também testes de integração nos endpoints e testes unitários na camada de serviço estão sendo elaborados, 
o que irá garantir escalabilidade para as novas versões da aplicação. 

#### Fluxo de testes

** Criação de conta
** Autenticação
** CRUD Marcas/categorias
** CRUD Patrimõnios
** Edição de informações pessoais do usuário.
** Recuperação de senha.


## Como iniciar o projeto na minha máquina?

### 1 
É necessário clonar ou baixar o projeto.zip

### 2 
Executar o comando:
```
docker-compose -f stack.yml up
```

ps: lembre-se de conferir se possuí o docker instalado e rodando.

Assim o banco de dados Postgress vai estar instalado e pronto pra uso.

### 3 

Acessar a linha de comando ou alguma abstração 
e executar o script init.sql

Sendo assim, as tabelas estaram criadas.

### 4

Configurar uma conta de e-mail (e-mail e senha) para poder conferir as funcionalidades de recuperação de conta.


Com esses passos realizados a aplicação deve iniciar normalmente, caso contrário não hesite em me acionar que veremos isso juntos, ok?

### Documentação

Todos os Endpoints atualmente construídos foram mapeados através da ferramente Swagger e 
estão disponíveis para consulta ao acessar {host:port}/swagger-ui.html#/ 
onde é possível conferir a nomenclatura dos endpoint propriedades e parâmetros.


## Autor

* **Lucas Frederico Mançan** - *Initial work* - [PurpleBooth](lucasfmancan@gmail.com)



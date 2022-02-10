# fenix-veiculos

_version 0.9.0_

Projeto da faculdade para gestão e listagem de veículos (somente carros :red_car:). 

Um projeto desenvolvido com:

- Spring Boot
- Spring Data JPA
- MySQL/MariaDB
- Flyway (Migration)
- Lombok
- AngularJS
- Angular Material
- OpenAPI && Swagger-ui (Docs)

*__OBS__: Imagens do sistema localizadas no final em Anexos.*

## Funcionalidades

- O sistema permite o cadastro de Marcas, Carros e Usuários.

- Autenticação JWT com Nome de usuário ou E-mail e Senha.

- Recuperação de senha via e-mail.

- Upload de imagens dos carros e marcas (salvos localmente por enquanto).

- Ativar/Desativar marcas (incluindo todos os carros vinculados) ou carros, podendo exluir permanentemente a marca ou o carro.

- Visualização detalhada do carro.

## Dev

#### Config

1. Crie um arquivo `application.yml` e copie tudo do arquivo `application-example.yml`.

2. Para envio de e-mail altere as propriedades:
    ```
    spring:
        mail:
            password: youpass
            username: youmail@gmail.com
            host: smtp.gmail.com
            port: 587
            from: frommail@gmail.com
    ```

3. Para conexão com banco de dados altere as propriedades: 
    ```
    datasource:
      url: "jdbc:mysql://localhost:3306/fenix_veiculos"
      username: fenixveiculos
      password: 12345
    flyway:
      url: jdbc:mysql://localhost:3306/fenix_veiculos
      user: fenixveiculos
      password: 12345
    ```

4. Defina a base url em:
    ```
    server:
      base-url: http://localhost:8080
    ```

5. Defina o diretório de upload em:
    ```
    file:
      local: 
        upload-dir: /home/myuser/fenix-veiculos/uploads
    ```

6. Defina a secret do JWT em:
    ```
    jwt:
      secret: mySecret
    ```


#### Install && Run

1. Via Terminal: Execute `mvn clean install` na raíz do projeto para fazer o build. Após isso use uma das seguintes alternativas para executar:

- Via Terminal com Maven: Execute `mvn spring-boot:run` na raíz do projeto.

- Via Terminal com JAVA JAR: Execute `java -jar target/fenix-veiculos-{version}-SNAPSHOT.jar` na raíz do projeto. Substitua `{version}` pela [versão atual do SNAPSHOT](https://github.com/Hetso/fenix-veiculos#fenix-veiculos) ([versão no topo do documento](https://github.com/Hetso/fenix-veiculos#fenix-veiculos) ou no [pom.xml](https://github.com/Hetso/fenix-veiculos/blob/master/pom.xml))

#### Docs

Documentação da API gerada com OpenAPI e Swagger-ui, acesse {your-app-url}/swagger-ui.html.

## Anexos

- Página inicial
![home page](https://user-images.githubusercontent.com/55853339/153433754-95593127-7f4a-4432-a711-229dfa88cb55.png)

- Visualização do carro
![car view](https://user-images.githubusercontent.com/55853339/153433783-448f1798-d5d6-4bf6-b992-8340714e1ee6.png)

- Visualização do carro 2
![car view 2](https://user-images.githubusercontent.com/55853339/153433813-e66a0a2b-9026-470d-bfd8-5df5d8a5410e.png)

- Painel administrativo - Marcas
![admin brands list](https://user-images.githubusercontent.com/55853339/153434051-3f1f452f-7317-4a61-8e59-975b392188db.png)

- Painel administrativo - Carros
![admin cars list](https://user-images.githubusercontent.com/55853339/153434156-e3dc06e8-7d84-401e-90f7-efbaf814252a.png)

- Painel administrativo - Editar carro
![admin edit car](https://user-images.githubusercontent.com/55853339/153434213-a3ac0201-e19c-43b6-81ba-908e33ca8ee4.png)

- Painel administrativo - Editar marca
![admin edit brand](https://user-images.githubusercontent.com/55853339/153434249-668ec42b-ad25-45d8-908d-15c0f5737e90.png)

- Login
![login](https://user-images.githubusercontent.com/55853339/153434734-e60a8ac2-ed72-40e4-bc72-fd7171fb003c.png)
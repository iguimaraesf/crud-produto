# Projeto: cadastro de produtos

Este projeto usa o framework reativo Quarkus. O propósito é armazenar em núvem o cadastro de produtos de forma moderna e performática.
## Running the application in dev mode

Para executar em desenvolvimento:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

O empacotamento deve ser assim:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

Para empacotar em modo nativo: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/produto-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Os passos para o desenvolvimento do projeto

[x] CRUD de produtos com chamadas que bloqueiam durante a gravação e leitura de dados (imperativo).

[x] O mesmo CRUD, mas sem bloquear, realizando as chamadas em outra thread (reativo).

[x] CRUD reativo e usando DTO como parâmetros na camada web.

[x] Tratamento de exceções com ExceptionMapper.

[ ] Uso do padrão Repository ao invés do Active Record.

[ ] Testes unitários.

[ ] Validações.

[ ] Chamada da API que verifica preços online.

[ ] Documentação de acordo com a OpenAPI.

[ ] Monitoramento.

[ ] Fazer as implementações caso o comando falhe (onFailure).


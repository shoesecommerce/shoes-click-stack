# SHOES CLICK - STACK

Projetos conceito para utilização de Serviço Rest com Spring Boot

##Projetos:
* **api--customer**
* **api--prodcut**
* **api--product**
* **service--notification**
* **service--payment**

## Iniciando o Projeto

Os projetos contém alguns exemplos de serviço REST. Os projetos já estão configurados em modo standalone

### Pré requisitos

```
Java 17 : ([https://www.oracle.com/br/java/technologies/javase/jdk17-archive-downloads.html]);

Spring Boot na versão 3.1.9:  (https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-dependencies/3.1.9);

Maven 3.9.5: (https://dlcdn.apache.org/maven/maven-3/3.9.5/binaries/apache-maven-3.9.5-bin.tar.gz)

Eclipse ou Intellij

Docker: Para Rodar o arquivo docker-compose (Contido no projeto principal) que contém a imagem do mysql para facilitar a integração

Postman para Testes : (https://www.postman.com/downloads/)
```

## Diagrama C4 com o fluxo proposto
```
SHOES-CLICK- DIAGRAMA C4.drawio
```


## Arquivo openapi para gerar o mock server dos serviços de pagamento:
   IMPORTANTE: Ao gerar o mock, defina as urls dos serviços no 
   arquivo application.yaml do projeto service--payment

```
apidocs-mock.yaml
```

## Rodando local

Inicie com a classe Application.java


## Rodando com postman

Importe o arquivo abaixo e execute os testes:

```
 Shoes.click.postman_collection.json
```

## Autores

* **Clayton Morais de Oliveira**
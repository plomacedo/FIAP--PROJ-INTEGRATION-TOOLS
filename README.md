# MBA Fullstack FIAP -  Integration Tools 

### 👩🏻‍🎓👨🏻‍🎓 Turma 1SCJR

+ RM346614 - Ebertt Costa dos Santos 
+ RM346139 - Juliana Mota Carneiro 
+ RM347401 - Pamela Lais Oliveira Macedo 
+ RM346573 - Rafael Luiz Ross de Moura 
+ RM346746 - Vitor Souza Alves 

### 🔧 Instalação
Ferramentas necessárias:
+ PostgreSQL: https://www.postgresql.org/download/
+ RabbitMQ: https://www.cloudamqp.com/
+ SpringBoot: https://spring.io/projects/spring-boot
+ Maven: https://maven.apache.org/
+ JPA: https://spring.io/guides/gs/accessing-data-jpa/

#### Drone Application
Microservice destinado ao Front do projeto aonde são inseridos os dados em um form Thymeleaf e persistidos em um database PostgreSQL.
Para iniciar a aplicação, importe o repositório Drone Application a sua IDE. Crie um database PostgreSQL, abra o arquivo application.properties
e insira a url, username e password referente ao database criado. 
```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

Uma vez configuradas as informações necessárias para a persistência dos valores, inicie a aplicação, acesse http://localhost:8080/ e insira os dados do drone

<img src="https://user-images.githubusercontent.com/114959652/205184917-8009041b-0cd4-4f08-a571-bfdedd64debf.png"  width="30%" height="30%">

Uma vez salvo os dados do drone, uma tela de confirmação exibindo as informações salvas será exibida.

<img src="https://user-images.githubusercontent.com/114959652/205185029-f4543815-f539-434f-bcc2-0b95d08df60d.png"  width="20%" height="20%">

É possível adicionar quantos drones forem necessários. Também é viavel inserir informações de um mesmo drone mais de uma vez.

<img src="https://user-images.githubusercontent.com/114959652/205185629-37967e1f-1fbb-44af-a78a-b5f0370cfcd0.png"  width="30%" height="30%">

#### Drone Producer
Microservice destinado ao Consumo dos dados inseridos no front, Job Scheduler e envio para a fila do RabbitMQ.
Crie uma nova instancia RabbitMQ. Insira a o url/password no campo addresses, e nomeie a fila no campo fiap.   
```
spring:
  rabbitmq:
    addresses: 
    
queue:
  fiap: 
  
server:
  port: 8081
uri: http://localhost:8080/externalAccess
```
Configure os valores desejados para o Scheduler
Acesse br.fiap.integrations.droneproducer.entities.ScheduledJob.PlayerService
```
public void runTimer() {
        final TimeDetails info = new TimeDetails();
        info.setTotalFireCount(6);
        info.setRemainingFireCount(info.getTotalFireCount());
        info.setRepeatIntervalMs(10000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My callback data");

        scheduler.schedule( ScheduledJob.class, info);
    }



# MBA Fullstack FIAP -  Integration Tools 

### 👩🏻‍🎓👨🏻‍🎓 Turma 1SCJR

+ RM346614 - Ebertt Costa dos Santos 
+ RM346139 - Juliana Mota Carneiro 
+ RM347401 - Pamela Lais Oliveira Macedo 
+ RM346573 - Rafael Luiz Ross de Moura 
+ RM346746 - Vitor Souza Alves 

### 🔧 Instalação
Ferramentas utilizadas:
+ PostgreSQL: https://www.postgresql.org/download/
+ RabbitMQ: https://www.cloudamqp.com/
+ SpringBoot: https://spring.io/projects/spring-boot
+ Maven: https://maven.apache.org/
+ JPA: https://spring.io/guides/gs/accessing-data-jpa/
+ Postman: https://www.postman.com/
+ Quartz: https://docs.spring.io/spring-boot/docs/2.0.0.M3/reference/html/boot-features-quartz.html

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
Crie uma nova instancia RabbitMQ. No arquivo application.yml, insira a o url/password no campo addresses, e nomeie a fila no campo fiap.   
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
Acesse br.fiap.integrations.droneproducer.services.PlayerService e insira os valores desejados para a configuração do scheduler:
+ setTotalFireCount: Quantidade de vezes que o job será executado. Para rodar para sempre, envie o valor -1.
+ serRemainingFireCount: Quantidade de vezes restantes para executar o job.
+ setRepeatIntervalMs: Intervalo de tempo entre cada repetição.
+ setInitialOffsetMs: Período de espera entre o play e a primeira repetição do job.

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
```

A lista de comando que deve ser executada dentro de cada iteração do scheduled job consta dentro de br.fiap.integrations.droneproducer.entities.ScheduledJob. Neste ponto solicitamos a leitura dos dados do microservice DroneApplication através de um Get Request (uri descrita no arquivo properties) de acordo com as repetições e periodo de tempo configurados anteriormente, assim como o envio para a fila do RabbitMQ: 

```
     @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TimeDetails info = (TimeDetails) jobDataMap.get( ScheduledJob.class.getSimpleName());
        sender.send( getDroneData());
        LOG.info("Remaining fire count is '{}'", info.getRemainingFireCount());
    }
    
        public String getDroneData(){
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }
    
```
Para disparar o início das repetições, inicialize o microservice e através do Postman envie um POST REQUEST - HTTP://localhost:8081/api/controller/main implementado no JobController:

```
package br.fiap.integrations.droneproducer.controller;

...

@RestController
@RequestMapping("/api/controller")
public class JobController {
...
    @PostMapping("/main")
    public void runMain(){
        service.runTimer();
    }
...
}
```

#### Drone Consumer
Microservice responsável por consumir as mensagens enviadas para a fila do RabbitMQ, analisar os dados recebidos e enviar um email caso o drone esteja fora das condições de segurança. Para a execução do microservice, tenha em mãos uma conta gmail. Acesse > Gerenciar sua conta Google > Segurança > Como fazer Login no Google > Senhas de app > adicione o DroneConsumer. Copie a senha genérica gerada pela google.

No aquivo application.properties, insira a url e nome das filas anteriormente inseridos no DroneProducer. 
Nos campos spring.mail.username e spring.mail.password, insira o email e a senha genérica que acabamos de configurar no Google. Esta será a conta que irá fazer o envio das mensagens de alerta da aplicação 
Nos campos spring.datasource.url e spring.datasource.password, insira a url/ senha do database PostgreSQL que irá persistir as mensagens de email.
No campo email.To, insira o endereço de email que deverá receber os alertas.

```
spring.rabbitmq.addresses= 
spring.rabbitmq.queue= 

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username= 
spring.mail.password= 
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

spring.datasource.url= 
spring.datasource.username=postgres
spring.datasource.password= 
spring.jpa.hibernate.ddl-auto=update

email.to =  
server.port=8082
```

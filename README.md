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
+ Quartz: https://docs.spring.io/spring-boot/docs/2.0.0.M3/reference/html/boot-features-quartz.html

Todos os microserviços são executados automaticamente assim que inicializados. 

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

Todas as informações do drone são persistidos atravez de JPA/PostreSQL

<img src="https://user-images.githubusercontent.com/114959652/205469846-07b5bce9-a0b0-4510-967f-d3e05c5ec1dd.png"  width="40%" height="40%">


Para isso, o projeto é composto de uma entidade Drone e seus respectivos Service e Repository. Através do controller, os dados inseridos através do form thymeleaf são conectados com a base de dados. 

<img src="https://user-images.githubusercontent.com/114959652/205469867-c20b1ab4-1036-4a64-850b-115bf559640d.png"  width="30%" height="30%">

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
Para configurar os valores desejados do Scheduler acesse  a classe PlayerService e insira:
+ setTotalFireCount: Quantidade de vezes que o job será executado.
+ setRepeatIntervalMs: Intervalo de tempo entre cada repetição.
+ setInitialOffsetMs: Período de espera entre o play e a primeira repetição do job.

```
public void runTimer() {
        final TimeDetails info = new TimeDetails();
        info.setTotalFireCount(1000);
        info.setRepeatIntervalMs(10000);
        info.setInitialOffsetMs(1000);

        scheduler.schedule( ScheduledJob.class, info);
    }
```

A lista de comando que deve ser executada dentro de cada iteração do scheduled job consta dentro da classe ScheduledJob. Neste ponto solicitamos a captura dos dados do microservice DronevApplication, e de acordo com as repetições e periodo de tempo configurados no PlayerService, ocorre o envio dos dados para a fila do RabbitMQ: 

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
Para disparar o início das repetições, apenas inicialize o microservice.

#### Drone Consumer
Microservice responsável por consumir as mensagens da fila do RabbitMQ, analise dos dados recebidos e envio programado doemail constando as informações dos dronsefora das condições de segurança. 

Para a execução do microservice, tenha em mãos uma conta gmail. Acesse > Gerenciar sua conta Google > Segurança > Como fazer Login no Google > Senhas de app > adicione o DroneConsumer. Copie a senha genérica gerada pela google.

<img src="https://user-images.githubusercontent.com/114959652/205470056-93e4b1cc-b71a-47a3-a92d-e72a3de78db7.png"  width="40%" height="40%"><img src="https://user-images.githubusercontent.com/114959652/205470034-692a035a-08fa-4a93-ada0-8f5b30f26de9.png"  width="40%" height="40%">


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

Ao iniciar a aplicação, o listener do RabbitMQ começará a escutar sem parar todas as mensagens enviadas para a fila. Assim que recebida uma mensagem, a aplicação faz a deserialização do json e a análise das condições de cada drone. 
```
package br.fiap.integrations.droneconsumerrabbit.consumer;
...

@Component
public class QueueConsumer {
...
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload String fileBody) {
        JSONObject mqMessage = Utils.messageConverter(fileBody);
        List<DroneRisk> riskList = DroneRiskService.checkDrones(mqMessage);

        for (DroneRisk drone: riskList) {
                droneRiskService.save(drone);
        }
    }
}
```

Caso o Drone esteja exposto a uma temperatura maior igual a 35, menor igual a 0, ou a Umidade <= 15%, o Drone será imediatamente persistido na tabela "TB_DRONE_RISK".  Para isso, a aplicação consta da entidade DroneRisk, assim como seu service (aonde as regras de segurança são validadas e Repository (JPA). 

```
package br.fiap.integrations.droneconsumerrabbit.services;
...
@Service
public class DroneRiskService {
...
  public static List<DroneRisk> checkDrones(JSONObject my_obj) {
        JSONObject drones = my_obj.getJSONObject( "drones" );
        JSONArray arrDrone = drones.getJSONArray( "drone" );

        List<DroneRisk> riskDronesList = new ArrayList<>();

        for (int i = 0; i < arrDrone.length(); i++) {
            JSONObject drone = arrDrone.getJSONObject( i );

            if ((drone.getInt( "temperature" ) >= 35) || (drone.getInt( "temperature" ) <= 0) || (drone.getDouble( "humidity" ) < 15)) {
                System.out.println(drone);

                DroneRisk droneRisk = new DroneRisk();
                droneRisk.setId( UUID.fromString( drone.getString( "id" ) ) );
                droneRisk.setDroneId( drone.getInt( "droneId" ) );
                droneRisk.setTemperature( drone.getInt( "temperature" ) );
                droneRisk.setLatitude( drone.getDouble( "latitude" ) );
                droneRisk.setLongitude( drone.getDouble( "longitude" ) );
                droneRisk.setHumidity( drone.getDouble( "humidity" ) );
                droneRisk.setTracker( drone.getBoolean( "tracker" ) );

                riskDronesList.add(droneRisk);
            }
        }
        return riskDronesList;
    }
```  
Para o serviço de envio de email, foi desenvolvido um outro JobScheduler (Quartz) que é iniciado junto a aplicação. O Job Email Scheduler é responsável por verificar a lista de drones em risco, e enviar um email de alerta para o email cadastrado no properties a cada 1 minuto. 

```
package br.fiap.integrations.droneconsumerrabbit.services;
...
@Service
public class EmailService {
    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail( LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);
            emailModel.setStatusEmail( StatusEmail.SENT);
        } catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }
        finally {
            return emailRepository.save(emailModel);
```            
Mensagem recebida via email:

<img src="https://user-images.githubusercontent.com/114959652/205470504-4a5f40a3-444f-4982-a0ea-d24875e6cb2f.png"  width="100%" height="100%">
<img src="https://user-images.githubusercontent.com/114959652/205470563-6766962b-a2de-4b40-9057-2add05749860.png"  width="100%" height="100%">

Para controle da aplicação, todos os emails também estão persistidos no postgreSQL através da tabela TB_EMAIL. Para isso a aplicação consta com a entidade EmailModel, assim como seu respectivo service e Repository (JPA). 

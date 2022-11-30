# MBA Fullstack FIAP -  Integration Tools 

### Turma 1SCJR
+ RM346614 - Ebertt Costa dos Santos 
+ RM346139 - Juliana Mota Carneiro 
+ RM347401 - Pamela Lais Oliveira Macedo 
+ RM346573 - Rafael Luiz Ross de Moura 
+ RM346746 - Vitor Souza Alves 

### 🔧 Instalação
<br>Ferramentas necessárias:<br>
<br>PostgreSQL: https://www.postgresql.org/download/</br>
RabbitMQ: https://www.cloudamqp.com/

#### Drone Application
<br>Aplicação destinada ao Front do projeto aonde são inseridos e persistidos os dados do drone. </br>
Para iniciar a aplicação, importe o repositório Drone Application a sua IDE. Após a criação do database PostgreSQL, abra o arquivo application.properties
e insira a url, username e password referente ao database criado. 

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

Uma vez configuradas as informações necessárias para a persistência dos valores, inicie a aplicação e acesse http://localhost:8080/

<img src="https://user-images.githubusercontent.com/114959652/204373792-5cb95598-be4a-43f1-a6d8-f1d32b554ebb.png"  width="40%" height="40%">


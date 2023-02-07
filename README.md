# CarRentalSpringRest
This application is training pet project about MVC



ATTENTION!!! To prepare the application for work, you need:

# First step: 
You need to create you own application.properties file (to main/resources folder) and then fill in property fields:


   #for javaMailSender for sent messages during registration new user
    spring.mail.host=

    spring.mail.username=

    spring.mail.password=

    spring.mail.port=

    spring.mail.protocol=

    mail.debug=
    
   #Data source configuration
  
    spring.datasource.driver-class-name=
    
    spring.datasource.url=
    
    spring.datasource.username=
    
    spring.datasource.password=
    
  
  #Jpa / Hibernate configuration
   
    spring.jpa.show-sql=
    spring.jpa.hibernate.ddl-auto=none
    spring.jpa.properties.hibernate.dialect=
    spring.jpa.properties.hibernate.current_session_context_class=org.springframework.orm.hibernate5.SpringSessionContext
   
  #Server port for localhost connection
  
    server.port=8081

# Second step: 
You need to download docker on your device and then star docker_postgres.sh file to create docker container container with postgresql and create database

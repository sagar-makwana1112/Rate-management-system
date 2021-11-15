# Rate-management-system
This application is rate management system.

## Required configuration

- Clone Rate-management-system repository into your local machine using command line or any UI, in command prompt : git clone https://github.com/sagar-makwana1112/Rate-management-system.git
- Checkout master branch ex: git checkout master.
- Go to project directory and open command prompt and run mvn eclipse:eclipse (Maven must be configured in local system).
- Run one more command in command prompt mvn clean install (Note: it will execute with Test class) if we need to ignore test class then run mvn clean install -DskipTests.
- Import project in STS or eclipse.
- Make sure 8080 port is free if not then change port using application.properties file and add new property server.port=<port number> ex: server.port=9090.
  
## Database configuration
- Create Database in MySql and name is Test.
- Update username and password for MySql database connection in application.properties file.

## Login Details
- In this application Basic authentication is implemented.
- Username = test and password = test
- Username = user1 and password = user1

## Rest calls
- URL = /surcharges, Method = GET to get all surcharges details. Response status code = OK(200) if success.
- URL = /surcharge/{id}, Method = GET to get specific surcharges details of rate and id is id of rate. Response status code = OK(200) if success.
- URL = /surcharge, Method = POST to save details of rate. Response status code = CREATED(201) if success.
  - Request body :
    {
      "rateDescription": "Rate Description 2",
      "rateEffectiveDate": "2021-11-11",
      "rateExpirationDate": "2021-11-11",
      "amount": 200
    }
- URL = /surcharge, Method = PUT to update rate details. Response status code = No Content(204) if success.
  - Request body : 
    {
      "id":1,
      "rateDescription": "Rate Description 3",
      "rateEffectiveDate": "2021-11-14",
      "rateExpirationDate": "2021-11-14",
      "amount": 300
    }
- URL = /surcharge/{id}, Method = DELETE to delete rate detail and id is id of rate. Response status code = No Content(204) if success.
  
## Run Step
- STS or Eclipse = Select project -> Right click -> Select Run As -> Select Java application or Spring boot app.
- Command Line = go to folder of where war file is placed and execute command = mvn spring-boot:run

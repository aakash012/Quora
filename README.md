# Quora
In this project, I worked on developing REST API endpoints of various functionalities required for a website (similar to Quora) from scratch. In order to observe the functionality of the endpoints, I have used the Swagger user interface and stored the data in the PostgreSQL database. Also, the project has to been implemented using Java Persistence API (JPA).

To run the file on your local machine, follow the given steps:

1.Make a databse name "quora" in you postgresSQL server.

2.Move to "quora-db" folder and inside resouces/config/localhost.properties, change the user and password according to your postgresSQL server.

3.Build the project in the main directory of the project by running the command "mvn clean install -DskipTests".

4.In order to activate the profile setup, move to quora-db folder using "cd quora-db" command in the terminal and then run "mvn clean install -Psetup" command to activate the profile setup.

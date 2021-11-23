# cliff-backend
This Repository contains a Basic Springboot Application, that exposes some Restful WebServices to perform CRUD operations on a MySQL database, with those endpoints we can perform below listed operations, the schema has 2 Entities Policy and Job with a one to many relationship between them.

##### Operations on Policy Entity - 
- Create a new Policy
- Update a new Policy
- Read a new Policy
- Delete a new Policy
- list all Policies in a particular page
- find a policy with child Job Id

##### Operations on Job Entity - 
- Create a new Job
- Update a new Job
- Read a new Job
- Delete a new Job
- list all Jobs in a particular page
- find a job with parent Policy Id


### Environment Setup -
- [Java SE Development Kit 11.0.12](https://www.oracle.com/in/java/technologies/javase/jdk11-archive-downloads.html)
- [Apache Maven 3.8.4](https://maven.apache.org/download.cgi)
- [MySQL 8.0.27](https://dev.mysql.com/downloads/installer/)

### Steps to boot the application -

1. Once the Environment setup is complete, you can check the installation of all three tools by running the below mentioned commands in your terminal that is running as an administrator, Once these commands show you the version number of the tools, proceed to the next step.
```
java -version
mvn -version
mysql -V
```
2. Clone the repository, to get the codebase in your local machine.
3. Run the following commands on your MySQL server.
```
CREATE DATABASE policydata;
USE policydata;

CREATE TABLE policy(id VARCHAR(100) , policy_name VARCHAR(100) , defination VARCHAR(300) ,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP() , updated_at TIMESTAMP , policy_status BIT , PRIMARY KEY(id));

CREATE TABLE jobs(id VARCHAR(100) , policy_id VARCHAR(100) , job_name VARCHAR(100) ,created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP() ,
updated_at TIMESTAMP , CONSTRAINT fk_policy_jobs FOREIGN KEY(policy_id) REFERENCES policy(id) ON DELETE CASCADE);
```
4. Once the policyData database is created and database schema is been established.
5. Open the terminal locate to the parent directory of this project, and run the below command, to import all the dependencies in your machine and to create the .jar file file for your springboot application.
```
mvn clean install
```
6. Once the process is completed, locate the jar file inside the target folder and get its absolute path.
7. Run the below command to start your springboot application, that will be up and running on the embedded tomcat server.
```
java –jar <JARFILE_ABSOLUTE_PATH> 
```
8. The tomcat server will host the application on http://localhost:8080, below is the snapshots of testing the endpoints on postman,
9. The user can see the reference documentation as OpenAPI docs, by accessing http://localhost:8080/swagger-ui.html




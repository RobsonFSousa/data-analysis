# Data Analysis

Data Analysis is a data processing service that reads batches of files, processes and reproduces a report

# Features

  - [x] Import .dat files from a default path
  - [x] Process .dat files
  - [x] Generate .dat output files with the data summary in a default path
  - [x] Generate report
  
# Prerequisite
It is a prerequisite to have the following technologies previously installed:
  - Java (JDK 1.8)
  - A Git client
  - A Maven build tool
  
# Running the application

### Running the application with IDE
  - Download the zip or clone the Git repository
  - Unzip the zip file (if you downloaded one)
  - Open Command Prompt and Change directory (cd) to folder containing pom.xml
  - Open Eclipse
    - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
	- Select the project
  - Choose the Spring Boot Application file (search for @SpringBootApplication)
  - Right Click on the file and Run as Java Application
  
### Running the application with Maven
Alternatively you can use the [Spring Boot Maven plug-in](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins.html#build-tool-plugins-maven-plugin) like so:  

```
$ git clone https://github.com/RobsonFSousa/data-analysis.git
$ cd data-analysis
$ mvn spring-boot:run
```

### Running the application with Executable JAR
The code can also be built into a jar and then executed/run. Once the jar is built, run the jar by double clicking on it or by using the command

```
$ git clone https://github.com/RobsonFSousa/data-analysis.git
$ cd data-analysis
$ mvn clean package -DskipTests
$ java -jar target/data.analysis-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
```
  
# Tech

The following technologies were used to develop the project:

- [Java](https://expo.io/)
- [SpringBoot](https://spring.io/projects/spring-boot)
- [Git](https://git-scm.com/)
- [Maven](http://maven.apache.org/)

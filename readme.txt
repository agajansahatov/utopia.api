* This is back-end api application for my front-end web app utopia.

* I used the "Spring Initializr" at "start.spring.io" to create this project. So, I used
    the Spring Boot Java Framework for this application.
* I have chosen "Gradle - Groovy" for dependency management and Java for the programming language.
* The application starts in the main method of the Application class in the package "com.utopia.api". The main method
    will run the Spring Boot Application. And then the Spring Boot framework takes care of starting and running
    the application with all of its classes, configurations, controllers etc. So, when we run our application,
    the spring boot framework will read the annotations inside our class files. According to the annotations,
    it will do its configuration and run our classes accordingly.
* Config - I have put the important configuration classes in the "com.utopia.api.config" package. Currently,
    the CorsMappings and the Database configurations are implemented there.
* Root - In the root folder, I have put the application.properties, build.gradle, Dockerfile.
    The application.properties file is used by the "DataSourceConfig.java" class in the config package.
    The build.gradle is used by the dependency manager. Dockerfile will only be used in the cloud (render.com).
* Controllers - I have put the controllers in the "com.utopia.api.controllers" package. Currently, there is
    only one controller there. Here, Controllers are used to handle the endpoint requests. The definition is
    controllers are classes responsible for handling incoming HTTP requests from clients and generating
    appropriate responses. The Spring boot annotations for controlling the requests are put in this package only.
    Why we need the other packages then? The other packages are used by the controllers inside this package.
* DAO - I created DAO (Data Access Object) classes. It's a design pattern used in software engineering
    to abstract and encapsulate all the operations related to data access in a separate layer of the application.
    DAO classes abstract the underlying data source and provide a clean interface for the rest of the application
    to perform CRUD (Create, Read, Update, Delete) operations without needing to know the details
    of how the data is accessed. So, I have created one DAO class for each table in the db. I have put them in the
    package "com.utopia.api.dao".
* Entities - I have put all the entities inside the package "com.utopia.api.entities". Entities are the data
    types which can be used to store the data retrieved from the database. We also need to use them to get and send the data
    at endpoint requests.
* DTO - Data Transfer Object. Java Spring Boot @RequestBody annotation only allows one object per request. But sometimes
    we need to transfer two objects on one request. So, we need to create a DTO class separately and put our two objects
    as the properties of this new DTO class. And when we accept a request from front-end app, we can accept
    only one object - DTO. So, there might be multiple DTO classes through entire project. That's why I created
    a new package for all DTO classes.

* Public folder - When we run our application, if someone sends a request to the root endpoint, this folder
    will be returned. What did I mean by that? It means that the index.html inside that folder will be returned by default.
    And the images, videos can be accessed from this folder. Other endpoints will be taken care of in the controller classes.
    So, don't use the name of the images' or videos' folder when you create a new endpoint. Otherwise, there will be
    crash and the images or videos cannot be accessed by default.

* INSTALLATION
* Here is the guideline of how you should install this app on your PC:
    - Open the MySQL, login with your login credentials;
    - In the folder "./src/main/java/com/utopia/api/config/",
    there is two MySQL database files - creatable.sql and importable.sql.
    In MySQL, import creatable.sql if you only want to create the schema of the db, but
    if your want to do the both of creating the schema and fill in the data, then
    import the importable.sql;
    - In the root folder, there is a file called "application.properties",
    there you will need to write your MySQL username and password. Attention: Don't touch other lines.
    - Run the application (You must run in port: 8080).

* You can collaborate to this project if you want.
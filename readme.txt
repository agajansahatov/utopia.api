If you import the "utopia.sql" into your database. You don't need to do these.
But If you don't import "utopia.sql", then you can do these steps to initialize the database for this application.

- Create a database/schema called "lz2001".
- In the "utopia-api\src\main\resources", there is a file called "application.properties",
there you will need to write your MySQL username and password. Attention: Don't touch other lines.
- In the "utopia-api\src\main\java\api\utopia\config", there is a java class called "TableCreator.java".
- Open it and uncomment the lines.
- Run the application (You must run in port: 8080).
- Re-comment all the lines again.
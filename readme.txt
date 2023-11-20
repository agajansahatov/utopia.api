This is back-end api application for my front-end web app utopia.

You can collaborate to this project if you want.

Here is how you should install this app on your PC:
- Create a database/schema called "utopia".
- In the root folder, there is a file called "application.properties",
there you will need to write your MySQL username and password. Attention: Don't touch other lines.

+ If you import the "utopia-db.sql" into your database. You don't need to do these.
But If you don't import "utopia.sql", then you can do these steps to initialize the database for this application.

- In the "utopia-api\src\main\java\api\utopia\config", there is a java class called "TableCreator.java".
- Open it and uncomment the lines.
- Run the application (You must run in port: 8080).
- Re-comment all the lines again.

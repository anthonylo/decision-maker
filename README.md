Instructions for DECISION MAKER Application

Currently in development


This is a Spring MVC project developed in Java and uses Maven as the dependency management tool with Tomcat as the App server.

Inside the resources folder, you can find a db_schema folder that will contain two database schemas for Oracle and MySQL. This commit is currently configured on Oracle, but all you need to do to pick one or the other is to run the script on your local machine and configure the dispatcher-servlet.xml's values to use the proper properties from the application.properties file.  

The end goal of this application is to allow users to send questions and answers back and forth to one another. Hopefully, this will turn into some kind of mobile application. 
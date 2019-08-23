# cartoon-characters

## System Overview

This JavaFX application provides an interface for the management of a database storing information on cartoons.  The functionalities provided include saving new and updating existing cartoons, locations, and characters; adding and deleting images of cartoons, locations, and characters; and viewing character statistics.

## Purpose

The purpose of this repository is to provide an example application utilizing the combination of JavaFX, Hibernate, and PostgreSQL.

## Setup and Run Instructions

**a)** Download and install PostgreSQL [here](https://www.postgresql.org/download/).

**b)** Download and install pgAdmin [here](https://www.pgadmin.org/download/).

**c)** In pgAdmin, run the scripts for database creation, schema creation, and table population.

**d)** Install Maven if not already installed.
 - On Linux, open a terminal and execute "sudo apt install maven". 

**e)** Build the project.
 - From the directory containing the POM file, execute the command, "mvn package".

**f)** Run the project.
 - Extract the contents of the compressed folder in the desired location.
 - Open a terminal and navigate to the project directory to find the jar file.
 - Run the program with the command, "java -jar &lt;name-of-JAR-file&gt;.jar"

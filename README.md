# spring_batch_example

A sample project using Spring Batch.

# What this project does?
- This project uses H2 database as a database. A SQL script `data.sql` runs on the project start and seeds the initial data into the database.
- The batch processing job when launched:
  - Reader takes the data from database.
  - Processor converts the strings fetched from database to upper case.
  - Writer writes the data to a CSV file located in `/output/` folder.

# How to start?
- Clone the repository.
- Start the server using your IDE or using command `mvn spring-boot:run`.
- Visit localhost:8080/
- Click on the start button.
- Check the output directory in root directory.

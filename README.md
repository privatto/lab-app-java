# lab-app-java
Basic Java SpringBoot app in Docker which prints the hostname and IP of the container


Build Status
----------


Run application
----------
./mvnw spring-boot:run


Build application
----------

Build the Docker image manually by cloning the Git repo.
  ```
  $ git clone <git_url>
  $ docker build -t lab-app-java .
  ```
Mavem
  ```
  mvn clean install
  ```

Run the container
----------
Create a container from the image.

  ```
  $ docker run --name lab-app-java -d -p 8080:8080 lab-app-java
  ```


Test
----------
    ```
    ```

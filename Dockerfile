FROM eclipse-temurin

RUN apt-get update
RUN apt-get install -y maven

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY /src ./src

ENV WAIT_VERSION 2.7.2
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

CMD ["/wait", "mvn", "spring-boot:run"]

# build
FROM maven:3.9.9-amazoncorretto-17-alpine  as build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

# run
FROM amazoncorretto:17
WORKDIR /app

COPY --from=build ./build/target/*.jar /libraryapi.jar

EXPOSE 8080
EXPOSE 8090

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''
ENV GOOGLE_CLIENT_ID='client_id'
ENV GOOGLE_CLIENT_SECRET='client_id'

ENV SPRING_PROFILES_ACTIVE='production'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar libraryapi.jar
FROM gradle:6.2
LABEL author="knautiluz"
USER root
WORKDIR /usr/spring-boot/apps
COPY . /usr/spring-boot/apps
RUN ./gradlew build --no-daemon
COPY build/libs/*.jar app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "app/spring-boot-application.jar"]

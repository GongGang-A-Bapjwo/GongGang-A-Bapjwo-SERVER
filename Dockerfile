FROM openjdk:21-jdk

RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

COPY build/libs/*.jar gonggang.jar
COPY src/main/resources/oraclecloud src/main/resources/oraclecloud

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Dspring.profiles.active=prod -jar /gonggang.jar"]

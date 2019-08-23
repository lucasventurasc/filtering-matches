FROM openjdk:8-jre-alpine

ADD build/libs/*.jar /build/app.jar
ARG DOptions=""

RUN echo "java -Dspring.profiles.active=prod ${DOptions} -jar /build/app.jar" >> /build/entrypoint.sh
RUN chmod 777 /build/entrypoint.sh

CMD ["sh", "-c", "/build/entrypoint.sh"]
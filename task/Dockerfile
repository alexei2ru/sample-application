FROM azul/zulu-openjdk-alpine:8
COPY ./target/task-*-microbundle.jar /tmp/task-microbundle.jar
CMD [ "java", "-Xmx1024m", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9009", "-jar", "/tmp/task-microbundle.jar" ]
EXPOSE 8080
EXPOSE 9009

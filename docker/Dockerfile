FROM alpine:edge
MAINTAINER mnicinski
RUN apk add --no-cache openjdk8

COPY ClientsApp.jar /app.jar
CMD ["/usr/bin/java", "-jar", "/app.jar"]
EXPOSE 8080
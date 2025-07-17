FROM openjdk:17-slim
WORKDIR /app/src
COPY src /app/src
RUN javac Main.java
CMD ["java", "Main"]

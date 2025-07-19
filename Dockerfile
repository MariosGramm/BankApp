FROM openjdk:17-slim
WORKDIR /app
COPY out/artifacts/BankApp_jar/BankApp.jar ./
CMD ["java","-jar", "BankApp.jar"]

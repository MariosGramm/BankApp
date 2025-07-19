# BankApp
A Java-based application implementing a service-oriented architecture (SOA) with a layered design, deployed on Amazon EKS using Kubernetes.

This Java application implements a Service-Oriented Architecture (SOA) using a layered design pattern. Instead of a database, it uses in-memory lists in the Data Access Object (DAO) layer for data storage. The application employs Data Transfer Objects (DTOs) for input and output, a Validator for input validation, a Service layer for business logic, and custom exceptions for error handling. The application is deployed on Amazon EKS with Kubernetes for container orchestration. The Kubernetes cluster was created and managed via AWS CloudShell, where the application was successfully deployed and operated.

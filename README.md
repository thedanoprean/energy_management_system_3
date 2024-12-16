# Energy Management System - Extended Version

## Description
This project extends the existing Energy Management System (EMS) by introducing a **Chat Microservice** and an **Authorization Component**. The EMS continues to serve as a robust solution for managing users and their smart energy metering devices, while now also supporting real-time communication and enhanced security.

### New Features:
1. **Chat Microservice**: Enables real-time communication between users and administrators.
2. **Authorization Component**: Provides secure access to all system microservices via OAuth2 and JWT integration.

## Key Features
### Core EMS Features:
- **Authentication and Authorization** for users and administrators.
- **CRUD Operations** for user accounts and smart energy devices.
- **Role-Based Access Control** to restrict unauthorized access.
- **Data Synchronization** between microservices for consistency.
- **Modular Design** with independent frontend and backend components.

### New Features:
#### Chat Microservice:
- Real-time messaging using WebSockets technology.
- Support for asynchronous messages between users and administrators.
- Administrators can manage multiple simultaneous chat sessions.
- Notifications for:
  - Message read receipts.
  - When the other party is typing.
- Integrated into the existing frontend application with a chat box interface.

#### Authorization Component:
- Secures access to microservices using **Spring Security** with **OAuth2** and **JWT**.
- Fully integrated into the **User Management Microservice** for seamless functionality.

## System Architecture
The EMS now includes the following additional components:
1. **Chat Microservice**: A WebSocket-based service for managing real-time communication.
2. **Authorization Component**: Integrated into the User Microservice to secure all endpoints.

### Deployment Overview
The updated system is containerized using Docker and orchestrated with Docker Compose. Key components include:
- **Frontend**: Exposed on port `7000`, built using Node.js and served with Nginx.
- **User Microservice**: Exposed on port `3000`, developed in Java (Spring Boot).
- **Device Microservice**: Exposed on port `3003`, developed in Java (Spring Boot).
- **Communication Microservice**: Exposed on port `8083`, developed in Java (Spring Boot).
- **Chat Microservice**: Exposed on port `9090`, developed in Java using WebSockets.
- **PostgreSQL Databases**: Dedicated databases for users, devices, communication, and chat.
- **RabbitMQ**: Facilitates communication between microservices.

## Functional Requirements
1. **User Management**:
   - User registration, login, update, and deletion.
   - Role-based redirection to administrator or user-specific pages.
2. **Device Management**:
   - Registration, retrieval, update, and deletion of smart energy devices.
   - Association of devices with specific users.
3. **Communication Management**:
   - Processing energy consumption data.
   - Hourly energy usage computation.
4. **Chat Management**:
   - Real-time messaging between users and administrators.
   - Notifications for message reads and typing statuses.
   - Support for multiple simultaneous chat sessions.
5. **Authorization**:
   - Secure access to microservices using OAuth2 and JWT.

## Non-Functional Requirements
- **Performance**: Response time under 1 second for most operations.
- **Scalability**: Supports an increasing number of users, devices, and chat sessions.
- **Reliability**: High availability and consistent data synchronization.
- **Usability**: Intuitive frontend with clear error messages and real-time chat features.
- **Interoperability**: Seamless communication between microservices via REST APIs and WebSockets.

## Installation and Setup
1. **Prerequisites**:
   - Docker and Docker Compose installed on your machine.
   - Java 17, Node.js, and PostgreSQL installed for local development.

2. **Steps to Run the Application**:
   - Clone the repository: `git clone <repository-url>`
   - Navigate to the project directory: `cd energy-management-system`
   - Build and start containers: `docker-compose up --build`
   - Access the application at `http://localhost:7000`.

3. **Ports Overview**:
   - Frontend: `7000`
   - User Microservice: `3000`
   - Device Microservice: `3003`
   - Communication Microservice: `8083`
   - Chat Microservice: `9090`
   - PostgreSQL Databases: `5404`, `5303`, `5505`, `5606`

## Future Enhancements
- **Advanced Analytics**: Incorporate energy usage patterns analysis.
- **Real-Time Monitoring**: Extend chat functionalities to include live monitoring updates.
- **Machine Learning**: Integrate predictive maintenance for energy devices.

## Conclusion
The extended EMS is a scalable, secure, and user-friendly platform for energy management, now enhanced with real-time chat and secure access controls. Designed for flexibility and efficiency, it caters to the needs of both end-users and administrators.

## Acknowledgments
- Built with **Java Spring Boot**, **React.js**, **PostgreSQL**, **Docker**, and **WebSockets**.
- Uses **Spring Security** with **OAuth2** and **JWT** for robust authorization.

For detailed documentation, refer to the project's official documentation file.

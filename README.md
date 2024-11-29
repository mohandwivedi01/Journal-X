# Journal Application

A **Journal Application** built with Spring Boot and MongoDB to manage user journal entries. This application includes features like user authentication, JWT-based security, and CRUD operations for journals.

## Features

- User Registration and Authentication with encrypted passwords.
- JWT-based authentication for secure access.
- CRUD operations for managing user journals.
- Integration with MongoDB for data persistence.
- Auditing with `createdAt` and `updatedAt` timestamps.
- Relationship management between users and their journal entries.

## Tech Stack

- **Backend**: Spring Boot, Spring Security, JWT
- **Database**: MongoDB
- **Build Tool**: Maven
- **Programming Language**: Java

## Project Structure

```bash
src/main/java/net/backend/journalApp
│
├── controller          # Contains RESTful controllers for user and journal operations
├── jwtFilter           # JWT filter for token-based authentication
├── model               # Defines MongoDB models for User and Journal
├── repository          # Interfaces for database interaction
├── services            # Service layer for business logic
├── utils               # Utility classes like JWT token generation/validation
└── resources
    ├── application.properties # Application configuration

```


## API Endpoints

### Authentication

Method   |     Endpoint    |    Description
---------|-----------------|--------------------------------
POST     |   /auth/signup  |    Register a new user
POST     |   /auth/login   |    Authenticate and get a JWT

### User Operations

Method   |     Endpoint     |  Description
---------|------------------|-------------------
GET      |  /user/id{id}	  |  Get user by ID
PUT      |  /user	          |  Update user profile

### Journal Operations

Method   |     Endpoint     |   Description
---------|------------------|------------------------------
POST     |   /journals	    |    Create a new journal entry
GET      |   /journals	    |    Get all journal entries
GET	     |   /journals/{id}	|  Get a journal entry by ID
DELETE	 |   /journals/{id}	|  Delete a journal entry by ID


## Installation
1. Clone the repository:

   ```bash
    git clone https://github.com/your-username/journal-app.git
    cd journal-app
   ```
2. Install dependencies:

   ```bash
   mvn clean install
   ```
3. Set up MongoDB:
   Ensure MongoDB is running on localhost:27017.
   Update application.properties for your MongoDB connection.

4. Run the application:
   ```bash
   mvn spring-boot:run
   
   ```
5. Configuration
   Update the following in `src/main/resources/application.properties`:
```bash
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/journal-app

# JWT Secret Key
jwt.secret=YourSecretKeyHere

```

## Usage
1. Register a new user by calling the `/auth/signup` endpoint.
2. Authenticate the user to receive a JWT token using `/auth/login`.
3. Use the token to access journal-related endpoints by including it in the Authorization header:

```bash
Authorization: Bearer <JWT_TOKEN>
```

## Contributing
***Contributions are welcome! Please fork this repository, make your changes, and submit a pull request.***

## License
***This project is licensed under the MIT License. See the LICENSE file for details.***

## Contact
- Author: Mohan
- Email: mohandwivedi1806@gmail.com
- GitHub: https://github.com/mohandwivedi01

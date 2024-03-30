# Expense Control API

This API provides functionalities for expense management, where regular users can perform queries and admins can perform queries and registrations.

## Features:

### Categories
- **URL:** `/categories`
  
  - Create categories (requires admin role)
  - Retrieve category by code
  - List all categories
  
  **Payload for creation:**
  
  ```json
  {
    "name": "Health"
  }
  ```

### Persons
- **URL:** `/persons`
  
  - Create persons (requires admin role)
  - Retrieve person by code
  - List all persons
  - Update persons (using PUT and PATCH methods)
  
  **Payload for creation:**
  
  ```json
  {
    "name": "John Fritzz",
    "address": {
      "address": "Elm Street",
      "addressLine2": null,
      "addressNumber": "223",
      "district": "Suburbia",
      "zipCode": "67890"
    },
    "active": false
  }
  ```

### Expenses
- **URL:** `/expenses`
  
  - Create expenses (requires admin role)
  - Retrieve expense by code
  - List all expenses with pagination and filters
    - Filter by description (query parameter: `description`)
    - Filter by due date range (query parameters: `dueDateFrom`, `dueDateTo`, format: yyyy-MM-dd)
  - Delete expense (requires admin role)
  
  **Payload for creation:**
  
  ```json
  {
    "description": "Lunch time",
    "dueDate": "2024-03-27",
    "paymentDate": "2024-03-27",
    "value": -40.0,
    "notes": "Lunch at McDonald's",
    "type": "EXPENSE",
    "category": {
      "code": 3
    },
    "person": {
      "code": 1
    }
  }
  ```
  
  *Note: Type can be either "Revenue" or "Expense"*

### Users
- **URL:** `/auth`
  
  - Register method (`/register`)

  **Payload for register:**
  
  ```json
  {
      "login":"auser",
      "password":"apassword",
      "name":"aname",
      "role": "ADMIN"
  }

  ```

  - Login method (`/login`)

  **Payload for login:**
  
  ```json
  {
      "login":"auser",
      "password":"apassword"
  }
  ```



## Development Stack:
- Spring Web
- Spring Data
- Spring Security
- JWT Authentication
- Mapstruct
- PostgreSQL
- Docker
  
  **Architecture:** Layered Architecture
  
  **References:**
  - [Spring Framework](https://spring.io/projects/spring-framework)
  - [JWT Authentication with Spring Security](https://www.baeldung.com/spring-security-authentication-with-jwt)
  - [Mapstruct Documentation](https://mapstruct.org/documentation/)
  - [Docker Documentation](https://docs.docker.com/)
  - [PostgreSQL Documentation](https://www.postgresql.org/docs/)
  
## How to Use:
1. Install Docker and Docker Compose
2. Clone the repository: `git clone git@github.com:ikk1/moneyApi.git` and enter on moneyApi folder
3. Run the command: `docker-compose up`

**Note.:** You will need to register a user to perform queries/registrations.
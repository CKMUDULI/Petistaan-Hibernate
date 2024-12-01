# Petistaan

## Overview
Petistaan is a Java-based project utilizing Hibernate ORM for database operations. The project focuses on pet-related data management using modern Java technologies.

## Technology Stack
- **Java Version**: Java 23
- **ORM Framework**: Hibernate 6.6.3
- **Database**: MySQL 9.1.0
- **Build Tool**: Maven

## Prerequisites
- Java 23 or higher
- MySQL 8.0 or higher
- Maven (latest version)
- IDE (Spring Tool Suite recommended)

## Project Dependencies
- Hibernate ORM Core
- MySQL Connector/J

## Setup and Installation

### 1. Database Setup
1. Install MySQL 8.0 or higher
2. Create a new database named `petistaan`:
3. Create database tables using the provided SQL script (`src/main/resources/database.sql`)
4. Populate the database with sample data using the provided SQL script (`src/main/resources/data.sql`)
5. Configure database connection in `src/main/resources/hibernate.properties`:
   ```properties
   hibernate.connection.url=jdbc:mysql://localhost:3306/petistaan
   hibernate.connection.username=your_username
   hibernate.connection.password=your_password
   ```

### 2. Project Setup
1. Clone the repository or download the source code
2. Import the project into Spring Tool Suite:
   - File → Import → Maven → Existing Maven Projects
   - Browse to the project directory
   - Select the project and click Finish

### 3. Build and Run
1. Right-click on the project in Project Explorer
2. Run As → Maven clean
3. Run As → Maven install
4. Run the application:
   - Locate `com.petistaan.App.java`
   - Right-click → Run As → Java Application

### 4. Verify Installation
- Check if the database tables are created
- Verify the sample data is populated
- Test the basic CRUD operations through the application

## Project Structure
```
petistaan/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── petistaan/           # Root package
│   │   │           ├── App.java         # Main application class
│   │   │           ├── config/          # Configuration classes
│   │   │           ├── dto/             # Data Transfer Objects
│   │   │           ├── entity/          # Database entities
│   │   │           ├── enums/           # Enumeration classes
│   │   │           ├── exception/       # Custom exceptions
│   │   │           ├── repository/      # Data access layer
│   │   │           ├── service/         # Business logic layer
│   │   │           └── util/            # Utility classes
│   │   └── resources/                   # Resource files
│   └── test/                            # Test classes
└── pom.xml                              # Maven configuration
```

### Package Details

#### `App.java`
- Main application entry point
- Menu-driven console interface
- Handles user interactions

#### `config`
- Hibernate configuration and database connection settings
- Session factory management

#### `dto`
- Data Transfer Objects for entity classes
- Data transformation between layers

#### `entity`
- JPA entity classes for database tables
- Entity relationships and mappings

#### `enums`
- Gender and PetType enumerations
- Type-safe constants

#### `exception`
- Custom exception classes
- Error handling mechanisms

#### `repository`
- Data access layer
- CRUD operations

#### `service`
- Business logic implementation
- Data validation and processing

#### `util`
- Helper classes and utilities
- Common constants

## Database Configuration
Configure your database connection in the Hibernate configuration file with your MySQL credentials:
- Database URL
- Username
- Password

## Database Structure

### Tables and Relationships

#### Base Structure
All entities inherit from a base class that provides:
- `id` (Primary Key, Auto-increment)

#### Owner Table (`owner_table`)
- `id` (PK, inherited)
- `first_name` (NOT NULL)
- `last_name` (NOT NULL)
- `gender` (ENUM, NOT NULL)
- `city` (NOT NULL)
- `state` (NOT NULL)
- `mobile_number` (NOT NULL, UNIQUE, length: 10)
- `email_id` (NOT NULL, UNIQUE)

#### Pet Table (`pet_table`)
Base table for all pets with inheritance strategy: JOINED
- `id` (PK, inherited)
- `name` (NOT NULL)
- `gender` (ENUM['F', 'M'], NOT NULL)
- `type` (ENUM['BIRD', 'CAT', 'DOG', 'FISH', 'RABBIT'], NOT NULL)

#### Domestic Pet Table (`domestic_pet_table`)
Extends Pet table
- All columns from `pet_table`
- `date_of_birth` (DATE, NOT NULL)

#### Wild Pet Table (`wild_pet_table`)
Extends Pet table
- All columns from `pet_table`
- `place_of_birth` (VARCHAR(255), NOT NULL)

#### Join Table (`owner_pet_table`)
Manages Many-to-Many relationship between owners and pets
- `owner_id` (FK to owner_table.id)
- `pet_id` (FK to pet_table.id)

### Relationships
1. **Owner ↔ Pet**: Many-to-Many
   - One owner can have multiple pets
   - One pet can have multiple owners
   - Relationship managed through `owner_pet_table`

2. **Pet Inheritance**:
   - `Pet` is the base entity
   - `DomesticPet` and `WildPet` extend `Pet`
   - Uses JOINED inheritance strategy

### Constraints
- Email and mobile number must be unique for owners
- All specified columns are NOT NULL
- Proper foreign key constraints in the join table
- Cascading operations (PERSIST, MERGE) for pet management
- Gender values limited to 'F' or 'M'
- Pet types limited to BIRD, CAT, DOG, FISH, RABBIT

## Version
Current version: 0.0.1

# HighSuit Card Game

## Overview

HighSuit is an academic Java-based card game project developed to demonstrate object-oriented design principles, unit testing, and structured software development practices. The project models a turn-based card game in which players are dealt hands from a standard deck and game logic determines round completion and scoring.

This repository is intended for educational use and coursework submission, emphasising clean architecture, test-driven development, and maintainable code.

## Objectives

- Apply object-oriented programming (OOP) concepts in Java
- Design modular and reusable game components
- Implement automated testing using JUnit
- Use build automation tools for repeatable compilation and execution

## Key Features

- Standard deck and card abstractions
- Player and hand management
- Game round control and scoring logic
- Deterministic behaviour suitable for unit testing
- Comprehensive JUnit test coverage

## Project Structure

```
HIghSuit/
├── src/                # Core source code
│   ├── Card.java
│   ├── Deck.java
│   ├── Hand.java
│   ├── Player.java
│   └── GameController.java
├── test/               # JUnit test cases
│   ├── PlayerTest.java
│   ├── ScoreTableTest.java
│   └── ...
├── build.xml           # Apache Ant build configuration
├── manifest.mf         # Application manifest
└── README.md           # Project documentation
```

## Technologies Used

- **Programming Language:** Java
- **Testing Framework:** JUnit
- **Build Tool:** Apache Ant
- **Version Control:** Git

## Build and Execution

1. Ensure Java JDK (8 or later) is installed.
2. Navigate to the project root directory.
3. Build the project using Ant:

   ```bash
   ant build
   ```

4. Run the application:

   ```bash
   ant run
   ```

## Testing

All core functionalities are validated using JUnit tests located in the `test` directory.

To execute the test suite:

```bash
ant test
```

## Software Engineering Practices

- Encapsulation and abstraction through class-based design
- Separation of concerns between game logic and testing
- Automated testing for regression prevention
- Readable and maintainable coding standards

## Academic Context

This project was developed as part of a university-level computing course to assess understanding of:

- Java programming fundamentals
- Object-oriented design
- Unit testing methodologies
- Software build automation

## Author

Priyanshu

## License

This project is intended for academic and educational use only.

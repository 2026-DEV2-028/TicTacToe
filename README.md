# TicTacToe

## Game Description

The application implements a classic **TicTacToe game** with the following characteristics:

- The game supports **two players**
- Players enter their names on a first screen
- The game is played on a second screen displaying the board
- Each player is randomly assigned a mark (**X** or **O**)
- The player assigned **X always starts first**
- The default board size is **3x3**

### Board Size Configuration

The board size can be changed easily by modifying the single constant `BOARD_SIZE` located in the
`GameConstants` object.

## Application Screens

The application is composed of two screens:

**1. Player Setup Screen** allows players to enter their names before starting the game.

**2. Game Screen** displays the TicTacToe board and manages turns, win detection, and draw
scenarios.

Navigation between screens is handled using **Navigation3**.

## Architecture

- The project aims to follow **Clean Code principles**
- Business logic is separated from UI logic
- UseCases encapsulate game rules and actions
- ViewModels expose state to the UI using a unidirectional data flow
- Dependency injection is handled using **Hilt**

## Testing

The project includes multiple levels of testing:

- **Unit Tests**
    - Unit tests for the 2 UseCases
    - Unit tests for the main ViewModel

- **UI Tests**
    - End-to-end UI tests covering:
        - Win scenario
        - Draw scenario

### Note About TDD

The original exercise stated that the project should be developed using **Test-Driven Development (TDD)**.

During the technical briefing, I was told that if I did not have prior experience with TDD, it was
acceptable not to strictly follow this methodology, provided that it was clearly explained.

For this reason, the project was not fully developed using TDD, but particular attention was given to:
- Writing meaningful unit tests
- Covering critical business logic
- Validating user flows through end-to-end UI tests

## Build Instructions
The project can be built using standard Gradle tasks from Android Studio or the command line.

Available actions include:
- Building the debug APK : `./gradlew assembleDebug`
- Running unit tests : `./gradlew test`
- Running UI tests on an emulator or connected device: `./gradlew connectedAndroidTest`

## CI/CD
The project includes a GitHub Actions workflow responsible for:

- Building the application
- Running unit tests
- Uploading the generated APK as a workflow artifact

This ensures that every push and pull request is automatically validated.

## Main Libraries Used
- Jetpack Compose
- Navigation3
- Hilt

## Possible Improvements
- Add a proper application icon
- Design a more polished and visually appealing user interface
- Introduce a design system (colors, typography, spacing)
- Allow users to access the history of played games
- Improve accessibility (content descriptions)
- Add better animations
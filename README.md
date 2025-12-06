# Codex Tic Tac Toe

A simple, console-based Tic Tac Toe game written in Kotlin for two human players.

## How to run
1. Compile the program (requires the Kotlin compiler):
   ```bash
   kotlinc src/main/kotlin/Main.kt -include-runtime -d tic-tac-toe.jar
   ```
2. Run the game:
   ```bash
   java -jar tic-tac-toe.jar
   ```

Enter your moves as two numbers separated by a space (row and column, both 1-3). The game announces wins or draws as soon as they occur.

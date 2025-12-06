import kotlin.system.exitProcess

/**
 * Simple console-based Tic Tac Toe game for two human players.
 */
fun main() {
    val game = TicTacToe()
    println("Welcome to Tic Tac Toe! Enter moves as row and column numbers (1-3).\n")

    while (true) {
        game.printBoard()
        val move = readPlayerMove(game.currentPlayer)
        if (!game.makeMove(move.first, move.second)) {
            println("Invalid move, please try again.\n")
            continue
        }

        when (val state = game.gameState()) {
            GameState.InProgress -> game.switchPlayer()
            is GameState.Win -> {
                game.printBoard()
                println("Player ${state.winner} wins!")
                exitProcess(0)
            }
            GameState.Draw -> {
                game.printBoard()
                println("It's a draw!")
                exitProcess(0)
            }
        }
    }
}

private fun readPlayerMove(player: Char): Pair<Int, Int> {
    while (true) {
        print("Player $player, enter your move (row and column): ")
        val input = readlnOrNull()?.trim()?.split(" ")?.filter { it.isNotBlank() }

        if (input == null || input.size != 2) {
            println("Please enter two numbers separated by a space, e.g., '1 3'.")
            continue
        }

        val (rowStr, colStr) = input
        val row = rowStr.toIntOrNull()
        val col = colStr.toIntOrNull()

        if (row == null || col == null || row !in 1..3 || col !in 1..3) {
            println("Rows and columns must be numbers between 1 and 3.")
            continue
        }

        return row - 1 to col - 1
    }
}

private class TicTacToe {
    private val board = Array(3) { CharArray(3) { ' ' } }
    var currentPlayer = 'X'
        private set

    fun printBoard() {
        println("  1   2   3")
        for (row in board.indices) {
            println(" ${board[row][0]} | ${board[row][1]} | ${board[row][2]}")
            if (row < 2) {
                println("---+---+---")
            }
        }
        println()
    }

    fun makeMove(row: Int, col: Int): Boolean {
        if (board[row][col] != ' ') return false
        board[row][col] = currentPlayer
        return true
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
    }

    fun gameState(): GameState {
        val winner = findWinner()
        return when {
            winner != null -> GameState.Win(winner)
            board.all { row -> row.all { it != ' ' } } -> GameState.Draw
            else -> GameState.InProgress
        }
    }

    private fun findWinner(): Char? {
        // Rows and columns
        for (i in 0..2) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0]
            }
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i]
            }
        }

        // Diagonals
        if (board[1][1] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[1][1]
        }
        if (board[1][1] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[1][1]
        }

        return null
    }
}

private sealed class GameState {
    data object InProgress : GameState()
    data class Win(val winner: Char) : GameState()
    data object Draw : GameState()
}

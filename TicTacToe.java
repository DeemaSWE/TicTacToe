import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {

        playGame();

    }

    private static void playGame() {

        Scanner scanner = new Scanner(System.in);
        int playerScore = 0;
        int computerScore = 0;
        int currentRound = 1;
        int totalRounds;
        String playerMark;
        String computerMark;

        try {
            System.out.println("Welcome to Tic-Tac-Toe! Let's start the game.");
            System.out.println("Would you like to play one round or three rounds? (1 or 3).");
            totalRounds = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            while (totalRounds != 1 && totalRounds != 3) {
                System.out.println("Invalid Input! Please enter 1 or 3: ");
                totalRounds = scanner.nextInt();
                scanner.nextLine();
            }

            System.out.println("Please choose between 'X' or 'O': ");
            playerMark = scanner.nextLine().toUpperCase();;
            while (!playerMark.equals("X") && !playerMark.equals("O")) {
                System.out.println("Invalid mark! Please choose between 'X' or 'O': ");
                playerMark = scanner.nextLine().toUpperCase();
            }
            computerMark = playerMark.equals("X") ? "O" : "X";

            while (currentRound <= totalRounds) {

                String[][] board = {
                        {"1", "2", "3"},
                        {"4", "5", "6"},
                        {"7", "8", "9"},
                };

                System.out.println("\n--- Round " + currentRound + " ---");
                displayBoard(board);

                while (true) {

                    playerTurn(board, playerMark, scanner);
                    displayBoard(board);

                    if (checkWinner(board).equals(playerMark)) {
                        playerScore++;
                        System.out.println("\nYou win the round!");
                        break;

                    } else if (isBoardFull(board)) {
                        System.out.println("\nIt's a tie!");
                        break;
                    }

                    computerTurn(board, computerMark);
                    displayBoard(board);

                    if (checkWinner(board).equals(computerMark)) {
                        System.out.println("\nComputer wins the round!");
                        computerScore++;
                        break;

                    } else if (isBoardFull(board)) {
                        System.out.println("\nIt's a tie!");
                        break;
                    }

                }

                currentRound++;

            }

            //Determine the winner after all rounds
            if (totalRounds == 3) {
                determineWinner(playerScore, computerScore);
            }

            System.out.println("Thank you for playing.");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Player Turn
    private static void playerTurn(String[][] board, String playerMark, Scanner s) {

        boolean isValid = false;
        int position = 0;

        System.out.println("\nYour turn. Please enter a position between (1-9).");

        while (!isValid) {

            position = s.nextInt();

            if (position >= 1 && position <= 9) {

                int row = (position - 1) / 3;
                int col = (position - 1) % 3;

                if (board[row][col].matches("\\d")) {
                    isValid = true;

                } else {
                    System.out.println("Position is already taken! Please try again.");
                }

            } else {
                System.out.println("Invalid position! Please enter a number between 1 and 9.");
            }

        }

        updateBoard(board, position, playerMark);

    }

    //Computer Turn
    private static void computerTurn(String[][] board, String computerMark) {

        System.out.println("\nComputer turn");
        boolean isValid = false;
        int random = 0;

        while (!isValid) {
            random = generateRandomNumber();

            int row = (random - 1) / 3;
            int col = (random - 1) % 3;

            if (board[row][col].matches("\\d")) {
                isValid = true;
            }

        }

        updateBoard(board, random, computerMark);
    }

    //Generate random number for computer's move
    private static int generateRandomNumber() {

        Random random = new Random();
        return random.nextInt(9) + 1;

    }

    //Check if there's a winner
    private static String checkWinner(String[][] board) {

        String[] lines = {
                board[0][0] + board[0][1] + board[0][2],
                board[1][0] + board[1][1] + board[1][2],
                board[2][0] + board[2][1] + board[2][2],
                board[0][0] + board[1][0] + board[2][0],
                board[0][1] + board[1][1] + board[2][1],
                board[0][2] + board[1][2] + board[2][2],
                board[0][0] + board[1][1] + board[2][2],
                board[0][2] + board[1][1] + board[2][0]
        };

        for (String line : lines) {

            if (line.equalsIgnoreCase("XXX")) {
                return "X";
            }

            if (line.equalsIgnoreCase("OOO")) {
                return "O";
            }

        }

        return " ";

    }

    //Check if the board is full
    private static boolean isBoardFull(String[][] board) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].matches("\\d")) {
                    return false;
                }
            }
        }
        return true;
    }

    //Determine the winner after all rounds
    private static void determineWinner(int playerWins, int computerWins) {

        System.out.println("\n--- Final winner ---");
        System.out.println("Your score: " + playerWins);
        System.out.println("Computer score: " + computerWins);

        if (playerWins > computerWins) {
            System.out.println("Congratulations you are the winner!");
        } else if (computerWins > playerWins) {
            System.out.println("Computer wins the game!");
        } else {
            System.out.println("It's a tie!");
        }

    }

    //Update the board after a move
    private static void updateBoard(String[][] board, int position, String mark) {

        int row = (position - 1) / 3;
        int col = (position - 1) % 3;

        board[row][col] = mark;

    }

    //Display board
    private static void displayBoard(String[][] board) {

        System.out.println("+---+---+---+");
        System.out.printf("| %s | %s | %s |%n", colorize(board[0][0]), colorize(board[0][1]), colorize(board[0][2]));
        System.out.println("+---+---+---+");
        System.out.printf("| %s | %s | %s |%n", colorize(board[1][0]), colorize(board[1][1]), colorize(board[1][2]));
        System.out.println("+---+---+---+");
        System.out.printf("| %s | %s | %s |%n", colorize(board[2][0]), colorize(board[2][1]), colorize(board[2][2]));
        System.out.println("+---+---+---+");

    }

    //Add colors to X and O marks
    private static String colorize(String str) {

        if (str.equalsIgnoreCase("X")) {
            return "\u001B[31mX\u001B[0m";

        } else if (str.equalsIgnoreCase("O")) {
            return "\u001B[34mO\u001B[0m";

        } else {
            return str;
        }
    }
}

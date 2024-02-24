import java.util.Random;
import java.util.Scanner;

public class TicTacToe {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String[][] board = {
                {"1", "2", "3"},
                {"4", "5", "6"},
                {"7", "8", "9"},
        };


        //Start the game
        System.out.println("Welcome to Tic-Tac-Toe! Let's start the game.");
        displayBoard(board);
        System.out.println("Would you like to play one round or three rounds? (1/3)");
        int round = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Please choose between 'X' or 'O': ");
        String playerMark = scanner.nextLine();
        validatePlayerMark(scanner, playerMark);
        String computerMark = playerMark.equalsIgnoreCase("X") ? "O" : "X";


        if (round == 1) {

            while (true) {

                //Player turn
                playerTurn(board, playerMark, scanner);
                displayBoard(board);
                if (isGameFinished(board, playerMark, computerMark)) {
                    break;
                }

                //Computer turn
                computerTurn(board, computerMark);
                displayBoard(board);
                if (isGameFinished(board, playerMark, computerMark)) {
                    break;
                }


            }

        } else {

            int playerWins = 0;
            int computerWins = 0;
            int countRound = 1;


            while (countRound <= 3) {

                System.out.println("Round " + countRound);

                board = new String[][]{
                        {"1", "2", "3"},
                        {"4", "5", "6"},
                        {"7", "8", "9"},
                };

                displayBoard(board);


                while (true) {

                    //Player turn
                    playerTurn(board, playerMark, scanner);
                    displayBoard(board);
                    if (checkWinner(board).equals(playerMark)) {
                        playerWins++;
                        break;
                    }

                    //Computer turn
                    computerTurn(board, computerMark);
                    displayBoard(board);
                    if (checkWinner(board).equals(computerMark)) {
                        computerWins++;
                        break;
                    }

                }

                countRound++;

            }

            //Determine winner
            if (playerWins > computerWins) {
                System.out.println("Congratulations you win the game!");

            } else if (playerWins < computerWins) {
                System.out.println("Computer wins the game!");

            } else if (playerWins == 0 && computerWins == 0) {
                System.out.println("No one win!");

            }

        }

    }


    private static void playerTurn(String[][] board, String playerMark, Scanner s) {

        System.out.println("Your turn. Please enter a position between (1-9)");
        String position = s.nextLine();
        boolean isValid = isValidPosition(board, position);

        while (!isValid) {
            System.out.println("Invalid position. Please try again");
            position = s.nextLine();
            isValid = isValidPosition(board, position);
        }

        updateBoard(board, position, playerMark);

    }

    private static void computerTurn(String[][] board, String computerMark) {

        System.out.println("Computer turn");
        String random = Integer.toString(generateRandomNumber());
        boolean isValid = isValidPosition(board, random);

        while (!isValid) {
            random = Integer.toString(generateRandomNumber());
            isValid = isValidPosition(board, random);
        }

        updateBoard(board, random, computerMark);
    }

    private static int generateRandomNumber() {

        Random random = new Random();
        return random.nextInt(9) + 1;

    }

    private static boolean isValidPosition(String[][] board, String position) {

        switch (position) {

            case "1":
                return board[0][0].equals("1");

            case "2":
                return board[0][1].equals("2");

            case "3":
                return board[0][2].equals("3");

            case "4":
                return board[1][0].equals("4");

            case "5":
                return board[1][1].equals("5");

            case "6":
                return board[1][2].equals("6");

            case "7":
                return board[2][0].equals("7");

            case "8":
                return board[2][1].equals("8");

            case "9":
                return board[2][2].equals("9");

            default:
                return false;
        }

    }

    private static void validatePlayerMark(Scanner s, String playerMark) {

        while (!playerMark.equalsIgnoreCase("X") && !playerMark.equalsIgnoreCase("O")) {
            System.out.println("Invalid mark. Please choose between 'X' or 'O':");
            playerMark = s.nextLine();
        }

    }


    private static void displayBoard(String[][] board) {

        System.out.println("+---+---+---+");
        System.out.printf("| %s | %s | %s |%n", board[0][0], board[0][1], board[0][2]);
        System.out.println("+---+---+---+");
        System.out.printf("| %s | %s | %s |%n", board[1][0], board[1][1], board[1][2]);
        System.out.println("+---+---+---+");
        System.out.printf("| %s | %s | %s |%n", board[2][0], board[2][1], board[2][2]);
        System.out.println("+---+---+---+");

    }

    private static void updateBoard(String[][] board, String position, String mark) {

        int pos = Integer.parseInt(position);

        int row = (pos - 1) / 3;
        int col = (pos - 1) % 3;

        board[row][col] = mark;

    }

    private static boolean isGameFinished(String[][] board, String playerMark, String computerMark) {

        String winner = checkWinner(board);

        if (winner.equalsIgnoreCase(playerMark)) {
            System.out.println("Congratulations you win the game!");
            return true;

        } else if (winner.equalsIgnoreCase(computerMark)) {
            System.out.println("Computer wins the game!");
            return true;

        } else if (winner.equals("draw")) {
            System.out.println("The game ended in a tie!");
            return true;

        }

        return false;
    }

    private static String checkWinner(String[][] board) {

        int draw = 0;

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

            if (!line.matches(".*\\d.*")) {
                draw++;
            }

        }

        if (draw == 8) {
            return "draw";
        }

        return " ";

    }


}


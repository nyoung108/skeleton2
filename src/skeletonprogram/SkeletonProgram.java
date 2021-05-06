package skeletonprogram;

import java.util.Scanner;

public class SkeletonProgram {

    public static class Main {

        char board[][];
        Player playerOne;
        Player playerTwo;

        Console console = new Console();

        public Main() {
            Scanner input = new Scanner(System.in);
            int boardSize = input.nextInt();
            console.printLeaderBoard();
            System.out.println("Enter wanted board size");
            board = new char[boardSize + 1][boardSize + 1]; //change

            playerOne = new Player(console.readLine("What is the name of player one? "));
            playerTwo = new Player(console.readLine("What is the name of player two? "));
            playerOne.setScore(0);
            playerTwo.setScore(0);

            do {
                playerOne.setSymbol(console.readChar((playerOne.getName()
                        + " what symbol do you wish to use X or O? ")));
                if (playerOne.getSymbol() != 'X' && playerOne.getSymbol() != 'O') {
                    console.println("Symbol to play must be uppercase X or O");
                }
            } while (playerOne.getSymbol() != 'X' && playerOne.getSymbol() != 'O');

            if (playerOne.getSymbol() == 'X') {     //changed  if (playerTwo.getSymbol() == 'X') to playerOne to fix picking symbol error
                playerTwo.setSymbol('O');
            } else {
                playerTwo.setSymbol('X');
            }

            char startSymbol = 'X';
            char replay;

            do {
                int noOfMoves = 0;
                boolean gameHasBeenDrawn = false;
                boolean gameHasBeenWon = false;
                clearBoard();
                console.println();
                displayBoard();
                if (startSymbol == playerOne.getSymbol()) {
                    console.println(playerOne.getName() + " starts playing " + startSymbol);
                } else {
                    console.println(playerTwo.getName() + " starts playing " + startSymbol);
                }
                console.println();
                char currentSymbol = startSymbol;
                boolean validMove;
                Coordinate coordinate;
                do {

                    do {

                        coordinate = getMoveCoordinates();
                        validMove = checkValidMove(coordinate, board);
                        if (!validMove) {
                            console.println("Coordinates invalid, please try again");
                        }
                    } while (!validMove);

                    board[coordinate.getX()][coordinate.getY()] = currentSymbol;
                    displayBoard();
                    gameHasBeenWon = checkXOrOHasWon();
                    noOfMoves++;

                    if (!gameHasBeenWon) {

                        if (noOfMoves == 16) { //board size squared
                            gameHasBeenDrawn = true;
                        } else {

                            if (currentSymbol == 'X') {
                                currentSymbol = 'O';
                            } else {
                                currentSymbol = 'X';
                            }

                        }
                    }

                } while (!gameHasBeenWon && !gameHasBeenDrawn);

                if (gameHasBeenWon) {
                    if (playerOne.getSymbol() == currentSymbol) {
                        console.println(playerOne.getName() + " congratulations you win!");
                        playerOne.addScore();
                    } else {
                        console.println(playerTwo.getName() + " congratulations you win!");
                        playerTwo.addScore();
                    }
                } else {
                    console.println("A draw this time!");
                }

                console.println("\n" + playerOne.getName() + " your score is: " + String.valueOf(playerOne.getScore()));
                console.println(playerTwo.getName() + " your score is: " + String.valueOf(playerTwo.getScore()));
                console.println();
                if (startSymbol == playerOne.getSymbol()) {
                    startSymbol = playerTwo.getSymbol();
                } else {
                    startSymbol = playerOne.getSymbol();
                }

                replay = console.readChar("\n Another game Y/N? ");

                while (replay != 'Y' && replay != 'N') {
                    System.out.println("Please enter Y/N");

                    replay = console.readChar("\n Another game Y/N? ");
                }

            } while (replay == 'Y');

            console.writeFile(playerOne.toString());
            console.writeFile(playerTwo.toString());

        }

        void displayBoard(int boardSize) {
            int row;
            int column;
            console.println(" | 1 2 3 4 "); //change
            console.println("--+------------");
            for (row = 1; row <= boardSize; row++) { //change
                console.write(row + " | ");
                for (column = 1; column <= boardSize; column++) { //change
                    console.write(board[column][row] + " ");
                }
                console.println();
            }
        }

        void clearBoard(int boardSize) {
            int row;
            int column;
            for (row = 1; row <= boardSize; row++) { //change
                for (column = 1; column <= boardSize; column++) { //change
                    board[column][row] = ' ';
                }
            }
        }

        Coordinate getMoveCoordinates() {
            Coordinate coordinate = new Coordinate(console.readInteger("Enter x Coordinate: "), console.readInteger("Enter y Coordinate: "));
            return coordinate;
        }

        boolean checkValidMove(Coordinate coordinate, char[][] board, int boardSize) {
            boolean validMove;
            validMove = true;

            if (coordinate.getX() < 1 || coordinate.getX() > boardSize || coordinate.getY() < 1 || coordinate.getY() > boardSize) { //change
                validMove = false;

            } else if (board[coordinate.getX()][coordinate.getY()] == 'X' || board[coordinate.getX()][coordinate.getY()] == 'O') {
                System.out.println("This space is taken");    // if move in array size but equals X or O 
                validMove = false;
            }

            return validMove;
        }

        boolean checkXOrOHasWon(int boardSize) {
            boolean xOrOHasWon;
            int row;
            int column;
            xOrOHasWon = false;

            for (column = 1; column < boardSize; column++) {
                for (int i = 1; i < boardSize; i++) {
                    if (column == i) {
                        if (board[column][i] == board[column + 1][i + 1] && board[column][i] != ' ') {
                            xOrOHasWon = true;
                        }
                    }

                }
            }
            for (row = 1; row <= 4; row++) { //change
                if (board[1][row] == board[2][row]
                        && board[2][row] == board[3][row]
                        && board[3][row] == board[4][row] //change
                        && board[2][row] != ' ') {
                    xOrOHasWon = true;
                }
            }

            if (board[1][1] == board[2][2] && board[2][2] == board[3][3] && board[3][3] == board[4][4] && board[2][2] != ' ') { //change
                xOrOHasWon = true;
            } else if (board[1][4] == board[2][3] && board[2][3] == board[3][2] && board[3][2] == board[4][1] && board[2][3] != ' ') { //change
                xOrOHasWon = true;
            }
            return xOrOHasWon;
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}

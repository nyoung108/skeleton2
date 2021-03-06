package skeletonprogram;

import java.util.Random;
import java.util.Scanner;

public class SkeletonProgram {

    public static class Main {

        char board[][];
        Player playerOne;
        Player playerTwo;
        Player playerThree;
        Console console = new Console();

        public Main() {
            console.printLeaderBoard();
            Scanner input = new Scanner(System.in);
            boolean valid = false;
            int boardSize = 0;
            while (valid == false) {
                try {
                    while (boardSize <= 2) {
                        System.out.println("Enter board size");
                        boardSize = input.nextInt();
                        if (boardSize <= 2) {
                            System.out.println("Board too small");
                        }
                        valid = true;
                    }

                } catch (Exception e) {
                    System.out.println(e);
                    input.next();
                    valid = false;
                }
            }


            board = new char[boardSize + 1][boardSize + 1]; //change

            playerOne = new Player(console.readLine("What is the name of player one? "));
            playerTwo = new Player(console.readLine("What is the name of player two? "));
            playerThree = new Player(console.readLine("What is the name of player three? "));
            playerOne.setScore(0);
            playerTwo.setScore(0);
            playerThree.setScore(0);

            do {
                playerOne.setSymbol(console.readChar((playerOne.getName()
                        + " what symbol do you wish to use X or O or @? ")));
                if (playerOne.getSymbol() != 'X' && playerOne.getSymbol() != 'O' && playerOne.getSymbol() != '@') {
                    console.println("Symbol to play must be uppercase X or O or @");
                }
            } while (playerOne.getSymbol() != 'X' && playerOne.getSymbol() != 'O' && playerOne.getSymbol() != '@');
            do {
                playerTwo.setSymbol(console.readChar((playerTwo.getName()
                        + " what symbol do you wish to use? ")));
                if (playerTwo.getSymbol() != 'X' && playerTwo.getSymbol() != 'O' && playerTwo.getSymbol() != '@') {
                    console.println("Symbol to play must be uppercase X or O or @");
                }
                while (playerTwo.getSymbol() == playerOne.getSymbol()) {
                    console.println(playerOne.getName() + " has chosen this symbol");
                    playerTwo.setSymbol(console.readChar((playerTwo.getName()
                            + " what symbol do you wish to use? ")));
                }
            } while (playerTwo.getSymbol() != 'X' && playerTwo.getSymbol() != 'O' && playerTwo.getSymbol() != '@');
            if (playerOne.getSymbol() == 'X' && playerTwo.getSymbol() == 'O') {     
                playerThree.setSymbol('@');
            } else if (playerOne.getSymbol() == 'O' && playerTwo.getSymbol() == '@') {
                playerThree.setSymbol('X');
            } else {
                playerThree.setSymbol('O');
            }
            char startSymbol = 'X';
            char replay;

            do {
                int noOfMoves = 0;
                boolean gameHasBeenDrawn = false;
                boolean gameHasBeenWon = false;
                clearBoard(board);
                console.println();
                displayBoard(board);
                Random rand = new Random();

                if (startSymbol == playerOne.getSymbol()) {
                    console.println(playerOne.getName() + " starts playing " + startSymbol);
                } else if (startSymbol == playerTwo.getSymbol()) {
                    console.println(playerTwo.getName() + " starts playing " + startSymbol);
                } else if (startSymbol == playerThree.getSymbol()) {
                    console.println(playerThree.getName() + " starts playing " + startSymbol);
                }
                console.println();
                char currentSymbol = startSymbol;
                boolean validMove;
                Coordinate coordinate;
                int xRand = rand.nextInt(board.length - 1) + 1;
                int yRand = rand.nextInt(board.length - 1) + 1;  //power up square

                do {

                    do {

                        coordinate = getMoveCoordinates();
                        validMove = checkValidMove(coordinate, board);
                        if (!validMove) {
                            console.println("Coordinates invalid, please try again");
                        }
                    } while (!validMove);
                    boolean moveAgain = false;
                    if (coordinate.getX() == xRand && coordinate.getY() == yRand) {
                        moveAgain = true;
                    }

                    board[coordinate.getX()][coordinate.getY()] = currentSymbol;
                    displayBoard(board);
                    gameHasBeenWon = checkXOrOHasWon(board);
                    noOfMoves++;

                    if (!gameHasBeenWon) {

                        if (noOfMoves == (board.length - 1) * (board.length - 1)) { //board size squared
                            gameHasBeenDrawn = true;
                        } else {

                            if (moveAgain) {
                                System.out.println("Power up square activated!");

                                if (playerOne.getSymbol() == currentSymbol) {          //allows you to move again
                                    System.out.println("It is " + playerOne.getName() + "'s turn again");
                                } else if (playerTwo.getSymbol() == currentSymbol) {
                                    System.out.println("It is " + playerTwo.getName() + "'s turn again");
                                } else {
                                    System.out.println("It is " + playerThree.getName() + "'s turn again");
                                }
                                moveAgain = false;
                            } else if (currentSymbol == 'X') {
                                currentSymbol = 'O';
                                if (playerOne.getSymbol() == 'O') {    //tells you who's move it is
                                    System.out.println("It is " + playerOne.getName() + "'s turn");
                                } else if (playerTwo.getSymbol() == 'O') {
                                    System.out.println("It is " + playerTwo.getName() + "'s turn");
                                } else {
                                    System.out.println("It is " + playerThree.getName() + "'s turn");
                                }
                            } else if (currentSymbol == 'O') {
                                currentSymbol = '@';
                                if (playerOne.getSymbol() == '@') {
                                    System.out.println("It is " + playerOne.getName() + "'s turn");
                                } else if (playerTwo.getSymbol() == '@') {
                                    System.out.println("It is " + playerTwo.getName() + "'s turn");
                                } else {
                                    System.out.println("It is " + playerThree.getName() + "'s turn");
                                }
                            } else if (currentSymbol == '@') {
                                currentSymbol = 'X';
                                if (playerOne.getSymbol() == 'X') {
                                    System.out.println("It is " + playerOne.getName() + "'s turn");
                                } else if (playerTwo.getSymbol() == 'X') {
                                    System.out.println("It is " + playerTwo.getName() + "'s turn");
                            } else {
                                System.out.println("It is " + playerThree.getName() + "'s turn");
                                }
                            }
                        }

                    }

                } while (!gameHasBeenWon && !gameHasBeenDrawn);

                if (gameHasBeenWon) {
                    if (playerOne.getSymbol() == currentSymbol) {
                        console.println(playerOne.getName() + " congratulations you win!");
                        playerOne.addScore();
                    } else if (playerTwo.getSymbol() == currentSymbol) {
                        console.println(playerTwo.getName() + " congratulations you win!");
                        playerTwo.addScore();
                    } else if (playerThree.getSymbol() == currentSymbol) {
                        console.println(playerThree.getName() + " congratulations you win!");
                        playerThree.addScore();
                    }
                } else {
                    console.println("A draw this time!");
                }

                console.println("\n" + playerOne.getName() + " your score is: " + String.valueOf(playerOne.getScore()));
                console.println(playerTwo.getName() + " your score is: " + String.valueOf(playerTwo.getScore()));
                console.println(playerThree.getName() + " your score is: " + String.valueOf(playerThree.getScore()));
                console.println();
                if (startSymbol == playerOne.getSymbol()) {
                    startSymbol = playerTwo.getSymbol();
                } else if (startSymbol == playerTwo.getSymbol()) {
                    startSymbol = playerThree.getSymbol();
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
            console.writeFile(playerThree.toString());

        }

        void displayBoard(char[][] board) {

            String xAxis = " | ";
            String Line = "--+";
            for (int i = 0; i <= board.length - 1; i++) {
                xAxis = xAxis + (i + " ");
                Line = Line + "--";
            }
            console.println(xAxis);
            console.println(Line);
            for (int row = 1; row <= board.length - 1; row++) { //change
                console.write(row + " | ");
                for (int column = 1; column <= board.length - 1; column++) { //change
                    console.write(board[column][row] + " ");
                }
                console.println();
            }
        }

        void clearBoard(char[][] board) {
            int row;
            int column;
            for (row = 1; row <= board.length - 1; row++) { //change
                for (column = 1; column <= board.length - 1; column++) { //change
                    board[column][row] = ' ';
                }
            }
        }

        Coordinate getMoveCoordinates() {
            Coordinate coordinate = new Coordinate(console.readInteger("Enter x Coordinate: "), console.readInteger("Enter y Coordinate: "));
            return coordinate;
        }

        boolean checkValidMove(Coordinate coordinate, char[][] board) {
            boolean validMove;
            validMove = true;

            if (coordinate.getX() < 1 || coordinate.getX() >= board.length || coordinate.getY() < 1 || coordinate.getY() >= board.length) { //change
                validMove = false;

            } else if (board[coordinate.getX()][coordinate.getY()] == 'X' || board[coordinate.getX()][coordinate.getY()] == 'O' || board[coordinate.getX()][coordinate.getY()] == '@') {
                System.out.println("This space is taken");
                validMove = false;
            }

            return validMove;
        }

        boolean checkXOrOHasWon(char[][] board) {
            boolean xOrOHasWon;
            int row;
            int column;
            xOrOHasWon = false;

            for (column = 1; column < board.length; column++) {
                for (int i = 1; i <= board.length - 3; i++) {

                    if (board[column][i] == board[column][i + 1] && board[column][i + 1] == board[column][i + 2] && board[column][i] != ' ') {
                        xOrOHasWon = true;
                        return xOrOHasWon;
                    }

                }
            }
            for (row = 1; row < board.length; row++) {
                for (int i = 1; i <= board.length - 3; i++) {

                    if (board[i][row] == board[i + 1][row] && board[i + 1][row] == board[i + 2][row] && board[i][row] != ' ') {
                        xOrOHasWon = true;
                        return xOrOHasWon;
                    }

                }
            }
            for (int i = 1; i <= board.length - 3; i++) {
                for (int j = 1; j <= board.length - 3; j++) {

                    if (board[i][j] == board[i + 1][j + 1] && board[i + 1][j + 1] == board[i + 2][j + 2] && board[i][j] != ' ') {
                        xOrOHasWon = true;
                        return xOrOHasWon;

                    } else if (board[i][board.length - j] == board[i + 1][board.length - j - 1]
                            && board[i + 1][board.length - j - 1] == board[i + 2][board.length - j - 2] && board[i][board.length - j] != ' ') {
                        xOrOHasWon = true;
                        return xOrOHasWon;
                    }

                }
            }
            return xOrOHasWon;
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}

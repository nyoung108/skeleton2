package skeletonprogram;

import java.util.Scanner;

public class SkeletonProgram {

    public static class Main {

        char board[][];
        Player playerOne;
        Player playerTwo;

        Console console = new Console();

        public Main() {
            System.out.println("Enter board size");
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
                clearBoard(board);
                console.println();
                displayBoard(board);
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
                    displayBoard(board);
                    gameHasBeenWon = checkXOrOHasWon(board);
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

            } else if (board[coordinate.getX()][coordinate.getY()] == 'X' || board[coordinate.getX()][coordinate.getY()] == 'O') {
                System.out.println("This space is taken");    // if move in array size but equals X or O 
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
                        }
                 

                }
            }
            for (row = 1; row < board.length; row++) {
                for (int i = 1; i <= board.length - 3; i++) {
                   
                        if (board[i][row] == board[i + 1][row] && board[i + 1][row] == board[i + 2][row] && board[i][row] != ' ') {
                            xOrOHasWon = true;
                        }
                   
                }
            }
            for (int i = 1; i <= board.length - 3; i++) {
                for (int j = 1; j <= board.length - 3; j++) {
                    
                
                if (board[i][j] == board[i + 1][j + 1] && board[i + 1][j + 1] == board[i + 2][j + 2] && board[i][j] != ' ') { //change
                    xOrOHasWon = true;
                    
                } else if (board[i][board.length - j] == board[i + 1][board.length - j - 1] && board[i + 1][board.length - j - 1] == board[i + 2][board.length - j - 2] && board[i][board.length - j] != ' ') {
                    xOrOHasWon = true;
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

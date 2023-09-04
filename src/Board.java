import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Board {
    Square[][] board2;
    LinkedList<Square> placedPieces = new LinkedList<>();
    ArrayList<Square> allowedList = new ArrayList<>();
    ArrayList<Square> player1pieces = new ArrayList<>();
    ArrayList<Square> player2pieces = new ArrayList<>();
    boolean isEmpty;

    public Board() {
        board2 = new Square[15][15];
        emptyBoard();
    }

    public Square[][] getBoard2() {
        return board2;
    }


    public LinkedList<Square> getPlacedPieces() {
        return placedPieces;
    }


    private void filLBoard() {
        Square newSquare;
        for (int i = 0; i < board2.length; i++) {
            for (int j = 0; j < board2.length; j++) {
                newSquare = new Square(i, j, false);
                board2[i][j] = newSquare;

            }
        }
        isEmpty = true;
        setNeighbors();
    }

    private void setNeighbors() {
        for (int i = 0; i < board2.length; i++) {
            for (int j = 0; j < board2.length; j++) {
                ArrayList<Square> neighbors = new ArrayList<>();
                int x = board2[i][j].getX();
                int y = board2[i][j].getY();
                // North-West
                if (checkNeighborWithinBoard(x - 1, y - 1)) {
                    neighbors.add(board2[x - 1][y - 1]);
                }
                // North
                if (checkNeighborWithinBoard(x - 1, y)) {
                    neighbors.add(board2[x - 1][y]);
                }
                // North-East
                if (checkNeighborWithinBoard(x - 1, y + 1)) {
                    neighbors.add(board2[x - 1][y + 1]);
                }
                // East
                if (checkNeighborWithinBoard(x, y + 1)) {
                    neighbors.add(board2[x][y + 1]);
                }
                // West
                if (checkNeighborWithinBoard(x, y - 1)) {
                    neighbors.add(board2[x][y - 1]);
                }
                // South-East
                if (checkNeighborWithinBoard(x + 1, y + 1)) {
                    neighbors.add(board2[x + 1][y + 1]);
                }
                // South
                if (checkNeighborWithinBoard(x + 1, y)) {
                    neighbors.add(board2[x + 1][y]);
                }
                // South-West
                if (checkNeighborWithinBoard(x + 1, y - 1)) {
                    neighbors.add(board2[x + 1][y - 1]);
                }
                board2[i][j].setNeighbors(neighbors);
            }
        }

    }

    public void printAvailablePlays(){
        List<Square> allowed = getAllowedPlay();
        for (Square square : allowed) {
            square.setSquare(3);
        }
        char c;
        int k = 0;
        System.out.println(" ");
        for (c = 'A'; k < board2.length; c++, k++) {
            if (c == 'A') {
                System.out.print("     " + c + "");
            } else {
                System.out.print("   " + c);
            }

        }
        System.out.println("");
        for (int i = 0; i < board2.length; i++) {
            int x = i + 1;
            if (i < 9) {
                System.out.print(x + "  ");
            } else {
                System.out.print(x + " ");
            }
            for (int j = 0; j < board2.length; j++) {
                if (j == 0) {
                    if (board2[i][j].getTag() == "_") {
                        System.out.print("|" + board2[i][j].getTag().repeat(3));
                    }
                    if (board2[i][j].getTag() == "X" || board2[i][j].getTag() == "O" || board2[i][j].getTag() == "*") {
                        System.out.print("|" + "_" + board2[i][j].getTag() + "_");
                    }
                } else {
                    if (board2[i][j].getTag() == "_") {
                        System.out.print("|" + board2[i][j].getTag().repeat(3));
                    }
                    if (board2[i][j].getTag() == "X" || board2[i][j].getTag() == "O" || board2[i][j].getTag() == "*") {
                        System.out.print("|" + "_" + board2[i][j].getTag() + "_");
                    }
                    if (j == 14) {
                        System.out.print("|");
                    }
                }
            }
            System.out.println(" " + x);
        }
        char g;
        int h = 0;
        for (g = 'A'; h < board2.length; g++, h++) {

            if (g == 'A') {
                System.out.print("     " + g + "");
            } else {
                System.out.print("   " + g);
            }
        }
        System.out.println(" ");
    }




    private boolean checkNeighborWithinBoard(int row, int col) {
        if ((row >= 15 || row < 0) || (col >= 15 || col < 0)) {
            return false;
        } else {
            return true;
        }
    }


    private void emptyBoard() {
        filLBoard();
    }


    public void printBoard() {
        char c;
        int k = 0;
        System.out.println(" ");
        for (c = 'A'; k < board2.length; c++, k++) {
            if (c == 'A') {
                System.out.print("     " + c + "");
            } else {
                System.out.print("   " + c);
            }
        }
        System.out.println("");
        for (int i = 0; i < board2.length; i++) {
            int x = i + 1;
            if (i < 9) {
                System.out.print(x + "  ");
            } else {
                System.out.print(x + " ");
            }
            for (int j = 0; j < board2.length; j++) {
                if (j == 0) {
                    if (board2[i][j].getTag() == "_" || board2[i][j].getTag() == "*") {
                        System.out.print("|" + "_" + "_" + "_");
                    }
                    if (board2[i][j].getTag() == "X" || board2[i][j].getTag() == "O") { // Här
                        System.out.print("|" + "_" + board2[i][j].getTag() + "_");
                    }
                } else {
                    if (board2[i][j].getTag() == "_") {

                        System.out.print("|" + board2[i][j].getTag().repeat(3));
                    }
                    if(board2[i][j].getTag() == "*"){
                        System.out.print("|" + "___");
                    }
                    if (board2[i][j].getTag() == "X" || board2[i][j].getTag() == "O") { // här
                        System.out.print("|" + "_" + board2[i][j].getTag() + "_");
                    }
                    if (j == 14) {
                        System.out.print("|");
                    }
                }
            }
            System.out.println(" " + x);
        }
        char g;
        int h = 0;
        for (g = 'A'; h < board2.length; g++, h++) {

            if (g == 'A') {
                System.out.print("     " + g + "");
            } else {
                System.out.print("   " + g);
            }
        }

        System.out.println(" ");
    }

    public ArrayList<Square> getPlayer1pieces() {
        return player1pieces;
    }


    public ArrayList<Square> getPlayer2pieces() {
        return player2pieces;
    }


    public boolean putPiece(int row, char columnChar, int player) {

        int column = Character.getNumericValue(columnChar);
        column = column - 10;
        if(!getAllowedPlay().contains(board2[row - 1][column])){
            return false;
        }
        if (player == 1) {
            board2[row - 1][column].setSquare(1);
            player1pieces.add(board2[row - 1][column]);
        } else {
            board2[row - 1][column].setSquare(2);
            player2pieces.add(board2[row - 1][column]);
        }
        placedPieces.add(board2[row - 1][column]);
        allowedList.remove(board2[row - 1][column]);
        clearAllowedPlayList(); // ta bort om det fuckar
        setAllowedPlayList(); // ta bort om det fuckar
        isEmpty = false;
        return true;
    }

    public void removeLastPlayed(){
        int lastRow = getPlacedPieces().getLast().getX();
        int lastColumn = getPlacedPieces().getLast().getY();
        if(player1pieces.contains(placedPieces.getLast())){
         player1pieces.remove(placedPieces.getLast());
        }
        if(player2pieces.contains(placedPieces.getLast())){
            player2pieces.remove(placedPieces.getLast());
        }
        placedPieces.removeLast();
        board2[lastRow][lastColumn].clear();
        clearAllowedPlayList();
        setAllowedPlayList();
        if(placedPieces.size() == 0){
            isEmpty = true;
        }

    }

    public void clearAllowedPlayList(){
        for (Square s : allowedList) {
            s.clear();
        }
        allowedList.clear();
    }

    public void setAllowedPlayList(){
        for (Square square : placedPieces) {
            for (Square neighbor : square.getNeighbors()) {
                if (!neighbor.isOccupied()) {
                    if(!allowedList.contains(neighbor))
                        allowedList.add(neighbor);
                    neighbor.setSquare(3);
                }
            }
        }
    }

    public ArrayList<Square> getAllowedPlay() {
        if (isEmpty) {
            for (int i = 0; i < board2.length; i++) {
                for (int j = 0; j < board2.length; j++) {
                    if (board2[i][j].getTag() == "_") {
                        allowedList.add(board2[i][j]);
                    }
                }
            }
        } else {
            clearAllowedPlayList();
            setAllowedPlayList();
        }
        return allowedList;
    }

    @Override
    public String toString() {
        return "Board{" +
                "board=" + Arrays.toString(board2) +
                '}';
    }
}

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Main {
    private static final int COMP_WIN = 1;
    private static final int COMP_LOSS = -1;
    private static final int DRAW = 0;

    static Board board2 = new Board();

    public static void main(String[] args) {
        humanVSmachineMachineStart();
    }


    private static void testBoard() {
        board2 = new Board();
        board2.printBoard();
        board2.putPiece(11, 'J', 1);
        board2.putPiece(10, 'J', 1);
        board2.putPiece(9, 'J', 1);
        board2.putPiece(9, 'K', 2);
        board2.putPiece(10, 'K', 2);
        board2.putPiece(11, 'K', 2);
    }

    private static void humanVSmachineMachineStart() {

        board2 = new Board();
        Scanner sc = new Scanner(System.in);
        int player = 1;
        String cplay = "yes";
        boolean allowed = true;
        boolean show = false;
        String rowString = "";
        String columnString = "";
        char row = '0';
        board2.printBoard();
        int piecesCount = 0;


        do {

            if (player == 1) {
                Square bestSquare;
                System.out.println("Player 1s tur, datorn ska välja");
                System.out.println(" ");
                if (board2.isEmpty) {
                    board2.putPiece(8, 'H', 1);
                    board2.printBoard();
                } else if (board2.placedPieces.size() <= 3) {
                    PRow bestRow = bestPosition(1);
                    int bestIndex = bestRow.getOpenEndIndex();
                    System.out.println(bestRow.getOpenEnds());
                    System.out.println(bestRow.getOpenEnds().get(0));
                    System.out.println(bestRow.getOpenEnds().get(bestIndex));
                    Square best = bestRow.getOpenEnds().get(bestIndex);
                    int x = best.getX() + 1;
                    int y = best.getY();
                    char z = (char) (y + 65);
                    board2.putPiece(x, z, 1);
                    board2.printBoard();

                } else {
                    // MIN MAX
                    MoveInfo bestMove = findCompMove(5, -99999, 99999);
                    // MIN MAX
                    bestSquare = bestMove.getSquare();
                    int x = bestSquare.getX();
                    int y = bestSquare.getY();
                    char z = (char) (y + 65);
                    boolean allowed1 = board2.putPiece(x + 1, z, 1);
                    board2.printBoard();
                    System.out.println(" ");
                    System.out.println("Datorn spelade denna move");
                    System.out.println("X: " + (x + 1));
                    System.out.println("Y: " + z);


                }

                player = 2;

            }

            if (player == 2) {
                if (!board2.isEmpty) {
                    if (fiveInRow(board2.getPlacedPieces().getLast())) {
                        player = board2.getPlacedPieces().getLast().getPlayer();
                        winPrint(player);
                        break;
                    }
                    if (fiveInColumn(board2.getPlacedPieces().getLast())) {
                        player = board2.getPlacedPieces().getLast().getPlayer();
                        winPrint(player);
                        break;
                    }
                    if (fiveInDiagonal(board2.getPlacedPieces().getLast())) {    // från nere vänster till uppe höger
                        player = board2.getPlacedPieces().getLast().getPlayer();
                        winPrint(player);
                        break;
                    }
                    if (fiveInRDiagonal(board2.getPlacedPieces().getLast())) { // från uppe vänster till nere höger
                        player = board2.getPlacedPieces().getLast().getPlayer();
                        winPrint(player);
                        break;
                    }
                }

                if (allowed == false) {
                    System.out.println("Unallowed square selected, please try again");
                    System.out.println("Want to see allowed squares, type \"yes\" or enter any key to continue");
                    String input = sc.nextLine();
                    if (input.equalsIgnoreCase("yes")) {
                        board2.printAvailablePlays();
                    }

                }


                System.out.println(" ");
                System.out.println("Player 2s turn");
                System.out.println("Column A-O?");
                columnString = sc.nextLine();

                if (columnString.equalsIgnoreCase("exit")) {
                    break;
                }

                if (columnString.equalsIgnoreCase("show")) {
                    board2.printAvailablePlays();
                    System.out.println("Player 2s turn");
                    System.out.println("Column A-O?");
                    columnString = sc.nextLine();
                }

                do {
                    if (columnString.equalsIgnoreCase("rm")) {
                        if (player == 1) {
                            player = 2;
                        } else {
                            player = 1;
                        }
                        board2.removeLastPlayed();
                        board2.printBoard();
                        System.out.println("Player 2s turn");
                        System.out.println("Column A-O?");
                        columnString = sc.nextLine();
                    }
                    if (columnString.equalsIgnoreCase("show")) {
                        board2.printAvailablePlays();
                        System.out.println("Player 2s turn");
                        System.out.println("Column A-O?");
                        columnString = sc.nextLine();
                    }

                } while (columnString.equalsIgnoreCase("rm") || columnString.equalsIgnoreCase("show"));

                row = columnString.charAt(0);
                System.out.println("Row 1-15?");
                rowString = sc.nextLine();

                do {
                    if (rowString.equalsIgnoreCase("rm")) {
                        if (player == 1) {
                            player = 2;
                        } else {
                            player = 1;
                        }
                        board2.removeLastPlayed();
                        board2.printBoard();
                        //board2.printAvailablePlays();
                        System.out.println("Player 2s turn");
                        System.out.println("Row 1-15?");
                        columnString = sc.nextLine();
                    }

                    if (rowString.equalsIgnoreCase("show")) {
                        board2.printAvailablePlays();
                        System.out.println("Player 2s turn");
                        System.out.println("Row 1-15?");
                        rowString = sc.nextLine();
                    }

                } while (rowString.equalsIgnoreCase("rm") || rowString.equalsIgnoreCase("show"));

                int column = Integer.valueOf(rowString);
                allowed = board2.putPiece(column, row, 2);
            }

            if (allowed == true) {
                if (player == 1) {
                    player = 2;
                } else {
                    player = 1;
                }
                piecesCount++;
            }

        } while (true);
    }

    private static void winPrint(int player) {
        System.out.println(" ");
        System.out.println("----------------------------------------------------------");
        System.out.println("   _________    __  _________   ____ _    ____________ ");
        System.out.println("  / ____/   |  /  |/  / ____/  / __ \\ |  / / ____/ __ \\ ");
        System.out.println(" / / __/ /| | / /|_/ / __/    / / / / | / / __/ / /_/ /");
        System.out.println("/ /_/ / ___ |/ /  / / /___   / /_/ /| |/ / /___/ _, _/ ");
        System.out.println("\\____/_/  |_/_/  /_/_____/   \\____/ |___/_____/_/ |_|");
        System.out.println("----------------------------------------------------------");
        System.out.println("                  PLAYER  " + player + " WIN!!!");
        System.out.println("       Playing with the pieces marked with " + "\"" + board2.getPlacedPieces().getLast().getTag() + "\"");
        System.out.println("----------------------------------------------------------");

    }




    public static MoveInfo findCompMove(int depth, int alpha, int beta) {

        int testValue;
        int value = -99999;
        Square bestSquare = board2.getAllowedPlay().get(0);
        Square last = board2.getPlacedPieces().getLast();
        PRow bestPos = bestPosition(1);
        PRow bestPosHuman = bestPosition(2);


        // 1. Det blev lika
        if (fullboard()) {
            //Inställd på 1 ifrån lika
            return new MoveInfo(last, 0);
        }

        // 2. Människan har vunnit
        if (win(board2.getPlacedPieces().getLast())) {
            return new MoveInfo(last, -200);
        }

        // Ger datorn direkt vinst, så datorn inte behöver testa fler scenarion.
        if (bestPos.getPlayed().size() == 4 && bestPos.getOpenEndNr() >= 1) {
            Square win = bestPos.getOpenEnds().get(bestPos.getOpenEndIndex());
            return new MoveInfo(win, 300);
        }

        if (bestPos.compareTo(bestPosHuman) >= 0) {
            if (bestPos.getPlayed().size() == 3 && bestPos.getOpenEndNr() == 2) {
                Square win = bestPos.getOpenEnds().get(bestPos.getOpenEndIndex());
                return new MoveInfo(win, 300);
            }
        } else if (depth == 0) {
            PRow pcBest = bestPosition(1);
            PRow humanBest = bestPosition(2);
            Square lastP = board2.getPlacedPieces().getLast();

            if (pcBest.compareTo(humanBest) == 1) {
                if (pcBest.getPlayed().size() == 4 && pcBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, 200);
                } else if (pcBest.getPlayed().size() == 4 && pcBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, 190);
                } else if (pcBest.getPlayed().size() == 3 && pcBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, 180);
                } else if (pcBest.getPlayed().size() == 3 && pcBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, 170);
                } else if (pcBest.getPlayed().size() == 2 && pcBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, 160);
                } else if (pcBest.getPlayed().size() == 2 && pcBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, 150);
                }

            }

            if (pcBest.compareTo(humanBest) == -1) {
                if (humanBest.getPlayed().size() == 4 && humanBest.getOpenEndNr() == 2) {
                } else if (humanBest.getPlayed().size() == 4 && humanBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, -190);
                } else if (humanBest.getPlayed().size() == 3 && humanBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, -180);
                } else if (humanBest.getPlayed().size() == 3 && humanBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, -170);
                } else if (humanBest.getPlayed().size() == 2 && humanBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, -160);
                } else if (humanBest.getPlayed().size() == 2 && humanBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, -150);
                }
            } else if (pcBest.compareTo(humanBest) == 0) {
                // Det är lika efter människans spelat. Detta gör att Datorn ligger bättre till.
                return new MoveInfo(lastP, 100);
            }
            // 4. Datorn testar en position och utvärderar den
        } else {
            for (int j = 0; j < board2.getAllowedPlay().size(); j++) {
                int row = board2.getAllowedPlay().get(j).getX() + 1;
                int column = board2.getAllowedPlay().get(j).getY();
                char colChar = (char) (column + 65);
                board2.putPiece(row, colChar, 1);                              // 1. Gör ett drag.
                MoveInfo testMove = findHumanMove(depth - 1, alpha, beta);     //  2.1 Spara draget
                testValue = testMove.getValue();                                     //  2.2 spara värdet av draget
                Square testedSquare2 = board2.placedPieces.getLast();                //  2.3 Spara Squaren som spelades
                board2.removeLastPlayed();                                           // 4. Ta bort senaste spelade från boarden
                if (testValue > value) {                                             // 5. om vi hittat ett nytt bästa drag
                    value = testValue;                                               // 5.1 Spara dragets värde
                    bestSquare = testedSquare2;                                      // 5.2 Spara Squaren som den bästa squaren att returnera
                }
                if (testValue > alpha) {
                    alpha = testValue;
                }
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return new MoveInfo(bestSquare, value);                             // 6. Returnera bästa rutan och dess värde.
    }


    private static MoveInfo findHumanMove(int depth, int alpha, int beta) {
        int testValue;
        int value = 99999;
        Square bestSquare = board2.getAllowedPlay().get(0);
        Square last = board2.getPlacedPieces().getLast();
        PRow bestPos = bestPosition(2);
        PRow bestComputerPos = bestPosition(1);

        // 1. Det blev lika
        if (fullboard()) {
            return new MoveInfo(last, 0);
        }
        // 2. Datorn har vunnit
        if (win(board2.getPlacedPieces().getLast())) {
            return new MoveInfo(last, 200);
        }
        // Ger människan vinst direkt, så människan inte behöver testa fler scenarion.
        if (bestPos.getPlayed().size() == 4 && bestPos.getOpenEndNr() >= 1) {
            Square win = bestPos.getOpenEnds().get(bestPos.getOpenEndIndex());
            return new MoveInfo(win, -300);
        }
        if (bestPos.compareTo(bestComputerPos) >= 0) {
            if (bestPos.getPlayed().size() == 3 && bestPos.getOpenEndNr() == 2) {
                Square win = bestPos.getOpenEnds().get(bestPos.getOpenEndIndex());
                return new MoveInfo(win, -300);
            }
        } else if (depth == 0) {
            PRow pcBest = bestPosition(1);
            PRow humanBest = bestPosition(2);
            Square lastP = board2.getPlacedPieces().getLast();

            if (pcBest.compareTo(humanBest) == 1) {
                if (pcBest.getPlayed().size() == 4 && pcBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, 200);
                } else if (pcBest.getPlayed().size() == 4 && pcBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, 190);
                } else if (pcBest.getPlayed().size() == 3 && pcBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, 180);
                } else if (pcBest.getPlayed().size() == 3 && pcBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, 170);
                } else if (pcBest.getPlayed().size() == 2 && pcBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, 160);
                } else if (pcBest.getPlayed().size() == 2 && pcBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, 150);
                }

            }

            if (pcBest.compareTo(humanBest) == -1) {
                if (humanBest.getPlayed().size() == 4 && humanBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, -200);
                } else if (humanBest.getPlayed().size() == 4 && humanBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, -190);
                } else if (humanBest.getPlayed().size() == 3 && humanBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, -180);
                } else if (humanBest.getPlayed().size() == 3 && humanBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, -170);
                } else if (humanBest.getPlayed().size() == 2 && humanBest.getOpenEndNr() == 2) {
                    return new MoveInfo(lastP, -160);
                } else if (humanBest.getPlayed().size() == 2 && humanBest.getOpenEndNr() == 1) {
                    return new MoveInfo(lastP, -150);
                }
            } else if (pcBest.compareTo(humanBest) == 0) {
                // Det är lika efter datorn har spelat. Detta gör att människan ligger bättre till.
                return new MoveInfo(lastP, -100);
            }


            // 4. Människan testar en position och utvärderar den
            else {
                for (int j = 0; j < board2.getAllowedPlay().size(); j++) {
                    int row = board2.getAllowedPlay().get(j).getX() + 1;
                    int column = board2.getAllowedPlay().get(j).getY();
                    char colChar = (char) (column + 65);
                    board2.putPiece(row, colChar, 2);                      // 1. Gör ett drag.
                    MoveInfo testMove = findCompMove(depth - 1, alpha, beta); //  2. Se vad datorn kan göra för bästa mottdrag
                    testValue = testMove.getValue();                             //  2.1 spara hur bra datorns mottdrag blir
                    Square testedSquare2 = board2.placedPieces.getLast();
                    board2.removeLastPlayed();                                   // 4. Ta bort senaste spelade från boarden
                    if (testValue < value) {                                      // 5. om vi hittat ett nytt bästa drag
                        value = testValue;                                        // 5.1 Spara dragets värde
                        bestSquare = testedSquare2;                               // 5.2 Spara Squaren som den bästa squaren att returnera
                    }
                    if (testValue < beta) {
                        beta = testValue;
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }
        return new MoveInfo(bestSquare, value);
    }






    private static PRow bestPosition(int player) {
        ArrayList<PRow> list = new ArrayList<>();
        PRow bestRow = bestVertical(player);

        // 1 VERTICAL ------------------------------------------------------------
        PRow bestVerticalRow = bestVertical(player);
        list.add(bestVerticalRow);
        // 1 VERTICAL -------------------------------------------------------------

        // 2 HORIZONTAL ----------------------------------------------------------
        PRow bestHorizontalRow = bestHorizontal(player);
        list.add(bestHorizontalRow);
        // 2 HORIZONTAL -----------------------------------------------------------

        // 3 DIAGONAL ----------------------------------------------------------
        PRow bestDiagonalRow = bestDiagonal(player);
        list.add(bestDiagonalRow);
        // 3 DIAGONAL ----------------------------------------------------------

        // 4 DIAGONAL REVERSED  ----------------------------------------------------------
        PRow bestDiagonalRevRow = bestDiagonalR(player);
        list.add(bestDiagonalRevRow);
        // 4 DIAGONAL REVERSED  ----------------------------------------------------------

        // Utvärdera vilken av alla rader som är bäst
        for (PRow p : list) {
            if (p.getPlayed().size() >= bestRow.getPlayed().size()) {
                if (p.getPlayed().size() == bestRow.getPlayed().size()) {
                    if (p.getOpenEndNr() > bestRow.getOpenEndNr()) {
                        bestRow = p;
                    }
                }
                if (p.getPlayed().size() > bestRow.getPlayed().size()) {
                    if (p.getOpenEndNr() > 0) {
                        bestRow = p;
                    }
                }
            }
        }
        return bestRow;
    }

    private static boolean win(Square last) {
        if (fiveInRow(last) || fiveInColumn(last) || fiveInDiagonal(last) || fiveInRDiagonal(last)) {
            return true;
        }
        return false;
    }

    private static boolean fiveInRow(Square last) {
        int x = last.getX();
        int y = last.getY();
        int counter = 1;
        if (y < board2.getBoard2().length - 1) {
            for (; y < board2.getBoard2().length - 1; y++) {
                if (board2.getBoard2()[x][y + 1].getTag() == last.getTag()) {
                    counter++;
                } else {
                    break;
                }
            }
        }
        y = last.getY();
        if (y > 0) {
            for (; y > 0; y--) {
                if (board2.getBoard2()[x][y - 1].getTag() == last.getTag()) {
                    counter++;
                } else {
                    break;
                }
            }
        }
        if (counter == 5) {
            return true;
        } else {
            return false;
        }
    }


    private static boolean fiveInColumn(Square last) {
        int x = last.getX();
        int y = last.getY();
        int counter = 1;
        if (x < board2.getBoard2().length - 1) {
            for (; x < board2.getBoard2().length - 1; x++) {
                if (board2.getBoard2()[x + 1][y].getTag() == last.getTag()) {
                    counter++;
                } else {
                    break;
                }
            }
        }
        x = last.getX();
        if (x > 0) {
            for (; x > 0; x--) {
                if (board2.getBoard2()[x - 1][y].getTag() == last.getTag()) {
                    counter++;
                } else {
                    break;
                }
            }
        }
        if (counter == 5) {
            return true;
        } else {
            return false;
        }
    }


    private static boolean fiveInDiagonal(Square last) { // från nere vänster till uppe höger

            int x = last.getX();
            int y = last.getY();
            int counter = 1;
            if ((x > 0) && (y < board2.getBoard2().length - 1)) {                 // från nere vänster till uppe höger
                for (; x > 0 && y < board2.getBoard2().length - 1; x--, y++) {
                    if (board2.getBoard2()[x - 1][y + 1].getTag() == last.getTag()) {
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            x = last.getX();
            y = last.getY();
            if (x < board2.getBoard2().length - 1 && y > 0) {
                for (; x < board2.getBoard2().length - 1 && y > 0; x++, y--) {
                    if (board2.getBoard2()[x + 1][y - 1].getTag() == last.getTag()) {    // från upp höger till ner vänster
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            if (counter == 5) {
                return true;
            } else {
                return false;
            }

    }

    private static boolean fiveInRDiagonal(Square last) {  // från uppe vänster till nere höger
            int x = last.getX();
            int y = last.getY();
            int counter = 1;
            if ((x > 0 && (y > 0))) {
                for (; x > 0 && y > 0; x--, y--) { // kollar nereifrån höger till upp vänster
                    if (board2.getBoard2()[x - 1][y - 1].getTag() == last.getTag()) {
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            x = last.getX();
            y = last.getY();
            if (x < board2.getBoard2().length - 1 && y < board2.getBoard2().length - 1) {
                for (; x < board2.getBoard2().length - 1 && y < board2.getBoard2().length - 1; x++, y++) {   // kollar uppifrån vänster till ner höger
                    if (board2.getBoard2()[x + 1][y + 1].getTag() == last.getTag()) {
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            if (counter == 5) {
                return true;
            } else {
                return false;
            }
    }


    private static boolean fullboard() {
        if(board2.getAllowedPlay().size() == 1){
            return true;
        }
        return false;
    }




    private static PRow bestVertical(int player) {
        PRow bestRow = new PRow();
        ArrayList<Square> totalPlayed = new ArrayList<>();
        if (player == 1) {
            totalPlayed = board2.getPlayer1pieces();
        } else {
            totalPlayed = board2.getPlayer2pieces();
        }
        for (Square s : totalPlayed) {
            LinkedList<Square> inRow = new LinkedList<>();
            int x = s.getX();
            int y = s.getY();
            inRow.add(s);
            int counter = 1;
            if (x < board2.getBoard2().length - 1) {
                for (; x < board2.getBoard2().length - 1; x++) {                                     // 1. Loop nedåt från X
                    if (board2.getBoard2()[x + 1][y].getTag() == s.getTag()) {
                        inRow.addLast(board2.getBoard2()[x + 1][y]);                                // lägg till sist
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            x = s.getX();
            if (x > 0) {
                for (; x > 0; x--) {                                                                  // 2. Loop uppåt från X
                    if (board2.getBoard2()[x - 1][y].getTag() == s.getTag()) {
                        inRow.addFirst(board2.getBoard2()[x - 1][y]);                                 // lägg till först
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            // Last är längst NER
            int lastX = inRow.getLast().getX();
            int lastY = inRow.getLast().getY();
            // First är längst UPP
            int firstX = inRow.getFirst().getX();
            int firstY = inRow.getFirst().getY();

            ArrayList<Square> openEnds = new ArrayList<>();
            if(firstX == 0){
                openEnds.add(0, null);
            }
            if (firstX > 0) {
                Square nextUp = board2.getBoard2()[firstX - 1][firstY];
                if (nextUp.getTag() == "*") {                         // om nästa UPPÅT är ledig, lägg till som OPEN!
                    openEnds.add(0, nextUp);
                } else {
                    openEnds.add(0, null);
                }
            }
            if (lastX < board2.getBoard2().length - 1) {
                Square nextDown = board2.getBoard2()[lastX + 1][lastY];
                if (nextDown.getTag() == "*") {                          // om nästa NEDÅT är ledig, lägg till som OPEN!
                    openEnds.add(1, nextDown);
                } else {
                    openEnds.add(1, null);
                }
            }
            if(lastX == 14){
                openEnds.add(1,null);
            }
            PRow newRow = new PRow(inRow, openEnds);
            int newSize = newRow.getPlayed().size();                                // ny radens size och öppna
            int bestSize = bestRow.getPlayed().size();                              // bästa radens size och öppna
            // HÄÄÄÄR
            if (newSize >= bestSize) {
                if(newSize == bestSize){                                            // 1. om dom är lika stora

                    if(newRow.getOpenEndNr() > bestRow.getOpenEndNr()){             // 2. välj den med mest öppna
                        bestRow = newRow;
                    }
                }
                if(newSize > bestSize){                                             // 3. Om den nya är större än den gamla
                    if(newRow.getOpenEndNr() > 0){
                        bestRow = newRow;                                           // välj nya om den har fler än 0 öppna.
                    }
                }

            }
        }
        return bestRow;
    }


    //  Returnerar den bästa från VÄNSTER till HÖGER
    private static PRow bestHorizontal(int player) {
        PRow bestRow = new PRow();
        ArrayList<Square> totalPlayed = new ArrayList<>();
        LinkedList<Square> bestList = new LinkedList<>();
        if (player == 1) {
            totalPlayed = board2.getPlayer1pieces();
        } else {
            totalPlayed = board2.getPlayer2pieces();
        }
        for (Square s : totalPlayed) {
            LinkedList<Square> inRow = new LinkedList<>();
            int x = s.getX();
            int y = s.getY();
            inRow.add(s);
            int counter = 1;
            if (y < board2.getBoard2().length - 1) {
                for (; y < board2.getBoard2().length - 1; y++) {                                     // går från VÄNSTER till HÖGER
                 //   System.out.println("vi kollar åt höger från denna noden");
                    if (board2.getBoard2()[x][y + 1].getTag() == s.getTag()) {
                        inRow.addLast(board2.getBoard2()[x][y + 1]);                                // lägg till sist
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            y = s.getY();
            if (y > 0) {
                for (; y > 0; y--) {                                                                  // går från HÖGER till VÄNSTER
                    if (board2.getBoard2()[x][y - 1].getTag() == s.getTag()) {
                        inRow.addFirst(board2.getBoard2()[x][y - 1]);                                 // lägg till först
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            // last är HÖGER
            int lastX = inRow.getLast().getX();
            int lastY = inRow.getLast().getY();
            // first är VÄNSTER
            int firstX = inRow.getFirst().getX();
            int firstY = inRow.getFirst().getY();

            ArrayList<Square> openEnds = new ArrayList<>();
            if (firstY > 0) {
                Square nextLeft = board2.getBoard2()[firstX][firstY - 1];
                if (nextLeft.getTag() == "*") {                         // om nästa VÄNSTER är ledig lägg till
                    openEnds.add(0, nextLeft);
                } else {
                    openEnds.add(0, null);
                }
            }
            if(firstY == 0){
                openEnds.add(0,null);
            }
            if (lastY < board2.getBoard2().length - 1) {
                Square nextRight = board2.getBoard2()[lastX][lastY + 1];
                if (nextRight.getTag() == "*") {                          // om nästa HÖGER är ledig lägg till
                    openEnds.add(1, nextRight);
                } else {
                    openEnds.add(1, null);
                }
            }
            if(lastY == 14){
                openEnds.add(1, null);
            }
            PRow newRow = new PRow(inRow, openEnds);
            int newSize = newRow.getPlayed().size();                                // ny radens size och öppna
            int bestSize = bestRow.getPlayed().size();                              // bästa radens size och öppna
            if (newSize >= bestSize) {
                if(newSize == bestSize){                                            // 1. om dom är lika stora

                    if(newRow.getOpenEndNr() > bestRow.getOpenEndNr()){             // 2. välj den med mest öppna
                        bestRow = newRow;
                    }
                }
                if(newSize > bestSize){                                             // 3. Om den nya är större än den gamla
                    if(newRow.getOpenEndNr() > 0){
                        bestRow = newRow;                                           // välj nya om den har fler än 0 öppna.
                    }
                }
            }
        }
        return bestRow;
    }


    private static PRow bestDiagonalR(int player) {
        PRow bestRow = new PRow();
        ArrayList<Square> totalPlayed = new ArrayList<>();
        if (player == 1) {
            totalPlayed = board2.getPlayer1pieces();
        } else {
            totalPlayed = board2.getPlayer2pieces();
        }
        for (Square s : totalPlayed) {
            LinkedList<Square> inRow = new LinkedList<>();
            int x = s.getX();
            int y = s.getY();
            inRow.add(s);
            int counter = 1;
            if ((x > 0 && (y > 0))) {
                for (; x > 0 && y > 0; x--, y--) {                                                          // kollar NERE höger till UPPE vänster
                    if (board2.getBoard2()[x - 1][y - 1].getTag() == s.getTag()) {
                        inRow.addLast(board2.getBoard2()[x - 1][y - 1]);
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            x = s.getX();
            y = s.getY();
            if (x < board2.getBoard2().length - 1 && y < board2.getBoard2().length - 1) {
                for (; x < board2.getBoard2().length - 1 && y < board2.getBoard2().length - 1; x++, y++) {   // kollar UPPE vänster till NERE höger
                    if (board2.getBoard2()[x + 1][y + 1].getTag() == s.getTag()) {
                        inRow.addFirst(board2.getBoard2()[x + 1][y + 1]);                                 // lägg till först
                        counter++;
                    } else {
                        break;
                    }
                }
            }

            // last är nästa UPPE VÄNSTER
            int lastX = inRow.getLast().getX();
            int lastY = inRow.getLast().getY();
            // first är nästa NERE HÖGER
            int firstX = inRow.getFirst().getX();
            int firstY = inRow.getFirst().getY();

            ArrayList<Square> openEnds = new ArrayList<>();
            if (firstX < board2.getBoard2().length - 1 && firstY < board2.getBoard2().length - 1) {
                Square nextDownRight = board2.getBoard2()[firstX + 1][firstY + 1];
                if (nextDownRight.getTag() == "*") {                         // om nästa NERE HÖGER är ledig
                    openEnds.add(0, nextDownRight);
                } else {
                    openEnds.add(0, null);
                }
            }
            if(firstX == 14 || firstY == 14){
                openEnds.add(0,null);
            }
            if (lastX > 0 && lastY > 0) {
                Square nextUpLeft = board2.getBoard2()[lastX - 1][lastY - 1];
                if (nextUpLeft.getTag() == "*") {                          // om nästa UPPE VÄNSTER är ledig
                    openEnds.add(1, nextUpLeft);
                }
                else {
                    openEnds.add(1, null);
                }
            }
            if(lastX == 0 || lastY == 0){
                openEnds.add(1,null);
            }
            PRow newRow = new PRow(inRow, openEnds);

            int newSize = newRow.getPlayed().size();                                // ny radens size och öppna
            //int newOpenSize = newRow.getOpenEnds().size();
            int bestSize = bestRow.getPlayed().size();                              // bästa radens size och öppna
            //int bestOpenSize = bestRow.getOpenEnds().size();

            // HÄÄÄÄR
            if (newSize >= bestSize) {
                if(newSize == bestSize){                                            // 1. om dom är lika stora

                    if(newRow.getOpenEndNr() > bestRow.getOpenEndNr()){             // 2. välj den med mest öppna
                        bestRow = newRow;
                    }
                }
                if(newSize > bestSize){                                             // 3. Om den nya är större än den gamla
                    if(newRow.getOpenEndNr() > 0){
                        bestRow = newRow;                                           // välj nya om den har fler än 0 öppna.
                    }
                }
            }
        }
        return bestRow;
    }

    // Kollar från diagonal, nedre vänster till övre höger
    private static PRow bestDiagonal(int player) {
        PRow bestRow = new PRow();
        ArrayList<Square> totalPlayed = new ArrayList<>();
        if (player == 1) {
            totalPlayed = board2.getPlayer1pieces();
        } else {
            totalPlayed = board2.getPlayer2pieces();
        }

        for (Square s : totalPlayed) {
            LinkedList<Square> inRow = new LinkedList<>();
            int x = s.getX();
            int y = s.getY();
            inRow.add(s);
            int counter = 1;
            if ((x > 0 && (y < board2.getBoard2().length - 1))) {
                for (; x > 0 && y < board2.getBoard2().length - 1; x--, y++) {                              // från NERE vänster till UPPE höger
                    if (board2.getBoard2()[x - 1][y + 1].getTag() == s.getTag()) {
                        inRow.addLast(board2.getBoard2()[x - 1][y + 1]);
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            x = s.getX();
            y = s.getY();
            if (x < board2.getBoard2().length - 1 && y > 0) {
                for (; x < board2.getBoard2().length - 1 && y > 0; x++, y--) {                              // kollar UPPIFRÅN vänster till NERE höger
                    if (board2.getBoard2()[x + 1][y - 1].getTag() == s.getTag()) {
                        inRow.addFirst(board2.getBoard2()[x + 1][y - 1]);
                        counter++;
                    } else {
                        break;
                    }
                }
            }
            // Last är längst upp till höger
            int lastX = inRow.getLast().getX();
            int lastY = inRow.getLast().getY();
            // first längst ner till vänster
            int firstX = inRow.getFirst().getX();
            int firstY = inRow.getFirst().getY();
            ArrayList<Square> openEnds = new ArrayList<>();
            if (firstX < board2.getBoard2().length - 1 && firstY > 0) {
                Square nextUp = board2.getBoard2()[firstX + 1][firstY - 1];
                if (nextUp.getTag() == "*") {                                       // om nästa NERE VÄNSRTER är ledig lägg till
                    openEnds.add(0, nextUp);
                } else {
                    openEnds.add(0, null);
                }
            }
            if(firstX == 14 || firstY == 0){
                openEnds.add(0,null);
            }
            if (lastX > 0 && lastY < board2.getBoard2().length - 1) {
                Square nextUpRight = board2.getBoard2()[lastX - 1][lastY + 1];
                if (nextUpRight.getTag() == "*") {                                  // om nästa UPPE HÖGER är ledig lägg till
                    openEnds.add(1, nextUpRight);
                } else {
                    openEnds.add(1, null);
                }
            }
            if(lastX == 0 || lastY == 14){
                openEnds.add(1,null);
            }
            PRow newRow = new PRow(inRow, openEnds);
            int newSize = newRow.getPlayed().size();                                // ny radens size och öppna
            int bestSize = bestRow.getPlayed().size();                              // bästa radens size och öppna
            if (newSize >= bestSize) {
                if(newSize == bestSize){                                            // 1. om dom är lika stora
                    if(newRow.getOpenEndNr() > bestRow.getOpenEndNr()){             // 2. välj den med mest öppna
                        bestRow = newRow;
                    }
                }
                if(newSize > bestSize){                                             // 3. Om den nya är större än den gamla
                    if(newRow.getOpenEndNr() > 0){
                        bestRow = newRow;                                           // välj nya om den har fler än 0 öppna.
                    }
                }
            }
        }
        return bestRow;
    }


}






import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Square {
    private int x;
    private int y;
    private int player;
    private String symbol;
    boolean occupied;
    ArrayList<Square> neighbors = new ArrayList<>();

    public Square(int x, int y, boolean occupied){
        this.x = x;
        this.y = y;
        this.symbol = "_";
        this.occupied = false;
        this.player = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ArrayList<Square> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Square> neighbors) {
        this.neighbors = neighbors;
    }


    public void setSquare(int player){
        if(player == 0){
            this.player = 0;
            symbol = "_";
            occupied = false;
        } else if (player == 1){
            this.player = 1;
            symbol = "X";
            occupied = true;
        } else if (player == 2){
            symbol = "O";
            this.player = 2;
            occupied = true;
        } else if (player == 3){ // granne
            symbol = "*";
            this.player = 3;
            occupied = false;
        }

    }

    public String getTag(){
        if(player == 0){
            return "_";
        }
        if(player == 1){
            return "X";
        } else if(player == 2){
            return "O";
        } else if(player == 3){
            return "*";
        }
        return "_";
    }

    @Override
    public String toString() {
        //Character.getNumericValue(getX() - 10)

        return "Square{" +
                "x:" + (x + 1) +
                " y:" + (char)(getY() + 65)+"}";
    }

    public void clear() {
        this.player = 0;
        this.occupied = false;
        this.symbol = "_";
    }
}

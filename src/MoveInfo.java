public class MoveInfo {
    Square square;
    public int value;

    public MoveInfo(Square square, int value){
        this.square = square;
        this.value = value;
    }


    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

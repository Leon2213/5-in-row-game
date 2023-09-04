import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class PRow implements Comparable{
    LinkedList<Square> played = new LinkedList<>();
    ArrayList<Square> openEnds = new ArrayList<>();
    int openEndNr;
    int openEndIndex;

    public int getOpenEndNr() {
        return openEndNr;
    }

    public void setOpenEndNr(int openEndNr) {
        this.openEndNr = openEndNr;
    }

    public int getOpenEndIndex() {
        return openEndIndex;
    }

    public void setOpenEndIndex(int openEndIndex) {
        this.openEndIndex = openEndIndex;
    }

    public PRow(LinkedList played, ArrayList openEnds) {
        this.played = played;
        this.openEnds = openEnds;
        if(openEnds.get(0) != null){
            // om båda är öppna
            if(openEnds.get(1) != null){
                openEndNr = 2;
                openEndIndex = 1;
                // om ena är öppen och andra är stängd
            } else if(openEnds.get(1) == null){
                openEndNr = 1;
                openEndIndex = 0;
            }
        }



        if(openEnds.get(0) == null){
            // om ena är öppen och andra är stängd
            if(openEnds.get(1) != null){
                openEndNr = 1;
                openEndIndex = 1;
                // om båda är stängda
            } else if(openEnds.get(1) == null){
                openEndNr = 0;
                openEndIndex = -1;
            }
        }
    }

    public PRow(){
        openEndNr = 0;
        openEndIndex = -1;
    }

    public LinkedList<Square> getPlayed() {
        return played;
    }

    public void setPlayed(LinkedList<Square> played) {
        this.played = played;
    }

    @Override
    public String toString() {
        String x = "";
        System.out.println("bästa raden är: ");
        if(!played.isEmpty()){
            for (Square s : played) {
                x +=  s.toString() + "\n";
            }
        }

        x += "och dess öppna ändar är: " + "\n";
        if(!openEnds.isEmpty()){
            for (Square s : openEnds) {
                if(s == null){
                    x += "Denna var tom";
                }
                if(s != null){
                    x +=   s.toString();
                }

            }
        }

        return x;

    }

    public ArrayList<Square> getOpenEnds() {
        return openEnds;
    }

    public void setOpenEnds(ArrayList<Square> openEnds) {
        this.openEnds = openEnds;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PRow pRow = (PRow) o;
        return openEndNr == pRow.openEndNr && openEndIndex == pRow.openEndIndex && Objects.equals(played, pRow.played) && Objects.equals(openEnds, pRow.openEnds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(played, openEnds, openEndNr, openEndIndex);
    }

    @Override
    public int compareTo(Object o) {
        PRow o1 = (PRow)o;
        if(played.size() == o1.getPlayed().size()){
            if(getOpenEndNr() > o1.getOpenEndNr()){
                return 1;
            } else if (getOpenEndNr() == o1.getOpenEndNr()){
                return 0;
            } else {
                return -1;
            }
        } else if(played.size() > o1.getPlayed().size()){
            return 1;
        } else {
            return -1;
        }
    }


}

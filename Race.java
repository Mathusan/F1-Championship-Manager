import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Race  implements Serializable {

    private Date date;
    private ArrayList<Formula1Driver> racers = new ArrayList<>();

    public Race(Date date, ArrayList<Formula1Driver> racers){
        this.date = date;
        this.racers = racers;

    }   


    public void setDate(Date date){
        this.date = date;
    }

    public void setPositions(ArrayList<Formula1Driver> racers){
        this.racers = racers;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Formula1Driver> getRacers(){
        return racers;
    }

    /*public Formula1Driver getPositions(int i ) {
        return racers.get(i);
    }*/

    public String toString(){
        return "date" + date
                + "racers" + racers;
    }

}

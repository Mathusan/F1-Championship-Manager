

public class Formula1Driver  extends  Driver   {
     private int[] positions = new int[10]; // no. of times 1st,2nd and 3rd and so on  positions[0] postions[1]
     private int points;
     private int noOfRaces;
     private final  static int[] pointScheme = {25,18,15,12,10,8,6,4,2,1};


    public Formula1Driver(String driverName, String driverLocation, String driverTeam,int noOfRaces,int[] positions) {
        super(driverName, driverLocation, driverTeam);
        this.noOfRaces = noOfRaces;
        this.positions = positions;
        calculatePoints();
    }


    //setter methods
     public void addPositions(int pos){
         this.positions[pos] += 1;
     }
     public void setPoints(int points)
     {
         this.points = points;
     }
     public void setNoOfRaces(int noOfRaces){
         this.noOfRaces = noOfRaces;
     }
     //end of setter methods



    //getter methods
    public int getPositions(int index){
        return positions[index];
    }

    public int[] getAllPositions(){
        return positions;
    }

    public int getPoints(){
        return  points;
    }
    public int getNoOfRaces() {
        return noOfRaces;
    }
    // end of getter methods




    public int calculatePoints() {
        points = 0; //set points 0 so it starts a new clean calculation
        for (int i = 0; i < positions.length; i++) {
            points = positions[i] * pointScheme[i] + points;
        }
        return points;
    }

    public String toString(){
        return super.toString() +
                "positions=" + positions +
                "points=" + points +
                "no. of races=" + noOfRaces;

    }


}

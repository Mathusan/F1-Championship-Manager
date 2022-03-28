import java.io.Serializable;

abstract public class Driver  implements Serializable  {
    private String driverName;
    private String driverLocation;
    private String driverTeam;


    //driver constructor
    public Driver(String driverName, String driverLocation, String driverTeam) {
        this.driverName = driverName;
        this.driverLocation = driverLocation;
        this.driverTeam = driverTeam;

    }

    //setter methods
    public void setDriverName(String name){
        driverName = name;
    }
    public void setDriverLocation(String location) {
        driverLocation = location;
    }
    public void setDriverTeam(String team) {
        driverTeam = team;
    }
    // end of setter methods

    //getter methods
    public String getDriverName() {
        return driverName;
    }
    public String getDriverLocation() {
        return driverLocation;
    }
    public String getDriverTeam() {
        return driverTeam;
    }

    //end of getter methods

    public String toString(){
        return "Driver Name=" + driverName +
                "Driver Team=" +driverTeam +
                "Driver Location=" +driverLocation;

    }
}

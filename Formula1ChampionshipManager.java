import java.io.*;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Formula1ChampionshipManager implements ChampionshipManager   {


    protected static ArrayList<Formula1Driver> formula1drivers = new ArrayList<Formula1Driver>();
    protected static ArrayList<Race> races = new ArrayList<>();
    protected static ArrayList<Formula1Driver> pointsSort;  // stores drivers ordered by points




    private Scanner s = new Scanner(System.in);
    private String input;



    @Override
    public void printMenu() {
        System.out.println("----------------------------------");
        System.out.println("Add driver - A");
        System.out.println("Delete driver - D");
        System.out.println("Change driver in team - C");
        System.out.println("Display statistics - S");
        System.out.println("Display F1 table - T");
        System.out.println("Add completed race statistics - R");
        System.out.println("Save to file - F"); 
        System.out.println("Graphical User Interface - G");
        System.out.println("Quit Program - Q");

    }

    @Override
    public void addDriver(){                                                              //add driver option
        System.out.println("Enter driver name");
        String name  = s.next();
        System.out.println("Enter driver location");
        String location = s.next();
        System.out.println("Enter driver team");
        String team = s.next();
        int positions[] = new int[10];
        int races =0;
        while(true){
            System.out.println("Choose a position and enter how many times the position was won");
            System.out.println("choose position from 1 - 10");
            int position = s.nextInt();
            System.out.println("enter no of times position: " + position + " was obtained.");
            int times = s.nextInt();
            positions[position -1] = times;
            System.out.println("Do you want to continue yes/no");
            input = s.next();
            if(input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")){
                break;
            }else if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")){
                continue;
            }
        }
        for(int i=0;i<positions.length;i++){
            races = races + positions[i];
        }
        //verifying section
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Team: " + team);

        System.out.println("Add driver to championship (yes/no)");
        input = s.next();
        if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")){
            Formula1Driver driverAdded = new Formula1Driver(name,location,team,races,positions);
            formula1drivers.add(driverAdded); // add driver to list
            sortDrivers();  // sorting driver after updating list

        }else{
            System.out.println("No driver added");

        }


    }

    @Override
    public void deleteDriver() {                                                       //delete driver option
        System.out.println("Select driver to delete");
        printDriverList();
        String delete = s.next();
        for(Formula1Driver f1 : formula1drivers){
            if (f1.getDriverName().toLowerCase().equals(delete.toLowerCase())){
                    System.out.println("Do you want to delete: "  + f1.getDriverName());
                    System.out.println("Enter yes/no");
                    String option = s.next();

                    if(option.toLowerCase().equals("yes")){
                        formula1drivers.remove(f1);
                        System.out.println("------------------------------------------------------------------------------");
                        System.out.println(f1.getDriverName() + " was successfully deleted");
                        sortDrivers();
                        return;
                    }else{
                        return;
                    }
            }
        }
        System.out.println(delete + " is not in the F1 championship");
    }
    @Override
    public void displayStats() {                                                      //display stats option
        sortDrivers();
        System.out.println("Select driver to statistics of ");
        printDriverList();
        input = s.next();
        for (Formula1Driver f1 : formula1drivers) {
            if (f1.getDriverName().toLowerCase().equals(input.toLowerCase())) {
                System.out.println("name: " + f1.getDriverName());
                System.out.println("location: " + f1.getDriverLocation());
                System.out.println("team: " + f1.getDriverTeam());
                System.out.println("no of races: " + f1.getNoOfRaces());
                System.out.println("points: " + f1.getPoints());
            }
        }
    }
    @Override
    public void changeDriver(){                                                       // change driver option
        System.out.println("Select team to change driver  ");
        printDriverList();
        input = s.next();
        for(Formula1Driver f1 : formula1drivers) {
            if (f1.getDriverName().toLowerCase().equals(input.toLowerCase()) || f1.getDriverTeam().toLowerCase().equals(input.toLowerCase())) {   // user can enter either driver name or team name
                System.out.println("Set new driver for team  " + f1.getDriverTeam());
                input = s.next();
                f1.setDriverName(input);
                sortDrivers();
            }
        }
    }
    @Override
    public void displayF1Table(){                                                   // display f1 table option
        sortDrivers();
        System.out.println("---------------------------POINTS TABLE OF THE F1 CHAMPIONSHIP---------------------------");
        System.out.println("name\tTeam\tPoints\t1STs\t2NDs\t3RD\t ");
        for(Formula1Driver f1 : pointsSort){
            System.out.println( f1.getDriverName() + "\t" +
                                f1.getDriverTeam() + "\t" +
                                f1.getPoints() + "\t" +
                                f1.getPositions(0)+ "\t" +
                                f1.getPositions(1)+ "\t" +
                                f1.getPositions(2));

        }
    }
    @Override
    public void addRace() {                                                         // add race option
        System.out.println("Enter date of race in mm-dd-yyyy format: ");
        String dateInput = s.next();
        Date date;
        try{
            date = new SimpleDateFormat("MM-dd-yyyy").parse(dateInput);
        }catch (ParseException ex){
            System.out.println("Invalid Date Format");
            return;
        }



        int i = 0;
        //https://stackoverflow.com/questions/6536094/java-arraylist-copy
        ArrayList<Formula1Driver> f1copy = new ArrayList<>(formula1drivers);
        ArrayList<Formula1Driver> racesPos = new ArrayList<>();
        while(i < formula1drivers.size()){
            for(Formula1Driver drivers : f1copy){
                System.out.println(drivers.getDriverName());
            }
            System.out.println("Enter driver who won position: " + (i+1));
            input = s.next();
            Formula1Driver selectedDriver = null;
            for(Formula1Driver f1 : formula1drivers){
                if(f1.getDriverName().toLowerCase().equals(input.toLowerCase()) && f1copy.contains(f1)){ // checks if driver is in league and also if driver position was set already
                    selectedDriver = formula1drivers.get(formula1drivers.indexOf(f1));
                }
            }
            if(selectedDriver == null || formula1drivers.contains(selectedDriver) == false){
                System.out.println("Invalid Input");
            }else{
                selectedDriver.setNoOfRaces(selectedDriver.getNoOfRaces()+1);
                selectedDriver.addPositions(i);
                selectedDriver.calculatePoints();
                racesPos.add(selectedDriver);
                sortDrivers();  // sorting driver after updating list
                //https://stackoverflow.com/questions/36028995/remove-object-from-arraylist-with-some-object-property
                f1copy.removeIf(Formula1Driver -> Formula1Driver.getDriverName().equalsIgnoreCase(input));
                i++;

                //verifying section;



            }
        }
        races.add(new Race(date,racesPos));
    }

    @Override
    public void saveToFile() throws IOException{
        //https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/

            FileOutputStream f = new FileOutputStream(new File("F1manager.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            for(Formula1Driver f1 : formula1drivers){
                o.writeObject(f1);
            }

            FileOutputStream fr = new FileOutputStream(new File("Races.txt"));
            ObjectOutputStream or = new ObjectOutputStream(fr);

            for(Race r : races){
                or.writeObject(r);
            }


            o.flush();
            f.close();
            o.close();


            or.flush();
            fr.close();
            or.close();


    }
    @Override
    public void loadFile() throws  IOException,ClassNotFoundException{
        FileInputStream fi = new FileInputStream("F1Manager.txt");
        ObjectInputStream oi = new ObjectInputStream(fi);

        for(;;){
            try{
                Formula1Driver f1 =    (Formula1Driver) oi.readObject();
                formula1drivers.add(f1);
                //System.out.println(f1);
            }catch(EOFException e ){
                break;

            }
        }
        fi.close();
        oi.close();

        System.out.println("---------------------------------------------------------");
        System.out.println(" Drivers Loaded Successfully");


        FileInputStream fir = new FileInputStream("Races.txt");
        ObjectInputStream oir = new ObjectInputStream(fir);

        for(;;){
            try{
                Race r =    (Race) oir.readObject();
                races.add(r);
                //System.out.println(f1);
            }catch(EOFException e ){
                break;

            }
        }
        fir.close();
        oir.close();

        System.out.println("---------------------------------------------------------");
        System.out.println(" Races Loaded Successfully");

        sortDrivers();  // sorting driver after updating list


    }


    public void printDriverList() {
        for (int i = 0; i < formula1drivers.size(); i++) {
            System.out.println(i + 1 + ". " + formula1drivers.get(i).getDriverName() + " " + formula1drivers.get(i).getDriverTeam());
        }
    }



    public void sortDrivers(){
        pointsSort = new  ArrayList<>(formula1drivers); // makes sure all new drivers are updating before sorting

        Collections.sort(pointsSort, new Comparator<Formula1Driver>() {
            @Override
            public int compare(Formula1Driver o1, Formula1Driver o2) {
                if(o1.getPoints() > o2.getPoints()){
                    return -1;
                }else if (o1.getPoints() < o2.getPoints()){
                    return 1;
                }else{
                    if(o1.getPositions(0) >o2.getPositions(0)){
                        return -1;
                    }else if (o1.getPositions(0) <o2.getPositions(0)){
                        return 1;
                    }else{
                        return 0;
                    }
                }
            }
        });
        }




}

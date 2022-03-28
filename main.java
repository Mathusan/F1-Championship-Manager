import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner s = new Scanner(System.in);
        String input;

        Formula1ChampionshipManager f1manager = new Formula1ChampionshipManager();



        try{                                        // load previous file if available
            f1manager.loadFile();
        }catch (FileNotFoundException e){
            System.out.println("No file found");

        }


        do{
            f1manager.printMenu();
            f1manager.sortDrivers();
            input = s.next().toUpperCase();
            switch (input){
                case("A"):
                    f1manager.addDriver();
                    break;
                case("D"):
                    f1manager.deleteDriver();
                    break;
                case("C"):
                    f1manager.changeDriver();
                    break;
                case("S"): //
                    f1manager.displayStats();
                    break;
                case("T"):
                    f1manager.displayF1Table();
                    break;
                case("R"):
                    f1manager.addRace();
                    break;
                case("F"):
                    f1manager.saveToFile();
                    break;
                case("G"):
                    new gui();
                    break;

            }
        }while(!input.equals("Q"));





    }
}

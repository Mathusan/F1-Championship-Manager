import java.io.IOException;

public interface ChampionshipManager {

    void printMenu();
    void addDriver();
    void deleteDriver();
    void displayStats();
    void changeDriver();
    void displayF1Table();
    void addRace();
    void saveToFile() throws IOException;
    void loadFile() throws IOException, ClassNotFoundException;
    //void gui();

}

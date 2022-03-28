import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class gui extends  Formula1ChampionshipManager implements ActionListener {

    private Object[][] data;
    private String[] columnNames ={"Name","Team","Location","Points","1st","2nd","3rd"} ;
    private String[] driverColumn = {"Date ", "Position"};
    private String[] raceColumn = {"Date","1st","2nd","3rd"};

    private DefaultTableModel tableModel;
    private JTable jtable;
    private DefaultTableModel driverModel;
    private JTable driverTable;


    private JFrame frame = new JFrame();
    private JFrame j;
    private JFrame warning;
    private  JFrame driver;


    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();


    private JButton randomRace = new JButton("Generate Random");
    private JButton calculatedRace = new JButton("Calculated Race");
    private JButton displayRace = new JButton("Display Race");
    private JButton search = new JButton("Search");
    private JButton refresh = new JButton("Refresh");


    private JRadioButton rb1 = new JRadioButton("Ascending Order");
    private JRadioButton rb2 = new JRadioButton("Descending Order");
    private JCheckBox checkBox = new JCheckBox("Sort by 1st Pos");

    private final JTextField text;
    private JLabel driverLabel = new JLabel();

    private  String searchInput;


    public gui(){

        frame.setLayout(new GridLayout(3,1));

        //Table
        tableModel = new DefaultTableModel(convertData(pointsSort),columnNames);

        jtable = new JTable(tableModel);
        jtable.setBounds(0,0,800,400);
        panel.add(new JScrollPane(jtable));



        // Buttons
        ButtonGroup group = new ButtonGroup();
        group.add(rb1);
        group.add(rb2);


        rb2.setSelected(true);  // initially drivers are sorted by points in descending order
        rb1.setBounds(20,700,150,50);
        rb1.addActionListener(this);
        panel2.add(rb1);
        rb2.setBounds(200,700,150,50);
        rb2.addActionListener(this);
        panel2.add(rb2);

        checkBox.addActionListener(this);
        panel2.add(checkBox);

        randomRace.setBounds(20,800,150,50);
        randomRace.addActionListener(this);
        panel2.add(randomRace);

        calculatedRace.setBounds(200,800,150,50);
        calculatedRace.addActionListener(this);
        panel2.add(calculatedRace);

        displayRace.setBounds(20,900,150,50);
        displayRace.addActionListener(this);
        panel2.add(displayRace);

        refresh.addActionListener(this);
        panel2.add(refresh);

        text = new JTextField(30);
        search.addActionListener(this);
        panel3.add(text);
        panel3.add(search);
        panel3.add(driverLabel);

        // JFrame setup
        frame.add(panel);
        frame.add(panel2);
        frame.add(panel3);

        frame.setSize(800,1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }



    //gui methods
    //http://www2.hawaii.edu/~takebaya/ics111/jtable_basic/jtable_basic.html
    public  Object[][] convertData(ArrayList<Formula1Driver> drivers){


        Object[][] data = new Object[drivers.size()][7];
        for(int i =0 ;i< drivers.size();i++){
            data[i][0] = drivers.get(i).getDriverName();
            data[i][1] = drivers.get(i).getDriverTeam();
            data[i][2] = drivers.get(i).getDriverLocation();
            data[i][3] = drivers.get(i).getPoints();
            data[i][4] = drivers.get(i).getPositions(0);
            data[i][5] = drivers.get(i).getPositions(1);
            data[i][6] = drivers.get(i).getPositions(2);

        }
        return  data;
    }

    public void  sortByFirst(){
        ArrayList<Formula1Driver> posSort = new ArrayList<>(formula1drivers);
        Collections.sort(posSort, new Comparator<Formula1Driver>() {
            @Override
            public int compare(Formula1Driver o1, Formula1Driver o2) {
                if(o1.getPositions(0) >o2.getPositions(0)){
                    return -1;
                }else if (o1.getPositions(0) <o2.getPositions(0)){
                    return 1;
                }else{
                    return 0;
                }

            }
        });
        jtable.setModel(new DefaultTableModel(convertData(posSort),columnNames));
    }




    public void generateRandomRace(){
         j = new JFrame("Add Date");
        String name = JOptionPane.showInputDialog(j,"Enter date of race (mm-dd-yyyy)");
        Date date;
        try{
            date = new SimpleDateFormat("MM-dd-yyyy").parse(name);
        } catch (ParseException ex){
            warning = new JFrame();
            JOptionPane.showMessageDialog(warning,"Invalid Date format","Alert",JOptionPane.WARNING_MESSAGE);
            return;
        }
        ArrayList<Formula1Driver> random = new ArrayList<>(formula1drivers);
        Collections.shuffle(random);
        int i = 0;
        for(Formula1Driver f1 : random){
            for(Formula1Driver f : formula1drivers){
                if(f.getDriverName().equals(f1.getDriverName())){
                    f.setNoOfRaces(f.getNoOfRaces() + 1 );
                    f.addPositions(i);
                    f.calculatePoints();
                    sortDrivers();
                    i++;
                }
            }
        }
        races.add(new Race(date,random));
        System.out.println("Race added succesfully");
    }

    public void generateProbablisticRace(){
        j = new JFrame("Add Date");
        String name = JOptionPane.showInputDialog(j,"Enter date of race");
        Date date;
        try{
            date = new SimpleDateFormat("MM-dd-yyyy").parse(name);
        } catch (ParseException ex){
            warning = new JFrame();
            JOptionPane.showMessageDialog(warning,"Invalid Date format","Alert",JOptionPane.WARNING_MESSAGE);
            return;
        }
        ArrayList<Formula1Driver> randomStartingPoints = new ArrayList<>(formula1drivers);
        Collections.shuffle(randomStartingPoints);


        //display starting positions
        for(Formula1Driver f1 :randomStartingPoints){
            System.out.println(f1.getDriverName());
        }

        Integer[] value = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                3, 3, 3, 3, 3, 3, 3, 3, 3, 3,4, 4, 4, 4, 4, 4, 4, 4, 4, 4,5,5,6,6,7,7,8,8,9,9};

        List<Integer> l = Arrays.asList(value);
        Collections.shuffle(l);


        Formula1Driver firstplace = null;

        Random r = new Random();
        int pos = r.nextInt(value.length);
        int p = l.get(pos);

        switch(p){
            case(1):
                firstplace = randomStartingPoints.get(0);
                break;
            case(2):
                firstplace = randomStartingPoints.get(1);
                break;
            case(3):
                firstplace = randomStartingPoints.get(2);
                break;
            case(4):
                firstplace = randomStartingPoints.get(3);
                break;
            case(5):
                firstplace = randomStartingPoints.get(4);
                break;
            case(6):
                firstplace = randomStartingPoints.get(5);
                break;
            case(7):
                firstplace = randomStartingPoints.get(6);
                break;
            case(8):
                firstplace = randomStartingPoints.get(7);
                break;
            case(9):
                firstplace = randomStartingPoints.get(8);
                break;
        }


        randomStartingPoints.remove(firstplace);
        ArrayList<Formula1Driver> finalPos = new ArrayList<>(randomStartingPoints);
        Collections.shuffle(finalPos);
        ArrayList<Formula1Driver> firstPos = new ArrayList<>();
        finalPos.add(firstplace);
        firstPos.addAll(finalPos);

        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println("");

        for(Formula1Driver f1 :firstPos){
            System.out.println(f1.getDriverName());
        }

        races.add(new Race(date,firstPos));
        System.out.println("Race added succesfully");
         // display finishing positions;
    }

    public void searchDriver(){
        searchInput= text.getText();
        for(Formula1Driver f1 : formula1drivers){
            if(f1.getDriverName().equalsIgnoreCase(searchInput)){
                driverLabel.setText(f1.getDriverName());
                Object[][] data = new Object[races.size()][2];
                int i = 0;
                for(Race r : races){
                    for(Formula1Driver f : r.getRacers()){
                            if(f.getDriverName().equals(f1.getDriverName())){
                                data[i][0] = r.getDate();
                                data[i][1] = (r.getRacers().indexOf(f) + 1 );
                                i++;
                            }
                    }

                }
                text.setText("");
                driver = new JFrame("Driver Info");
                driverModel = new DefaultTableModel(data,driverColumn);
                driverTable = new JTable(driverModel);
                driver.add(new JScrollPane(driverTable));
                driver.setSize(300,300);
                driver.setVisible(true);
                return;
            }
        }
        driverLabel.setText("Invalid input");
        text.setText("");
    }

    public void showRaces(){
        ArrayList<Race> dateSorted = new ArrayList<>(races);
        Collections.sort(dateSorted, new Comparator<Race>() {
            @Override
            public int compare(Race o1, Race o2) {
                if(o1.getDate() == null || o2.getDate() == null){
                    return 0;
                }
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        //sorted
        Object[][] data = new Object[dateSorted.size()][4];
        int i =0;
        for(Race r : dateSorted){
            ArrayList<Formula1Driver> racers = r.getRacers();
                data[i][0] = r.getDate();
                data[i][1] = racers.get(0).getDriverName();
                data[i][2] = racers.get(1).getDriverName();
                data[i][3] = racers.get(2).getDriverName();
                i++;
        }
        jtable.setModel(new DefaultTableModel(data,raceColumn));
        }





    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == randomRace){
            generateRandomRace();
        }else if(e.getSource() == displayRace){
            showRaces();
        }else if(e.getSource() == checkBox){
            if(checkBox.isSelected() == true){
                sortByFirst();
                rb1.setEnabled(false);
                rb2.setEnabled(false);
            }else if (checkBox.isSelected() == false){
                rb1.setEnabled(true);
                rb2.setEnabled(true);
                jtable.setModel(new DefaultTableModel(convertData(pointsSort),columnNames));
            }
        }else if(e.getSource() == search){
            searchDriver();
        }else if(e.getSource() == rb1){
            ArrayList<Formula1Driver> duplicate = new ArrayList<>(pointsSort);
            Collections.reverse(duplicate);
            jtable.setModel(new DefaultTableModel(convertData(duplicate),columnNames));
        }else if(e.getSource() == rb2){
            jtable.setModel(new DefaultTableModel(convertData(pointsSort),columnNames));
        }else if(e.getSource() == refresh){
            jtable.setModel(new DefaultTableModel(convertData(pointsSort),columnNames));
            checkBox.setSelected(false);
            rb1.setEnabled(true);
            rb2.setEnabled(true);
            rb2.setSelected(true);
        }
        else if(e.getSource() == calculatedRace){
            generateProbablisticRace();
        }
        //https://coderedirect.com/questions/153510/how-to-refresh-data-in-jtable-i-am-using-tablemodel
    }
    public int[] autoArray(int times,int value){
        int[] a = new int[times];
        for(int i = 0;i<a.length;i++){
            a[i] = value;
        }
        return  a;
    }
}






//https://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist


//  https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
// https://stackoverflow.com/questions/8183840/probability-in-java



    // display  drivers in descending order
    //give user  option to choose to ascending
    // give user option to sort by no of first places won
    // generate random race
    // generate random race with proababilty
    //display races by date asscendning
    // search box



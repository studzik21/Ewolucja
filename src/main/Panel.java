import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel extends JPanel implements ActionListener {
    public final  int height= 500;
    public final  int width= 300;

    private JTextField delay;
    private JTextField startEnergy;
    private JTextField mapWidth;
    private JTextField mapHeight;
    private JTextField jungleRatio;
    private JTextField moveEnergy;
    private JTextField plantEnergy;
    private JTextField numberOfAnimals;
    private JTextField numberOfGrass;

    private final JLabel delayL = new JLabel("Delay:                       ");
    private final JLabel startEnergyL =new JLabel("Aniaml start energy: ");;
    private final JLabel mapWidthL = new JLabel("Map width:        ");
    private final JLabel mapHeightL= new JLabel("Map height:          ");
    private final JLabel junleRatioL= new JLabel("Juble Ratio: ");
    private final JLabel moveEnergyL=new JLabel("Animal move energy: ");
    private final JLabel plantEnergyL= new JLabel("Aniamal food energy: ");
    private final JLabel numberOfAnimalsL= new JLabel("Number of animals: ");
    private final JLabel numberOfGrassL= new JLabel("Number of grass: ");

    private JButton button1=new JButton("Start one map");
    private JButton button2=new JButton("Start two maps");



    public Panel(JSONObject jsonObject) {
        button1.addActionListener(this);
        button2.addActionListener(this);
        button1.setActionCommand("one");
        button2.setActionCommand("two");



        add(new JLabel("                 Enter data                        "));

        delay = new JTextField();
        delay.setColumns(10);
        delay.setText(jsonObject.get("delay").toString());

        startEnergy = new JTextField();
        startEnergy.setColumns(10);
        startEnergy.setText(jsonObject.get("startEnergy").toString());

        mapWidth = new JTextField();
        mapWidth.setColumns(10);
        mapWidth.setText(jsonObject.get("width").toString());

        mapHeight = new JTextField();
        mapHeight.setColumns(10);
        mapHeight.setText(jsonObject.get("height").toString());


        jungleRatio = new JTextField();
        jungleRatio.setColumns(10);
        jungleRatio.setText(jsonObject.get("jungleRatio").toString());

        moveEnergy = new JTextField();
        moveEnergy.setColumns(10);
        moveEnergy.setText(jsonObject.get("moveEnergy").toString());

        plantEnergy= new JTextField();
        plantEnergy.setColumns(10);
        plantEnergy.setText(jsonObject.get("plantEnergy").toString());

        numberOfAnimals = new JTextField();
        numberOfAnimals.setColumns(10);
        numberOfAnimals.setText(jsonObject.get("noOfAnim").toString());

        numberOfGrass = new JTextField();
        numberOfGrass.setColumns(10);
        numberOfGrass.setText(jsonObject.get("noOfGrass").toString());



        JPanel delayPanel= new JPanel();
        JPanel startEnergyPanel= new JPanel();
        JPanel mapWidthPanel= new JPanel();
        JPanel mapHeightPanel= new JPanel();
        JPanel jungleRatioPanel= new JPanel();
        JPanel moveEnergyPanel= new JPanel();
        JPanel plantEnergyPanel= new JPanel();
        JPanel noOfAnimalPanel= new JPanel();
        JPanel noOfGrassPanel= new JPanel();
        JPanel buttonPanel= new JPanel();




        delayPanel.add(delayL);
        delayPanel.add(delay);


        startEnergyPanel.add(startEnergyL);
        startEnergyPanel.add(startEnergy);

        mapWidthPanel.add(mapWidthL);
        mapWidthPanel.add(mapWidth);

        mapHeightPanel.add(mapHeightL);
        mapHeightPanel.add(mapHeight);

        jungleRatioPanel.add(junleRatioL);
        jungleRatioPanel.add(jungleRatio);

        moveEnergyPanel.add(moveEnergyL);
        moveEnergyPanel.add(moveEnergy);

        plantEnergyPanel.add(plantEnergyL);
        plantEnergyPanel.add(plantEnergy);

        noOfAnimalPanel.add(numberOfAnimalsL);
        noOfAnimalPanel.add(numberOfAnimals);

        noOfGrassPanel.add(numberOfGrassL);
        noOfGrassPanel.add(numberOfGrass);



        buttonPanel.add(button1);
        buttonPanel.add(button2);
        add(delayPanel);
        add(startEnergyPanel);
        add(mapHeightPanel);
        add(mapWidthPanel);
        add(jungleRatioPanel);
        add(moveEnergyPanel);
        add(plantEnergyPanel);
        add(noOfAnimalPanel);
        add(noOfGrassPanel);
        add(buttonPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command.equals("one")) {
            RectangularMap map = new RectangularMap(
                    Integer.parseInt(mapWidth.getText()),
                    Integer.parseInt(mapHeight.getText()),
                    Integer.parseInt(jungleRatio.getText()),
                    Integer.parseInt(moveEnergy.getText()),
                    Integer.parseInt(startEnergy.getText()),
                    Integer.parseInt(plantEnergy.getText()),
                    Integer.parseInt(numberOfGrass.getText()));

            SimulationEngine engine = new SimulationEngine(map,Integer.parseInt(numberOfAnimals.getText()),Integer.parseInt(delay.getText()));
            engine.run();

        }
        else if(command.equals("two")){
            RectangularMap map1 = new RectangularMap(
                    Integer.parseInt(mapWidth.getText()),
                    Integer.parseInt(mapHeight.getText()),
                    Integer.parseInt(jungleRatio.getText()),
                    Integer.parseInt(moveEnergy.getText()),
                    Integer.parseInt(startEnergy.getText()),
                    Integer.parseInt(plantEnergy.getText()),
                    Integer.parseInt(numberOfGrass.getText())
            );

            RectangularMap map2 = new RectangularMap(
                    Integer.parseInt(mapWidth.getText()),
                    Integer.parseInt(mapHeight.getText()),
                    Integer.parseInt(jungleRatio.getText()),
                    Integer.parseInt(moveEnergy.getText()),
                    Integer.parseInt(startEnergy.getText()),
                    Integer.parseInt(plantEnergy.getText()),
                    Integer.parseInt(numberOfGrass.getText())
            );

            SimulationEngine engine1 = new SimulationEngine(map1,Integer.parseInt(numberOfAnimals.getText()),Integer.parseInt(delay.getText()));
            engine1.run();


            SimulationEngine engine2 = new SimulationEngine(map2,Integer.parseInt(numberOfAnimals.getText()),Integer.parseInt(delay.getText()));
            engine2.run();


        }



    }
}

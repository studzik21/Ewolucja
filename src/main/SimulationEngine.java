import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimulationEngine implements ActionListener, IEngine {


    private RectangularMap map;
    private final int noStartAnimals;
    public final int delay;
    public Timer timer;
    private final StatisticWriter statisticWriter;
    public FieldPanel fieldPanel;
    public Statistic statistic;



    SimulationEngine(RectangularMap map,int noAnimal, int delay) {
        this.delay=delay;
        this.noStartAnimals=noAnimal;
        this.map = map;
        this.statistic=new Statistic(this.map);
        add();
        this.statisticWriter = new StatisticWriter(statistic);
        this.fieldPanel = new FieldPanel(map.getMapVisualizer(),map,this,statistic);
        timer=new Timer(delay,this);




    }

    public void add(){
        int i=0;
        List<String> genes = new ArrayList<>();
        while(i<noStartAnimals){
            Random generator = new Random();
            Vector2d tmp = new Vector2d(generator.nextInt(this.map.getPositionWH().x+1),generator.nextInt(this.map.getPositionWH().y+1));

            if(this.map.canPlace(tmp)) {
                Animal animal = new Animal(map, tmp,map.startEnergy);
                genes.add(animal.genes.getGenesString());
                this.map.place(animal);
                i++;

            }

        }
        statistic.addGenes(genes);
    }



    public void run() {
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        statistic.addDay();
        String command = e.getActionCommand();

        if(command!=null) {
            if (command.equals("START"))
                timer.start();
            if (command.equals("STOP"))
                timer.stop();
            if(command.equals("SAVE"))
                this.statisticWriter.writeStatisticToFile("archive.txt");

        }
        int tmp[]= this.map.removeDead(statistic.getDays());
        statistic.addDead(Arrays.copyOfRange(tmp,0,2));
        //statistic.addDead(this.map.removeDead(statistic.getDays()));
        if (tmp[2]==1) timer.stop();

        for(Animal animal: map.getAnimalList())
        {
            if(animal.getEnergy()>0) {
                animal.move();          //if nie potrzebny?

            }
        }

        this.map.eat();
        statistic.addGenes(this.map.copulation());

        this.map.addGrass();
        this.fieldPanel.updateContent();

        //System.out.println(map.toString());
    }



}

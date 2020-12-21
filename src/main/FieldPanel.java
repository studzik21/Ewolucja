import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FieldPanel {

    private RectangularMap map;
    private SimulationEngine simulationEngine;
    private MapVisualizer mapVisualizer;
    private JFrame frame;
    private JMenuBar simulationWindowMenuBar;
    private JMenuItem stop;
    private JMenuItem start;
    private JMenuItem save;
    Statistic statistic;
    PlotRender plotRender;

    public FieldPanel(MapVisualizer mV,RectangularMap map,SimulationEngine simulationEngine, Statistic statistic){

        this.map=map;
        this.statistic=statistic;
        plotRender=new PlotRender(statistic);
        this.simulationEngine=simulationEngine;
        this.mapVisualizer=mV;
        this.frame = new JFrame("Simulation");

        frame.setSize(1000,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        simulationWindowMenuBar = new JMenuBar();
        stop= new JMenuItem("STOP");
        start = new JMenuItem("START");
        save = new JMenuItem("SAVE");
        start.setActionCommand("START");
        stop.setActionCommand("STOP");
        start.addActionListener(simulationEngine);
        stop.addActionListener(simulationEngine);
        save.addActionListener(simulationEngine);


        simulationWindowMenuBar.add(start);
        simulationWindowMenuBar.add(stop);
        simulationWindowMenuBar.add(save);

        

        frame.setJMenuBar(simulationWindowMenuBar);
        frame.setLayout(new GridLayout(1,2));
        frame.add(plotRender);
        frame.add(this.mapVisualizer);
        frame.setVisible(true);


    }

    public  void updateContent(){
        mapVisualizer.updateMap();
        plotRender.updateLabels();
        plotRender.repaint();
        mapVisualizer.repaint();


    }



}

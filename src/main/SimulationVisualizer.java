import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationVisualizer  {
    JFrame frame;


    SimulationVisualizer() {

        frame = new JFrame("Settings");
        frame.setSize(300,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void loadData(JSONObject jsonObject){
        frame.add(new Panel(jsonObject));
        frame.setVisible(true);

    }


}

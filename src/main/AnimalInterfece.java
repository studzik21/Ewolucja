import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimalInterfece extends JFrame implements ActionListener {
    private final Animal animal;

    private JPanel genomPanel = new JPanel();
    private JPanel childrenPanel = new JPanel();
    private JPanel deadPanel = new JPanel();
    private JPanel successorPanel = new JPanel();

    JLabel successorsText = new JLabel("Number of successors: ");
    JLabel childrenText= new JLabel("Number of children: ");
    JLabel deadText = new JLabel("Date of death: ");


    private JLabel genLabel=new JLabel();
    JLabel childrenLabel= new JLabel();
    JLabel succesorsLabel= new JLabel();
    JLabel deadLabel = new JLabel();

    JButton button = new JButton("HIDE");



    public AnimalInterfece(Animal animal) {
        this.animal=animal;


        genomPanel.add(genLabel);
        childrenPanel.add(childrenText);
        childrenPanel.add(childrenLabel);
        deadPanel.add(deadText);
        deadPanel.add(deadLabel);
        successorPanel.add(successorsText);
        successorPanel.add(succesorsLabel);

        add(button);
        add(genomPanel);
        add(successorPanel);
        add(childrenPanel);
        if(animal.getDateOfDeath()!=0) add(deadPanel);
        setLayout(new GridLayout(5,1));

        setSize(300,400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        button.addActionListener(this);

    }

    public void update(){
        deadLabel.setText(String.valueOf(animal.getDateOfDeath()));
        succesorsLabel.setText(String.valueOf(animal.successors()));
        genLabel.setText(animal.genes.getGenesString());
        childrenLabel.setText(String.valueOf(animal.getChildrensSize()));
        if(animal.getDateOfDeath()!=0) add(deadPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        animal.followed=1;

    }
}

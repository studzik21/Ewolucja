import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapVisualizer extends JPanel{
    private final RectangularMap map;
    private JLabel mapVisualization[][];




    public  MapVisualizer(RectangularMap map){

        this.map = map;
        this.mapVisualization = new JLabel[map.getMapWidth()][map.getMapHeight()];
        setLocation(600,0);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(new EmptyBorder(0,0,0,0));
        for(int i=0;i<map.getMapWidth();i++)
            for(int j=0;j<map.getMapHeight();j++){
                this.mapVisualization[i][j]=new JLabel();
                this.mapVisualization[i][j].setText(" ");
                this.mapVisualization[i][j].setBackground(Color.ORANGE);
                //this.mapVisualization[i][j].setBorder(border);
                this.mapVisualization[i][j].setOpaque(true);


                add(this.mapVisualization[i][j]);

            }
        setSize(getPreferredSize());
        setLayout(new GridLayout(map.getMapHeight(),map.getMapHeight()));


    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600,962);
    }





    public void updateMap(){
        for(int i=0;i<map.getMapWidth();i++)
            for(int j=0;j<map.getMapHeight();j++){
                Vector2d tmpVec = new Vector2d(i,j);
                if(!this.map.isOccupied(tmpVec)){
                    this.mapVisualization[i][j].setBackground(new Color(170, 224, 103));
                    this.mapVisualization[i][j].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {

                        }
                    });
                }
                else if(this.map.objectAt(tmpVec) instanceof Animal){
                this.mapVisualization[i][j].setBackground(((Animal) this.map.objectAt(tmpVec)).getColor());
                    int finalI = i;
                    int finalJ = j;
                    this.mapVisualization[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Animal firstAnimal = (Animal) map.objectAt(new Vector2d(finalI, finalJ));
                        firstAnimal.display();

                        }
                    });
                }
                else if(this.map.objectAt(tmpVec) instanceof Grass){
                    this.mapVisualization[i][j].setBackground(new Color(0,100,0));
                }


            }



    }



}
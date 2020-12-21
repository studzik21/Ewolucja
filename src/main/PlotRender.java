import javax.swing.*;
import java.awt.*;

public class PlotRender extends JPanel {
    private Plot plot;
    private Statistic statistic;
    private StatisticLabel statisticLabel;

    public PlotRender(Statistic statistic) {
        this.statistic=statistic;
        this.statisticLabel=new StatisticLabel();
        this.plot=new Plot(statistic);
        add(plot);
        add(this.statisticLabel);


    }
    public void updateLabels(){ this.statisticLabel.updateLabels();}

    class StatisticLabel extends JPanel{
        private JLabel currentNoOfAnimals;
        private JLabel currentNoOfGrass;
        private JLabel avgLifeTime;
        private JLabel avgNoOfChildren;
        private JLabel days;
        private JLabel mostCommonGen;

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(300,262);

        }

        StatisticLabel(){

            setSize(new Dimension(300,600));
            this.currentNoOfAnimals=new JLabel();
            this.currentNoOfGrass=new JLabel();
            this.avgLifeTime=new JLabel();
            this.avgNoOfChildren=new JLabel();
            this.days=new JLabel();
            this.mostCommonGen= new JLabel();


            this.add(days);
            this.add(avgLifeTime);
            this.add(avgNoOfChildren);
            this.add(currentNoOfAnimals);
            this.add(currentNoOfGrass);
            this.add(mostCommonGen);

            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));


        }
        public void updateLabels(){
            this.currentNoOfGrass.setText("Current number of grasses:  "+ statistic.getCurrentGrass());
            this.currentNoOfAnimals.setText("Current number of animals: "+statistic.numberOfLivingAnimals());
            this.avgNoOfChildren.setText("Average number of children: "+ statistic.avgAmountOfChildren());
            this.avgLifeTime.setText("Average LifeTime: "+ statistic.getAvgLifeTime());
            this.days.setText("Day: "+ statistic.getDays());
            this.mostCommonGen.setText("MostCommonGen: "+statistic.getMostCommonGen());

        }



    }

    class Plot extends JPanel{
        private final Statistic statistic;

        public Plot(Statistic statistic){
            this.statistic=statistic;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500,700);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Vector2d panelSize = new Vector2d(getSize().width,getSize().height);
            g.setColor(Color.RED);
            g.clearRect(0,0,panelSize.x,panelSize.y);

            //setMinimumSize(new Dimension(500,500));
            setBackground(Color.CYAN);
            float max =-1;
            for(Integer animalSize: this.statistic.getAnimalsToPlot())
                max=Math.max(max,animalSize);

            for(Integer grassSize: this.statistic.getGrassToPlot())
                max=Math.max(max,grassSize);

            g.setColor(Color.black);
            g.drawLine(0,500,500,500);
            g.drawLine(0,0,0,500);


            for(int i=0;i<this.statistic.getAnimalsToPlot().size()-1;i++) {
                g.setColor(Color.RED);
                g.drawLine(500 / this.statistic.getAnimalsToPlot().size() * i,
                        (int) (500 - 450.0 / max * statistic.getAnimalsToPlot().get(i)),
                        500 / this.statistic.getAnimalsToPlot().size() * (i + 1),
                        (int) (500 - 450.0 / max * statistic.getAnimalsToPlot().get(i + 1)));
            }
            for(int i=0;i<this.statistic.getGrassToPlot().size()-1;i++) {
                g.setColor(Color.green);
                g.drawLine(500 / this.statistic.getGrassToPlot().size() * i,
                        (int) (500 - 450.0 / max * statistic.getGrassToPlot().get(i)),
                        500 / this.statistic.getGrassToPlot().size() * (i + 1),
                        (int) (500 - 450.0 / max * statistic.getGrassToPlot().get(i + 1)));
            }


            }


    }




}

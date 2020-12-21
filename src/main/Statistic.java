import java.util.*;

public class Statistic {
    private int days;
    private float avgLifeTime=0;
    private int deadAnimalsCounter=0;
    private RectangularMap map;
    private List<Integer> animalsToPlot = new ArrayList<>();
    private List<Integer> grassToPlot= new ArrayList<>();
    public List<String> archiveStats = new LinkedList<>();
    private final Map<String,Integer> geneticCodeCounter = new HashMap<>();
    private String mostCommonGen = null;





    public Statistic(RectangularMap map)
    {
        this.map=map;
    }

    public int numberOfLivingAnimals(){
        return map.getAnimalSize();
    }

    public float avgEnergy(){
        float avg=0;
        for( Animal animal: map.getAnimalList())
            avg+=animal.getEnergy();
        return avg/numberOfLivingAnimals();

    }

    public float avgAmountOfChildren(){
        float avg=0;
        for(Animal animal: map.getAnimalList())
            avg+=animal.amountOfChildern();
        return avg/numberOfLivingAnimals();

    }

    public float getAvgLifeTime() {
        return avgLifeTime;
    }

    public void addDead(int[] lifeTime){
        if(lifeTime[0]>0) {
            avgLifeTime = (avgLifeTime * deadAnimalsCounter + lifeTime[0]) / (deadAnimalsCounter + lifeTime[1]);
            deadAnimalsCounter += lifeTime[1];
        }
    }
    public int getCurrentGrass(){
        return map.getGrassSize();

    }
    public String getMostCommonGen(){ return mostCommonGen;}

    public void addDay(){
        animalsToPlot.add(map.getAnimalSize());
        if(animalsToPlot.size()>100) animalsToPlot.remove(0);
        grassToPlot.add(map.getGrassSize());
        if(grassToPlot.size()>100) grassToPlot.remove(0);
        days++;
        archiveStats.add(this.toString());

    }

    public int getDays() {
        return days;
    }

    public List<Integer> getAnimalsToPlot() {
        return animalsToPlot;
    }

    public List<Integer> getGrassToPlot() {
        return grassToPlot;
    }

    public void addGenes(List<String> genes){
        if(genes.size()>0) {
            for (String g : genes) {
                if (geneticCodeCounter.containsKey(g))
                    geneticCodeCounter.put(g, geneticCodeCounter.get(g) + 1);
                else
                    geneticCodeCounter.put(g, 1);

                if (mostCommonGen == null) mostCommonGen = g;
                else if (geneticCodeCounter.get(mostCommonGen) < geneticCodeCounter.get(g))
                    mostCommonGen = g;

            }
            if (mostCommonGen != null)
                for (Animal animal : map.getAnimalList()) {
                    if (mostCommonGen.equals(animal.genes.getGenesString())) animal.setHaveDominateGen(true);
                    else animal.setHaveDominateGen(false);
                }
        }
    }



    @Override
    public String toString() {
        return "Day: "+ days+ "Live animals:" + this.animalsToPlot.size()+
                " Average lifetime: " + avgLifeTime + " Dead animals: " + this.deadAnimalsCounter +
                " Amount of grasses: " + this.grassToPlot.size() + " Average children: " + this.avgAmountOfChildren()+"\n";


    }
}

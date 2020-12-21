import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Animal  {
    private Vector2d position;
    private MapDirection orientation;
    private int energy;
    public Genes genes;
    private int old;
    private int dateOfDeath;
    private RectangularMap map;
    private List<IPositionChangeObserver> observers= new ArrayList<>();
    private List<Animal> childerns=new ArrayList<>();
    public int followed =0;
    private AnimalInterfece animalInterfece;
    private boolean haveDominateGen=false;

    Random generator = new Random();



    public Animal(){
        this.orientation= MapDirection.values()[generator.nextInt(8)];
        this.genes=new Genes();
        this.position=new Vector2d(2,2);
        old=0;
        this.animalInterfece=new AnimalInterfece(this);

    }



    public Animal(RectangularMap map)
    {
        this();
        this.map=map;
    }
    public Animal(RectangularMap map, Vector2d initialPosition){
        this(map);
        this.position=initialPosition;

    }


    public Animal(RectangularMap map, Vector2d initialPosition,int energy) {
        this(map,initialPosition);
        this.energy=energy;
        this.genes= new Genes();
    }



    public void changeEnergy( int val){
        this.energy=this.energy + val;
    }


    public void removeObservers(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }


    public void addObservers(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }




    public void positionChanged(Vector2d oldPosition,Vector2d newPosition){
        for (IPositionChangeObserver observer: observers){
            observer.positionChanged(oldPosition,newPosition,this);
        }

    }




    @Override
    public String toString() {
        return String.valueOf(energy);
    }

    public void move(){
        old++;
        Random generator = new Random();
        for(int i=0;i<=this.genes.genes[generator.nextInt(this.genes.genes.length)];i++)
            this.orientation=this.orientation.next();
        //this.orientation= MapDirection.orientation(this.genes.genes[generator.nextInt(this.genes.genes.length)]);
        Vector2d oldPosition = this.getPosition();
        Vector2d step =position.add(orientation.toUnitVector());
        Vector2d vec=map.getPositionWH();
        step = new Vector2d((step.x+vec.x+1)%(vec.x+1),(step.y+vec.y+1)% (vec.y+1));
        this.position=step;
        this.positionChanged(oldPosition,step);
        this.changeEnergy(-this.map.moveEnergy);

    }

    public Animal copulation(Animal father){
        int childEnergy = (int) (this.getEnergy()/4 + father.getEnergy()/4);
        this.changeEnergy((int)- this.getEnergy()/4);
        father.changeEnergy((int)- father.getEnergy()/4);
        Animal animal= new Animal(this.map,this.map.findBirthPosition(this.position),childEnergy);
        animal.addObservers(this.map);
        //System.out.println(animal.observers);
        this.childerns.add(animal);
        father.childerns.add(animal);
        Genes gen=new Genes(this.genes,father.genes);
        animal.genes=gen;
        return animal;

    }

    public void setDateOfDeath(int date){
        dateOfDeath=date;
        if(followed==1) {
            display();
        }
    }

    public int successors(){
        if (this.childerns.size()<1) return childerns.size();
        int tmp= childerns.size();
        for (Animal child: childerns)
            tmp+=child.successors();

        return tmp;

    }


    public void display(){
        this.animalInterfece.update();
        animalInterfece.setVisible(true);

    }



    public void setHaveDominatGen(boolean haveDominateGen) {
        this.haveDominateGen = haveDominateGen;
    }



    public Color getColor(){
        if(haveDominateGen) return Color.RED;
        if(energy <0.25 *map.startEnergy) return new Color(224, 142, 127);
        if(energy<0.5*map.startEnergy) return new Color(164, 92, 82);
        if(energy <0.75*map.startEnergy) return new Color(88, 50, 44);
        else return new Color(74, 42, 37);

    }


    public int getDateOfDeath() {
        return dateOfDeath;
    }
    public int amountOfChildern(){
        return this.childerns.size();
    }

    public double getEnergy() { return energy; }

    public Vector2d getPosition() {
        return position;
    }

    public int getOld(){
        return old;
    }

    public int getChildrensSize(){
        return childerns.size();
    }
}



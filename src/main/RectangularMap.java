import java.util.*;

import static java.lang.Math.abs;

public class RectangularMap implements IWorldMap,IPositionChangeObserver {

    private Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private List<Animal> animalList = new ArrayList<>();
    private Map<Vector2d,Grass> grass = new HashMap<>();
    private MapVisualizer mapVisualizer;


    private final Vector2d position00 = new Vector2d(0,0);
    private final Vector2d positionWH;
    private final int mapHeight;
    private final int mapWidth;
    private final int heightJungle;
    private final int widthJungle ;
    public final Vector2d jungleLowerLeft;
    public final Vector2d jungleUpperRight;
    private final int jungleRatio;
    private final int noStartGrass;

    public final int moveEnergy;
    public final int startEnergy;
    public final int plantEnergy;
    private final int copulateEnergy;
    Random generator = new Random();

    public RectangularMap(int width, int height, int jungleRatio,int moveEnergy,int startEnergy,int plantEnergy,int noOfGrass) {
        this.positionWH = new Vector2d(width-1,height-1);
        mapHeight=height;
        mapWidth=width;
        widthJungle = width/(jungleRatio);
        heightJungle = height/(jungleRatio);
        mapVisualizer = new MapVisualizer(this);
        this.jungleLowerLeft = new Vector2d((width/2)-widthJungle/2,(height/2)-heightJungle/2);
        this.jungleUpperRight = new Vector2d((width/2)+widthJungle/2,(height/2)+heightJungle/2);
        this.jungleRatio = jungleRatio;
        this.moveEnergy=moveEnergy;
        this.startEnergy=startEnergy;
        this.plantEnergy=plantEnergy;
        this.copulateEnergy=startEnergy/2;
        this.noStartGrass = noOfGrass;
        addStartGrass();

    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public Vector2d getPositionWH() {
        return positionWH;
    }

    public List<Animal> getAnimalList() {
        return animalList;
    }

    public Map<Vector2d, Grass> getGrass() {
        return grass;
    }

    public boolean canPlace(Vector2d position) {
        return !this.animals.containsKey(position);

    }


    @Override
    public Object objectAt(Vector2d position) {
        if(isOccupied(position)){
            if (this.animals.containsKey(position))
                return this.animals.get(position).get(0);

            else return this.grass.get(position);

        }
        return null;
    }
    public boolean isOccupied(Vector2d position) {
        return this.animals.containsKey(position) || this.grass.containsKey(position);
    }

    public boolean place(Animal animal) {
        if (canPlace(animal.getPosition())) {
            addAnimal(animal);
            animal.addObservers(this);
            return true;
        }
        return false;
    }

    public void addAnimal(Animal animal){

        if(this.animals.containsKey(animal.getPosition())){
            this.animals.get(animal.getPosition()).add(animal);
            this.animals.get(animal.getPosition()).sort(new Comparator<Animal>() {
                @Override
                public int compare(Animal o1, Animal o2) {
                    if(o1.getEnergy() > o2.getEnergy()) return -1;
                    if (o1.getEnergy() < o2.getEnergy()) return 1;
                    return 0;
                }
            });
        }
        else{
            List<Animal> tmp = new ArrayList<>();
            tmp.add(animal);
            animals.put(animal.getPosition(),tmp);
        }
        if (!animalList.contains(animal)) this.animalList.add(animal);


    }
    public void removeAnimal(Animal animal){
        if(this.animals.get(animal.getPosition()).size()==1) this.animals.remove(animal.getPosition());
        else this.animals.get(animal.getPosition()).remove(animal);

        animalList.remove(animal);

    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        if(this.animals.get(oldPosition).size()==1) this.animals.remove(oldPosition);
        else this.animals.get(oldPosition).remove(animal);
        addAnimal(animal);
    }

    public void addTuft(Grass tuft){
        if(!isOccupied(tuft.getPosition())) {
            this.grass.put(tuft.getPosition(), tuft);
        }
    }

    public void addGrass()
    {

        Vector2d posIn ;
        int i=0;
        int mapSize= mapHeight*mapHeight;
        while ( i <2*mapSize){
            posIn=new Vector2d(generator.nextInt(this.widthJungle+1)+this.jungleLowerLeft.x,generator.nextInt(this.heightJungle+1)+this.jungleLowerLeft.y );
            if(!grass.containsKey(posIn)) {
                addTuft(new Grass(posIn));
                break;
            }
            i++;
        }


        Vector2d posOut;
        i=0;
        while(i<2*mapSize){
            posOut = new Vector2d(generator.nextInt(mapWidth), generator.nextInt(mapHeight));
            if(!( isOccupied(posOut) && !isInJungle(posOut))) {
                addTuft(new Grass(posOut));
                break;
            }
            i++;

        }

    }


    public  void addStartGrass(){
        for(int i=0;i<noStartGrass/2;i++)
            addGrass();

    }


    public int[] removeDead(int date){
        int days=0;
        int number=0;
        List<Animal> animalToRemove = new ArrayList<>();
//        for(List<Animal> tmpList: this.animals.values()){
//            for (Animal animal: tmpList)
//                if(animal.getEnergy() <=0) animalToRemove.add(animal);
//
//        }
        int x=0;
        for(Animal animal: animalList)
            if(animal.getEnergy() <=0) {
                animalToRemove.add(animal);
                days+=animal.getOld();
                number++;
            }
        for (Animal animal: animalToRemove) {
            animal.setDateOfDeath(date);
            x = animal.followed;
            removeAnimal(animal);

        }
        return new int[]{days,number,x};
    }

    public void eat() {
        List<Grass> grassToRemove=new ArrayList<>();
        for(Grass tuft: grass.values()){
            if(this.animals.containsKey(tuft.getPosition())){
                List <Animal> tmp=this.animals.get(tuft.getPosition());
                int i=0;
                int l=0;
                while(i <tmp.size()-1 && tmp.get(i).getEnergy()==tmp.get(i+1).getEnergy()) {
                    l++;
                    i++;
                }
                int q=l+1;
                while(l>=0) {

                    tmp.get(l).changeEnergy(plantEnergy/(q));
                    l--;
                }
                grassToRemove.add(tuft);
            }
        }
        for (Grass tuft: grassToRemove)
            grass.remove(tuft.getPosition());
    }

    public List<String> copulation(){
        List<String> genes= new ArrayList<>();
        List<Animal> animalsToPlace = new ArrayList<>();
        for (List<Animal> tmp:this.animals.values()){
            if(tmp.size()>1)
                if(tmp.get(1).getEnergy()>= copulateEnergy)
                    animalsToPlace.add(tmp.get(0).copulation(tmp.get(1)));
        }
        for(Animal animal: animalsToPlace) {
            genes.add(animal.genes.getGenesString());
            this.addAnimal(animal);
        }
        return genes;
    }


    public Vector2d findBirthPosition(Vector2d position){
        int i=generator.nextInt(8);
        MapDirection orient= MapDirection.orientation(i);
        int j=0;
        Vector2d tmp=position.add(orient.toUnitVector());
        while((!isInBorder(tmp)|| isOccupied(tmp))  && j<=8) {
            j++;
            orient = orient.next();
            tmp=position.add(orient.toUnitVector());
        }
        return tmp;
//        if(!isOccupied(position.add(orient.toUnitVector())))
//            return position.add(orient.toUnitVector());
//        else{
//            while (!this.canPlace(tmp))
//                tmp = new Vector2d(generator.nextInt(positionWH.x+1),generator.nextInt(positionWH.y+1));
//
//            return tmp;
//        }

    }

    private boolean isInBorder(Vector2d position){
        return position.precedes(this.positionWH) && position.follows(position00);

    }
    private boolean isInJungle(Vector2d position){
        return position.precedes(jungleUpperRight) && position.follows(jungleLowerLeft);
    }

    public MapVisualizer getMapVisualizer() {
        return mapVisualizer;
    }
    public int getGrassSize(){
        return grass.size();
    }
    public int getAnimalSize(){return animalList.size();}

}

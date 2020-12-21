import java.util.Arrays;
import java.util.Random;

public class Genes {
    private final int numGenes = 8;
    private final int genesLength=32;
    private final String genesString;
    public int[] genes = new int[genesLength];
    private int[] numOfEveryGene=new int[numGenes];
    Random gen = new Random();


    public Genes(){
        randomGenes();
        genesString = toString();
    }


    @Override
    public String toString() {
        String tmp="";
        for(int i=0;i<32;i++)
            tmp+=String.valueOf(genes[i]);

        return tmp;
    }

    public Genes(Genes genes1, Genes genes2) {
        int i= gen.nextInt(genesLength-3);
        int j=gen.nextInt(genesLength-i-2)+i+1;
        for(int k=0;k<=i;k++) {
            this.genes[k] = genes1.genes[k];
            numOfEveryGene[genes1.genes[k]]++;
        }
        for(int k=i+1;k<=j;k++){
            this.genes[k]=genes2.genes[k];
            numOfEveryGene[genes2.genes[k]]++;
        }
        for(int k=j+1;k<genesLength;k++){
            genes[k]=genes1.genes[k];
            numOfEveryGene[genes1.genes[k]]++;
        }
        repair();
        Arrays.sort(genes);
        genesString=toString();

    }

    public void randomGenes(){
        for(int i=0; i<genesLength;i++){
            int tmp=gen.nextInt(numGenes);
            genes[i]=tmp;
            numOfEveryGene[tmp]++;

        }
        repair();
        Arrays.sort(genes);
    }
    private boolean containsEveryGene(){
        for(int i=0;i<numGenes;i++)
            if(numOfEveryGene[i]==0) return false;

        return true;

    }
    public void repair(){
        while(!containsEveryGene()){
            for(int i=0;i<numGenes;i++)
                if(numOfEveryGene[i]==0){
                    int tmp=gen.nextInt(genesLength);
                    while(numOfEveryGene[genes[tmp]]<=1){
                        tmp=gen.nextInt(genesLength);
                    }
                    numOfEveryGene[genes[tmp]]--;
                    genes[tmp]=i;
                    numOfEveryGene[i]++;
                }

        }

    }
    public String getGenesString(){
        return genesString;

    }

    public int[] getNumOfEveryGene(){
        return numOfEveryGene;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genes genes1 = (Genes) o;
        return Arrays.equals(genes, genes1.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }
}


import java.io.FileWriter;
import java.io.IOException;

public class StatisticWriter {
    private  final Statistic statistic;

    StatisticWriter(Statistic statistic){
        this.statistic=statistic;
    }

    public void  writeStatisticToFile(String path){
        try {
            FileWriter fileWriter = new FileWriter(path);
            for(String line:statistic.archiveStats)
                fileWriter.write(line);

            fileWriter.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}

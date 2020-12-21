import org.json.JSONObject;

public class World {
    public static void main(String[] args) {

        ParametersLoader parametersLoader = new ParametersLoader();

        JSONObject jsonObject =parametersLoader.getJsonObject();

        SimulationVisualizer sV=new SimulationVisualizer();
        sV.loadData(jsonObject);



    }
}

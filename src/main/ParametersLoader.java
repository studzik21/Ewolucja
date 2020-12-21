import netscape.javascript.JSObject;
import org.json.JSONObject;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



public class ParametersLoader {
    private JSONObject jsonObject;


    public ParametersLoader(){
        try{
            String contents = new String(Files.readAllBytes(Paths.get("E:\\Users\\Patryk\\IdeaProjects\\Ewolucja\\src\\main\\parameters.json")));
            this.jsonObject = new JSONObject(contents);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }



}

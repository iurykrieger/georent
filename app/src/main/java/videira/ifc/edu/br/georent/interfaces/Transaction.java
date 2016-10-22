package videira.ifc.edu.br.georent.interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by iuryk on 30/08/2016.
 */
public interface Transaction {

    HashMap<String, String> doBefore();

    void doAfterArray(JSONArray jsonArray);

    void doAfterObject(JSONObject jsonObject);
}

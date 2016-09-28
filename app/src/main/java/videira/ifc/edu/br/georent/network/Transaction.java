package videira.ifc.edu.br.georent.network;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by iuryk on 30/08/2016.
 */
public interface Transaction {

    HashMap<String, String> doBefore();

    void doAfter(JSONArray jsonArray);
}

package videira.ifc.edu.br.georent.services;

import org.json.JSONArray;

import java.util.HashMap;

import videira.ifc.edu.br.georent.network.Transaction;

/**
 * Created by iuryk on 20/09/2016.
 */

public class ResidenceService implements Transaction {
    @Override
    public HashMap<String, String> doBefore() {
        return null;
    }

    @Override
    public void doAfter(JSONArray jsonArray) {

    }
}

package videira.ifc.edu.br.georent.network;

import org.json.JSONArray;

import videira.ifc.edu.br.georent.models.NetworkObject;

/**
 * Created by iuryk on 30/08/2016.
 */
public interface Transaction {

    NetworkObject doBefore();

    void doAfter(JSONArray jsonArray);
}

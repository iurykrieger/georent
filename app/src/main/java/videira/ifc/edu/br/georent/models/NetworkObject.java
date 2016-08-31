package videira.ifc.edu.br.georent.models;

/**
 * Created by iuryk on 30/08/2016.
 */
public class NetworkObject {

    private Object object;
    private String method;

    public NetworkObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

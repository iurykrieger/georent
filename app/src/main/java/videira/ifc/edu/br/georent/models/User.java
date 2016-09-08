package videira.ifc.edu.br.georent.models;

import java.util.Date;

/**
 * Created by iuryk on 28/08/2016.
 */
public class User {

    private Integer idCity;
    private String name;
    private String uf;

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}

package videira.ifc.edu.br.georent.interfaces;

import java.util.List;

import videira.ifc.edu.br.georent.models.City;

/**
 * Created by luizb on 01/12/2016.
 */

public interface BindCity {

    void bindCities(List<City> cities);

    void doError(Exception ex);
}

package videira.ifc.edu.br.georent.interfaces;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by iuryk on 27/09/2016.
 */

public interface Bind<T> {

    void doStartLoad();

    void doBind(T result);

    void doError(String error);
}

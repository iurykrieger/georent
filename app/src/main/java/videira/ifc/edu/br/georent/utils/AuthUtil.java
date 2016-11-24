package videira.ifc.edu.br.georent.utils;

import videira.ifc.edu.br.georent.models.User;

/**
 * Created by Aluno on 24/11/2016.
 */

public final class AuthUtil {

    public static User loggedUser;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User user) {
        AuthUtil.loggedUser = user;
    }
}

package videira.ifc.edu.br.georent.utils;

import android.content.Context;
import android.content.SharedPreferences;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.models.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aluno on 24/11/2016.
 */

public final class AuthUtil {

    private User loggedUser;
    private static AuthUtil instance;

    public synchronized static AuthUtil getInstance() {
        if (instance == null) {
            instance = new AuthUtil();
        }
        return instance;
    }

    public static Integer getLoggedUserId(Context context) {
        return context.getSharedPreferences(context.getString(R.string.shared_preferences), MODE_PRIVATE)
                .getInt(context.getString(R.string.id_user), 0);
    }

    public static String getLoggedUserToken(Context context) {
        return context.getSharedPreferences(context.getString(R.string.shared_preferences), MODE_PRIVATE)
                .getString(context.getString(R.string.user_token), null);
    }

    public User getLoggedUser(Context context) {
        if (loggedUser == null) {
            User u = new User();
            u.setIdUser(getLoggedUserId(context));
            return u;
        } else {
            return loggedUser;
        }
    }

    public void setLoggedUser(Context context, User user, String token) {
        this.loggedUser = user;
        SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.shared_preferences), MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(context.getString(R.string.id_user), user.getIdUser());
        editor.putString(context.getString(R.string.user_token), token);
        editor.commit();
    }
}

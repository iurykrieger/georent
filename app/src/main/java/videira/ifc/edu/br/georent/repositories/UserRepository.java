package videira.ifc.edu.br.georent.repositories;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.HashMap;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.enums.ActionEnum;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.interfaces.Transaction;
import videira.ifc.edu.br.georent.models.User;
import videira.ifc.edu.br.georent.network.JSONArrayRequest;
import videira.ifc.edu.br.georent.network.JSONObjectRequest;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.utils.AuthUtil;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

/**
 * Created by Aluno on 18/11/2016.
 */

public class UserRepository implements Transaction {

    /**
     * Atributos
     */
    private String service;
    private Context mContext;
    private HashMap<String, String> params;
    private Gson gson;
    private Bind bind;
    private ActionEnum action;
    private User mUser;

    public UserRepository(Context mContext, Bind b) {
        this.bind = b;
        this.mContext = mContext;
        this.params = new HashMap<>();
        this.service = NetworkUtil.getStringUrl(mContext, R.string.user_service);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        mUser = new User();
    }

    /**
     * Prepara a requisição do servidor
     *
     * @return
     */
    @Override
    public HashMap<String, String> doBefore() {
        //Verifica conexão com a internet
        if (NetworkUtil.verifyConnection(mContext)) {
            if (action != ActionEnum.ACTION_LOGIN) {
                params.put("jsonObject", gson.toJson(mUser));
                if (action != ActionEnum.ACTION_STORE) {
                    params.put("api_token", AuthUtil.getLoggedUserToken(mContext));
                }
                Log.i("LOG", params.toString());
            } else {
                params.put("email", mUser.getEmail());
                params.put("password", mUser.getPassword());
                Log.i("LOG - login true", params.toString());
            }
            return params;
        } else {
            return null;
        }
    }

    @Override
    public void doAfterArray(JSONArray jsonArray) {

    }

    @Override
    public void doAfterObject(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                /**  GET USER LOGIN TOKEN **/
                JsonParser parser = new JsonParser();
                JsonObject obj = parser.parse(String.valueOf(jsonObject)).getAsJsonObject();
                String userToken = obj.get("api_token").getAsString();
                mUser = gson.fromJson(jsonObject.toString(), User.class);

                if (userToken != null) {
                    AuthUtil.setLoggedUser(mContext, mUser, userToken);
                }
                /******************************/
                bind.doSingleBind(mUser);

            } catch (Exception e) {
                e.printStackTrace();
                bind.doError(e);
            }
        } else {
            bind.doError(new ConnectException());
        }
    }

    /****************************************************************************************
     * *                             MÉTODOS PERSONALIZADOS                               * *
     ****************************************************************************************/

    public void createUser(User user) {
        mUser = user;
        action = ActionEnum.ACTION_STORE;
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONObjectRequest(this, mContext.getClass().getName(), JSONObjectRequest.Method.POST, service);
    }

    public void login(User user) {
        mUser = user;
        action = ActionEnum.ACTION_LOGIN;
        service = NetworkUtil.getStringUrl(mContext, R.string.login);
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONObjectRequest(this, mContext.getClass().getName(), JSONObjectRequest.Method.POST, service);
    }

    public void getUserById(Integer idUser) {
        action = ActionEnum.ACTION_INDEX;
        service = String.format(service + "/" + idUser + "/eager");
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONObjectRequest(this, mContext.getClass().getName(), JSONArrayRequest.Method.GET, service);
    }
}

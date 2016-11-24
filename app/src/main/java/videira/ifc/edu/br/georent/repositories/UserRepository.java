package videira.ifc.edu.br.georent.repositories;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.HashMap;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.interfaces.Transaction;
import videira.ifc.edu.br.georent.models.NetworkObject;
import videira.ifc.edu.br.georent.models.User;
import videira.ifc.edu.br.georent.network.JSONObjectRequest;
import videira.ifc.edu.br.georent.network.NetworkConnection;
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

    private User mUser;

    public UserRepository(Context mContext, Bind b) {
        this.bind = b;
        this.mContext = mContext;
        this.params = new HashMap<>();
        this.service = NetworkUtil.getStringUrl(mContext, R.string.user_service);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

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
            //NetworkObject no = new NetworkObject(mUser);
            params.put("jsonObject", gson.toJson(mUser));
            Log.i("LOG", params.toString());
            return params;
        }
        return null;
    }

    @Override
    public void doAfterArray(JSONArray jsonArray) {

    }

    @Override
    public void doAfterObject(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                mUser = gson.fromJson(jsonObject.toString(), User.class);
                bind.doSingleBind(mUser);
            } catch (Exception e) {
                e.printStackTrace();
                bind.doError(e);
            }
        } else {
            bind.doError(new ConnectException());
        }
    }

    public void createUser(User u) {
        mUser = u;
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONObjectRequest(this, mContext.getClass().getName(), JSONObjectRequest.Method.POST, service);
    }

    public void login(User user) {
        mUser = user;
        String url = NetworkUtil.getStringUrl(mContext, R.string.auth_service);
        url = String.format(url + "/login");
        Log.i("URL", url);
        NetworkConnection.getInstance(mContext)
                .executeJSONObjectRequest(this, mContext.getClass().getName(), JSONObjectRequest.Method.POST, url);
    }
}

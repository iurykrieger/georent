package videira.ifc.edu.br.georent.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.fragments.TestFragment;
import videira.ifc.edu.br.georent.models.NetworkObject;
import videira.ifc.edu.br.georent.models.User;
import videira.ifc.edu.br.georent.network.CustomRequest;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.network.Transaction;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

/**
 * Created by iuryk on 05/09/2016.
 */
public class UserService implements Transaction {

    private static final String URL = "http://a57.foxnews.com/global.fncstatic.com/static/managed/img/fb/Sbc/entrepreneurs/0/0/Facebook-CEO-Mark-Zuckerberg.jpg";

    /**
     * Atributos
     */
    private String service;
    private Context mContext;
    private TestFragment mFragment;
    private int range;
    private HashMap<String, String> params;
    private Gson gson;

    /**
     * Construtor
     * @param mFragment
     */
    public UserService(TestFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getActivity();
        this.range = 0;
        this.params = new HashMap<>();
        this.service = NetworkUtil.getStringUrl(mFragment.getContext(), R.string.user_service);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public void setParams(Object object){
        params.put("jsonObject", gson.toJson(object));
        params.put("idUser_gte", String.valueOf(range));
        params.put("idUser_lte", String.valueOf(range+10));
    }

    /**
     * Prepara a requisição do servidor
     * @return
     */
    @Override
    public HashMap<String,String> doBefore() {
        //Verifica conexão com a internet
        if(NetworkUtil.verifyConnection(mContext)){
            User user = new User();
            NetworkObject no = new NetworkObject(user);
            setParams(no);
            mFragment.startLoading();
            return params;
        }
        return null;
    }

    /**
     * Obtém a resposta do servidor
     * @param jsonArray
     */
    @Override
    public void doAfter(JSONArray jsonArray) {

        List<User> users = new ArrayList();
        if(jsonArray != null) {
            try {
                for(int i = 0; i < jsonArray.length(); i++){
                    User u = gson.fromJson(jsonArray.getJSONObject(i).toString(), User.class);
                    u.setPhoto(URL);
                    users.add(u);
                }
                this.range += jsonArray.length();
                mFragment.onBindUsers(users);
            } catch (JSONException e) {
                e.printStackTrace();
                mFragment.nofityError("Erro ao dar parse no JSON!");
            }
        }else{
            mFragment.nofityError("Deu Pau Jovem!");
        }

    }

    /****************************************************************************************
     **                             MÉTODOS PERSONALIZADOS                                 **
     ****************************************************************************************/
    public void getUsers(){
        service = String.format(NetworkUtil.getStringUrl(mContext, R.string.user_service)
                + "?idUser_gte=" + String.valueOf(range) + "&idUser_lte=" + String.valueOf(range+10));
        Log.i("URL", service);
        NetworkConnection.getConnection(mContext).executeJSONRequest(this, mContext.getClass().getName(), CustomRequest.Method.GET, service);
    }

    public void cancelRequests(){
        NetworkConnection.getConnection(mContext).getRequestQueue().cancelAll(mContext.getClass().getName());
    }
}

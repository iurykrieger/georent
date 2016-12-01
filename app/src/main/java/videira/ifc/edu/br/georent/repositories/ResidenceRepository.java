package videira.ifc.edu.br.georent.repositories;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.interfaces.Transaction;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.network.JSONArrayRequest;
import videira.ifc.edu.br.georent.network.JSONObjectRequest;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.utils.AuthUtil;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

/**
 * Created by iuryk on 05/09/2016.
 */
public class ResidenceRepository implements Transaction {

    /**
     * Atributos
     */
    private String service;
    private Context mContext;
    private int range;
    private HashMap<String, String> params;
    private Gson gson;
    private Bind bind;
    private Residence residence;

    /**
     * Construtor
     *
     * @param bind
     */
    public ResidenceRepository(Context mContext, Bind bind) {
        this.bind = bind;
        this.mContext = mContext;
        this.range = 0;
        this.params = new HashMap<>();
        this.service = NetworkUtil.getStringUrl(mContext, R.string.residence_service);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        this.residence = new Residence();
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
            params.put("jsonObject", gson.toJson(residence));
            params.put("api_token", AuthUtil.getLoggedUserToken(mContext));
            return params;
        }
        return null;
    }

    /**
     * Obtém a resposta de array do servidor
     *
     * @param jsonArray
     */
    @Override
    public void doAfterArray(JSONArray jsonArray) {
        List<Residence> residences = new ArrayList();
        if (jsonArray != null) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Residence r = gson.fromJson(jsonArray.getJSONObject(i).toString(), Residence.class);
                    residences.add(r);
                }
                this.range += jsonArray.length();
                bind.doMultipleBind(residences);
            } catch (JSONException e) {
                e.printStackTrace();
                bind.doError(e);
            }
        } else {
            bind.doError(new ConnectException());
        }
    }

    @Override
    public void doAfterObject(JSONObject jsonObject) {
        Residence residence;
        if (jsonObject != null) {
            try {
                residence = gson.fromJson(jsonObject.toString(), Residence.class);
                bind.doSingleBind(residence);
            } catch (Exception e) {
                e.printStackTrace();
                bind.doError(e);
            }
        } else {
            //TODO Implementar serviço de exceções!
            bind.doError(new ConnectException());
        }
    }

    /****************************************************************************************
     * *                             MÉTODOS PERSONALIZADOS                               * *
     ****************************************************************************************/
    public void getResidences() {
        service = String.format(NetworkUtil.getStringUrl(mContext, R.string.residence_service)
                + "/limit/" + String.valueOf(range) + "/" + String.valueOf(range + 10));
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONArrayRequest(this, mContext.getClass().getName(), JSONArrayRequest.Method.GET, service);
    }

    public void getResidenceById(Integer idResidence) {
        service = String.format(service + "/" + idResidence);
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONObjectRequest(this, mContext.getClass().getName(), JSONArrayRequest.Method.GET, service);
    }

    public void getEagerResidenceById(Integer idResidence) {
        service = String.format(service + "/" + idResidence + "/eager");
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONObjectRequest(this, mContext.getClass().getName(), JSONArrayRequest.Method.GET, service);
    }

    public void createResidence(Residence residence) {
        this.residence = residence;
        NetworkConnection.getInstance(mContext).executeJSONObjectRequest(this, mContext.getClass().getName(), JSONObjectRequest.Method.POST, service);
    }

    public void cancelRequests() {
        NetworkConnection.getInstance(mContext).getRequestQueue().cancelAll(mContext.getClass().getName());
    }
}

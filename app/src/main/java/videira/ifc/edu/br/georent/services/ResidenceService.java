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
import videira.ifc.edu.br.georent.fragments.ResidenceIndexFragment;
import videira.ifc.edu.br.georent.models.NetworkObject;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.network.CustomRequest;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.network.Transaction;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

/**
 * Created by iuryk on 05/09/2016.
 */
public class ResidenceService implements Transaction {

    /**
     * Atributos
     */
    private String service;
    private Context mContext;
    private ResidenceIndexFragment mFragment;
    private int range;
    private HashMap<String, String> params;
    private Gson gson;

    /**
     * Construtor
     * @param mFragment
     */
    public ResidenceService(ResidenceIndexFragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getActivity();
        this.range = 0;
        this.params = new HashMap<>();
        this.service = NetworkUtil.getStringUrl(mFragment.getContext(), R.string.user_service);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    /**
     * Seta os parametros necessários
     * @param object
     */
    public void setParams(Object object) {
        params.put("jsonObject", gson.toJson(object));
        params.put("id_gte", String.valueOf(range));
        params.put("id_lte", String.valueOf(range + 10));
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
            Residence residence = new Residence();
            NetworkObject no = new NetworkObject(residence);
            setParams(no);
            mFragment.doStartLoad();
            return params;
        }
        return null;
    }

    /**
     * Obtém a resposta do servidor
     *
     * @param jsonArray
     */
    @Override
    public void doAfter(JSONArray jsonArray) {

        List<Residence> residences = new ArrayList();
        if (jsonArray != null) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Residence r = gson.fromJson(jsonArray.getJSONObject(i).toString(), Residence.class);
                    residences.add(r);
                }
                this.range += jsonArray.length();
                mFragment.doBind(residences);
            } catch (JSONException e) {
                e.printStackTrace();
                mFragment.doError("Erro ao dar parse no JSON!");
            }
        } else {
            mFragment.doError("Deu Pau Jovem!");
        }

    }

    /****************************************************************************************
     * *                             MÉTODOS PERSONALIZADOS                                 **
     ****************************************************************************************/
    public void getResidences() {
        service = String.format(NetworkUtil.getStringUrl(mContext, R.string.residence_service)
                + "?id_gte=" + String.valueOf(range) + "&id_lte=" + String.valueOf(range + 10));
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONRequest(this, mContext.getClass().getName(), CustomRequest.Method.GET, service);
    }

    public void cancelRequests() {
        NetworkConnection.getInstance(mContext).getRequestQueue().cancelAll(mContext.getClass().getName());
    }
}

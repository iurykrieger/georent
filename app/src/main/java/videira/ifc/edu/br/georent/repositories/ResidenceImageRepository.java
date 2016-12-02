package videira.ifc.edu.br.georent.repositories;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.GsonAdapter;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.interfaces.Transaction;
import videira.ifc.edu.br.georent.models.NetworkObject;
import videira.ifc.edu.br.georent.models.ResidenceImage;
import videira.ifc.edu.br.georent.network.JSONArrayRequest;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.utils.AuthUtil;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

/**
 * Created by Aluno on 14/10/2016.
 */

public class ResidenceImageRepository implements Transaction {

    /**
     * Atributos
     */
    private String service;
    private Context mContext;
    private Gson gson;
    private Bind bind;
    private int range;

    /**
     * Construtor
     *
     */
    public ResidenceImageRepository(Context mContext, Bind bind) {
        this.mContext = mContext;
        this.bind = bind;
        this.range = 0;
        this.service = NetworkUtil.getStringUrl(mContext, R.string.residence_image_service);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
                .registerTypeAdapter(Boolean.class, GsonAdapter.booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, GsonAdapter.booleanAsIntAdapter)
                .create();
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
            ResidenceImage residenceImage = new ResidenceImage();
            HashMap<String, String> params = new HashMap<>();
            params.put("jsonObject", gson.toJson(residenceImage));
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
        List<ResidenceImage> residenceImages = new ArrayList();
        if (jsonArray != null) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    ResidenceImage r = gson.fromJson(jsonArray.getJSONObject(i).toString(), ResidenceImage.class);
                    residenceImages.add(r);
                    Log.i("LOG",r.getIdResidence().getTitle());
                }
                this.range += jsonArray.length();
                bind.doMultipleBind(residenceImages);
            } catch (Exception e) {
                e.printStackTrace();
                bind.doError(e);
            }
        } else {
            bind.doError(new ConnectException());
        }
    }

    @Override
    public void doAfterObject(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                ResidenceImage residenceImage = gson.fromJson(jsonObject.toString(), ResidenceImage.class);
                bind.doSingleBind(residenceImage);
            } catch (Exception e) {
                e.printStackTrace();
                bind.doError(e);
            }
        } else {
            bind.doError(new ConnectException());
        }
    }

    /****************************************************************************************
     **                             MÉTODOS PERSONALIZADOS                                 **
     ****************************************************************************************/
    public void getTopImages() {
        service = String.format(NetworkUtil.getStringUrl(mContext, R.string.residence_image_service)
                + "/top/limit/" + String.valueOf(range) + "/" + String.valueOf(range + 10));
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONArrayRequest(this, mContext.getClass().getName(),
                JSONArrayRequest.Method.GET, service);
    }

    public void cancelRequests() {
        NetworkConnection.getInstance(mContext).getRequestQueue().cancelAll(mContext.getClass().getName());
    }
}

package videira.ifc.edu.br.georent.repositories;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.interfaces.Transaction;
import videira.ifc.edu.br.georent.models.NetworkObject;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.models.ResidenceImage;
import videira.ifc.edu.br.georent.network.JSONArrayRequest;
import videira.ifc.edu.br.georent.network.NetworkConnection;
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
    private Residence residence;
    private Bind bind;

    /**
     * Construtor
     *
     * @param residence
     */
    public ResidenceImageRepository(Context mContext, Bind bind, Residence residence) {
        this.mContext = mContext;
        this.residence = residence;
        this.bind = bind;
        this.service = NetworkUtil.getStringUrl(mContext, R.string.residence_image_service);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    /**
     * Prepara a requisição do servidor
     *
     * @return
     */
    @Override
    public HashMap<String, String> doBefore() {
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
                }
                residence.setResidenceImages(residenceImages);
            } catch (Exception e) {
                e.printStackTrace();
                bind.doError(e.getMessage());
            }
        } else {
            bind.doError("Deu Pau Jovem!");
        }
    }

    @Override
    public void doAfterObject(JSONObject jsonObject) {
        Residence residence;
        if (jsonObject != null) {
            try {
                ResidenceImage residenceImage = gson.fromJson(jsonObject.toString(), ResidenceImage.class);
                bind.doSingleBind(residenceImage);
            } catch (Exception e) {
                e.printStackTrace();
                bind.doError(e.getMessage());
            }
        } else {
            bind.doError("Deu Pau Jovem!");
        }
    }

    /****************************************************************************************
     * *                             MÉTODOS PERSONALIZADOS                                 **
     ****************************************************************************************/
    public void getImagesByResidence() {
        /*service = String.format(NetworkUtil.getStringUrl(mContext, R.string.residence_service)
                + "/limit/" + String.valueOf(range) + "/" + String.valueOf(range + 10));*/
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONArrayRequest(this, mContext.getClass().getName(), JSONArrayRequest.Method.GET, service);
    }

    public void cancelRequests() {
        NetworkConnection.getInstance(mContext).getRequestQueue().cancelAll(mContext.getClass().getName());
    }
}

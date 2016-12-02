package videira.ifc.edu.br.georent.repositories;

import android.content.Context;

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
import videira.ifc.edu.br.georent.interfaces.BindCity;
import videira.ifc.edu.br.georent.interfaces.Transaction;
import videira.ifc.edu.br.georent.models.City;
import videira.ifc.edu.br.georent.network.JSONArrayRequest;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

/**
 * Created by luizb on 01/12/2016.
 */

public class CityRepository implements Transaction {

    /**
     * Atributos
     */
    private String service;
    private Context mContext;
    private HashMap<String, String> params;
    private Gson gson;
    private BindCity bind;
    private City mCity;

    public CityRepository(Context mContext, BindCity b) {
        this.bind = b;
        this.mContext = mContext;
        this.params = new HashMap<>();
        this.service = NetworkUtil.getStringUrl(mContext, R.string.city_service);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        mCity = new City();
    }

    @Override
    public HashMap<String, String> doBefore() {
        if (NetworkUtil.verifyConnection(mContext)) {
            params.put("jsonObject", gson.toJson(mCity));
            params.put("idCity", String.valueOf(mCity.getIdCity()));
            params.put("name", mCity.getName());
        }
        return params;
    }


    @Override
    public void doAfterArray(JSONArray jsonArray) {
        List<City> cities = new ArrayList();
        if (jsonArray != null) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    City c = gson.fromJson(jsonArray.getJSONObject(i).toString(), City.class);
                    cities.add(c);
                }
                bind.bindCities(cities);
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

    }

    /****************************************************************************************
     * *                             MÉTODOS PERSONALIZADOS                               * *
     ****************************************************************************************/

    public void getCity(String state) {
        String url = String.format(service + "/state/" + state);
        NetworkConnection.getInstance(mContext).executeJSONArrayRequest(this, mContext.getClass().getName(), JSONArrayRequest.Method.GET, url);
    }
}

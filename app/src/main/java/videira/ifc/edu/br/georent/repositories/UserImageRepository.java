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
import videira.ifc.edu.br.georent.adapters.GsonAdapter;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.interfaces.Transaction;
import videira.ifc.edu.br.georent.models.UserImage;
import videira.ifc.edu.br.georent.network.JSONObjectRequest;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.utils.AuthUtil;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

/**
 * Created by Aluno on 18/11/2016.
 */

public class UserImageRepository implements Transaction {

    /**
     * Atributos
     */
    private String service;
    private Context mContext;
    private HashMap<String, String> params;
    private Gson gson;

    private UserImage mUserImage;

    public UserImageRepository(Context mContext) {
        this.mContext = mContext;
        this.params = new HashMap<>();
        this.service = NetworkUtil.getStringUrl(mContext, R.string.user_image_service);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
                .registerTypeAdapter(Boolean.class, GsonAdapter.booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, GsonAdapter.booleanAsIntAdapter)
                .create();

        mUserImage = new UserImage();
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
            params.put("jsonObject", gson.toJson(mUserImage));
            params.put("api_token", AuthUtil.getLoggedUserToken(mContext));
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
                mUserImage = gson.fromJson(jsonObject.toString(), UserImage.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createUserImage(UserImage u) {
        mUserImage = u;
        Log.i("URL", service);
        NetworkConnection.getInstance(mContext).executeJSONObjectRequest(this, mContext.getClass().getName(), JSONObjectRequest.Method.POST, service);
    }

}

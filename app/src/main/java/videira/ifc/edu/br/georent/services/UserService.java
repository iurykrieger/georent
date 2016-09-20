package videira.ifc.edu.br.georent.services;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.UserAdapter;
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
public class UserService implements Transaction{

    /**
     * Atributos
     */
    private String service;
    private Context mContext;
    private View mView;
    private UserAdapter mUserAdapter;
    private int range;
    private HashMap<String, String> params;
    private Gson gson;
    protected ProgressBar mPbLoad;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Construtor
     * @param mContext
     * @param view
     */
    public UserService(Context mContext, View view, UserAdapter adapter) {
        this.mContext = mContext;
        this.mView = view;
        this.mUserAdapter = adapter;
        this.range = 0;
        this.params = new HashMap<>();
        this.service = NetworkUtil.getStringUrl(mContext, R.string.user_service);
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        mPbLoad = (ProgressBar) view.findViewById(R.id.pb_load_user);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sr_users);
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
            mPbLoad.setVisibility(View.VISIBLE);
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

        boolean isNewer = mSwipeRefreshLayout.isRefreshing();
        mPbLoad.setVisibility(View.GONE);
        if(jsonArray != null) {
            try {
                for(int i = 0; i < jsonArray.length(); i++){
                    User u = gson.fromJson(jsonArray.getJSONObject(i).toString(), User.class);
                    if(isNewer){
                        mUserAdapter.addListItem(u, 0);
                    }else{
                        mUserAdapter.addListItem(u, mUserAdapter.getItemCount());
                    }
                }
                this.range += jsonArray.length();
                if(isNewer) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(mContext, "Deu pau piazão.", Toast.LENGTH_SHORT).show();
        }

    }

    /****************************************************************************************
     **                             MÉTODOS PERSONALIZADOS                                 **
     ****************************************************************************************/
    public void getUsers(){
        service = String.format(NetworkUtil.getStringUrl(mContext, R.string.user_service)
                + "?idUser_gte=" + String.valueOf(range) + "&idUser_lte=" + String.valueOf(range+10));
        Log.i("URL", service);
        NetworkConnection.getConnection(mContext).execute(this, mContext.getClass().getName(), CustomRequest.Method.GET, service);
    }

    public void cancelRequests(){
        NetworkConnection.getConnection(mContext).getRequestQueue().cancelAll(mContext.getClass().getName());
    }
}

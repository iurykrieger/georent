package videira.ifc.edu.br.georent.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import videira.ifc.edu.br.georent.interfaces.Transaction;

/**
 * Created by iuryk on 30/08/2016.
 */
public class NetworkConnection {

    /**
     * Atributos
     */
    private static NetworkConnection instance;
    private Context mContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    /**
     * Construtor
     *
     * @param mContext
     */
    public NetworkConnection(Context mContext) {
        this.mContext = mContext;
        this.mRequestQueue = getRequestQueue();
        this.mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    /**
     * Obtém a conexão baseado no contexto da aplicação
     *
     * @param context
     * @return
     */
    public static synchronized NetworkConnection getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkConnection(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Obtém a fila de requisições baseado no contexto da aplicação
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    /**
     * Adiciona uma requisição na fila
     *
     * @param request
     * @param <T>
     */
    public <T> void addRequest(Request<T> request) {
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getRequestQueue().add(request);
    }

    /**
     * Pega o Carregador do Cache de Imagens
     *
     * @return
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * Executa a transação da tag
     *
     * @param transaction
     * @param tag
     */
    public void executeJSONArrayRequest(final Transaction transaction, String tag, int method, String url) {
        HashMap<String, String> params = transaction.doBefore();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        if (params == null) {
            return;
        }

        JSONArrayRequest request = new JSONArrayRequest(method,
                url,
                params,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("JSON", response.toString());
                        transaction.doAfterArray(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("JSON", error.toString());
                        transaction.doAfterArray(null);
                    }
                });
        request.setTag(tag);

        addRequest(request);
    }

    /**
     * Executa a transação da tag
     *
     * @param transaction
     * @param tag
     */
    public void executeJSONObjectRequest(final Transaction transaction, String tag, int method, String url) {
        HashMap<String, String> params = transaction.doBefore();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        if (params == null) {
            return;
        }

        JSONObjectRequest request = new JSONObjectRequest(method,
                url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("JSON", response.toString());
                        transaction.doAfterObject(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("JSON", error.toString());
                        transaction.doAfterObject(null);
                    }
                });
        request.setTag(tag);

        addRequest(request);
    }
}

package videira.ifc.edu.br.georent.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import videira.ifc.edu.br.georent.R;

/**
 * Created by iuryk on 30/08/2016.
 */
public class NetworkUtil {

    /**
     * Método que verifica se existe alguma conexão com a Internet baseado no contexto
     * @param context
     * @return
     */
    public static boolean verifyConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Monta a url de conexão com base no nome do servico
     * @param idServicePath
     * @return
     */
    public static String getStringUrl(Context context, int idServicePath){
        String url = context.getResources().getString(R.string.ip) + ":" +
                context.getResources().getString(R.string.port) + "/" +
                context.getResources().getString(R.string.prefix) + "/" +
                context.getResources().getString(idServicePath);
        return url;
    }
}

package videira.ifc.edu.br.georent.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import videira.ifc.edu.br.georent.R;

/**
 * Created by iuryk on 31/10/2016.
 */

public class LayoutUtils {

    public static void setTextViewColorByBoolean(final Context context, final TextView textView, final Boolean bool){
        if(bool){
            textView.setText(context.getString(R.string.yes));
            textView.setTextColor(ContextCompat.getColor(context, R.color.accent));
        }else{
            textView.setText(context.getString(R.string.no));
            textView.setTextColor(ContextCompat.getColor(context, R.color.primary));
        }
    }
}

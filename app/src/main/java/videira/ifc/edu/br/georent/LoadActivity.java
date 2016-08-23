package videira.ifc.edu.br.georent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_load);

        /* Set progress bar color */
        ProgressBar pbLoad = (ProgressBar) findViewById(R.id.pbLoad);
        pbLoad.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent),android.graphics.PorterDuff.Mode.SRC_IN);

        /* Set TextView logo font */
        TextView tvLogo = (TextView) findViewById(R.id.tvLogo);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/source-sans-pro.regular.ttf");
        tvLogo.setTypeface(typeFace);
    }
}

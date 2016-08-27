package videira.ifc.edu.br.georent.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import videira.ifc.edu.br.georent.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Set TextView logo font */
        final EditText etEmail = (EditText) findViewById(R.id.et_email);
        final EditText etPassword = (EditText) findViewById(R.id.et_password);
        final TextView tvLogo = (TextView) findViewById(R.id.tv_logo);

        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/source-sans-pro.regular.ttf");
        etEmail.setTypeface(typeFace);
        etPassword.setTypeface(typeFace);
        tvLogo.setTypeface(typeFace);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}

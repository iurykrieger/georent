package videira.ifc.edu.br.georent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.net.UnknownHostException;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.repositories.ResidenceRepository;
import videira.ifc.edu.br.georent.utils.FakeGenerator;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

public class ShowResidenceActivity extends AppCompatActivity implements Bind<Residence> {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private ResidenceRepository mResidenceRepository;
    private Intent mIntent;
    private Residence mResidence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_residence);

        mIntent = getIntent();
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ct_residence);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_residence_show);
        mResidenceRepository = new ResidenceRepository(this, this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mResidenceRepository.getResidenceById(mIntent.getIntExtra("idResidence",0));
        doLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /*************************************************************************
     * *                            SERVIÇO                                  **
     *************************************************************************/

    @Override
    public void doLoad() {
        if (NetworkUtil.verifyConnection(this)) {
            mProgressBar.setVisibility(View.VISIBLE);
            //mResidenceImageRepository.getImages(); //Bind Correto
            doSingleBind(FakeGenerator.getInstance().getResidence(
                    mIntent.getIntExtra("idResidence", 0))); //Geração Fake!
        } else {
            doError(new UnknownHostException());
        }
    }

    @Override
    public void doSingleBind(Residence result) {
        mResidence = result;
        Log.i("Residence", mResidence.getTitle());

        final NetworkImageView nivResidence = (NetworkImageView) findViewById(R.id.niv_residence);
        final TextView tvAddress = (TextView) findViewById(R.id.tv_address_residence);
        final TextView tvCity = (TextView) findViewById(R.id.tv_city_residence);
        final TextView tvDescription = (TextView) findViewById(R.id.tv_description_residence);
        final TextView tvObservation = (TextView) findViewById(R.id.tv_observation_residence);

        nivResidence.setImageUrl(mResidence.getResidenceImages().get(0).getPath(),
                NetworkConnection.getInstance(this).getImageLoader());
        tvAddress.setText(mResidence.getAddress());
        tvCity.setText(mResidence.getIdLocation().getIdCity().getName() + " - " +
                mResidence.getIdLocation().getIdCity().getUf());
        tvDescription.setText(mResidence.getDescription());
        tvObservation.setText(mResidence.getObservation());

        mCollapsingToolbarLayout.setTitle(mResidence.getTitle());
    }

    @Override
    public void doMultipleBind(List<Residence> results) {

    }

    @Override
    public void doError(Exception ex) {
        mProgressBar.setVisibility(View.GONE);
        Snackbar snackbar = null;

        if (ex instanceof UnknownHostException) {
            snackbar = Snackbar
                    .make(findViewById(R.id.cl_residence_show), R.string.no_internet, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Conectar", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(it);
                        }
                    })
                    .setActionTextColor(ContextCompat.getColor(this, R.color.accent));
        } else {
            snackbar = Snackbar
                    .make(findViewById(R.id.cl_residence_show), R.string.unknown_error, Snackbar.LENGTH_SHORT)
                    .setActionTextColor(ContextCompat.getColor(this, R.color.accent));
        }

        snackbar.show();
    }
}

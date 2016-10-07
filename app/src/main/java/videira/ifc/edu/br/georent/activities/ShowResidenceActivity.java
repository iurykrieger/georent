package videira.ifc.edu.br.georent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.repositories.ResidenceRepository;

public class ShowResidenceActivity extends AppCompatActivity implements Bind<List<Residence>>{

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private ResidenceRepository mResidenceRepository;
    private ProgressBar mPbResidence;
    private Intent mIntent;
    private Residence mResidence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residence_show);

        mIntent = getIntent();

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.cardview_light_background));

        mPbResidence = (ProgressBar) findViewById(R.id.pb_load_residence);

        mResidenceRepository = new ResidenceRepository(this, this);
        mResidenceRepository.getResidenceById(mIntent.getIntExtra("idResidence",0));
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
    public void doStartLoad() {
        mPbResidence.setVisibility(View.VISIBLE);
    }

    @Override
    public void doBind(List<Residence> result) {
        mResidence = result.get(0);
        Log.i("Residence", mResidence.getTitle());
        mToolbar.setTitle(mResidence.getTitle());
        mPbResidence.setVisibility(View.GONE);
    }

    @Override
    public void doError(String error) {

    }
}

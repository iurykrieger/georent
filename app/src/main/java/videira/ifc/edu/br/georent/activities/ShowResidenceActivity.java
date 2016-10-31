package videira.ifc.edu.br.georent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.ViewPagerAdapter;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.models.ResidenceImage;
import videira.ifc.edu.br.georent.repositories.ResidenceRepository;
import videira.ifc.edu.br.georent.utils.FakeGenerator;
import videira.ifc.edu.br.georent.utils.LayoutUtils;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

public class ShowResidenceActivity extends AppCompatActivity implements Bind<Residence>, ViewPager.OnPageChangeListener, View.OnClickListener {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private ResidenceRepository mResidenceRepository;
    private Intent mIntent;
    private Residence mResidence;

    /**
     * ViewPager
     */
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private LinearLayout mPagerIndicator;
    private ImageView[] dots;
    private int dotsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_residence);

        mIntent = getIntent();
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ct_residence);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_residence_show);
        mViewPager = (ViewPager) findViewById(R.id.vp_residence);
        mPagerIndicator = (LinearLayout) findViewById(R.id.vp_count_dots);
        mResidenceRepository = new ResidenceRepository(this, this);

        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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
            if (mProgressBar.getVisibility() == View.GONE) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            mCollapsingToolbarLayout.setTitle(getString(R.string.loading));

            //mResidenceRepository.getEagerResidenceById(mIntent.getIntExtra("idResidence", 0)); //Bind Correto
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
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
        }
        mCollapsingToolbarLayout.setTitle(mResidence.getTitle());

        /* Cabeçalho */
        //final NetworkImageView nivResidence = (NetworkImageView) findViewById(R.id.niv_residence);
        final TextView tvTitle = (TextView) findViewById(R.id.tv_title_residence);
        final TextView tvAddress = (TextView) findViewById(R.id.tv_address_residence);

        /* Informações */
        final TextView tvCity = (TextView) findViewById(R.id.tv_city_residence);
        final TextView tvDescription = (TextView) findViewById(R.id.tv_description_residence);
        final TextView tvObservation = (TextView) findViewById(R.id.tv_observation_residence);

        /* Preferências */
        final LinearLayout llPreferences = (LinearLayout) findViewById(R.id.ll_preferences_residence);
        final TextView tvSponsor = (TextView) findViewById(R.id.tv_sponsor_residence);
        final TextView tvRoom = (TextView) findViewById(R.id.tv_room_residence);
        final TextView tvIncome = (TextView) findViewById(R.id.tv_income_residence);
        final TextView tvStay = (TextView) findViewById(R.id.tv_stay_residence);
        final TextView tvLoadPreferences = (TextView) findViewById(R.id.tv_load_preferences_residence);
        final TextView tvVacancy = (TextView) findViewById(R.id.tv_vacancy_residence);
        final TextView tvCondominium = (TextView) findViewById(R.id.tv_condominium_residence);
        final TextView tvPet = (TextView) findViewById(R.id.tv_pet_residence);
        final TextView tvChild = (TextView) findViewById(R.id.tv_child_residence);

        List<String> resources = new ArrayList<>();
        for (ResidenceImage ri : mResidence.getResidenceImages()) {
            resources.add(ri.getPath());
        }
        mViewPagerAdapter = new ViewPagerAdapter(this, resources);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(3);
        setUiPageViewController();

        tvTitle.setText(mResidence.getTitle());
        tvAddress.setText(mResidence.getAddress());
        tvCity.setText(mResidence.getIdLocation().getIdCity().getName() + " - " +
                mResidence.getIdLocation().getIdCity().getUf());
        tvDescription.setText(mResidence.getDescription());
        tvObservation.setText(mResidence.getObservation());
        tvRoom.setText(mResidence.getIdPreference().getRoom().toString());
        tvIncome.setText("$ " + new DecimalFormat("0.00").format(mResidence.getIdPreference().getIncome()));
        tvIncome.setTextColor(ContextCompat.getColor(this, R.color.accent));
        tvStay.setText(mResidence.getIdPreference().getStay().toString() + " " + getString(R.string.months));
        tvVacancy.setText(mResidence.getIdPreference().getVacancy().toString());

        LayoutUtils.setTextViewColorByBoolean(this, tvSponsor, mResidence.getIdPreference().getSponsor());
        LayoutUtils.setTextViewColorByBoolean(this, tvCondominium, mResidence.getIdPreference().getCondominium());
        LayoutUtils.setTextViewColorByBoolean(this, tvPet, mResidence.getIdPreference().getPet());
        LayoutUtils.setTextViewColorByBoolean(this, tvChild, mResidence.getIdPreference().getChild());

        tvLoadPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llPreferences.getVisibility() == View.GONE) {
                    llPreferences.setVisibility(View.VISIBLE);
                    tvLoadPreferences.setText(getString(R.string.show_less));
                } else {
                    llPreferences.setVisibility(View.GONE);
                    tvLoadPreferences.setText(getString(R.string.show_more));
                }
            }
        });
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

    /*************************************************************************
     * *                            VIEW PAGER                               **
     *************************************************************************/

    private void setUiPageViewController() {
        dotsCount = mViewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.item_nonselected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(4, 0, 4, 0);

            mPagerIndicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.item_selected));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.item_nonselected));
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.item_selected));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

    }
}

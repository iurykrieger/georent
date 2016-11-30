package videira.ifc.edu.br.georent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.MatchAdapter;
import videira.ifc.edu.br.georent.adapters.NetworkViewPagerAdapter;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.models.ResidenceImage;
import videira.ifc.edu.br.georent.repositories.ResidenceRepository;
import videira.ifc.edu.br.georent.utils.FakeGenerator;
import videira.ifc.edu.br.georent.utils.LayoutUtils;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

public class ResidenceShowActivity extends AppCompatActivity implements Bind<Residence>,
        ViewPager.OnPageChangeListener, View.OnClickListener, OnMapReadyCallback {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private ResidenceRepository mResidenceRepository;
    private Intent mIntent;
    private Residence mResidence;

    /**
     * ViewPager
     */
    private NetworkViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private LinearLayout mPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residence_show);

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
    }

    @Override
    protected void onStart() {
        doLoad();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit: {

                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
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

        /* Matches */
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final RecyclerView rvMatch = (RecyclerView) findViewById(R.id.rv_match_residence);
        final MatchAdapter matchAdapter = new MatchAdapter(mResidence.getMatches().subList(0, 4), this);

        /* Location */
        final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        final TextView tvCiyLocation = (TextView) findViewById(R.id.tv_city_location_residence_show);
        final TextView tvAddressLocation = (TextView) findViewById(R.id.tv_address_location_residence_show);

        List<String> resources = new ArrayList<>();
        for (ResidenceImage ri : mResidence.getResidenceImages()) {
            resources.add(ri.getPath());
        }
        mViewPagerAdapter = new NetworkViewPagerAdapter(this, resources, mPagerIndicator);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(3);

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

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMatch.setHasFixedSize(true);
        rvMatch.setLayoutManager(linearLayoutManager);
        rvMatch.setAdapter(matchAdapter);

        mapFragment.getMapAsync(this);
        tvCiyLocation.setText(mResidence.getIdLocation().getIdCity().getName() + " - " +
            mResidence.getIdLocation().getIdCity().getUf());
        tvAddressLocation.setText(mResidence.getAddress());
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
     * *                            VIEW PAGER                             * *
     *************************************************************************/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mViewPagerAdapter.setIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

    }

    /*************************************************************************
     * *                                MAPA                               * *
     *************************************************************************/

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng location = new LatLng(mResidence.getIdLocation().getLatitude(),
                mResidence.getIdLocation().getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
        map.addMarker(new MarkerOptions()
                .title(mResidence.getIdLocation().getIdCity().getName())
                .snippet(mResidence.getAddress())
                .position(location));
    }
}

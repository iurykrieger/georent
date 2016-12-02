package videira.ifc.edu.br.georent.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.ViewPagerAdapter;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.models.Location;
import videira.ifc.edu.br.georent.models.Preference;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.models.ResidenceImage;
import videira.ifc.edu.br.georent.repositories.ResidenceImageRepository;
import videira.ifc.edu.br.georent.repositories.ResidenceRepository;
import videira.ifc.edu.br.georent.utils.AuthUtil;
import videira.ifc.edu.br.georent.utils.FakeGenerator;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

import static android.R.attr.data;

public class ResidenceRegisterActivity extends AppCompatActivity implements Bind<Residence>,
        ViewPager.OnPageChangeListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private static final int REQUEST_GALLERY_IMAGE = 1;
    private static final int REQUEST_CAMERA_IMAGE = 2;
    private static final int REQUEST_PLACE_PICKER = 3;

    private Toolbar mToolbar;
    /**
     * ViewPager
     **/
    private ViewPagerAdapter mImageAdapter;
    private ViewPager mViewPager;
    private LinearLayout mPagerIndicator;
    private Residence mResidence;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ResidenceRepository mResidenceRepository;
    private EditText etTitle;
    private EditText etDescription;
    private EditText etObservation;
    private EditText etAddress;
    private EditText etCity;
    private EditText etState;
    private SeekBar sbRent;
    private TextView tvRent;
    private SwitchCompat scSponsor;
    private SwitchCompat scCondominium;
    private SwitchCompat scPet;
    private SwitchCompat scChild;
    private SwitchCompat scLaundry;
    private Spinner sVacancy;
    private Spinner sRoom;
    private Spinner sBathroom;
    private Button btRegister;
    private FloatingActionButton fabAddPhoto;
    private FloatingActionButton fabAddPlace;

    private List<String> mListUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residence_register);

        mResidenceRepository = new ResidenceRepository(this, this);

        etTitle = (EditText) findViewById(R.id.et_title_residence_register);
        etDescription = (EditText) findViewById(R.id.et_description_residence_register);
        etObservation = (EditText) findViewById(R.id.et_observation_residence_register);
        etAddress = (EditText) findViewById(R.id.et_address_residence_register);
        sbRent = (SeekBar) findViewById(R.id.sb_rent_residence_register);
        tvRent = (TextView) findViewById(R.id.tv_rent_residence_register);
        scSponsor = (SwitchCompat) findViewById(R.id.sc_sponsor);
        scCondominium = (SwitchCompat) findViewById(R.id.sc_condominium);
        scPet = (SwitchCompat) findViewById(R.id.sc_pet);
        scChild = (SwitchCompat) findViewById(R.id.sc_child);
        scLaundry = (SwitchCompat) findViewById(R.id.sc_laundry);
        sVacancy = (Spinner) findViewById(R.id.spinner_vacancy);
        sRoom = (Spinner) findViewById(R.id.spinner_rooms);
        sBathroom = (Spinner) findViewById(R.id.spinner_bathroom);

        btRegister = (Button) findViewById(R.id.bt_register_user);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.vp_user);
        mPagerIndicator = (LinearLayout) findViewById(R.id.vp_user_count_dots);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        etState = (EditText) findViewById(R.id.et_state_residence_register);
        etCity = (EditText) findViewById(R.id.et_city_residence_register);
        btRegister = (Button) findViewById(R.id.bt_residence_register);
        mToolbar = (Toolbar) findViewById(R.id.tb_residence_register);
        mViewPager = (ViewPager) findViewById(R.id.vp_residence_register);
        mPagerIndicator = (LinearLayout) findViewById(R.id.vp_dots_residence_register);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctl_residence_register);
        fabAddPhoto = (FloatingActionButton) findViewById(R.id.fab_photo_residence_register);
        fabAddPlace = (FloatingActionButton) findViewById(R.id.fab_location_residence_register);

        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*     CHANGE DISTANCE    */
        sbRent.setOnSeekBarChangeListener(this);

        /**     ViewPager    **/
        mImageAdapter = new ViewPagerAdapter(this, mPagerIndicator);
        mViewPager.setAdapter(mImageAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(0);

        /**    PICKUP IMAGE   **/
        final String[] items = new String[]{"Tirar foto", "Selecionar do Cartao"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione uma opção");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(), "tmp_avatar" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                    Uri imageCaptureUri = Uri.fromFile(file);
                    try {
                        getIntent().putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
                        getIntent().putExtra("return data", true);

                        startActivityForResult(intent, REQUEST_CAMERA_IMAGE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    dialog.cancel();
                } else {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, REQUEST_GALLERY_IMAGE);
                }
            }
        });

        final AlertDialog dialog = builder.create();
        fabAddPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });

        /** SPINNERS   **/
        String[] numbers = getResources().getStringArray(R.array.numbers);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.states));

        fabAddPlace.setOnClickListener(this);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLoad();
            }
        });
    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mImageAdapter.setIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case REQUEST_GALLERY_IMAGE: {
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap image = BitmapFactory.decodeStream(imageStream);

                        mImageAdapter.addListItem(imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
            case REQUEST_CAMERA_IMAGE: {
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imgStream = getContentResolver().openInputStream(imageUri);
                        mImageAdapter.addListItem(imageUri);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
            case REQUEST_PLACE_PICKER: {
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(this, imageReturnedIntent);
                    String toastMsg = String.format("Place: %s", place.getName());
                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

    /*************************************************************************
     * *                            REPOSITORIO                             **
     *************************************************************************/
    @Override
    public void doLoad() {
        if (NetworkUtil.verifyConnection(this)) {
            mResidence = new Residence();
            Preference preference = new Preference();

            mResidence.setIdUser(AuthUtil.getInstance().getLoggedUser(this));
            mResidence.setTitle(etTitle.getText().toString());
            mResidence.setDescription(etDescription.getText().toString());
            mResidence.setObservation(etObservation.getText().toString());
            mResidence.setAddress(etAddress.getText().toString());
            mResidence.setRent((float) sbRent.getProgress());

            preference.setSponsor(scSponsor.isChecked());
            preference.setCondominium(scCondominium.isChecked());
            preference.setPet(scPet.isChecked());
            preference.setChild(scChild.isChecked());
            preference.setLaundry(scLaundry.isChecked());
            preference.setVacancy(Integer.parseInt(sVacancy.getSelectedItem().toString()));
            preference.setRoom(Integer.parseInt(sRoom.getSelectedItem().toString()));
            preference.setBathroom(Integer.parseInt(sBathroom.getSelectedItem().toString()));

            Location l = FakeGenerator.getInstance().getResidences().get(0).getIdLocation();
            l.setIdLocation(null);

            mResidence.setIdPreference(preference);
            mResidence.setIdLocation(l);

            mResidenceRepository.createResidence(mResidence);
        } else {
            doError(new UnknownHostException());
        }
    }

    @Override
    public void doSingleBind(Residence result) {
        Log.i("LOG", "Residência cadastrada: " + result.getTitle());
        ResidenceImageRepository residenceImageRepository = new ResidenceImageRepository(this, this);

        for (Uri u : mImageAdapter.getAll()) {
            try {
                ResidenceImage residenceImage = new ResidenceImage();
                residenceImage.setIdResidence(result);
                residenceImage.setOrderImage(mImageAdapter.getCount() - mImageAdapter.getItemPosition(u));
                Log.i("LOG", "Imagem enviada");

                InputStream imageStream = getContentResolver().openInputStream(u);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                final Bitmap image = BitmapFactory.decodeStream(imageStream);
                image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteFormat = stream.toByteArray();
                String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

                residenceImage.setPath(imgString);
                //residenceImageRepository.createResidenceImage(residenceImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doMultipleBind(List<Residence> results) {

    }

    @Override
    public void doError(Exception ex) {

    }

    /*************************************************************************
     * *                            SEEK BAR                               * *
     *************************************************************************/

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            if (progress >= 0 && progress <= sbRent.getMax()) {
                String progressString = String.valueOf(progress);
                tvRent.setText(progressString + " $");
                seekBar.setSecondaryProgress(progress);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_location_residence_register: {
                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    startActivityForResult(builder.build(this), REQUEST_PLACE_PICKER);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }
}

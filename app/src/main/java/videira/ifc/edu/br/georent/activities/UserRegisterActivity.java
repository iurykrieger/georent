package videira.ifc.edu.br.georent.activities;


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

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.ViewPagerAdapter;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.models.City;
import videira.ifc.edu.br.georent.models.Preference;
import videira.ifc.edu.br.georent.models.User;
import videira.ifc.edu.br.georent.models.UserImage;
import videira.ifc.edu.br.georent.repositories.UserImageRepository;
import videira.ifc.edu.br.georent.repositories.UserRepository;
import videira.ifc.edu.br.georent.utils.FakeGenerator;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

import static android.support.v7.mediarouter.R.id.image;

public class UserRegisterActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, Bind<User> {

    private static final int REQUEST_GALLERY_IMAGE = 1;
    private static final int REQUEST_CAMERA_IMAGE = 2;
    String[] numbers;
    private Integer[] imgs = {R.drawable.new_image, R.drawable.photo2, R.drawable.user};
    private Toolbar mToolbar;
    /**
     * ViewPager
     **/
    private ViewPagerAdapter mImageAdapter;
    private ViewPager mViewPager;
    private LinearLayout mPagerIndicator;
    private User mUser;
    private Preference mPreference;
    private List<Integer> mImageResources;
    private EditText mEtBirthDate;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private UserRepository mUserRepository;
    private EditText etName;
    private EditText etEmail;
    private EditText etBirthDate;
    private EditText etTel;
    private EditText etPassword;
    private SeekBar skDistance;
    private TextView tvDistance;
    private SwitchCompat scSponsor;
    private SwitchCompat scCondominium;
    private SwitchCompat scPet;
    private SwitchCompat scChild;
    private SwitchCompat scLaundry;
    private Spinner sVacancy;
    private Spinner sRoom;
    private Spinner sBathroom;
    private MaterialBetterSpinner spnState;
    private Button btRegister;

    private List<String> mListUserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        List<String> mListUserImage = new ArrayList<String>();

        etName = (EditText) findViewById(R.id.et_name_user);
        etEmail = (EditText) findViewById(R.id.et_email_user);
        etBirthDate = (EditText) findViewById(R.id.et_birth_date_user);
        etTel = (EditText) findViewById(R.id.et_tel_user);
        etPassword = (EditText) findViewById(R.id.et_password_user);
        skDistance = (SeekBar) findViewById(R.id.sb_distance_user);
        tvDistance = (TextView) findViewById(R.id.tv_range_number_user);

        scSponsor = (SwitchCompat) findViewById(R.id.sc_sponsor);
        scCondominium = (SwitchCompat) findViewById(R.id.sc_condominium);
        scPet = (SwitchCompat) findViewById(R.id.sc_pet);
        scChild = (SwitchCompat) findViewById(R.id.sc_child);
        scLaundry = (SwitchCompat) findViewById(R.id.sc_laundry);

        sVacancy = (Spinner) findViewById(R.id.spinner_vacancy);
        sRoom = (Spinner) findViewById(R.id.spinner_rooms);
        sBathroom = (Spinner) findViewById(R.id.spinner_bathroom);
        spnState = (MaterialBetterSpinner) findViewById(R.id.spn_state_user_register);

        btRegister = (Button) findViewById(R.id.bt_register_user);


        mUserRepository = new UserRepository(this, this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.vp_user);
        mPagerIndicator = (LinearLayout) findViewById(R.id.vp_user_count_dots);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

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
        skDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    if (progress >= 0 && progress <= skDistance.getMax()) {

                        String progressString = String.valueOf(progress + 1);
                        tvDistance.setText(progressString + "Km");
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

        });

        /**     ViewPager    **/
        mImageAdapter = new ViewPagerAdapter(this, mPagerIndicator);
        mViewPager.setAdapter(mImageAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(0);

        /**    PICKUP IMAGE   **/
        /*
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CAMERA_IMAGE);
            }
        });
        */
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
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });

        /** SPINNERS   **/
        numbers = getResources().getStringArray(R.array.numbers);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.states));
        spnState.setAdapter(arrayAdapter);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLoad();
            }
        });
    }

    /**
     * DATE PICKER
     **/
    public void onStart() {
        super.onStart();
        EditText mEtBirthDate = (EditText) findViewById(R.id.et_birth_date_user);
        mEtBirthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dialog = new DateDialog(v);
                    dialog.setAllowReturnTransitionOverlap(false);
                    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });
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
    public void onClick(View v) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case REQUEST_GALLERY_IMAGE:
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
        }
    }

    /*************************************************************************
     * *                            REPOSITORIO                             **
     *************************************************************************/
    @Override
    public void doLoad() {
        if (NetworkUtil.verifyConnection(this)) {
            mUser = new User();
            mPreference = new Preference();


            mUser.setName(etName.getText().toString());
            mUser.setEmail(etEmail.getText().toString());

            try {
                mUser.setBirthDate(new SimpleDateFormat("MM/dd/yyyy").parse(etBirthDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mUser.setPhone(etTel.getText().toString());
            mUser.setPassword(etPassword.getText().toString());
            mUser.setDistance(skDistance.getProgress());
            mUser.setType(User.OCCUPIER);

            mPreference.setSponsor(scSponsor.isChecked());
            mPreference.setCondominium(scCondominium.isChecked());
            mPreference.setPet(scPet.isChecked());
            mPreference.setChild(scChild.isChecked());
            mPreference.setLaundry(scLaundry.isChecked());
            mPreference.setVacancy(java.lang.Integer.getInteger(sVacancy.getSelectedItem().toString()));
            mPreference.setRoom(java.lang.Integer.getInteger(sRoom.getSelectedItem().toString()));
            mPreference.setBathroom(java.lang.Integer.getInteger(sBathroom.getSelectedItem().toString()));

            mUser.setIdPreference(mPreference);
            City c = FakeGenerator.getResidences().get(0).getIdLocation().getIdCity();
            c.setIdCity(1);
            mUser.setIdCity(c);

            mUserRepository.createUser(mUser);
        } else {
            doError(new UnknownHostException());
        }
    }

    @Override
    public void doSingleBind(User result) {
        Log.i("LOG", "Usuario cadastrado = " + result.getName());
        UserImageRepository uir = new UserImageRepository(this);

        for (Uri u: mImageAdapter.getAll()) {
            UserImage ui = new UserImage();
            ui.setIdUser(result);
            ui.setOrder(0);
            Log.i("LOG", "Imagem enviada");
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(u);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                final Bitmap image = BitmapFactory.decodeStream(imageStream);
                image.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                byte[] byteFormat = stream.toByteArray();
                String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

                ui.setPath(imgString);
                uir.createUserImage(ui);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doMultipleBind(List<User> results) {

    }

    @Override
    public void doError(Exception ex) {

    }


}

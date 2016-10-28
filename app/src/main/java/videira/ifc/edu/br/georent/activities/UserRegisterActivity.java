package videira.ifc.edu.br.georent.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.ImageAdapter;

public class UserRegisterActivity extends AppCompatActivity {

    private Integer[] imgs = {R.drawable.new_image, R.drawable.photo2, R.drawable.user};
    private ViewPager mViewPager;
    private ImageAdapter mImageAdapter;
    private Toolbar mToolbar;
    private static final int REQUEST_GALLERY_IMAGE = 1;
    private static final int REQUEST_CAMERA_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.vp_user_register);
        mImageAdapter = new ImageAdapter(this);
        mViewPager.setAdapter(mImageAdapter);

        /*     CHANGE DISTANCE    */
        final SeekBar skDistance = (SeekBar) findViewById(R.id.sb_distance);
        final TextView tvDistance = (TextView) findViewById(R.id.tv_range_number);
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

        /*    Gallery     */
        /*Gallery ga = (Gallery) findViewById(R.id.gallery);
        ga.setAdapter(new ImageAdapter(this, imgs));*/

        for (int i = 0; i < imgs.length; i++) {
            mImageAdapter.addItem(imgs[i]);
        }

        /*    PICKUP IMAGE   */
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CAMERA_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap yourSelectedImage;
        switch (requestCode) {
            case REQUEST_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    final Drawable imageDrawable = new BitmapDrawable(getResources(), yourSelectedImage);

                    /*ImageSwitcher is = (ImageSwitcher) findViewById(R.id.is_user);
                    is.setFactory(new ViewSwitcher.ViewFactory() {
                        @Override
                        public View makeView() {
                            ImageView imageView = new ImageView(getApplicationContext());
                            imageView.setImageDrawable(imageDrawable);
                            return imageView;
                        }
                    });*/
                }
                break;
            case REQUEST_CAMERA_IMAGE: {
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        final Drawable imageDrawable = new BitmapDrawable(getResources(), selectedImage);

                        /*ImageSwitcher is = (ImageSwitcher) findViewById(R.id.is_user);
                        is.setFactory(new ViewSwitcher.ViewFactory() {
                            @Override
                            public View makeView() {
                                ImageView imageView = new ImageView(getApplicationContext());
                                imageView.setImageDrawable(imageDrawable);
                                return imageView;
                            }
                        });*/
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}

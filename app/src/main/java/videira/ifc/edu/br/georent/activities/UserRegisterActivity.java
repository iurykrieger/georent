package videira.ifc.edu.br.georent.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.ViewPagerAdapter;
import videira.ifc.edu.br.georent.models.User;

public class UserRegisterActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private Integer[] imgs = {R.drawable.new_image, R.drawable.photo2, R.drawable.user};
    private Toolbar mToolbar;
    private static final int REQUEST_GALLERY_IMAGE = 1;
    private static final int REQUEST_CAMERA_IMAGE = 2;

    /** ViewPager **/
    private ViewPagerAdapter mImageAdapter;
    private ViewPager mViewPager;
    private LinearLayout mPagerIndicator;
    private ImageView[] dots;
    private int dotsCount;
    private User mUser;


    private List<Integer> mImageResources ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.vp_user);
        mPagerIndicator = (LinearLayout) findViewById(R.id.vp_user_count_dots);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


        /**     ViewPager    **/
        List<Integer> resources = new ArrayList<>();
        resources.add(R.drawable.new_image);
        mImageAdapter = new ViewPagerAdapter(this, resources);
        mViewPager.setAdapter(mImageAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(3);
        setUiPageViewController();


        /**    PICKUP IMAGE   **/
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CAMERA_IMAGE);
            }
        });
    }


    private void setUiPageViewController() {
        dotsCount = mImageAdapter.getCount();
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

                }
                break;
            case REQUEST_CAMERA_IMAGE: {
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        final Drawable imageDrawable = new BitmapDrawable(getResources(), selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}

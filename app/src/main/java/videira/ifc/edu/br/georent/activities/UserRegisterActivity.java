package videira.ifc.edu.br.georent.activities;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.SeekBar;
import android.widget.TextView;
import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.ImageAdapter;

public class UserRegisterActivity extends AppCompatActivity {

    final Integer[] imgs = {R.drawable.new_image, R.drawable.photo2, R.drawable.user};
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private ImageSwitcher mswitcher;
    private FloatingActionButton mFButton;
    private Uri imageCaptureUri;
    private SeekBar mSeekBar;
    private TextView mTvDistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final SeekBar skDistance = (SeekBar) findViewById(R.id.sb_distance);
        final TextView tvDistance = (TextView) findViewById(R.id.tv_distance);

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
        Gallery ga = (Gallery) findViewById(R.id.gallery);
        ga.setAdapter(new ImageAdapter(this, imgs));

    }
}

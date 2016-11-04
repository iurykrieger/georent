package videira.ifc.edu.br.georent.adapters;

/**
 * Created by luizb on 25/10/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import videira.ifc.edu.br.georent.R;

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<Uri> mResources;
    private List<ImageView> mIndex;
    private LinearLayout mPagerIndicator;

    public ViewPagerAdapter(Context mContext, LinearLayout mPagerIndicator) {
        this.mContext = mContext;
        this.mResources = new ArrayList<>();
        this.mIndex = new ArrayList<>();
        this.mPagerIndicator = mPagerIndicator;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_pager, container, false);
        final ImageView mImageView = (ImageView) itemView.findViewById(R.id.niv_item_image);

        try {
            Bitmap bmp = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),mResources.get(position));
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, new ByteArrayOutputStream());
            Bitmap resized = scaleDown(bmp, 500, true);
            mImageView.setImageBitmap(resized);
        } catch (IOException e) {
            e.printStackTrace();
        }

        container.addView(itemView);
        addIndex();
        return itemView;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,boolean filter) {
        float ratio = Math.min((float) maxImageSize / realImage.getWidth(), (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    public void addListItem(Uri uri) {
        mResources.add(uri);
        notifyDataSetChanged();
    }

    public void removeListItem(int position) {
        mResources.remove(position);
        notifyDataSetChanged();
    }

    private void addIndex() {
        ImageView iv = new ImageView(mContext);
        iv.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.item_nonselected));
        mIndex.add(iv);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(4, 0, 4, 0);

        mPagerIndicator.addView(iv, params);
    }

    public void setIndex(int position) {
        for (ImageView iv : mIndex) {
            if(mIndex.indexOf(iv) == position) {
                iv.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.item_selected));
            }else{
                iv.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.item_nonselected));
            }
        }
    }
}



package videira.ifc.edu.br.georent.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.network.NetworkConnection;

/**
 * Created by iuryk on 31/10/2016.
 */

public class NetworkViewPagerAdapter extends PagerAdapter{

    private Context mContext;
    private List<String> mResources;
    private LinearLayout mPagerIndicator;
    private List<ImageView> mIndex;

    public NetworkViewPagerAdapter(Context mContext, List<String> mResources, LinearLayout mPagerIndicator) {
        this.mContext = mContext;
        this.mResources = mResources;
        this.mIndex = new ArrayList<>();
        this.mPagerIndicator = mPagerIndicator;
        loadIndex();
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_pager_network, container, false);

        final NetworkImageView networkImageView = (NetworkImageView) itemView.findViewById(R.id.niv_item_pager);
        networkImageView.setImageUrl(mResources.get(position), NetworkConnection.getInstance(mContext).getImageLoader());
        networkImageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(networkImageView.getDrawable() != null){
                    final ProgressBar pbItemView = (ProgressBar) itemView.findViewById(R.id.pb_item_pager);
                    pbItemView.setVisibility(View.GONE);
                }
            }
        });

        container.addView(itemView);
        Log.i("Log", "instantiateItem()");
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
        Log.i("Log", "removeItem()");
    }

    public void addListItem(String url) {
        mResources.add(url);
        addIndex();
        notifyDataSetChanged();
    }

    public void removeListItem(int position) {
        mResources.remove(position);
        removeIndex(position);
        notifyDataSetChanged();
    }

    private void loadIndex(){
        for (int i = 0; i < mResources.size(); i++) {
            addIndex();
        }
        setIndex(0);
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

    private void removeIndex(int position) {
        mPagerIndicator.removeView(mIndex.get(position));
        mIndex.remove(position);
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

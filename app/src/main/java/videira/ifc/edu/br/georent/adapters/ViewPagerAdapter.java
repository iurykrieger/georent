package videira.ifc.edu.br.georent.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.network.NetworkConnection;

/**
 * Created by iuryk on 31/10/2016.
 */

public class ViewPagerAdapter extends PagerAdapter{

    private Context mContext;
    private List<String> mResources;

    public ViewPagerAdapter(Context mContext, List<String> mResources) {
        this.mContext = mContext;
        this.mResources = mResources;
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
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_pager, container, false);

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
    }
}

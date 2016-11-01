package videira.ifc.edu.br.georent.adapters;

/**
 * Created by luizb on 25/10/2016.
 */

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.List;
import videira.ifc.edu.br.georent.R;

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<Integer> mResources;

    public ViewPagerAdapter(Context mContext, List<Integer> mResources) {
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

        final ImageView mImageView = (ImageView) itemView.findViewById(R.id.niv_item_image);

        mImageView.setImageResource(mResources.get(position));

        container.addView(itemView);
        Log.i("Log", "instantiateItem()");
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

}



package videira.ifc.edu.br.georent.adapters;

/**
 * Created by luizb on 25/10/2016.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageAdapter extends BaseAdapter {
    private Context ctx;
    private Integer[] imgs;

    public ImageAdapter(Context c, Integer[] mImageIds) {
        this.imgs = mImageIds;
        ctx = c;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int pos) {
        return imgs[pos];
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout ll = new LinearLayout(ctx);
        ll.setOrientation(LinearLayout.VERTICAL);
        Gallery.LayoutParams lp = new Gallery.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(lp);

        ImageView i = new ImageView(ctx);
        lp = new Gallery.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        i.setLayoutParams(lp);
        i.setImageResource(imgs[position]);
        ll.addView(i);

        return (ll);
    }

}



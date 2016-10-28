package videira.ifc.edu.br.georent.adapters;

/**
 * Created by luizb on 25/10/2016.
 */

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import videira.ifc.edu.br.georent.R;

public class ImageAdapter extends PagerAdapter {
    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<Integer> imgs;
    private Integer[] test = {R.drawable.new_image, R.drawable.photo2, R.drawable.user};

    public ImageAdapter(Context context) {
        this.imgs = new ArrayList<>();
        for (int i = 0; i < test.length; i++) {
            imgs.add(test[i]);
        }
        this.ctx = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (view == (LinearLayout) object){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.item_image, container);
        ImageView ivImage = (ImageView) itemView.findViewById(R.id.iv_image);

        ivImage.setImageResource(imgs.get(position));

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void addItem(Integer resource){
        imgs.add(resource);
        notifyDataSetChanged();
    }

    /*@Override
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
    }*/

}



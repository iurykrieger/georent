package videira.ifc.edu.br.georent.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import videira.ifc.edu.br.georent.interfaces.RecyclerViewOnClickListener;

/**
 * Created by iuryk on 29/08/2016.
 * <p>
 * Classe que faz a detecção do toque dos itens da lista e
 * dispara o método da interface correspondente
 */
public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

    private Context mContext;
    private GestureDetector mGestureDetector;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;

    /**
     * Sobrescreve os metodos do GestureDetector para executar os métodos da interface
     *
     * @param context
     * @param recyclerView
     * @param recyclerViewOnClickListenerHack
     */
    public RecyclerViewTouchListener(Context context, final RecyclerView recyclerView, RecyclerViewOnClickListener recyclerViewOnClickListenerHack) {
        this.mContext = context;
        this.mRecyclerViewOnClickListener = recyclerViewOnClickListenerHack;
        this.mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
            /**
             * Clique simples no item
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View v = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (v != null && mRecyclerViewOnClickListener != null) {
                    mRecyclerViewOnClickListener.onClickListener(v, recyclerView.getChildAdapterPosition(v));
                }
                return true;
            }

            /**
             * Clique longo no item
             * @param e
             */
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View v = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (v != null && mRecyclerViewOnClickListener != null) {
                    mRecyclerViewOnClickListener.onLongClickListener(v, recyclerView.getChildAdapterPosition(v));
                }
            }
        });
    }

    /**
     * Intercepta o clique na tela e executa o evento do GestureDetector
     *
     * @param rv
     * @param e
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

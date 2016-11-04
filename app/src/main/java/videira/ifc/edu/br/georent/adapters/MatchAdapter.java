package videira.ifc.edu.br.georent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.models.Match;
import videira.ifc.edu.br.georent.network.NetworkConnection;

/**
 * Created by Aluno on 01/11/2016.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    /**
     * Atributos
     */
    private List<Match> mMatchList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    /**
     * Construtor
     *
     * @param mMatchList
     * @param mContext
     */
    public MatchAdapter(List<Match> mMatchList, Context mContext) {
        this.mMatchList = mMatchList;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Cria as views e mantém elas no cache
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MatchAdapter.MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * Infla o layout do item e preenche o layout com o holder
         */
        View view = mLayoutInflater.inflate(R.layout.item_match, parent, false);
        MatchAdapter.MatchViewHolder mvh = new MatchAdapter.MatchViewHolder(view);
        return mvh;
    }

    /**
     * Chamado para setar o item da lista na view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MatchAdapter.MatchViewHolder holder, int position) {
        /**
         * Preenche os valores do objeto no holder baseado na posição da lista
         */
        holder.nivMatch.setImageUrl(mMatchList.get(position).getIdUser().getProfileImage().getPath(),
                NetworkConnection.getInstance(mContext).getImageLoader());
        holder.nivMatch.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                /*if(holder.nivResidence.getDrawable() != null){
                    holder.pbResidenceImage.setVisibility(View.GONE);
                }*/
            }
        });
        holder.tvName.setText(mMatchList.get(position).getIdUser().getName());
        holder.tvDate.setText(mMatchList.get(position).getDateTime().toString());
    }

    /**
     * Número de itens da lista
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mMatchList.size();
    }

    /**
     * Adiciona um novo item na lista
     *
     * @param match
     * @param position
     */
    public void addListItem(Match match, int position) {
        mMatchList.add(match);
        notifyItemInserted(position);
    }

    /**
     * Pega item na posição desejada
     * @param position
     * @return
     */
    public Match getListItem(int position){
        return mMatchList.get(position);
    }

    /**
     * Remove um item da lista
     *
     * @param position
     */
    public void removeListItem(int position) {
        mMatchList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Faz o cache de views na lista -- Obrigatório
     * Utilizado pelo RecyclerViewAdapter para gerenciar o cache de itens
     */
    public class MatchViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView nivMatch;
        public TextView tvName;
        public TextView tvDate;
        public ImageView ivChat;

        public MatchViewHolder(View itemView) {
            super(itemView);

            nivMatch = (NetworkImageView) itemView.findViewById(R.id.niv_item_match);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_item_match);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date_item_match);
            ivChat = (ImageView) itemView.findViewById(R.id.iv_chat_item_match);
        }
    }
}
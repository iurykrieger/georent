package videira.ifc.edu.br.georent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.models.Match;
import videira.ifc.edu.br.georent.models.ResidenceImage;
import videira.ifc.edu.br.georent.network.NetworkConnection;

/**
 * Created by Aluno on 01/11/2016.
 */

public class MatchAdapter extends RecyclerView.Adapter<ResidenceImageAdapter.ResidenceViewHolder> {

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
    public ResidenceImageAdapter.ResidenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * Infla o layout do item e preenche o layout com o holder
         */
        View view = mLayoutInflater.inflate(R.layout.item_user_card, parent, false);
        ResidenceImageAdapter.ResidenceViewHolder rvh = new ResidenceImageAdapter.ResidenceViewHolder(view);
        return rvh;
    }

    /**
     * Chamado para setar o item da lista na view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ResidenceImageAdapter.ResidenceViewHolder holder, int position) {
        /**
         * Preenche os valores do objeto no holder baseado na posição da lista
         */
        holder.nivResidence.setImageUrl(mMatchList.get(position).getPath(),
                NetworkConnection.getInstance(mContext).getImageLoader());
        holder.nivResidence.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(holder.nivResidence.getDrawable() != null){
                    holder.pbResidenceImage.setVisibility(View.GONE);
                }
            }
        });
        holder.tvTitle.setText(mMatchList.get(position).getResidence().getTitle());
        holder.tvAddress.setText(mMatchList.get(position).getResidence().getAddress());
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
     * @param residenceImage
     * @param position
     */
    public void addListItem(ResidenceImage residenceImage, int position) {
        mMatchList.add(residenceImage);
        notifyItemInserted(position);
    }

    /**
     * Pega item na posição desejada
     * @param position
     * @return
     */
    public ResidenceImage getListItem(int position){
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

        public NetworkImageView nivResidence;
        public TextView tvTitle;
        public TextView tvAddress;
        public ProgressBar pbResidenceImage;

        public MatchViewHolder(View itemView) {
            super(itemView);

            nivResidence = (NetworkImageView) itemView.findViewById(R.id.niv_residence_card);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_card);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address_card);
            pbResidenceImage = (ProgressBar) itemView.findViewById(R.id.pb_residence_card);
        }
    }
}
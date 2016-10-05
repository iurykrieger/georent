package videira.ifc.edu.br.georent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.network.NetworkConnection;

/**
 * Created by iuryk on 28/08/2016.
 */
public class ResidenceAdapter extends RecyclerView.Adapter<ResidenceAdapter.ResidenceViewHolder> {

    //Just a test!!!!
    private static final String URL = "http://amazingarchitecture.net/wp-content/uploads/2015/08/Burlingame-Residence-23.jpg";

    /**
     * Atributos
     */
    private List<Residence> mResidenceList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    /**
     * Construtor
     *
     * @param mResidenceList
     * @param mContext
     */
    public ResidenceAdapter(List<Residence> mResidenceList, Context mContext) {
        this.mResidenceList = mResidenceList;
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
    public ResidenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * Infla o layout do item e preenche o layout com o holder
         */
        View view = mLayoutInflater.inflate(R.layout.item_residence_card, parent, false);
        ResidenceViewHolder rvh = new ResidenceViewHolder(view);
        return rvh;
    }

    /**
     * Chamado para setar o item da lista na view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ResidenceViewHolder holder, int position) {
        /**
         * Preenche os valores do objeto no holder baseado na posição da lista
         */
        holder.nivResidence.setImageUrl(URL, NetworkConnection.getInstance(mContext).getImageLoader());
        holder.tvTitle.setText(mResidenceList.get(position).getTitle());
        holder.tvAddress.setText(mResidenceList.get(position).getAddress());
    }

    /**
     * Número de itens da lista
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mResidenceList.size();
    }

    /**
     * Adiciona um novo item na lista
     *
     * @param residence
     * @param position
     */
    public void addListItem(Residence residence, int position) {
        mResidenceList.add(residence);
        notifyItemInserted(position);
    }

    /**
     * Remove um item da lista
     *
     * @param position
     */
    public void removeListItem(int position) {
        mResidenceList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Faz o cache de views na lista -- Obrigatório
     * Utilizado pelo RecyclerViewAdapter para gerenciar o cache de itens
     */
    public class ResidenceViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView nivResidence;
        public TextView tvTitle;
        public TextView tvAddress;

        public ResidenceViewHolder(View itemView) {
            super(itemView);

            nivResidence = (NetworkImageView) itemView.findViewById(R.id.niv_residence_card);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_card);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address_card);
        }
    }
}

package videira.ifc.edu.br.georent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.interfaces.RecyclerViewOnClickListenerHack;
import videira.ifc.edu.br.georent.models.User;

/**
 * Created by iuryk on 28/08/2016.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    /**
     * Atributos
     */
    private List<User> mUserList;
    private LayoutInflater mLayoutInflater;

    /**
     * Construtor
     * @param mUserList
     * @param context
     */
    public UserAdapter(List<User> mUserList, Context context) {
        this.mUserList = mUserList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Cria as views e mantém elas no cache
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * Infla o layout do item e preenche o layout com o holder
         */
        View view = mLayoutInflater.inflate(R.layout.item_user, parent, false);
        UserViewHolder uvh = new UserViewHolder(view);
        return uvh;
    }

    /**
     * Chamado para setar o item da lista na view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        /**
         * Preenche os valores do objeto no holder baseado na posição da lista
         */
        holder.ivUser.setImageResource(mUserList.get(position).getPhoto());
        holder.tvName.setText(mUserList.get(position).getName());
        holder.tvEmail.setText(mUserList.get(position).getEmail());
    }

    /**
     * Número de itens da lista
     * @return
     */
    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    /**
     * Adiciona um novo item na lista
     * @param user
     * @param position
     */
    public void addListItem(User user, int position){
        mUserList.add(user);
        notifyItemInserted(position);
    }

    /**
     * Remove um item da lista
     * @param position
     */
    public void removeListItem(int position){
        mUserList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Faz o cache de views na lista -- Obrigatório
     * Utilizado pelo RecyclerViewAdapter para gerenciar o cache de itens
     */
    public class UserViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivUser;
        public TextView tvName;
        public TextView tvEmail;

        public UserViewHolder(View itemView) {
            super(itemView);

            ivUser = (ImageView) itemView.findViewById(R.id.iv_user);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
        }
    }
}

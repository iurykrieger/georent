package videira.ifc.edu.br.georent.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.activities.MainActivity;
import videira.ifc.edu.br.georent.adapters.UserAdapter;
import videira.ifc.edu.br.georent.interfaces.RecyclerViewOnClickListenerHack;
import videira.ifc.edu.br.georent.listeners.RecyclerViewTouchListener;
import videira.ifc.edu.br.georent.models.NetworkObject;
import videira.ifc.edu.br.georent.models.User;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.network.Transaction;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

public class HomeFragment extends Fragment implements RecyclerViewOnClickListenerHack, Transaction{
    //Parâmetros constantes do fragment
    private static final String ARG_PAGE = "HOME";

    //Parâmetros variáveis do fragment
    private int page;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<User> mUserList;
    private LinearLayoutManager mLinearLayoutManager;
    private UserAdapter mUserAdapter;
    protected ProgressBar mPbLoad;

    public static HomeFragment newInstance(int page) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE,page);
        fragment.setArguments(args);
        return fragment;
    }

    /*************************************************************************
     **                             VIEW                                    **
     *************************************************************************/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.page = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Infla o layout do fragment e pega a view
         */
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        mUserList = new ArrayList<>();
        mPbLoad = (ProgressBar) view.findViewById(R.id.pb_load_user);

        /**
         * Seta as propriedades do LayoutManager para a lista
         */
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        /**
         * Seta as propriedades do recyclerView (componente gráfico da lista)
         */
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_users);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        /**
         * Cria o adapter para amarrar a view aos objetos e seta ele na view
         */
        mUserAdapter = new UserAdapter(mUserList,getActivity());
        //mUserAdapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(mUserAdapter);
        //Adiciona os eventos na lista
        mRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), mRecyclerView, this));

        //Seta listeners de ações do componente
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            /**
             * Chamado ao carregar mais itens na tela
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                /**
                 * Carrega mais itens se o último já foi exibido
                 */
                if(mUserList.size() == mLinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1){
                    NetworkConnection.getConnection(getActivity()).execute(HomeFragment.this, HomeFragment.class.getName());
                }
            }
        });

        /**
         * Seta o refresh do layout
         */
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sr_users);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(NetworkUtil.verifyConnection(getActivity())){
                    NetworkConnection.getConnection(getActivity()).execute(HomeFragment.this, HomeFragment.class.getName());
                }else{
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        /**
         * Retorna a view para preencher a tela
         */
        return view;
    }

    /**
     * Carrega os dados após a view ser criada
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * Executa a requisição
         */
        NetworkConnection.getConnection(getActivity()).execute(this, HomeFragment.class.getName());
    }

    /*************************************************************************
     **                             CLICK                                   **
     *************************************************************************/

    /**
     * Ação de click no item da lista a partir da interface
     * @param view
     * @param position
     */
    @Override
    public void onClickListener(View view, int position) {
        //Joga uma mensagem curta com a posição na tela.
        Log.i("LOG","Clicou!");
        Toast.makeText(getActivity(),"Clique curto! Posição: "+position, Toast.LENGTH_SHORT).show();
    }

    /**
     * Ação de click longo no item da lista a partir da interface
     * @param view
     * @param position
     */
    @Override
    public void onLongClickListener(View view, int position) {
        Toast.makeText(getActivity(),"Position: "+position, Toast.LENGTH_SHORT).show();
        mUserAdapter.removeListItem(position);
    }

    /*************************************************************************
     **                             NETWORK                                 **
     *************************************************************************/

    /**
     * Prepara a requisição do servidor
     * @return
     */
    @Override
    public NetworkObject doBefore() {
        mPbLoad.setVisibility(View.VISIBLE);

        //Verifica conexão com a internet
        if(NetworkUtil.verifyConnection(getActivity())){
            User user = new User();
            return new NetworkObject(user);
        }
        return null;
    }

    /**
     * Obtém a resposta do servidor
     * @param jsonArray
     */
    @Override
    public void doAfter(JSONArray jsonArray) {

        mPbLoad.setVisibility(View.GONE);

        if(jsonArray != null) {
            Gson gson = new Gson();
            try {
                for(int i = 0; i < jsonArray.length(); i++){
                    User u = gson.fromJson(jsonArray.getJSONObject(i).toString(), User.class);
                    mUserAdapter.addListItem(u, 0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getActivity(), "Deu pau pizaão.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Cancela todas as requisições
     */
    @Override
    public void onStop() {
        super.onStop();
        NetworkConnection.getConnection(getActivity()).getRequestQueue().cancelAll(HomeFragment.class.getName());
    }
}

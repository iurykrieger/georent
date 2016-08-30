package videira.ifc.edu.br.georent.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.activities.MainActivity;
import videira.ifc.edu.br.georent.adapters.UserAdapter;
import videira.ifc.edu.br.georent.interfaces.RecyclerViewOnClickListenerHack;
import videira.ifc.edu.br.georent.listeners.RecyclerViewTouchListener;
import videira.ifc.edu.br.georent.models.User;

public class HomeFragment extends Fragment implements RecyclerViewOnClickListenerHack{
    //Parâmetros constantes do fragment
    private static final String ARG_PAGE = "HOME";

    //Parâmetros variáveis do fragment
    private int page;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<User> mUserList;
    private LinearLayoutManager mLinearLayoutManager;
    private UserAdapter mUserAdapter;

    public static HomeFragment newInstance(int page) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE,page);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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
        mUserList = ((MainActivity) getActivity()).getSetUserList(10);
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
                    List<User> tmpList = ((MainActivity) getActivity()).getSetUserList(10);
                    for (User u : tmpList){
                        mUserAdapter.addListItem(u, mUserList.size());
                    }
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
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        /**
         * Retorna a view para preencher a tela
         */
        return view;
    }

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
}

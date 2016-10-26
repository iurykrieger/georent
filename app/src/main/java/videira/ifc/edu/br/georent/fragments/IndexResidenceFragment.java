package videira.ifc.edu.br.georent.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.activities.ShowResidenceActivity;
import videira.ifc.edu.br.georent.adapters.ResidenceImageAdapter;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.interfaces.RecyclerViewOnClickListener;
import videira.ifc.edu.br.georent.listeners.RecyclerViewTouchListener;
import videira.ifc.edu.br.georent.models.ResidenceImage;
import videira.ifc.edu.br.georent.repositories.ResidenceImageRepository;
import videira.ifc.edu.br.georent.utils.FakeGenerator;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

public class IndexResidenceFragment extends Fragment implements RecyclerViewOnClickListener, Bind<ResidenceImage> {
    //Parâmetros constantes do fragment
    private static final String ARG_PAGE = "HOME";

    //Parâmetros variáveis do fragment
    private int page;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ResidenceImage> mResidenceImageList;
    private LinearLayoutManager mLinearLayoutManager;
    private ResidenceImageAdapter mResidenceImageAdapter;
    private ResidenceImageRepository mResidenceImageRepository;
    protected ProgressBar mProgressBar;

    public static IndexResidenceFragment newInstance(int page) {
        IndexResidenceFragment fragment = new IndexResidenceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    /*************************************************************************
     * *                             VIEW                                    **
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
        final View view = inflater.inflate(R.layout.fragment_index_residence, container, false);

        mResidenceImageRepository = new ResidenceImageRepository(this.getActivity(), this);
        mResidenceImageList = new ArrayList<>();
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_residence_index);

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
        mResidenceImageAdapter = new ResidenceImageAdapter(mResidenceImageList, getActivity());
        mRecyclerView.setAdapter(mResidenceImageAdapter);
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
                if (mResidenceImageList.size() == mLinearLayoutManager.findLastCompletelyVisibleItemPosition() + 1) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    doLoad();
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
                doLoad();
            }
        });

        if (mResidenceImageList.isEmpty()) {
            mProgressBar.setVisibility(View.VISIBLE);
            doLoad();
        }

        /**
         * Retorna a view para preencher a tela
         */
        return view;
    }

    @Override
    public void onStop() {
        mResidenceImageRepository.cancelRequests();
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        super.onStop();
    }

    /*************************************************************************
     **                             CLICK                                   **
     *************************************************************************/

    /**
     * Ação de click no item da lista a partir da interface
     *
     * @param view
     * @param position
     */
    @Override
    public void onClickListener(View view, int position) {
        //Joga uma mensagem curta com a posição na tela.
        int index = mResidenceImageAdapter.getListItem(position).getResidence().getIdResidence();
        Intent i = new Intent(getActivity(), ShowResidenceActivity.class);
        i.putExtra("idResidence", index);
        startActivity(i);
        Log.i("LOG", "Clicou!");
    }

    /**
     * Ação de click longo no item da lista a partir da interface
     *
     * @param view
     * @param position
     */
    @Override
    public void onLongClickListener(View view, int position) {
        Toast.makeText(getActivity(), "Position: " + position, Toast.LENGTH_SHORT).show();
        //mResidenceImageAdapter.removeListItem(position);
    }

    /*************************************************************************
     * *                            SERVIÇO                                  **
     *************************************************************************/

    @Override
    public void doLoad() {
        if (NetworkUtil.verifyConnection(getActivity())) {
            //mResidenceImageRepository.getTopImages(); //Bind Correto
            doMultipleBind(FakeGenerator.getInstance().getResidenceImages(10));
        } else {
            doError(new UnknownHostException());
        }
    }

    @Override
    public void doSingleBind(ResidenceImage result) {

    }

    @Override
    public void doMultipleBind(List<ResidenceImage> result) {
        boolean isNewer = mSwipeRefreshLayout.isRefreshing();

        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

        for (ResidenceImage residenceImage : result) {
            if (isNewer) {
                mResidenceImageAdapter.addListItem(residenceImage, 0);
            } else {
                mResidenceImageAdapter.addListItem(residenceImage, mResidenceImageAdapter.getItemCount());
            }
        }
    }

    @Override
    public void doError(Exception ex) {
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar snackbar = null;

        if (ex instanceof UnknownHostException) {
            snackbar = Snackbar
                    .make(getView(), R.string.no_internet, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Conectar", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(it);
                        }
                    })
                    .setActionTextColor(ContextCompat.getColor(getActivity(), R.color.accent));
        } else {
            snackbar = Snackbar
                    .make(getView(), R.string.unknown_error, Snackbar.LENGTH_SHORT)
                    .setActionTextColor(ContextCompat.getColor(getActivity(), R.color.accent));
        }

        snackbar.show();
    }
}

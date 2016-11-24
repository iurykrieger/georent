package videira.ifc.edu.br.georent.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.net.UnknownHostException;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.models.User;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.repositories.ResidenceRepository;
import videira.ifc.edu.br.georent.repositories.UserRepository;
import videira.ifc.edu.br.georent.utils.CircularNetworkImageView;
import videira.ifc.edu.br.georent.utils.FakeGenerator;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

public class UserProfileFragment extends Fragment implements Bind<User>{

    //Parâmetros constantes do fragment
    public static final String ARG_PAGE_PROFILE = "PROFILE";

    //Variáveis do fragment
    private int page;
    private ProgressBar mProgressBar;
    private UserRepository mUserRepository;
    private Intent mIntent;
    private User mUser;
    private View mView;

    public static UserProfileFragment newInstance(int page) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_PROFILE, page);
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
            this.page = getArguments().getInt(ARG_PAGE_PROFILE);
        }
    }

    @Override
    public void onStart() {
        doLoad();
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        return mView;
    }

    /*************************************************************************
     **                            SERVIÇO                                  **
     *************************************************************************/

    @Override
    public void doLoad() {
        if (NetworkUtil.verifyConnection(getActivity())) {
            if (mProgressBar.getVisibility() == View.GONE) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            //mUserRepository.getEagerResidenceById(mIntent.getIntExtra("idResidence", 0)); //Bind Correto
            doSingleBind(FakeGenerator.getInstance().getUser(0)); //Geração Fake!
        } else {
            doError(new UnknownHostException());
        }
    }

    @Override
    public void doSingleBind(User result) {
        mUser = result;
        Log.i("User", mUser.getName());
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
        }

        final CircularNetworkImageView cnivUser = (CircularNetworkImageView) mView.findViewById(R.id.cniv_user_profile);
        final TextView tvName = (TextView) mView.findViewById(R.id.tv_name_user_profile);
        final TextView tvEmail = (TextView) mView.findViewById(R.id.tv_email_user_profile);

        cnivUser.setImageUrl(mUser.getProfileImage().getPath(), NetworkConnection.getInstance(getActivity()).getImageLoader());
        tvName.setText(mUser.getName());
        tvEmail.setText(mUser.getEmail());
    }

    @Override
    public void doMultipleBind(List<User> results) {

    }

    @Override
    public void doError(Exception ex) {

    }
}

package videira.ifc.edu.br.georent.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.net.UnknownHostException;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.interfaces.Bind;
import videira.ifc.edu.br.georent.models.User;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.repositories.UserRepository;
import videira.ifc.edu.br.georent.utils.AuthUtil;
import videira.ifc.edu.br.georent.utils.CircularNetworkImageView;
import videira.ifc.edu.br.georent.utils.NetworkUtil;

public class UserProfileFragment extends Fragment implements Bind<User>, CompoundButton.OnCheckedChangeListener {

    //Parâmetros constantes do fragment
    public static final String ARG_PAGE_PROFILE = "PROFILE";

    //Variáveis do fragment
    private int page;
    private ProgressBar mProgressBar;
    private UserRepository mUserRepository;
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
        mView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.pb_user_profile);
        mUserRepository = new UserRepository(getActivity(), this);

        return mView;
    }

    /*************************************************************************
     * *                            SERVIÇO                                * *
     *************************************************************************/

    @Override
    public void doLoad() {
        if (NetworkUtil.verifyConnection(getActivity())) {
            if (mProgressBar.getVisibility() == View.GONE) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            mUserRepository.getUserById(AuthUtil.getLoggedUserId(getActivity()));
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
        final Switch swType = (Switch) mView.findViewById(R.id.sw_type_user_profile);
        final TextView tvSettings = (TextView) mView.findViewById(R.id.tv_settings_user_profile);
        final TextView tvType = (TextView) mView.findViewById(R.id.tv_type_user_profile);
        final TextView tvLocation = (TextView) mView.findViewById(R.id.tv_location_user_profile);
        final TextView tvRange = (TextView) mView.findViewById(R.id.tv_range_user_profile);

        if (mUser.getProfileImage() != null) {
            cnivUser.setImageUrl(mUser.getProfileImage().getPath(), NetworkConnection.getInstance(getActivity()).getImageLoader());
        }
        tvName.setText(mUser.getName());
        tvEmail.setText(mUser.getEmail());
        swType.setOnCheckedChangeListener(this);
        swType.setChecked(mUser.getType().equals(1));
        if (swType.isChecked()) {
            tvType.setText(getString(R.string.locator));
        } else {
            tvType.setText(getString(R.string.occupier));
        }
        tvLocation.setText(mUser.getIdLocation().getIdCity().getName() + " - " +
                mUser.getIdLocation().getIdCity().getUf() + ". ");
        tvRange.setText("Distância de busca : " + mUser.getDistance() + "Km");
    }

    @Override
    public void doMultipleBind(List<User> results) {

    }

    @Override
    public void doError(Exception ex) {

    }

    /*************************************************************************
     * *                            CHECK                                  * *
     *************************************************************************/

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final TextView tvType = (TextView) mView.findViewById(R.id.tv_type_user_profile);
        if (isChecked) {
            tvType.setText(getString(R.string.locator));
        } else {
            tvType.setText(getString(R.string.occupier));
        }
    }
}

package videira.ifc.edu.br.georent.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.network.NetworkConnection;
import videira.ifc.edu.br.georent.utils.CircularNetworkImageView;
import videira.ifc.edu.br.georent.utils.FakeGenerator;

public class UserProfileFragment extends Fragment {

    //Parâmetros constantes do fragment
    public static final String ARG_PAGE_PROFILE = "PROFILE";

    //Variáveis do fragment
    private int page;

    public static ResidenceIndexFragment newInstance(int page) {
        ResidenceIndexFragment fragment = new ResidenceIndexFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_PROFILE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.page = getArguments().getInt(ARG_PAGE_PROFILE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        final CircularNetworkImageView cnivUser = (CircularNetworkImageView) view.findViewById(R.id.cniv_user_profile);
        cnivUser.setImageUrl(FakeGenerator.getInstance().getUsers().get(0).getProfileImage().getPath(), NetworkConnection.getInstance(getActivity()).getImageLoader());

        return view;
    }
}

package videira.ifc.edu.br.georent.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import videira.ifc.edu.br.georent.R;

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
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }
}

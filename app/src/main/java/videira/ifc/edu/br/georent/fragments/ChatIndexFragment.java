package videira.ifc.edu.br.georent.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import videira.ifc.edu.br.georent.R;

public class ChatIndexFragment extends Fragment {
    //Parâmetros constantes do fragment
    public static final String ARG_PAGE_CHAT = "CHAT";

    //Parâmetros variáveis do fragment
    private int page;


    public static ChatIndexFragment newInstance(int page) {
        ChatIndexFragment fragment = new ChatIndexFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_CHAT, page);
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
            this.page = getArguments().getInt(ARG_PAGE_CHAT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_index, container, false);
    }
}

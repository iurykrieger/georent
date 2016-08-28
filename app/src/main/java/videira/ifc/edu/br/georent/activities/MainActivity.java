package videira.ifc.edu.br.georent.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.ViewPagerAdapter;
import videira.ifc.edu.br.georent.fragments.HomeFragment;
import videira.ifc.edu.br.georent.models.User;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private final int[] imageResId = {R.drawable.ic_home_black_24dp,
                                      R.drawable.ic_chat_black_24dp,
                                      R.drawable.ic_person_black_24dp};

    /**
     * Cria a activity e seta as propriedades do layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Inicializa o tabLayout
         */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        /**
         * Adiciona icones e remove textos
         * Passar esse código para um componente separado para controlar só o tabLayout
         */
        for (int i = 0; i < imageResId.length; i++){
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
            tabLayout.getTabAt(i).getIcon().setTint(getResources().getColor(R.color.icons, getTheme()));
            tabLayout.getTabAt(i).setText(null);
            tabLayout.setSelectedTabIndicatorHeight(6);
        }

        /**
         * Troca a cor do icone ao selecionar a aba
         */
        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getIcon().setTint(getResources().getColor(R.color.accent, getTheme()));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setTint(getResources().getColor(R.color.accent, getTheme()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setTint(getResources().getColor(R.color.icons, getTheme()));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Inicializa o viewPager a adiciona os fragments
     * @param viewPager
     */
    public void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "RENT");
        adapter.addFragment(new HomeFragment(), "CHAT");
        adapter.addFragment(new HomeFragment(), "PROFILE");
        viewPager.setAdapter(adapter);
    }

    /**
     * Cria o menu com base no layout do menu_main
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public List<User> getSetUserList(int qtd){
        String[] names = new String[] {"Iury Krieger", "José Luiz", "Anderson Rostirolla",
                                        "Klaus Krieger", "Maristela Krieger", "João Silva",
                                        "Phelipp Silva", "Matheus Mezalira", "Matheus Zanini",
                                        "Angelita FDP"};
        String[] emails = new String[] {"iurykrieger96@gmail.com", "joseluiz.27@gmail.com", "andersongay@gmail.com",
                                        "klaus.krieger@hotmail.com","maristela.krieger@hotmail.com", "joao.silva@suamae.com.br",
                                        "ph.matador@hotmail.com","teteus.purpurina@star.flower.br","zanini.moto@motocas.com",
                                        "angelita.deanjontemnada@vtnc.com"};
        List<User> tmpList = new ArrayList<>();
        for(int i = 0; i < qtd; i++){
            User u = new User();
            u.setName(names[i % names.length]);
            u.setEmail(emails[i % emails.length]);
            u.setPhoto(R.drawable.ic_person_black_24dp);
            tmpList.add(u);
        }
        return tmpList;
    }

}

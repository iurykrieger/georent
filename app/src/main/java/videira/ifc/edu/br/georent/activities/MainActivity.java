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
import videira.ifc.edu.br.georent.fragments.TestFragment;
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
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.primary, getTheme()));

        /**
         * Adiciona icones e remove textos
         * Passar esse código para um componente separado para controlar só o tabLayout
         */
        for (int i = 0; i < imageResId.length; i++){
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
            tabLayout.getTabAt(i).getIcon().setTint(getResources().getColor(R.color.secondary_text, getTheme()));
            tabLayout.getTabAt(i).setText(null);
            tabLayout.setSelectedTabIndicatorHeight(getResources().getInteger(R.integer.selected_tab_indicator_height));
        }

        /**
         * Troca a cor do icone ao selecionar a aba
         */
        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getIcon().setTint(getResources().getColor(R.color.primary, getTheme()));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setTint(getResources().getColor(R.color.primary, getTheme()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setTint(getResources().getColor(R.color.secondary_text, getTheme()));
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
        adapter.addFragment(new TestFragment(), "RENT");
        adapter.addFragment(new TestFragment(), "CHAT");
        adapter.addFragment(new TestFragment(), "PROFILE");
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

    /**
     * Cria lista fictícia que no futuro virá do banco de dados
     * @param qtd
     * @return
     */
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
            //u.setPhoto(R.drawable.logo_no_shadow);
            tmpList.add(u);
        }
        return tmpList;
    }

}

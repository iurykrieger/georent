package videira.ifc.edu.br.georent.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.ViewPagerAdapter;
import videira.ifc.edu.br.georent.fragments.ResidenceIndexFragment;

public class MainActivity extends AppCompatActivity {

    private final int[] imageResId = {R.drawable.ic_home_black_24dp,
            R.drawable.ic_chat_black_24dp,
            R.drawable.ic_person_black_24dp};
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton mFloatingActionButton;

    /**
     * Cria a activity e seta as propriedades do layout
     *
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
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.primary));

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fb_action);

        /**
         * Adiciona icones e remove textos
         * Passar esse código para um componente separado para controlar só o tabLayout
         */
        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
            tabLayout.getTabAt(i).getIcon().setTint(ContextCompat.getColor(this, R.color.secondary_text));
            tabLayout.getTabAt(i).setText(null);
            tabLayout.setSelectedTabIndicatorHeight(getResources().getInteger(R.integer.selected_tab_indicator_height));
        }

        /**
         * Troca a cor do icone ao selecionar a aba
         */
        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getIcon().setTint(ContextCompat.getColor(MainActivity.this, R.color.primary));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setTint(ContextCompat.getColor(MainActivity.this, R.color.primary));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setTint(ContextCompat.getColor(MainActivity.this, R.color.secondary_text));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /**
         * Seta o click do botão
         */
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowResidenceActivity.class);
                startActivity(i);
            }
        });
    }

    /**
     * Inicializa o viewPager a adiciona os fragments
     *
     * @param viewPager
     */
    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ResidenceIndexFragment(), "RENT");
        adapter.addFragment(new ResidenceIndexFragment(), "CHAT");
        adapter.addFragment(new ResidenceIndexFragment(), "PROFILE");
        viewPager.setAdapter(adapter);
    }

    /**
     * Cria o menu com base no layout do menu_main
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

}

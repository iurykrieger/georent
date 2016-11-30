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
import android.view.MenuItem;
import android.view.View;

import videira.ifc.edu.br.georent.R;
import videira.ifc.edu.br.georent.adapters.FragmentPagerAdapter;
import videira.ifc.edu.br.georent.fragments.ChatIndexFragment;
import videira.ifc.edu.br.georent.fragments.ResidenceIndexFragment;
import videira.ifc.edu.br.georent.fragments.UserProfileFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TabLayout.OnTabSelectedListener, MenuItem.OnMenuItemClickListener {

    private final int[] imageResId = {
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_chat_black_24dp,
            R.drawable.ic_person_black_24dp};
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFloatingActionButton;
    private Menu mMenu;

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
         * Inicializa o mTabLayout
         */
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fb_action);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.primary));
        mTabLayout.setSelectedTabIndicatorHeight(getResources().getInteger(R.integer.selected_tab_indicator_height));

        /**
         * Adiciona icones e remove textos
         * Passar esse código para um componente separado para controlar só o mTabLayout
         */
        for (int i = 0; i < imageResId.length; i++) {
            mTabLayout.getTabAt(i).setIcon(imageResId[i]);
            mTabLayout.getTabAt(i).setText(null);
            mTabLayout.getTabAt(i).getIcon().setTint(ContextCompat.getColor(this, R.color.secondary_text));
        }

        /**
         * Troca a cor do icone ao selecionar a aba
         */
        mTabLayout.addOnTabSelectedListener(this);

        /**
         * Seta o click do botão
         */
        mFloatingActionButton.setOnClickListener(this);
    }

    /**
     * Inicializa o mViewPager a adiciona os fragments
     *
     * @param viewPager
     */
    public void setupViewPager(ViewPager viewPager) {
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ResidenceIndexFragment(), ResidenceIndexFragment.ARG_PAGE_RESIDENCE);
        adapter.addFragment(new ChatIndexFragment(), ChatIndexFragment.ARG_PAGE_CHAT);
        adapter.addFragment(new UserProfileFragment(), UserProfileFragment.ARG_PAGE_PROFILE);
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
        String pageTitle = mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem()).toString();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mMenu = menu;
        mMenu.findItem(R.id.action_edit).setVisible(false);
        mMenu.findItem(R.id.action_edit).setOnMenuItemClickListener(this);
        return true;
    }

    /*************************************************************************
     * *                             VIEW                                  * *
     *************************************************************************/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb_action: {
                String pageTitle = mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem()).toString();
                Intent intent = null;
                switch (pageTitle) {
                    case ResidenceIndexFragment.ARG_PAGE_RESIDENCE: {
                        intent = new Intent(MainActivity.this, MapsActivity.class);
                    }
                    break;
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
            break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).getIcon().setTint(ContextCompat.getColor(this, R.color.secondary_text));
        }
        tab.getIcon().setTint(ContextCompat.getColor(MainActivity.this, R.color.primary));
        FragmentPagerAdapter adapter = (FragmentPagerAdapter) mViewPager.getAdapter();
        String pageTitle = adapter.getPageTitle(mViewPager.getCurrentItem()).toString();


        switch (pageTitle) {
            case ResidenceIndexFragment.ARG_PAGE_RESIDENCE: {
                mFloatingActionButton.setImageResource(R.drawable.ic_add_black_24dp);
                mFloatingActionButton.setVisibility(View.VISIBLE);
                mMenu.findItem(R.id.action_search).setVisible(true);
                mMenu.findItem(R.id.action_edit).setVisible(false);
            }
            break;
            case UserProfileFragment.ARG_PAGE_PROFILE: {
                mFloatingActionButton.setVisibility(View.GONE);
                mMenu.findItem(R.id.action_search).setVisible(false);
                mMenu.findItem(R.id.action_edit).setVisible(true);
            }
            break;
            default: {
                mMenu.findItem(R.id.action_search).setVisible(false);
                mMenu.findItem(R.id.action_edit).setVisible(false);
                mFloatingActionButton.setVisibility(View.GONE);
            }
            break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, UserRegisterActivity.class);
        startActivity(intent);
        return true;
    }
}

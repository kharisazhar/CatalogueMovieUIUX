package com.dicoding.kharisazhar.cataloguemovieuiux;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.dicoding.kharisazhar.cataloguemovieuiux.adapter.AdapterMovie;
import com.dicoding.kharisazhar.cataloguemovieuiux.fragment.FavoriteFragment;
import com.dicoding.kharisazhar.cataloguemovieuiux.fragment.NowPlayingFragment;
import com.dicoding.kharisazhar.cataloguemovieuiux.fragment.PopularFragment;
import com.dicoding.kharisazhar.cataloguemovieuiux.fragment.UpComingFragment;
import com.dicoding.kharisazhar.cataloguemovieuiux.model.Result;
import com.dicoding.kharisazhar.cataloguemovieuiux.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
//    private Fragment nowFragment = NowPlayingFragment;
    NowPlayingFragment nowPlayingFragment;
    Fragment nowFragment = new NowPlayingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (savedInstanceState != null){
            nowFragment = getSupportFragmentManager().getFragment(savedInstanceState, "KEY_VALUE");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "KEY_VALUE", nowFragment);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new NowPlayingFragment(), "NOW PLAING");
        String now_playing = getResources().getString(R.string.now_playing);
        adapter.addFragment(new NowPlayingFragment(),now_playing);

        String popular = getResources().getString(R.string.popular);
        adapter.addFragment(new PopularFragment(), popular);

        String up_coming = getResources().getString(R.string.up_coming);
        adapter.addFragment(new UpComingFragment(), up_coming);

        String favorite = getResources().getString(R.string.favorite);
        adapter.addFragment(new FavoriteFragment(), favorite);

        viewPager.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                intent.putExtra("EXTRA_QUERY",s);
                startActivity(intent);
                Toast.makeText(MainActivity.this, " "+s, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

package mobi.gastronomica.activity;

import mobi.gastronomica.UILApplication;
import mobi.gastronomica.R;
import mobi.gastronomica.adapte.PagerSlidingTabStrip;
import mobi.gastronomica.adapte.ViewPagerAdapteRestaurantes;
import mobi.gastronomica.components.TabPagerItemRestaurantes;
import mobi.gastronomica.utils.Constants;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class ViewPagerActivityRestaurantes extends BaseActivity {

    private static final String TAG = makeLogTag(ViewPagerActivityRestaurantes.class);
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_restaurantes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //obtener la posicion del restauran tocado
        if (getIntent().getExtras().containsKey(Constants.ID)) {
            this.position = getIntent().getExtras().getInt(Constants.ID);
            LOGI(TAG, Integer.toString(this.position));
        } else {
            finish();
        }

        //Tab
        UILApplication.TABS.add(new TabPagerItemRestaurantes(0, getResources().getString(R.string.info)));
        UILApplication.TABS.add(new TabPagerItemRestaurantes(1, getResources().getString(R.string.resena)));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new ViewPagerAdapteRestaurantes(getSupportFragmentManager(), UILApplication.TABS));

        PagerSlidingTabStrip mSlidingTabLayout = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mSlidingTabLayout.setTextColorResource(android.R.color.white);
        mSlidingTabLayout.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        //getSupportActionBar().setTitle(getResources().getString(R.string.info));
                        break;
                    case 1:
                        //getSupportActionBar().setTitle(getResources().getString(R.string.resena));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public int dataInformation() {//agregar, obtener el valor de la lista
        return this.position;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            UILApplication.TABS.clear();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        UILApplication.TABS.clear();
        finish();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        UILApplication.TABS.clear();
    }

}
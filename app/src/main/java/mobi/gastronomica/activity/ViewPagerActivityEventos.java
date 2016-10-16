package mobi.gastronomica.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import mobi.gastronomica.UILApplication;
import mobi.gastronomica.R;
import mobi.gastronomica.adapte.PagerSlidingTabStrip;
import mobi.gastronomica.adapte.ViewPagerAdapteEvetos;
import mobi.gastronomica.components.TabPagerItemEventos;
import mobi.gastronomica.model.EventosRecientes;

import static mobi.gastronomica.utils.Logs.makeLogTag;

public class ViewPagerActivityEventos extends BaseActivity {

    private static final String TAG = makeLogTag(ViewPagerActivityEventos.class);

    //var
    private int position = 0;
    EventosRecientes obj = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_eventos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_view_pager_activity_eventos));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Tab
        UILApplication.TABS_EVENTOS.add(new TabPagerItemEventos(0, getResources().getString(R.string.reientes)));
        UILApplication.TABS_EVENTOS.add(new TabPagerItemEventos(1, getResources().getString(R.string.pasados)));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new ViewPagerAdapteEvetos(getSupportFragmentManager(), UILApplication.TABS_EVENTOS));

        PagerSlidingTabStrip mSlidingTabLayout = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mSlidingTabLayout.setTextColorResource(android.R.color.white);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        UILApplication.TABS_EVENTOS.clear();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
        UILApplication.TABS_EVENTOS.clear();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        UILApplication.TABS_EVENTOS.clear();
    }
}

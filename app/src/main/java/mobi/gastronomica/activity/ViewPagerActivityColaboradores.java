package mobi.gastronomica.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import mobi.gastronomica.UILApplication;
import mobi.gastronomica.R;
import mobi.gastronomica.adapte.PagerSlidingTabStrip;
import mobi.gastronomica.adapte.ViewPagerAdapteColaboradores;
import mobi.gastronomica.components.TabPagerItemColaboradores;
import mobi.gastronomica.model.Colaboradores;

import static mobi.gastronomica.utils.Logs.makeLogTag;

public class ViewPagerActivityColaboradores extends BaseActivity {

    private static final String TAG = makeLogTag(ViewPagerActivityColaboradores.class);

    //var
    private int position = 0;
    Colaboradores obj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_activity_colaboradores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Actionbar
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //obtener la posicion del restauran tocado
        if (getIntent().getExtras().containsKey("PARCEL")) {
            this.obj = getIntent().getParcelableExtra("PARCEL");
        } else {
            finish();
        }

        //Tab
        UILApplication.TABS_COLABORADORES.add(new TabPagerItemColaboradores(0, getResources().getString(R.string.info)));
        UILApplication.TABS_COLABORADORES.add(new TabPagerItemColaboradores(1, getResources().getString(R.string.publicacion)));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(1);

        mViewPager.setAdapter(new ViewPagerAdapteColaboradores(getSupportFragmentManager(), UILApplication.TABS_COLABORADORES));

        PagerSlidingTabStrip mSlidingTabLayout = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mSlidingTabLayout.setTextColorResource(android.R.color.white);
        mSlidingTabLayout.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_view_pager_activity_colaboradores));
                        break;
                    case 1:
                        getSupportActionBar().setTitle(getResources().getString(R.string.publicacion));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public Colaboradores dataInformation() {//agregar, obtener el valor de la lista
        return this.obj;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        UILApplication.TABS_COLABORADORES.clear();
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        UILApplication.TABS_COLABORADORES.clear();
        finish();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        UILApplication.TABS_COLABORADORES.clear();
    }
}

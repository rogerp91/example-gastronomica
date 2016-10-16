package mobi.gastronomica.adapte;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import mobi.gastronomica.components.TabPagerItemColaboradores;

public class ViewPagerAdapteColaboradores extends FragmentPagerAdapter{

    private List<TabPagerItemColaboradores> mTabs;

    public ViewPagerAdapteColaboradores(FragmentManager fragmentManager, List<TabPagerItemColaboradores> mTabs) {
        super(fragmentManager);
        // TODO Auto-generated constructor stub
        this.mTabs = mTabs;
    }

    @Override
    public Fragment getItem(int pos) {
        // TODO Auto-generated method stub
        return this.mTabs.get(pos).createFragment();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTabs.get(position).getTitle();
    }
}

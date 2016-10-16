package mobi.gastronomica.components;

import android.support.v4.app.Fragment;

import mobi.gastronomica.fragment.InfoColaboradoresFragment;
import mobi.gastronomica.fragment.PublicacionesColaboradoresFragment;

public class TabPagerItemColaboradores {
    private final CharSequence mTitle;
    private final int position;
    private Fragment[] listFragments;

    public TabPagerItemColaboradores(int position, CharSequence title) {
        // TODO Auto-generated constructor stub
        this.mTitle = title;
        this.position = position;
        this.listFragments = new Fragment[]{new InfoColaboradoresFragment().newInstance(), new PublicacionesColaboradoresFragment().newInstance()};
    }

    public Fragment createFragment() {
        return this.listFragments[position];
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }
}

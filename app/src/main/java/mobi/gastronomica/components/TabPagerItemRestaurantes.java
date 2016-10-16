package mobi.gastronomica.components;

import mobi.gastronomica.fragment.InfoDondeIrFragment;
import mobi.gastronomica.fragment.ResenaRestaurantFragment;
import android.support.v4.app.Fragment;

public class TabPagerItemRestaurantes {

	private final CharSequence mTitle;
	private final int position;
	private Fragment[] listFragments;

	public TabPagerItemRestaurantes(int position, CharSequence title) {
		// TODO Auto-generated constructor stub
		this.mTitle = title;
		this.position = position;
		this.listFragments = new Fragment[] {new InfoDondeIrFragment().newInstance(), new ResenaRestaurantFragment().newInstance() };
	}

	public Fragment createFragment() {
		return this.listFragments[position];
	}

	public CharSequence getTitle() {
		return this.mTitle;
	}
	
}// endClass

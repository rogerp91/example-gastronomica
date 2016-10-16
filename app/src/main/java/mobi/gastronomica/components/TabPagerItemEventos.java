package mobi.gastronomica.components;

import android.support.v4.app.Fragment;

import mobi.gastronomica.fragment.PasadosEventosFragment;
import mobi.gastronomica.fragment.RecientesEventosFragment;

public class TabPagerItemEventos {

	private final CharSequence mTitle;
	private final int position;
	private Fragment[] listFragments;

	public TabPagerItemEventos(int position, CharSequence title) {
		// TODO Auto-generated constructor stub
		this.mTitle = title;
		this.position = position;
		this.listFragments = new Fragment[] {new RecientesEventosFragment().newInstance(), new PasadosEventosFragment().newInstance()};
	}

	public Fragment createFragment() {
		return this.listFragments[position];
	}

	public CharSequence getTitle() {
		return this.mTitle;
	}
	
}// endClass

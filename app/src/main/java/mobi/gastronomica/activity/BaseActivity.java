package mobi.gastronomica.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import mobi.gastronomica.R;
import mobi.gastronomica.fragment.DetailResenaFragment;
import mobi.gastronomica.fragment.DetailsFragmentEventos;
import mobi.gastronomica.fragment.DetailsImagenFragment;
import mobi.gastronomica.fragment.ListColaboradoresFragment;
import mobi.gastronomica.fragment.ListDondeIrFragment;
import mobi.gastronomica.fragment.PublicacionDetailsFragment;
import mobi.gastronomica.fragment.RecientesEventosFragment;
import mobi.gastronomica.utils.Constants;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void IdFragment(Intent intent) {//ver que fragment fue llamado

        if (intent.getExtras() != null) {

            switch (intent.getExtras().getInt(Constants.ID_FRAGMENT)) {

                case Constants.FRAGMENT_LIST_DONDE_IR://FRAGMENT_RESTAURANT
                    goToFragment(ListDondeIrFragment.newInstance());
                    break;

                case Constants.FRAGMENT_LIST_EVENTOS://FRAGMENT_EVENTOS
                    goToFragment(RecientesEventosFragment.newInstance());
                    break;

                case Constants.FRAGMENT_DETAIL_IMG://FRAGMENT_DETAIL_IMG
                    goToFragment(DetailsImagenFragment.newInstance(intent.getExtras().getParcelable(Constants.SEND_DATA)));
                    break;

                case Constants.FRAGMENT_EVENTOS_DETAIL://FRAGMENT_EVENTOS_DETAIL
                    goToFragment(DetailsFragmentEventos.newInstance(intent.getExtras().getParcelable(Constants.SEND_DATA)));
                    break;

                case Constants.FRAGMENT_LIST_COLABORADORES://FRAGMENT_EVENTOS_DETAIL
                    goToFragment(ListColaboradoresFragment.newInstance());
                    break;

                case Constants.FRAGMENT_PUBLICACION_DETAILS://FRAGMENT_EVENTOS_DETAIL
                    goToFragment(PublicacionDetailsFragment.newInstance(intent.getExtras().getParcelable(Constants.SEND_DATA)));
                    break;
                case Constants.FRAGMENT_RESENA_DETAILS://FRAGMENT_EVENTOS_DETAIL
                    goToFragment(DetailResenaFragment.newInstance(intent.getExtras().getParcelable(Constants.PARCELABLE)));
                    break;
            }
        }

    }

    protected void goToFragment(android.support.v4.app.Fragment fragment) {//ir al fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();
    }

    protected void goToFragmentExtras(android.support.v4.app.Fragment fragment) {//ir al fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
    }

    protected void goToActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public void setActionBar(Toolbar toolbar) {
        super.setActionBar(toolbar);
    }

    @SuppressWarnings("deprecation")
    public Drawable getDrawableResource(int ids) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getDrawable(ids);
        } else {
            return getResources().getDrawable(ids);
        }
    }
}//endClass

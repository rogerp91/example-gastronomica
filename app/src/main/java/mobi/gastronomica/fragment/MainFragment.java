package mobi.gastronomica.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;
import mobi.gastronomica.R;
import mobi.gastronomica.activity.ContainerActivity;
import mobi.gastronomica.activity.ViewPagerActivityEventos;
import mobi.gastronomica.components.SimpleLocation;
import mobi.gastronomica.utils.Constants;


public class MainFragment extends BaseFragment {

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    View view = null;
    private SimpleLocation location = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, this.view);

        actionBarActivity = (AppCompatActivity) getActivity();// obtener actividad en q esta trabajando el fragment
        actionBarActivity.getSupportActionBar().setElevation(0.0f);
        actionBarActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        location = new SimpleLocation(getActivity());

        return view;
    }

    @OnClick(R.id.btn_donde_ir)//click al boton
    public void Restaurantes() {//metodo del listado de restaurantes
        if (!location.hasLocationEnabled()) {
            showNetworkGPS();
        } else {
            Intent i = new Intent(getActivity(), ContainerActivity.class);
            i.putExtra(Constants.ID_FRAGMENT, Constants.FRAGMENT_LIST_DONDE_IR);
            goToIntent(i);//metodo heredado de la clase abstracta
        }
    }//end

    @OnClick(R.id.btn_eventos)//click al boton
    public void Eventos() {//metodo de los eventos
        Log.i("Eventos", "btn_eventos");
        if (!location.hasLocationEnabled()) {
            showNetworkGPS();
        } else {
            goToIntent(new Intent(getActivity(), ViewPagerActivityEventos.class));
        }

    }//end

    @OnClick(R.id.btn_colaboraciones)//click al boton
    public void Colaboraciones() {//metodo de colaboraciones
        goToIntent(new Intent(getActivity(), ContainerActivity.class).putExtra(Constants.ID_FRAGMENT, Constants.FRAGMENT_LIST_COLABORADORES));
    }//end

    @Override
    public void onPause() {
        // stop location updates (saves battery)
        location.endUpdates();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        location.beginUpdates();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void showNetworkGPS() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.title_conectese)
                .content(R.string.conectese)
                .positiveText(R.string.agree)
                .backgroundColor(Color.WHITE)
                .negativeColor(getResources().getColor(R.color.secondary_text))
                .positiveColor(getResources().getColor(R.color.secondary_text))
                .negativeText(R.string.disagree)
                .iconRes(R.mipmap.ic_launcher)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {

                    }
                })
                .show();
    }
}

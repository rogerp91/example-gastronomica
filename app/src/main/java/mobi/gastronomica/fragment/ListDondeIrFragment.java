package mobi.gastronomica.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nispok.snackbar.listeners.ActionSwipeListener;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mobi.gastronomica.R;
import mobi.gastronomica.UILApplication;
import mobi.gastronomica.activity.ViewPagerActivityRestaurantes;
import mobi.gastronomica.adapte.AdapteRestaurante;
import mobi.gastronomica.components.MethodsInterfaces;
import mobi.gastronomica.components.SimpleLocation;
import mobi.gastronomica.model.Restaurantes;
import mobi.gastronomica.utils.ApiManager;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;
import mobi.gastronomica.utils.JsonUtils;

import static mobi.gastronomica.UILApplication.SQL;
import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class ListDondeIrFragment extends BaseFragment implements MethodsInterfaces {

    private static final String TAG = makeLogTag(ListDondeIrFragment.class);

    public static ListDondeIrFragment newInstance() {
        ListDondeIrFragment fragment = new ListDondeIrFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private SimpleLocation location;
    @InjectView(R.id.progress)
    View mProgressView;
    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.btn_no_conexion)
    Button btnConexion;
    @InjectView(R.id.btn_no_ubicacion)
    Button btnUbicacion;
    @InjectView(R.id.btn_no_restautantes)
    Button btnRestaurantes;
    @InjectView(R.id.list_recycler)
    RecyclerView recyclerCercaDeMi;

    double latitude = 0.0f;
    double longitude = 0.0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_acerca_de_mi, container, false);

        ButterKnife.inject(this, view);

        location = new SimpleLocation(getActivity());
        location.beginUpdates();

        actionBarActivity = (AppCompatActivity) getActivity();// obtener actividad en q esta trabajando el fragment
        actionBarActivity.getSupportActionBar().setTitle(getResources().getString(R.string.cerca_de_mi));// asignar
        actionBarActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerCercaDeMi.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerCercaDeMi.setLayoutManager(manager);

        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(UILApplication.context);
        } else {
            if (isAdded() && getActivity() != null) {
                if (!SQL.checkRestauranteCerca()) {
                    if (Functions.isOnline()) {
                        showView(true, mProgressView);
                        mCaptureaSend();
                    } else {
                        showView(true, btnConexion);//agergar
                    }
                } else {
                    setDataAdapte();
                }
            }
        }

        // SwipeRefresh
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ApiManager.cancelgetAll();
                if (isAdded() && getActivity() != null) {
                    if (Functions.isOnline()) {
                        if (!SQL.checkRestauranteCerca()) {
                            showView(false, btnUbicacion);
                            showView(false, btnConexion);//agergar
                            showView(false, mProgressView);
                            mCaptureaSend();
                        }else{
                            showView(false, btnUbicacion);
                            showView(false, btnConexion);//agergar
                            showView(false, mProgressView);
                            mCaptureaSend();
                        }
                    } else {
                        mSwipeRefreshLayout.setRefreshing(false);
                        showSnackbar(getResources().getString(R.string.no_conexion_2));
                    }
                }

            }
        });// Swipe
        return view;
    }

    @OnClick(R.id.btn_no_restautantes)
    public void mNoRestaurantes() {
        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(UILApplication.context);
        } else {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            showView(false, btnUbicacion);
            showView(false, btnRestaurantes);
            showView(false, btnConexion);//agergar
            showView(true, mProgressView);
            mCaptureaSend();
        }

    }

    @OnClick(R.id.btn_no_ubicacion)
    public void mNoUbicacion() {
        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(UILApplication.context);
        } else {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            showView(false, btnUbicacion);
            showView(false, btnRestaurantes);
            showView(false, btnConexion);//agergar
            showView(true, mProgressView);
            mCaptureaSend();
        }

    }

    @OnClick(R.id.btn_no_conexion)//agrergar
    public void btnReconect() {
        LOGI(TAG, "btnReconect");
        if (!location.hasLocationEnabled()) {
            SimpleLocation.openSettings(UILApplication.context);
        } else {
            if (Functions.isOnline()) {
                showView(false, btnUbicacion);
                showView(false, btnRestaurantes);
                showView(false, btnConexion);//agergar
                showView(true, mProgressView);
                mCaptureaSend();
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ApiManager.cancelgetAll();
    }

    protected void requestData(String url) { //obtener restaurantes
        super.requestData();
        ApiManager.getAcerca(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (isAdded() && getActivity() != null) {
                    showView(false, mProgressView);
                    if (!SQL.checkRestauranteCerca()) {//si n hay ningun dato mostrar el boton
                        showView(true, btnConexion);
                    } else {
                        if(mSwipeRefreshLayout.isRefreshing()){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        Snackbar.with(getActivity()).text(getResources().getString(R.string.no_contenido)).show(getActivity()); // activity where it is displayed
                    }
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LOGI("TAG", response.toString());
                mSwipeRefreshLayout.setRefreshing(false);//para que no aparezca reflesh
                try {
                    if (isAdded() && getActivity() != null) {
                        if (response.getInt("status") == 204) {
                            showView(true, btnRestaurantes);//exconderr el progress
                        } else {
                            JsonUtils.parseJsonAcerca(response);// parsear el json web
                            if(mSwipeRefreshLayout.isRefreshing()){
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                            setDataAdapte();//adaptar
                            showView(false, btnRestaurantes);
                            showView(false, mProgressView);//exconderr el progress
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (isAdded() && getActivity() != null) {
                    if (mSwipeRefreshLayout != null) {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                    showView(false, mProgressView);
                    if (mProgressView != null) {
                        showView(false, mProgressView);
                    } else {
                        mProgressView = null;
                    }
                }
            }

        });
    }

    @Override
    public void showView(final boolean show, final View view) {//animacion en la vista
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            view.setVisibility(show ? View.VISIBLE : View.GONE);
            view.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });
        } else {
            view.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    protected void setDataAdapte() {
        super.setDataAdapte();
        if (isAdded() && getActivity() != null) {
            showView(true, mSwipeRefreshLayout);
            if (SQL.getRestauranteCerca() != null) {
                ArrayList<Restaurantes> array = SQL.getRestauranteCerca();
                AdapteRestaurante adapter = new AdapteRestaurante(array);
                recyclerCercaDeMi.setAdapter(adapter);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = recyclerCercaDeMi.getChildPosition(view);
                        showView(false, mProgressView);
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (Functions.isOnline()) {
                            goToIntent(new Intent(getActivity(), ViewPagerActivityRestaurantes.class).putExtra(Constants.ID, pos));
                        } else {
                            showSnackbar(getResources().getString(R.string.no_conexion_2));
                        }
                    }
                });
            } else {
                showView(true, btnConexion);
            }
        }
    }

    @Override
    public void showView(boolean show) {
    }

    @Override
    public void onResume() {
        super.onResume();
        location.beginUpdates();
        if (!location.hasLocationEnabled()) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    @Override
    public void onPause() {
        // stop location updates (saves battery)
        location.endUpdates();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ApiManager.cancelgetAll();
    }

    public void mCaptureaSend() {

        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        Log.i("LATLOG", "latitude=" + Double.toString(latitude) + "&" + "longitude=" + Double.toString(longitude));
        if (Functions.isOnline()) {
            if (this.latitude == 0.0f && this.longitude == 0.0f) {
                //String url = "restaurante?latitude=" + this.latitude + "&" + "longitude=" + this.longitude;
                String url = "restaurantes?latitude=10.9904304&longitude=-63.830483,15";
                LOGI("URL", url);
                requestData(url);
            } else {
                showView(false, mProgressView);
                showView(true, btnUbicacion);
                location.beginUpdates();
            }
        } else {
            showView(true, btnConexion);//agergar
        }
    }//end

    public void showSnackbar(String texto) {
        super.showSnackbarNoconnection();
        SnackbarManager.show(Snackbar.with(getActivity()).text(texto).swipeListener(new ActionSwipeListener() {
            @Override
            public void onSwipeToDismiss() {
                Toast.makeText(getActivity(), "swipe to dismiss", Toast.LENGTH_SHORT).show();
            }
        }).actionListener(new ActionClickListener() {
            @Override
            public void onActionClicked(Snackbar snackbar) {
                ApiManager.cancelgetAll();
                if (!Functions.isOnline()) {
                    showSnackbarNoconnection();
                }
            }
        }));
    }//end
}
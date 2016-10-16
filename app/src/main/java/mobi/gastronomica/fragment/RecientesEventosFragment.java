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
import mobi.gastronomica.UILApplication;
import mobi.gastronomica.R;
import mobi.gastronomica.activity.ContainerActivity;
import mobi.gastronomica.adapte.AdapteEvetosRecientes;
import mobi.gastronomica.components.MethodsInterfaces;
import mobi.gastronomica.components.SimpleLocation;
import mobi.gastronomica.model.EventosRecientes;
import mobi.gastronomica.utils.ApiManager;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;
import mobi.gastronomica.utils.JsonUtils;

import static mobi.gastronomica.UILApplication.SQL;
import static mobi.gastronomica.utils.Logs.LOGI;

public class RecientesEventosFragment extends BaseFragment implements MethodsInterfaces {

    public static RecientesEventosFragment newInstance() {
        RecientesEventosFragment fragment = new RecientesEventosFragment();
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
    @InjectView(R.id.btn_no_eventos)
    Button btnEventos;
    @InjectView(R.id.btn_no_ubicacion)
    Button btnUbicacion;

    @InjectView(R.id.list_recycler)
    RecyclerView recyclerEventos;
    double latitude = 0.0f;
    double longitude = 0.0f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_eventos, container, false);
        ButterKnife.inject(this, view);
        location = new SimpleLocation(getActivity());
        location.beginUpdates();

        actionBarActivity = (AppCompatActivity) getActivity();// obtener actividad en q esta trabajando el fragment
        actionBarActivity.getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_view_pager_activity_eventos));// asignar

        recyclerEventos.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerEventos.setLayoutManager(manager);


        if (!location.hasLocationEnabled()) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        } else {
            if (Functions.isOnline()) {
                if (!SQL.checkEventos()) {
                    showView(true, mProgressView);
                    mCaptureaSend();
                } else {
                    showView(true, mProgressView);
                    mCaptureaSend();
                }
            } else {
                //showSnackbar("Verifique conexión de internet");
                showView(true, btnConexion);//agergar
            }
        }

        // SwipeRefresh
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ApiManager.cancelgetAll();
                showView(false, btnEventos);
                showView(false, btnConexion);//agergar
                showView(true, mProgressView);
                mCaptureaSend();

            }
        });// Swipe

        return view;

    }

    @OnClick(R.id.btn_no_conexion)//agrergar
    public void btnReconect() {
        if (!location.hasLocationEnabled()) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        } else {
            if (Functions.isOnline()) {
                showView(false, btnConexion);//agergar
                showView(false, btnEventos);
                showView(false, btnConexion);//agergar
                showView(true, mProgressView);
                mCaptureaSend();
            }
        }
    }

    @OnClick(R.id.btn_no_eventos)
    public void mEventos() {
        if (!location.hasLocationEnabled()) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        } else {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            showView(false, btnConexion);//agergar
            showView(false, btnEventos);
            showView(false, btnConexion);//agergar
            showView(true, mProgressView);
            mCaptureaSend();
        }

    }

    @OnClick(R.id.btn_no_ubicacion)
    public void mNoUbicacion() {
        if (!location.hasLocationEnabled()) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        } else {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            showView(false, btnEventos);
            showView(false, btnConexion);//agergar
            showView(true, mProgressView);
            mCaptureaSend();
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

    private void requestData(String url) {//obtener restaurantes
        ApiManager.getEventos(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (isAdded() && getActivity() != null) {
                    mSwipeRefreshLayout.setRefreshing(false);//para que no aparezca reflesh
                    showView(false, mProgressView);//exconderr el progress
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LOGI("TAG", response.toString());
                try {
                    if (isAdded() && getActivity() != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        if (response.getString("responseData").equals("[]")) {
                            showView(false, mProgressView);
                            showView(true, btnEventos);
                        } else {
                            JsonUtils.perseJsonGetEventos(response);
                            showView(false, btnEventos);
                            showView(false, mProgressView);
                            setDataAdapte();
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
                Log.i("TAG", "onRetry");
            }

            @Override
            public void onCancel() {
                super.onCancel();

                Log.i("TAG", "onCancel");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.i("TAG", "onFinish");
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
        if(isAdded() && getActivity() != null){
            showView(true, mSwipeRefreshLayout);
            if(SQL.getEventos() != null){
                final ArrayList<EventosRecientes> array = SQL.getEventos();
                AdapteEvetosRecientes adapter = new AdapteEvetosRecientes(array);
                recyclerEventos.setAdapter(adapter);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = recyclerEventos.getChildPosition(view);
                        showView(false, mProgressView);
                        mSwipeRefreshLayout.setRefreshing(false);
                        EventosRecientes obj = array.get(pos);
                        Intent intent = new Intent(getActivity(), ContainerActivity.class);
                        Bundle b = new Bundle();
                        b.putInt(Constants.ID_FRAGMENT, Constants.FRAGMENT_EVENTOS_DETAIL);//fragment donde voy
                        b.putParcelable(Constants.SEND_DATA, obj);//la informacion que voy a enviar
                        intent.putExtras(b);
                        goToIntent(intent);

                    }
                });
            }else{
                showView(true, btnConexion);//agergar
            }
        }
    }

    @Override
    public void showView(boolean show) {
    }

    public void mCaptureaSend() {

        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        Log.i("LATLOG", "latitude=" + Double.toString(latitude) + "&" + "longitude=" + Double.toString(longitude));
        if (Functions.isOnline()) {
            if (this.latitude != 0.0f && this.longitude != 0.0f) {
                String url = "eventos?latitude=" + this.latitude + "&" + "longitude=" + this.longitude;
                //String url = "eventos?latitude=10.9904304&longitude=-63.830483,15";
                LOGI("URL", url);
                requestData(url);
            } else {
                showView(false, mProgressView);
                showView(true, btnUbicacion);
                location.beginUpdates();
            }
        }else{
            showView(false, btnConexion);//agergar
        }
    }//end


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

}


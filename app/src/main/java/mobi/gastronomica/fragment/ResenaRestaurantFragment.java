package mobi.gastronomica.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import mobi.gastronomica.R;
import mobi.gastronomica.UILApplication;
import mobi.gastronomica.activity.ContainerActivity;
import mobi.gastronomica.activity.ViewPagerActivityRestaurantes;
import mobi.gastronomica.adapte.AdapteResena;
import mobi.gastronomica.components.MethodsInterfaces;
import mobi.gastronomica.model.Resena;
import mobi.gastronomica.utils.ApiManager;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;
import mobi.gastronomica.utils.JsonUtils;

import static mobi.gastronomica.UILApplication.SQL;
import static mobi.gastronomica.utils.Logs.LOGI;

public class ResenaRestaurantFragment extends BaseFragment implements MethodsInterfaces {

    public static ResenaRestaurantFragment newInstance() {
        ResenaRestaurantFragment fragment = new ResenaRestaurantFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ViewPagerActivityRestaurantes vp = null;
    @InjectView(R.id.progress)
    View mProgressView;
    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.btn_no_conexion)
    Button btnConexion;
    @InjectView(R.id.btn_no_contenido)
    Button btnContenido;
    @InjectView(R.id.btn_no_resena)
    Button btnNoResena;
    @InjectView(R.id.list_recycler)
    RecyclerView recyclerCercaDeMi;
    int pos, k;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vp = (ViewPagerActivityRestaurantes) getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_resena, container, false);
        ButterKnife.inject(this, view);

        actionBarActivity = (AppCompatActivity) getActivity();// obtener actividad en q esta trabajando el fragment
        actionBarActivity.getSupportActionBar().setTitle(getResources().getString(R.string.app_name));// asignar
        actionBarActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerCercaDeMi.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerCercaDeMi.setLayoutManager(manager);

        this.pos = vp.dataInformation();
        LOGI("ResenaRestaurantFragment POS", Integer.toString(this.pos));
        LOGI(" ResenaRestaurantFragment POS Array", Integer.toString(UILApplication.ITEMS.get(this.pos).getId()));
        if (this.pos != -1) {
            LOGI("ResenaRestaurantFragment", "==NULL");
            if (isAdded() && getActivity() != null) {
                if (!SQL.checkResena()) {
                    if (Functions.isOnline()) {
                        showView(true, mProgressView);
                        LOGI("POS Array", Integer.toString(UILApplication.ITEMS.get(this.pos).getId()));
                        requestData(UILApplication.ITEMS.get(this.pos).getId());
                    } else {
                        showView(true, btnConexion);//agergar
                    }
                } else {
                    setDataAdapte(this.pos);
                }
            }
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void requestData(final int pos) { //obtener restaurantes
        super.requestData();

        k = pos;

        ApiManager.get("resenas/" + Integer.toString(k), null, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (isAdded() && getActivity() != null) {
                    showView(false, mProgressView);
                    if (!SQL.checkResena()) {//si n hay ningun dato mostrar el boton
                        showView(true, btnConexion);
                    } else {
                        if (mSwipeRefreshLayout.isRefreshing()) {
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
                        if (response.getInt("status") != 200) {
                            showView(false, mProgressView);
                            showView(true, btnContenido);//exconderr el progress
                        } else {
                            if (!response.getString("responseData").equals("[]")) {
                                JsonUtils.perseResena(response, UILApplication.ITEMS.get(k).getId());// parsear el json web
                                if (mSwipeRefreshLayout.isRefreshing()) {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                                showView(true, recyclerCercaDeMi);
                                showView(true, mSwipeRefreshLayout);
                                setDataAdapte(k);//adaptar
                                showView(false, btnNoResena);
                                showView(false, mProgressView);//exconderr el progress
                            } else {
                                showView(true, btnNoResena);//exconderr el progress
                            }
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
    public void showView(boolean show) {

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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        // stop location updates (saves battery)
        super.onPause();
    }

    @Override
    protected void setDataAdapte(int j) {
        super.setDataAdapte();
        if (isAdded() && getActivity() != null) {
            showView(true, mSwipeRefreshLayout);
            if (SQL.getResena(UILApplication.ITEMS.get(j).getId()).size() != 0) {
                showView(true, recyclerCercaDeMi);
                final ArrayList<Resena> array = SQL.getResena(UILApplication.ITEMS.get(j).getId());
                AdapteResena adapter = new AdapteResena(array);
                recyclerCercaDeMi.setAdapter(adapter);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = recyclerCercaDeMi.getChildPosition(view);
                        Resena obj = array.get(pos);
                        showView(false, mProgressView);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Intent intent = new Intent(getActivity(), ContainerActivity.class);
                        Bundle b = new Bundle();
                        b.putParcelable(Constants.PARCELABLE, obj);
                        b.putInt(Constants.ID_FRAGMENT, Constants.FRAGMENT_RESENA_DETAILS);
                        intent.putExtras(b);
                        goToIntent(intent);

                    }
                });
            } else {
                showView(false, recyclerCercaDeMi);
                showView(false, mSwipeRefreshLayout);
                showView(true, btnNoResena);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_resena, menu);
        menu.findItem(R.id.menu_resena).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case R.id.menu_resena:
                Toast.makeText(getActivity(), R.string.app_name, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}//endClass

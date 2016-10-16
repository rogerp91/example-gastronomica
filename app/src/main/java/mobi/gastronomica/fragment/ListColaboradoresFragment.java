package mobi.gastronomica.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
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
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mobi.gastronomica.R;
import mobi.gastronomica.activity.ViewPagerActivityColaboradores;
import mobi.gastronomica.adapte.AdapteColaboradores;
import mobi.gastronomica.components.MethodsInterfaces;
import mobi.gastronomica.model.Colaboradores;
import mobi.gastronomica.utils.ApiManager;
import mobi.gastronomica.utils.Functions;
import mobi.gastronomica.utils.JsonUtils;

import static mobi.gastronomica.UILApplication.SQL;
import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class ListColaboradoresFragment extends BaseFragment implements MethodsInterfaces {

    private static final String TAG = makeLogTag(ListColaboradoresFragment.class);

    public static ListColaboradoresFragment newInstance() {
        ListColaboradoresFragment fragment = new ListColaboradoresFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @InjectView(R.id.progress)
    View mProgressView;
    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.btn_no_conexion)
    Button btnConexion;
    @InjectView(R.id.list_recycler)
    RecyclerView recyclerColaboradores;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_colaboradores, container, false);
        ButterKnife.inject(this, view);// libreria

        actionBarActivity = (AppCompatActivity) getActivity();// obtener actividad en q esta trabajando el fragment
        // titulo
        actionBarActivity.getSupportActionBar().setTitle(getResources().getString(R.string.colaboradores));// asignar
        actionBarActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerColaboradores.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerColaboradores.setLayoutManager(manager);

        if (isAdded() && getActivity() != null) {
            // SQl
            if (!SQL.checkColaboradores()) {// si no hay restaurantes y no hay internet manda mensaje de conexion
                if (!Functions.isOnline()) {
                    showView(true, btnConexion);
                } else {
                    showView(false, btnConexion);//agergar
                    showView(true, mProgressView);
                    requestData();
                }
            } else {
                setDataAdapte();
            }
        }

        // SwipeRefresh
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ApiManager.cancelgetAll();
                if (isAdded() && getActivity() != null) {
                    if (!Functions.isOnline() && SQL.checkColaboradores()) {
                        //mSwipeRefreshLayout.setEnabled(false);
                        //showView(false, btnConexion);
                        mSwipeRefreshLayout.setRefreshing(false);
                        showSnackbarNoconnection();
                    } else {
                        if (!Functions.isOnline() && !SQL.checkColaboradores()) {
                            //mSwipeRefreshLayout.setEnabled(false);
                            //mSwipeRefreshLayout.setRefreshing(false);
                            showView(true, btnConexion);
                        } else {
                            if (Functions.isOnline() && SQL.checkColaboradores()) {
                                requestData();
                            }
                        }
                    }
                }
            }
        });// Swipe
        return view;
    }

    @OnClick(R.id.btn_no_conexion)//agrergar
    public void btnReconect() {
        LOGI(TAG, "btnReconect");
        if (Functions.isOnline()) {
            showView(false, btnConexion);
            showView(true, mProgressView);
            requestData();
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ApiManager.cancelgetAll();
        //Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ApiManager.cancelgetAll();
        view = null;
        mSwipeRefreshLayout.setRefreshing(false);
        if (mProgressView != null) {
            showView(false, mProgressView);
        } else {
            mProgressView = null;
        }
        actionBarActivity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ApiManager.cancelgetAll();
        view = null;
        mSwipeRefreshLayout.setRefreshing(false);
        if (mProgressView != null) {
            showView(false, mProgressView);
        } else {
            mProgressView = null;
        }
        actionBarActivity = null;
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
    protected void showSnackbarNoconnection() {//cuando no hay conexion a internet mostrar mensaje
        super.showSnackbarNoconnection();
        SnackbarManager.show(Snackbar.with(getActivity()).text(getResources().getString(R.string.no_conexion))
                .actionLabel(getResources().getString(R.string.volver_intertarlo))
                .swipeListener(new ActionSwipeListener() {
                    @Override
                    public void onSwipeToDismiss() {
                        Toast.makeText(getActivity(), "swipe to dismiss", Toast.LENGTH_SHORT).show();
                    }
                }).actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        ApiManager.cancelgetAll();
                        if (!Functions.isOnline() && SQL.checkColaboradores()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            showSnackbarNoconnection();
                        } else {
                            if (!Functions.isOnline() && !SQL.checkColaboradores()) {
                                showView(true, btnConexion);
                            } else {
                                if (Functions.isOnline() && SQL.checkColaboradores()) {
                                    requestData();
                                } else {
                                    requestData();
                                }
                            }
                        }
                    }
                }));
    }

    @Override
    public void showView(boolean show) {
    }

    @Override
    protected void requestData() {//obtener restaurantes
        super.requestData();
        ApiManager.get("colaboradores", null, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {//existe un fallo
                super.onFailure(statusCode, headers, throwable, errorResponse);
                LOGI(TAG, "onFailure");
                if (!SQL.checkColaboradores()) {//si n hay ningun dato mostrar el boton
                    showView(false, mProgressView);
                    showView(true, btnConexion);
                } else {
                    if (isAdded() && getActivity() != null) {
                        showView(false, mProgressView);
                        showView(true, mSwipeRefreshLayout);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Snackbar.with(getActivity()).text(getResources().getString(R.string.no_contenido)).show(getActivity()); // activity where it is displayed
                    }
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) { // 200, ok
                super.onSuccess(statusCode, headers, response);
                LOGI(TAG, "onSuccess");
                LOGI(TAG, response.toString());
                if (isAdded() && getActivity() != null) {
                    mSwipeRefreshLayout.setRefreshing(false);//para que no aparezca reflesh
                    JsonUtils.parseJsonColaboradores(response);// parsear el json web
                    setDataAdapte();//adaptar
                    showView(false, mProgressView);//exconderr el progress
                }
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }

            @Override
            public void onCancel() {
                super.onCancel();
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

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    @Override
    protected void setDataAdapte() {//adaptar la informacion al recycler
        super.setDataAdapte();
        LOGI(TAG, "Setapdate");
        if (isAdded() && getActivity() != null) {
            showView(true, mSwipeRefreshLayout);
            if(SQL.getColaboradores() != null){
                final ArrayList<Colaboradores> array = SQL.getColaboradores();
                AdapteColaboradores adapter = new AdapteColaboradores(array);
                recyclerColaboradores.setAdapter(adapter);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = recyclerColaboradores.getChildPosition(view);
                        showView(false, mProgressView);
                        mSwipeRefreshLayout.setRefreshing(false);
                        goToIntent(new Intent(getActivity(), ViewPagerActivityColaboradores.class).putExtra("PARCEL", array.get(pos)));

                    }
                });
            }else{
                showView(true, btnConexion);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_colaboradores, menu);
        menu.findItem(R.id.menu_colaboradores).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case R.id.menu_colaboradores:
                Toast.makeText(getActivity(), R.string.app_name, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}

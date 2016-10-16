package mobi.gastronomica.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mobi.gastronomica.UILApplication;
import mobi.gastronomica.R;
import mobi.gastronomica.activity.ContainerActivity;
import mobi.gastronomica.activity.ViewPagerActivityColaboradores;
import mobi.gastronomica.adapte.AdaptePublicaciones;
import mobi.gastronomica.model.Colaboradores;
import mobi.gastronomica.model.Publicaciones;
import mobi.gastronomica.utils.Constants;

public class PublicacionesColaboradoresFragment extends BaseFragment {

    public static PublicacionesColaboradoresFragment newInstance() {
        PublicacionesColaboradoresFragment fragment = new PublicacionesColaboradoresFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ViewPagerActivityColaboradores vp = null;

    @InjectView(R.id.list_recycler_publicacion)
    RecyclerView recyclerPublicacion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vp = (ViewPagerActivityColaboradores) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_publicaciones_colaboradores, container, false);

        ButterKnife.inject(this, view);
        Colaboradores obj = null;
        //actionBarActivity = (AppCompatActivity) getActivity();// obtener actividad en q esta trabajando el fragment
        //actionBarActivity.getSupportActionBar().setTitle(getResources().getString(R.string.publicacion));// asignar

        obj = vp.dataInformation();

        recyclerPublicacion.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerPublicacion.setLayoutManager(manager);

        final ArrayList<Publicaciones> array = UILApplication.SQL.getPublicaciones(obj.getId());
        AdaptePublicaciones adapter = new AdaptePublicaciones(array);
        recyclerPublicacion.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = recyclerPublicacion.getChildPosition(view);
                Intent intent = new Intent(getActivity(), ContainerActivity.class);
                Bundle b = new Bundle();
                b.putInt(Constants.ID_FRAGMENT, Constants.FRAGMENT_PUBLICACION_DETAILS);//fragment donde voy
                b.putParcelable(Constants.SEND_DATA, array.get(pos));//la informacion que voy a enviar
                intent.putExtras(b);
                goToIntent(intent);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

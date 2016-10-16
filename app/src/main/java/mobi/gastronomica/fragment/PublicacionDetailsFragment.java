package mobi.gastronomica.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mobi.gastronomica.R;
import mobi.gastronomica.UILApplication;
import mobi.gastronomica.activity.ContainerActivity;
import mobi.gastronomica.model.DetailsImagen;
import mobi.gastronomica.model.Publicaciones;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;

import static mobi.gastronomica.utils.Logs.LOGI;

public class PublicacionDetailsFragment extends BaseFragment {

    public static PublicacionDetailsFragment newInstance(Parcelable parcelable) {
        PublicacionDetailsFragment fragment = new PublicacionDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.PARCELABLE, parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    Publicaciones obj = null;
    @InjectView(R.id.img_header)
    ImageView img_header;
    @InjectView(R.id.text_heder)
    TextView text_heder;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.type)
    TextView type;
    @InjectView(R.id.description)
    TextView description;
    @InjectView(R.id.date_publicated)
    TextView date_publicated;
    String mUrl = "";
    String mTitle = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.obj = getArguments().getParcelable(Constants.PARCELABLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_publicacion_details, container, false);
        ButterKnife.inject(this, view);

        actionBarActivity = (AppCompatActivity) getActivity();// obtener actividad en q esta trabajando el fragment
        actionBarActivity.getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_view_pager_activity_colaboradores));// asignar
        actionBarActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0)).build();

        mTitle = obj.getTitle();
        title.setText(mTitle);
        type.setText(obj.getType());
        description.setText(Html.fromHtml(obj.getContent()));
        date_publicated.setText(obj.getDatePublicated());

        title.setTypeface(Functions.getTypeface("Roboto-LightItalic"));
        type.setTypeface(Functions.getTypeface("Roboto-Regular"));

        mUrl = Constants.API_BASE_URL_2 + obj.getImagen();//iimagen
        ImageLoader.getInstance().displayImage(mUrl, img_header, options);

        return view;
    }

    @OnClick(R.id.img_header)
    public void goToImg() {
        if (img_header != null) {
            Intent intent = new Intent(getActivity(), ContainerActivity.class);
            Bundle b = new Bundle();
            b.putInt(Constants.ID_FRAGMENT, Constants.FRAGMENT_DETAIL_IMG);
            b.putParcelable(Constants.SEND_DATA, new DetailsImagen(this.mTitle, this.mUrl));
            intent.putExtras(b);
            goToIntent(intent);
        }
    }
}

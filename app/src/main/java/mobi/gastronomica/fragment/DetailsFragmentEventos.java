package mobi.gastronomica.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mobi.gastronomica.R;
import mobi.gastronomica.activity.ContainerActivity;
import mobi.gastronomica.model.DetailsImagen;
import mobi.gastronomica.model.EventosRecientes;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;

public class DetailsFragmentEventos extends BaseFragment {

    public static DetailsFragmentEventos newInstance(Parcelable parcelable) {
        DetailsFragmentEventos fragment = new DetailsFragmentEventos();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EVENTOS_PARCELABLE, parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    EventosRecientes obj = null;

    @InjectView(R.id.img_header)
    ImageView img_header;

    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.org_name)
    TextView org_name;
    @InjectView(R.id.place)
    TextView place;
    @InjectView(R.id.price)
    TextView price;
    @InjectView(R.id.fecha)
    TextView fecha;
    @InjectView(R.id.confimation)
    TextView confimation;
    @InjectView(R.id.description)
    HtmlTextView description;

    String mUrl = "";
    String mTitle = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.obj = getArguments().getParcelable(Constants.EVENTOS_PARCELABLE);
            Log.i("FRAGMENT", obj.getTitle());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_events, container, false);
        ButterKnife.inject(this, view);

        actionBarActivity = (AppCompatActivity) getActivity();// obtener actividad en q esta trabajando el fragment
        actionBarActivity.getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_view_pager_activity_eventos));// asignar
        actionBarActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0)).build();

        if (this.obj != null) {
            mUrl = Constants.API_BASE_URL_NOT_VERSION + this.obj.getImagen();
            Log.i("onCreateView", mUrl);
            if (!mUrl.isEmpty()) {
                ImageLoader.getInstance().displayImage(mUrl, img_header, options);
            }

            title.setText(obj.getTitle());
            this.mTitle = obj.getTitle().trim();
            if (title.length() > 0) {
                char primero = this.mTitle.charAt(0);
                this.mTitle = Character.toUpperCase(primero) + this.mTitle.substring(1, this.mTitle.length());
                title.setText(mTitle);
                title.setTypeface(Functions.getTypeface("Roboto-Regular"));
            }

            org_name.setText(obj.getOrgName());
            place.setText(obj.getPlace());
            price.setText("Precio Bs." + Integer.toString(obj.getPrice()) + "(pp)");
            fecha.setText(obj.getFecha());
            if (obj.getConfirmation() == 1) {
                confimation.setText("Necesario confirmar");
            } else {
                confimation.setText("No necesario confirmar");
            }

            //description.setText(Html.fromHtml(obj.getDescription()));
            description.setHtmlFromString(obj.getDescription(), true);
            //description.setTypeface(Functions.getTypeface("Roboto-Light"));

            if (mUrl.length() > 0) {
                img_header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), ContainerActivity.class);
                        Bundle b = new Bundle();
                        b.putInt(Constants.ID_FRAGMENT, Constants.FRAGMENT_DETAIL_IMG);
                        b.putParcelable(Constants.SEND_DATA, new DetailsImagen(mTitle, mUrl));
                        intent.putExtras(b);
                        goToIntent(intent);
                    }
                });
            }

        }
        return view;
    }
}

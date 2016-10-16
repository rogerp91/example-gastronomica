package mobi.gastronomica.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import mobi.gastronomica.R;
import mobi.gastronomica.activity.ContainerActivity;
import mobi.gastronomica.activity.ViewPagerActivityColaboradores;
import mobi.gastronomica.model.Colaboradores;
import mobi.gastronomica.model.DetailsImagen;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;

public class InfoColaboradoresFragment extends BaseFragment {

    public static InfoColaboradoresFragment newInstance() {
        InfoColaboradoresFragment fragment = new InfoColaboradoresFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ViewPagerActivityColaboradores vp = null;
    private String mUrl;
    String mname = "";

    @InjectView(R.id.img_header)
    ImageView img_header;
    @InjectView(R.id.text_heder)
    TextView text_heder;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.description)
    TextView description;

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
        view = inflater.inflate(R.layout.fragment_info_colaboradores, container, false);

        ButterKnife.inject(this, view);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0)).build();

        Colaboradores obj = null;
        obj = vp.dataInformation();

        String url = Constants.API_BASE_URL_2 + obj.getAvatar();//iimagen
        this.mUrl = url;//bugs

        if (Functions.validateField(obj.getAvatar())) {
            img_header.setVisibility(View.GONE);
            text_heder.setVisibility(View.VISIBLE);
        } else {
            ImageLoader.getInstance().displayImage(url, img_header, options);
        }

        mname = obj.getFirstName() + " " + obj.getLastName();
        name.setText(mname);
        title.setText(obj.getTitles());
        description.setText(Functions.upperCaseFirst(obj.getDescription() + "."));

        name.setTypeface(Functions.getTypeface("Roboto-Regular"));
        title.setTypeface(Functions.getTypeface("Roboto-LightItalic"));

        if (mUrl.length() > 0) {
            img_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ContainerActivity.class);
                    Bundle b = new Bundle();
                    b.putInt(Constants.ID_FRAGMENT, Constants.FRAGMENT_DETAIL_IMG);
                    b.putParcelable(Constants.SEND_DATA, new DetailsImagen(mname, mUrl));
                    intent.putExtras(b);
                    goToIntent(intent);
                }
            });
        }

        return view;
    }

}
package mobi.gastronomica.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mobi.gastronomica.R;
import mobi.gastronomica.activity.ContainerActivity;
import mobi.gastronomica.model.DetailsImagen;
import mobi.gastronomica.model.Resena;
import mobi.gastronomica.utils.Constants;

public class DetailResenaFragment extends BaseFragment {

    public static DetailResenaFragment newInstance(Parcelable parcelable) {
        DetailResenaFragment fragment = new DetailResenaFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.PARCELABLE, parcelable);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectView(R.id.re_title)
    TextView re_title;
    @InjectView(R.id.re_content)
    TextView re_content;
    @InjectView(R.id.user_name)
    TextView user_name;
    @InjectView(R.id.created_at)
    TextView created_at;
    @InjectView(R.id.img_header)
    ImageView img_header;
    Resena obj = null;
    String mUrl = null;
    String mTitle = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments() != null) {
                this.obj = getArguments().getParcelable(Constants.PARCELABLE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_resena, container, false);
        ButterKnife.inject(this, view);
        actionBarActivity = (AppCompatActivity) getActivity();// obtener actividad en q esta trabajando el fragment
        actionBarActivity.getSupportActionBar().setTitle(getResources().getString(R.string.resena));// asignar
        actionBarActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitle = this.obj.getUserName() +" "+ this.obj.getUserLastName();
        re_title.setText(this.obj.getReIitle());
        re_content.setText(this.obj.getReContent());
        user_name.setText(this.obj.getUserName() +" "+ this.obj.getUserLastName());
        created_at.setText(this.obj.getCreatedAt().substring(0, 10));


        DisplayImageOptions options = new DisplayImageOptions.Builder().
                showImageOnFail(R.mipmap.ic_launcher).
                cacheInMemory(true).
                cacheOnDisk(true).
                considerExifParams(true).
                bitmapConfig(Bitmap.Config.RGB_565).
                build();

        this.mUrl = Constants.API_BASE_URL_NOT_VERSION + obj.getProfileUser();
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
package mobi.gastronomica.fragment;

import android.app.Activity;
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
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import mobi.gastronomica.R;
import mobi.gastronomica.model.DetailsImagen;
import mobi.gastronomica.utils.Constants;


public class DetailsImagenFragment extends BaseFragment {

    public static DetailsImagenFragment newInstance(Parcelable parcel) {
        DetailsImagenFragment fragment = new DetailsImagenFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.SEND_DATA, parcel);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectView(R.id.image_detail)
    ImageView imagen;
    @InjectView(R.id.title_detail)
    TextView title;

    private DetailsImagen mDetls;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mDetls = getArguments().getParcelable(Constants.SEND_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details_imagen, container, false);
        ButterKnife.inject(this, view);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DisplayImageOptions options = new DisplayImageOptions.Builder().
                showImageOnFail(R.mipmap.ic_launcher).
                cacheInMemory(true).
                cacheOnDisk(true).
                considerExifParams(true).
                bitmapConfig(Bitmap.Config.RGB_565).
                build();

        title.setText(this.mDetls.getTitle());
        if (!this.mDetls.getUrl().isEmpty()) {
            ImageLoader.getInstance().displayImage(mDetls.getUrl(), imagen, options);
        }

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

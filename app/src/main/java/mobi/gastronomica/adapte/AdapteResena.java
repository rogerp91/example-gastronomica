package mobi.gastronomica.adapte;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import mobi.gastronomica.R;
import mobi.gastronomica.model.Resena;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;

import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class AdapteResena extends RecyclerView.Adapter<AdapteResena.ItemViewHolder> implements View.OnClickListener {

    private static final String TAG = makeLogTag(AdapteResena.class);

    //var
    private ArrayList<Resena> arrayList = null;
    private View.OnClickListener listener = null;
    Resena obj = null;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;

    public AdapteResena(ArrayList<Resena> arrayList) {
        this.arrayList = arrayList;
        LOGI(TAG, "AdapteResena");
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(0)).build();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_resena, parent, false);
        view.setOnClickListener(this);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        obj = this.arrayList.get(position);//obtengo los objetos
        Date date = null;
        holder.re_title.setText(obj.getReIitle());
        holder.re_title.setTypeface(Functions.getTypeface("Roboto-Regular"));
        holder.re_content.setText(obj.getReContent());
        holder.re_content.setTypeface(Functions.getTypeface("Roboto-Light"));

        try {
            holder.created_at.setText(obj.getCreatedAt().substring(0,10));
        } catch (Exception e) {

        }
        String url = Constants.API_BASE_URL_NOT_VERSION + obj.getProfileUser();
        ImageLoader.getInstance().displayImage(url, holder.img, options, animateFirstListener);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        // var
        public ImageView img;
        public TextView re_content;
        public TextView re_title;
        public TextView created_at;

        public ItemViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.image);
            re_content = (TextView) view.findViewById(R.id.re_content);
            re_title = (TextView) view.findViewById(R.id.re_title);
            created_at = (TextView) view.findViewById(R.id.created_at);
        }

    }//endHolder

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}//end

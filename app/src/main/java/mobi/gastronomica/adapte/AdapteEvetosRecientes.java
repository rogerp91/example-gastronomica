package mobi.gastronomica.adapte;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mobi.gastronomica.R;
import mobi.gastronomica.model.EventosRecientes;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;

import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class AdapteEvetosRecientes extends RecyclerView.Adapter<AdapteEvetosRecientes.ItemViewHolder> implements View.OnClickListener {

    private static final String TAG = makeLogTag(AdapteEvetosRecientes.class);

    //var
    private ArrayList<EventosRecientes> arrayList = null;
    private View.OnClickListener listener = null;
    EventosRecientes obj = null;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;

    public AdapteEvetosRecientes(ArrayList<EventosRecientes> arrayList) {
        this.arrayList = arrayList;
        LOGI(TAG, "AdapteEvetosRecientes");
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())
                .build();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_eventos, parent, false);
        view.setOnClickListener(this);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        obj = this.arrayList.get(position);//obtengo los objetos
        String url = Constants.API_BASE_URL_NOT_VERSION + obj.getImagen();
        if (!url.isEmpty()) {
            ImageLoader.getInstance().displayImage(url, holder.img, options, animateFirstListener);
        }

        String title = obj.getTitle().trim();
        if (title.length() > 0) {
            char primero = title.charAt(0);
            title = Character.toUpperCase(primero) + title.substring(1, title.length());
            holder.title.setText(title);
            holder.title.setTypeface(Functions.getTypeface("Roboto-Regular"));
        }
        holder.place.setText(obj.getPlace());
        holder.place.setTypeface(Functions.getTypeface("Roboto-Light"));
        holder.fecha.setText(obj.getFecha());
        holder.header.setBackgroundColor(Color.parseColor(obj.getColor()));
        holder.category.setText(obj.getCategory());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        // var
        public TextView title;
        public TextView place;
        public TextView fecha;
        public TextView category;
        public ImageView img;
        public LinearLayout header;

        public ItemViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.image);
            title = (TextView) view.findViewById(R.id.title);
            place = (TextView) view.findViewById(R.id.place);
            fecha = (TextView) view.findViewById(R.id.fecha);
            category = (TextView) view.findViewById(R.id.category);
            header = (LinearLayout) view.findViewById(R.id.header_eventos);
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

package mobi.gastronomica.adapte;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mobi.gastronomica.R;
import mobi.gastronomica.UILApplication;
import mobi.gastronomica.model.Restaurantes;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;

import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class AdapteRestaurante extends RecyclerView.Adapter<AdapteRestaurante.ItemViewHolder> implements View.OnClickListener {

    private static final String TAG = makeLogTag(AdapteRestaurante.class);

    //var
    private ArrayList<Restaurantes> arrayList = null;
    private View.OnClickListener listener = null;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private DisplayImageOptions options;

    public AdapteRestaurante(ArrayList<Restaurantes> arrayList) {
        this.arrayList = arrayList;
        LOGI(TAG, "AdapteRestaurante");

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
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(AdapteRestaurante.ItemViewHolder holder, int position) {
        Restaurantes obj = this.arrayList.get(position);//obtengo los objetos
        holder.title.setText(obj.getTitle());
        holder.menu_region.setText(obj.getMenuRegion());

        holder.title.setTypeface(Functions.getTypeface("Roboto-Regular"));
        holder.menu_region.setTypeface(Functions.getTypeface("Roboto-LightItalic"));
        String url = Constants.API_BASE_URL_2 + obj.getImage();

        //pricae
        int price = obj.getPrice();
        switch (price) {
            case 1:
                holder.price.setImageDrawable(getDrawableResource(R.drawable.precio_1));
                break;
            case 2:
                holder.price.setImageDrawable(getDrawableResource(R.drawable.precio_2));
                break;
            case 3:
                holder.price.setImageDrawable(getDrawableResource(R.drawable.precio_3));
                break;
            case 4:
                holder.price.setImageDrawable(getDrawableResource(R.drawable.precio_4));
                break;
            case 5:
                holder.price.setImageDrawable(getDrawableResource(R.drawable.precio_5));
                break;
            default:
                break;
        }

        holder.distance.setText(obj.getDistance().substring(0,3).toString() + " Km");

        //img
        if (!url.isEmpty()) {
            //Uri uri = Uri.parse(url);
            //Obtenet imagen de la web con lib
            ImageLoader.getInstance().displayImage(url, holder.img, options, animateFirstListener);
            //Glide.with(UILApplication.context).load(uri).error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher).into(holder.img);
        }
    }

    @Override
    public AdapteRestaurante.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_restaurante, parent, false);
        view.setOnClickListener(this);
        return new ItemViewHolder(view);
    }

    /**
     * Extras
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        // var
        public ImageView img;
        public TextView title;
        public ImageView price;
        public TextView menu_region;
        public TextView distance;

        public ItemViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            title = (TextView) view.findViewById(R.id.title);
            price = (ImageView) view.findViewById(R.id.price);
            menu_region = (TextView) view.findViewById(R.id.menu_region);
            distance = (TextView) view.findViewById(R.id.distance);
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

    @SuppressWarnings("deprecation")
    public Drawable getDrawableResource(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return UILApplication.context.getDrawable(id);
        } else {
            return UILApplication.context.getResources().getDrawable(id);
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
}//endClass
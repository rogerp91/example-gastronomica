package mobi.gastronomica.adapte;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mobi.gastronomica.R;
import mobi.gastronomica.model.Publicaciones;
import mobi.gastronomica.utils.Functions;

import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class AdaptePublicaciones extends RecyclerView.Adapter<AdaptePublicaciones.ItemViewHolder> implements View.OnClickListener {

    private static final String TAG = makeLogTag(AdaptePublicaciones.class);

    //var
    private ArrayList<Publicaciones> arrayList = null;
    private View.OnClickListener listener = null;
    Publicaciones obj = null;

    public AdaptePublicaciones(ArrayList<Publicaciones> arrayList) {
        this.arrayList = arrayList;
        LOGI(TAG, "AdaptePublicacion");

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_publicaciones, parent, false);
        view.setOnClickListener(this);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        obj = this.arrayList.get(position);//obtengo los objetos

        holder.title.setText(obj.getTitle());
        holder.content.setText(Html.fromHtml(obj.getContent()));
        holder.date_publicated.setText(obj.getDatePublicated());

        holder.content.setTypeface(Functions.getTypeface("Roboto-Regular"));
        holder.date_publicated.setTypeface(Functions.getTypeface("Roboto-Regular"));
        holder.title.setTypeface(Functions.getTypeface("Roboto-Light"));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        // var
        public TextView content;
        public TextView title;
        public TextView date_publicated;                        ;

        public ItemViewHolder(View view) {
            super(view);
            content = (TextView) view.findViewById(R.id.content);
            title = (TextView) view.findViewById(R.id.title);
            date_publicated = (TextView) view.findViewById(R.id.date_publicated);
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
}//end

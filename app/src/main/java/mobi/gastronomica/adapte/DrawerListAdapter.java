package mobi.gastronomica.adapte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mobi.gastronomica.R;
import mobi.gastronomica.model.ItemNavigation;

public class DrawerListAdapter extends BaseAdapter{

    Context mContext;
    ArrayList<ItemNavigation> mNavItems;

    public DrawerListAdapter(Context context, ArrayList<ItemNavigation> navItems) {
        mContext = context;
        mNavItems = navItems;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            int layout = R.layout.drawer_item;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);
        } else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText(mNavItems.get(position).getTitle());
        iconView.setImageResource(mNavItems.get(position).getIcon());

        return view;
    }
}

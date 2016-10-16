package mobi.gastronomica.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mobi.gastronomica.R;

public class BaseFragment extends Fragment {

    //var
    public View view = null;
    AppCompatActivity actionBarActivity = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    protected void goToFragment(android.support.v4.app.Fragment fragment) {//ir al fragment
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();
    }

    protected void goToIntent(Intent intent) {
        startActivity(intent);
    }

    protected void requestData() {
    }

    protected void setDataAdapte() {
    }

    protected void setDataAdapte(int id) {
    }
    protected void showSnackbarNoconnection() {
    }

    protected void showInformation(int i) {
    }

    @SuppressWarnings("deprecation")
    public Drawable getDrawableResource(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getActivity().getDrawable(id);
        } else {
            return getActivity().getResources().getDrawable(id);
        }
    }
}

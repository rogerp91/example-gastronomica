package mobi.gastronomica.components;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

public interface MethodsInterfaces {//metodos que se comparten los fragmennt y activity

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showView(final boolean show, final View view);

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showView(final boolean show);
}

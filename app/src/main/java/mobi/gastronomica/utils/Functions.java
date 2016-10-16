package mobi.gastronomica.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.Hashtable;

import mobi.gastronomica.UILApplication;

import static mobi.gastronomica.UILApplication.context;
import static mobi.gastronomica.utils.Logs.LOGE;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class Functions {//funciones de ayuda

    //var
    private static final Hashtable<String, Typeface> typefaceCache = new Hashtable<>();
    private static final String TAG = makeLogTag(Functions.class);

    // internet
    public static boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }

    public static Typeface getTypeface(String assetPath) { // Tipo de Letra
        synchronized (typefaceCache) {
            if (!typefaceCache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(context.getAssets(), "fonts/" + assetPath + ".ttf");
                    typefaceCache.put(assetPath, t);
                } catch (Exception e) {
                    LOGE("Typefaces", "Error en typeface '" + assetPath
                            + "' Por " + e.getMessage());
                    return null;
                }
            }
            return typefaceCache.get(assetPath);
        }
    }// end

    public static String upperCaseFirst(String cadena) {//colocar el primer elemento en mayuscula
        char[] caracteres = cadena.toCharArray();
        caracteres[0] = Character.toUpperCase(caracteres[0]);
        for (int i = 0; i < cadena.length(); i++) {
            if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') {
                caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
                return new String(caracteres);
            }
        }
        return cadena;
    }

    public static String removeComa(String cadena) {//remover coma
        char[] caracteres = cadena.toCharArray();
        caracteres[0] = ' ';
        return new String(caracteres);
    }


    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    public static boolean isNameValid(String lastName) {
        return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    }

    public static boolean isNameValidLenght(String password) {
        return password.length() > 3;
    }

    public static boolean isConnectingNetwork() {
        ConnectivityManager connectivity = (ConnectivityManager) UILApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static boolean getConnectivityStatus() {//verificar eñ estadop de conectividad
        ConnectivityManager cm = (ConnectivityManager) UILApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    public static void SendEmail(Activity activity) {//enviar email

        String[] to = {"moviltuts@gmail.com", "otroEmail@ejemplo.com"};
        String[] cc = {"otroEmail@ejemplo.com"};

        //String[] to = direccionesEmail;
        //String[] cc = copias;

        String asunto = "Gastronomica: ¿Dudas?, ¿Comentarios?, ¿Necesitas Ayuda?";
        String mensaje = "";
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");
        activity.startActivity(Intent.createChooser(emailIntent, "Email"));
    }

    public static boolean validateField(String texto) {//verificar campos
        return texto.equals("") || texto.equals("null") || texto.equals(" ") || texto.equals("[]") || TextUtils.isEmpty(texto);
    }


    public static boolean isGoogleMapsInstalled() {
        try {
            ApplicationInfo info = UILApplication.context.getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0 );
            return true;
        }
        catch(PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean checkPlayServices(Activity activity) {//verificar si existe google play services
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                Log.i("checkPlayServices", "Dispositivo no soportado.");
            } else {
                Log.i("checkPlayServices", "Dispositivo no soportado.");
            }
            return false;
        }
        return true;
    }


}//endClass
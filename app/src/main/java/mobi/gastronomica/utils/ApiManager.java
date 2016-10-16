package mobi.gastronomica.utils;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import mobi.gastronomica.UILApplication;

public class ApiManager {//administrador de apis //acomodar api

    private static final String BASE_URL = Constants.API_BASE_URL_RESTAURANTE + Constants.API_ACCESS_TOKEN;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getAllRestaurantes(RequestParams params, AsyncHttpResponseHandler responseHandler) {//obtener todos los restaurantes
        client.get(BASE_URL, params, responseHandler);
    }

    public static void cancelgetAll() {//cancelar todo
        client.cancelAllRequests(true);
    }

    public static void post(String url, JSONObject registro, AsyncHttpResponseHandler responseHandler) {//obtener todos los restaurantes
        try {
            client.addHeader("Accept", "application/json");
            client.addHeader("Content-Type", "application/json");
            StringEntity entity = new StringEntity(registro.toString());
            client.setTimeout(30000);
            client.post(UILApplication.context, getAbsoluteUrl(url), entity, "application/json", responseHandler);
        } catch (Exception e) {
            Log.i("", e.toString());
        }
    }

    public static void postLogin(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {//obtener todos los restaurantes
        try {
            client.post(UILApplication.context, getAbsoluteUrl2(url), params, responseHandler);
        } catch (Exception e) {
            Log.i("", e.toString());
        }
    }

    public static void getEventos(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        try {
            client.get(getAbsoluteUrl2(url), params, responseHandler);
        } catch (Exception e) {
            Log.i("", e.toString());
        }
    }

    public static void getAcerca(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        try {
            client.get(getAbsoluteUrl2(url), params, responseHandler);
        } catch (Exception e) {
            Log.i("", e.toString());
        }
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {//obtener todos los restaurantes
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.i("URL",Constants.API_BASE_URL + relativeUrl + Constants.ACCESS_TOKEN);
        return Constants.API_BASE_URL + relativeUrl + Constants.ACCESS_TOKEN;
    }

    private static String getAbsoluteUrl2(String relativeUrl) {
        Log.i("URL",Constants.API_BASE_URL + relativeUrl + "&" + Constants.API_ACCESS_TOKEN);
        return Constants.API_BASE_URL + relativeUrl + "&" + Constants.API_ACCESS_TOKEN;
    }

}

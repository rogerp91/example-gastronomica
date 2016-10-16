package mobi.gastronomica.utils;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mobi.gastronomica.model.Colaboradores;
import mobi.gastronomica.model.EventosRecientes;
import mobi.gastronomica.model.Publicaciones;
import mobi.gastronomica.model.Resena;
import mobi.gastronomica.model.Restaurantes;

import static mobi.gastronomica.UILApplication.SQL;
import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class JsonUtils {

    public static Handler handler = new Handler();
    private static final String TAG = makeLogTag(JsonUtils.class);

    public static JSONObject JsonClient(String first_name, String last_name, String email, String password) {//Enviar PostResgistrar
        JSONObject objRegister = new JSONObject();
        JSONObject obj = new JSONObject();
        try {

            objRegister.put(Constants.FIRST_NAME, first_name);
            objRegister.put(Constants.LAST_NAME, last_name);
            objRegister.put(Constants.EMAIL, email);
            objRegister.put(Constants.ROLL_DEFAULT, Constants.ROLL);
            objRegister.put(Constants.LEVEL_DEFAULT, Constants.LEVEL);
            objRegister.put(Constants.PASSWORD, password);
            obj.put(Constants.CLIENT, objRegister);

            Log.w("JSON", obj.toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return obj;

    }//end


    public static void parseJsonPostClient(JSONObject json, Activity activity) {//Login
        try {
            PreferenceManager.putString(Constants.CLIENT_ID, json.getString(Constants.CLIENT_ID), activity);
            PreferenceManager.putString(Constants.CLIENT_EMAIL, json.getString(Constants.CLIENT_EMAIL), activity);
            PreferenceManager.putString(Constants.CLIENT_FIRST_NAME, json.getString(Constants.CLIENT_FIRST_NAME), activity);
            PreferenceManager.putString(Constants.CLIENT_LAST_NAME, json.getString(Constants.CLIENT_LAST_NAME), activity);

            if (!json.isNull(Constants.CLIENT_AVATAR)) {//Registra se trae el avatar, login no
                PreferenceManager.putString(Constants.CLIENT_AVATAR, json.getString(Constants.CLIENT_AVATAR), activity);
            } else {
                PreferenceManager.putString(Constants.CLIENT_AVATAR, "null", activity);
            }

            PreferenceManager.putString(Constants.CLIENT_ROLL, json.getString(Constants.CLIENT_ROLL), activity);
            PreferenceManager.putString(Constants.CLIENT_LEVEL, json.getString(Constants.CLIENT_LEVEL), activity);
            PreferenceManager.putBoolean(Constants.LOGIN_KEY, true, activity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void perseJsonGetEventos(JSONObject response) {
        int i;
        JSONArray responseData = null;
        JSONObject data = null;
        JSONObject result = null;

        try {

            responseData = response.getJSONArray("responseData");// convertir
            // array
            for (i = 0; i < responseData.length(); i++) {
                data = responseData.getJSONObject(i);
                result = data.getJSONObject("data");
                Log.i("JSONPARSE", "id");
                if (!SQL.checkIdEventos(result.getInt("id"))) {
                    EventosRecientes obj = new EventosRecientes(
                            result.getInt("id"),
                            result.getString("title"),
                            result.getString("color"),
                            result.getString("category"),
                            result.getString("date"),
                            result.getString("place"),
                            result.getString("image"),
                            result.getInt("price"),
                            result.getString("description"),
                            conterBooleanInt(result.getBoolean("confirmation")),
                            result.getString("org_name"), result
                            .getString("org_descrip"));
                    if (SQL.addEventos(obj)) {
                        LOGI(TAG, "Add Eventos");
                    }
                    ;

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static int conterBooleanInt(boolean var) {
        return var ? 1 : 0;
    }

    public static void parseJsonResetPassword(JSONObject json, Activity activity) {
        try {
            PreferenceManager.putString(Constants.RESET_MESSAGE, json.getString(Constants.RESET_MESSAGE), activity);
            PreferenceManager.putString(Constants.RESET_CORREO_DESTINO, json.getString(Constants.RESET_CORREO_DESTINO), activity);
            PreferenceManager.putString(Constants.RESET_RESET_PASSWORD_TOKEN, json.getString(Constants.RESET_RESET_PASSWORD_TOKEN), activity);
            PreferenceManager.putString(Constants.RESET_PASSWORD_RESET_SENT_AT, json.getString(Constants.RESET_PASSWORD_RESET_SENT_AT), activity);
            PreferenceManager.putBoolean(Constants.RESET_FLAT, true, activity);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void parseJsonAcerca(JSONObject response) {
        int i;
        JSONArray responseData = null;
        JSONObject data = null;
        JSONObject result = null;

        try {
            responseData = response.getJSONArray("responseData");//convertir array
            for (i = 0; i < responseData.length(); i++) {
                data = responseData.getJSONObject(i);
                result = data.getJSONObject("data");
                if (!SQL.checkIdCerca(result.getInt("id"))) {
                    if (SQL.addRestauranteCerca(new Restaurantes(result.getInt("id"), result.getString("title"), result.getString("menu_region"), result.getInt("price"), result.getString("image_thumb"), result.toString(), result.getString("distance")))) {
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }// endMethod

    public static void parseJsonColaboradores(JSONObject response) {
        int i;
        JSONArray responseData = null;
        JSONObject data;
        JSONObject result;
        JSONArray pub;
        JSONObject resultpub;
        try {
            responseData = response.getJSONArray("responseData");//convertir array
            for (i = 0; i < responseData.length(); i++) {
                data = responseData.getJSONObject(i);
                result = data.getJSONObject("data");
                if (!SQL.checkIdColaboradores(result.getInt("id"))) {
                    int id = result.getInt("id");
                    if (SQL.addColaboradores(new Colaboradores(id, result.getString("email"), result.getString("first_name"), result.getString("last_name"), result.getString("avatar"), result.getString("titles"), result.getString("description")))) {
                        pub = result.getJSONArray("publicaciones");
                        for (i = 0; i < pub.length(); i++) {
                            resultpub = pub.getJSONObject(i);
                            if (SQL.addPublicaciones(new Publicaciones(resultpub.getInt("id"), id, resultpub.getString("title"), resultpub.getString("type"), resultpub.getString("content"), resultpub.getString("location"), resultpub.getDouble("latitude"), resultpub.getDouble("longitude"), resultpub.getString("imagen"), resultpub.getString("date_publicated")))) {
                                LOGI("PUBLIC", "Good");
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void perseResena(JSONObject response, int id) {
        int i;
        JSONArray responseData = null;
        JSONObject data = null;
        JSONObject result = null;
        try {

            responseData = response.getJSONArray("responseData");// convertir
            // array
            for (i = 0; i < responseData.length(); i++) {
                data = responseData.getJSONObject(i);
                result = data.getJSONObject("data");
                Log.i("JSONPARSE", "id");
                if (!SQL.checkIdResena(result.getInt("id"))) {
                    if (SQL.addResena(new Resena(
                            result.getInt("id"),
                            result.getString("re_title"),
                            result.getString("re_content"),
                            result.getString("re_start"),
                            id,
                            result.getString("created_at"),
                            result.getString("user_name"),
                            result.getString("user_last_name"),
                            result.getString("profile_user")))) {
                        ;
                        LOGI("perseResena", "Good");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}// endClass
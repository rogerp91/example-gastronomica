package mobi.gastronomica.SQLite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mobi.gastronomica.UILApplication;
import mobi.gastronomica.model.Colaboradores;
import mobi.gastronomica.model.EventosRecientes;
import mobi.gastronomica.model.Publicaciones;
import mobi.gastronomica.model.Resena;
import mobi.gastronomica.model.Restaurantes;
import mobi.gastronomica.utils.Constants;

import static mobi.gastronomica.utils.Logs.LOGI;
import static mobi.gastronomica.utils.Logs.makeLogTag;

public class SQLite {

    private SQLiteDBHelper sqliteDBHelper = null;
    private SQLiteDatabase db = null;
    private static final String TAG = makeLogTag(SQLite.class);

    public SQLite() {
        // TODO Auto-generated constructor stub
        sqliteDBHelper = new SQLiteDBHelper(UILApplication.context);
    }

    // Abre conexion a base de datos
    public void open() {
        LOGI("SQLite", "Se abre conexion a la base de datos " + sqliteDBHelper.getDatabaseName());
        db = sqliteDBHelper.getWritableDatabase();
        UILApplication.ITEMS.clear();
    }

    // Cierra conexion a la base de datos
    public void close() {
        LOGI("SQLite", "Se cierra conexion a la base de datos " + sqliteDBHelper.getDatabaseName());
        sqliteDBHelper.close();
    }

    public void clear() {
        LOGI("SQLite", "Se borrar la base de datos " + sqliteDBHelper.getDatabaseName());
        sqliteDBHelper.onDelete(db);
    }

    public boolean addEventos(EventosRecientes eventos) {
        if (eventos != null) {
            ContentValues values = new ContentValues();
            values.put(Constants.EVENTOS_ID, eventos.getId());
            values.put(Constants.EVENTOS_TITLE, eventos.getTitle());
            values.put(Constants.EVENTOS_COLOR, eventos.getColor());
            values.put(Constants.EVENTOS_CATEGORY, eventos.getCategory());
            values.put(Constants.EVENTOS_DATE, eventos.getFecha());
            values.put(Constants.EVENTOS_PLACE, eventos.getPlace());
            values.put(Constants.EVENTOS_IMAGE, eventos.getImagen());
            values.put(Constants.EVENTOS_DESCRIPTION, eventos.getDescription());
            values.put(Constants.EVENTOS_PRICE, eventos.getPrice());
            values.put(Constants.EVENTOS_CONFIRMATION, eventos.getConfirmation());
            values.put(Constants.EVENTOS_ORG_NAME, eventos.getOrgName());
            values.put(Constants.EVENTOS_ORG_DESCRIP, eventos.getOrgDescrip());
            LOGI("SQLite", "Add Eventos");
            return (db.insert(Constants.TABLE_EVENTOS, null, values) != -1);
        } else {
            return false;
        }

    }

    public boolean checkEventos() {// verificar si existe algun dato en la
        return getEventos().size() > 0;
    }

    public boolean checkIdEventos(int id) {//check los eventos
        int i = 0;
        String query = "SELECT id FROM " + Constants.TABLE_EVENTOS + " WHERE id" + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        i = cursor.getCount();
        cursor.close();
        return i <= 0 ? false : true;
    }

    public ArrayList<EventosRecientes> getEventos() {

        EventosRecientes obj = null;
        ArrayList<EventosRecientes> array = new ArrayList<>();
        String query = "SELECT * FROM " + Constants.TABLE_EVENTOS;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                obj = new EventosRecientes(
                        cursor.getInt(cursor.getColumnIndex(Constants.EVENTOS_ID)),
                        cursor.getString(cursor.getColumnIndex(Constants.EVENTOS_TITLE)),
                        cursor.getString(cursor.getColumnIndex(Constants.EVENTOS_COLOR)),
                        cursor.getString(cursor.getColumnIndex(Constants.EVENTOS_CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(Constants.EVENTOS_DATE)),
                        cursor.getString(cursor.getColumnIndex(Constants.EVENTOS_PLACE)),
                        cursor.getString(cursor.getColumnIndex(Constants.EVENTOS_IMAGE)),
                        cursor.getInt(cursor.getColumnIndex(Constants.EVENTOS_PRICE)),
                        cursor.getString(cursor.getColumnIndex(Constants.EVENTOS_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndex(Constants.EVENTOS_CONFIRMATION)),
                        cursor.getString(cursor.getColumnIndex(Constants.EVENTOS_ORG_NAME)),
                        cursor.getString(cursor.getColumnIndex(Constants.EVENTOS_ORG_DESCRIP)));
                array.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return array;
    }

    public boolean addRestauranteCerca(Restaurantes obj) {
        if (obj != null) {
            // db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(Constants.ID, obj.getId());
            values.put(Constants.TITLE, obj.getTitle());
            values.put(Constants.MENU_REGION, obj.getMenuRegion());
            values.put(Constants.PRICE, obj.getPrice());
            values.put(Constants.IMAGE, obj.getImage());
            values.put(Constants.DATA, obj.getData());
            values.put(Constants.DISTANCE, obj.getDistance());
            LOGI("SQLite", "Add Restaurante");
            return (db.insert(Constants.TABLE_CERCA, null, values) != -1);
        } else {
            return false;
        }

    }

    public boolean checkIdCerca(int id) {
        int i = 0;
        String query = "SELECT id FROM " + Constants.TABLE_CERCA + " WHERE id" + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        i = cursor.getCount();
        cursor.close();
        return i <= 0 ? false : true;
    }

    public ArrayList<Restaurantes> getRestauranteCerca() {

        Restaurantes obj = null;
        ArrayList<Restaurantes> array = new ArrayList<>();
        String query = "SELECT * FROM " + Constants.TABLE_CERCA;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                obj = new Restaurantes(
                        cursor.getInt(cursor.getColumnIndex(Constants.ID)),
                        cursor.getString(cursor.getColumnIndex(Constants.TITLE)),
                        cursor.getString(cursor.getColumnIndex(Constants.MENU_REGION)),
                        cursor.getInt(cursor.getColumnIndex(Constants.PRICE)),
                        cursor.getString(cursor.getColumnIndex(Constants.IMAGE)),
                        cursor.getString(cursor.getColumnIndex(Constants.DATA)),
                        cursor.getString(cursor.getColumnIndex(Constants.DISTANCE)));
                array.add(obj);
                UILApplication.ITEMS.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return array;
    }

    public boolean checkRestauranteCerca() {// verificar si existe algun dato en la
        // db
        return getRestauranteCerca().size() > 0 ? true : false;
    }

    /**
     * Colaboradores *
     */

    public boolean addColaboradores(Colaboradores obj) {
        if (obj != null) {
            // db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(Constants.COLABORADORES_ID, obj.getId());
            values.put(Constants.COLABORADORES_EMAIL, obj.getEmail());
            values.put(Constants.COLABORADORES_FIRSTNANE, obj.getFirstName());
            values.put(Constants.COLABORADORES_LASTNAME, obj.getLastName());
            values.put(Constants.COLABORADORES_AVATAR, obj.getAvatar());
            values.put(Constants.COLABORADORES_TITLES, obj.getTitles());
            values.put(Constants.COLABORADORES_DESCRIPTION, obj.getDescription());
            LOGI("SQLite", "Add Colaboradores");
            return (db.insert(Constants.TABLE_COLABORADORES, null, values) != -1);
        } else {
            return false;
        }
    }

    public ArrayList<Colaboradores> getColaboradores() {
        Colaboradores obj = null;
        ArrayList<Colaboradores> array = new ArrayList<>();
        String query = "SELECT * FROM " + Constants.TABLE_COLABORADORES;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                obj = new Colaboradores(cursor.getInt(cursor.getColumnIndex(Constants.COLABORADORES_ID)), cursor.getString(cursor.getColumnIndex(Constants.COLABORADORES_EMAIL)), cursor.getString(cursor.getColumnIndex(Constants.COLABORADORES_FIRSTNANE)), cursor.getString(cursor.getColumnIndex(Constants.COLABORADORES_LASTNAME)), cursor.getString(cursor.getColumnIndex(Constants.COLABORADORES_AVATAR)), cursor.getString(cursor.getColumnIndex(Constants.COLABORADORES_TITLES)), cursor.getString(cursor.getColumnIndex(Constants.COLABORADORES_DESCRIPTION)));
                array.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return array;
    }

    public boolean checkColaboradores() {// verificar si existe algun dato en la
        // db
        return getColaboradores().size() > 0 ? true : false;
    }

    public boolean checkIdColaboradores(int id) {
        int i = 0;
        String query = "SELECT id FROM " + Constants.TABLE_COLABORADORES + " WHERE id" + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        i = cursor.getCount();
        return i <= 0 ? false : true;
    }

    /**
     * Publiciones *
     */

    public boolean addPublicaciones(Publicaciones obj) {
        if (obj != null) {
            // db.beginTransaction();

            LOGI("ADDP", obj.getType());

            ContentValues values = new ContentValues();
            values.put(Constants.PUBLICACIONES_ID, obj.getId());
            values.put(Constants.PUBLICACIONES_ID_COLABORADORES, obj.getIdColaboradores());
            values.put(Constants.PUBLICACIONES_TITLE, obj.getTitle());
            values.put(Constants.PUBLICACIONES_TYPE, obj.getType());
            values.put(Constants.PUBLICACIONES_CONTENT, obj.getContent());
            values.put(Constants.PUBLICACIONES_LOCATION, obj.getLocation());
            values.put(Constants.PUBLICACIONES_LATITIDE, Double.toString(obj.getLatitude()));
            values.put(Constants.PUBLICACIONES_LONGITUDE, Double.toString(obj.getLongitude()));
            values.put(Constants.PUBLICACIONES_IMAGEN, obj.getImagen());
            values.put(Constants.PUBLICACIONES_DATE_PUBLICATE, obj.getDatePublicated());
            LOGI("SQLite", "Add Colaboradores");
            return (db.insert(Constants.TABLE_PUBLICACIONES, null, values) != -1);
        } else {
            return false;
        }
    }

    public ArrayList<Publicaciones> getPublicaciones(int id_colaboradores) {
        Publicaciones obj = null;
        ArrayList<Publicaciones> array = new ArrayList<>();
        String query = "SELECT * FROM " + Constants.TABLE_PUBLICACIONES + " WHERE " + Constants.TABLE_PUBLICACIONES + ".id_colaboradores=" + id_colaboradores;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                obj = new Publicaciones(cursor.getInt(cursor.getColumnIndex(Constants.PUBLICACIONES_ID)), cursor.getInt(cursor.getColumnIndex(Constants.PUBLICACIONES_ID_COLABORADORES)), cursor.getString(cursor.getColumnIndex(Constants.PUBLICACIONES_TITLE)), cursor.getString(cursor.getColumnIndex(Constants.PUBLICACIONES_TYPE)), cursor.getString(cursor.getColumnIndex(Constants.PUBLICACIONES_CONTENT)), cursor.getString(cursor.getColumnIndex(Constants.PUBLICACIONES_LOCATION)), cursor.getDouble(cursor.getColumnIndex(Constants.PUBLICACIONES_LATITIDE)), cursor.getDouble(cursor.getColumnIndex(Constants.PUBLICACIONES_LONGITUDE)), cursor.getString(cursor.getColumnIndex(Constants.PUBLICACIONES_IMAGEN)), cursor.getString(cursor.getColumnIndex(Constants.PUBLICACIONES_DATE_PUBLICATE)));
                array.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return array;
    }


    public ArrayList<Resena> getResena() {
        Resena obj = null;
        ArrayList<Resena> array = new ArrayList<>();
        String query = "SELECT * FROM " + Constants.TABLE_RESENA;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                obj = new Resena(cursor.getInt(cursor.getColumnIndex(Constants.RESENA_ID)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_RE_TITLE)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_RE_CONTENT)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_RE_START)), cursor.getInt(cursor.getColumnIndex(Constants.RESENA_RESTAURANT_ID)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_CREATED_AT)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_USER_NAME)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_USER_LAST_NAME)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_PROFILE_USER)));
                array.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return array;
    }

    public ArrayList<Resena> getResena(int id) {
        Resena obj = null;
        ArrayList<Resena> array = new ArrayList<>();
        String query = "SELECT * FROM " + Constants.TABLE_RESENA + " WHERE " + Constants.TABLE_RESENA+".restaurant_id="+id;
        LOGI("query",query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                obj = new Resena(cursor.getInt(cursor.getColumnIndex(Constants.RESENA_ID)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_RE_TITLE)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_RE_CONTENT)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_RE_START)), cursor.getInt(cursor.getColumnIndex(Constants.RESENA_RESTAURANT_ID)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_CREATED_AT)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_USER_NAME)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_USER_LAST_NAME)), cursor.getString(cursor.getColumnIndex(Constants.RESENA_PROFILE_USER)));
                array.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return array;
    }

    public boolean addResena(Resena obj) {
        if (obj != null) {
            // db.beginTransaction();

            LOGI("ADD", "Resena");
            ContentValues values = new ContentValues();
            values.put(Constants.RESENA_ID, obj.getId());
            values.put(Constants.RESENA_RE_TITLE, obj.getReIitle());
            values.put(Constants.RESENA_RE_CONTENT, obj.getReContent());
            values.put(Constants.RESENA_RE_START, obj.getReStart());
            values.put(Constants.RESENA_RESTAURANT_ID, obj.getRestaurantId());
            values.put(Constants.RESENA_CREATED_AT, obj.getCreatedAt());
            values.put(Constants.RESENA_USER_NAME, obj.getUserName());
            values.put(Constants.RESENA_USER_LAST_NAME, obj.getUserLastName());
            values.put(Constants.RESENA_PROFILE_USER, obj.getProfileUser());
            LOGI("SQLite", "Add Resena");
            return (db.insert(Constants.TABLE_RESENA, null, values) != -1);
        } else {
            return false;
        }
    }

    public boolean checkResena() {// verificar si existe algun dato en la
        // db
        return getResena().size() > 0 ? true : false;
    }

    public boolean checkIdResena(int id) {
        int i = 0;
        String query = "SELECT id FROM " + Constants.TABLE_RESENA + " WHERE id" + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        i = cursor.getCount();
        return i <= 0 ? false : true;
    }

}// end class
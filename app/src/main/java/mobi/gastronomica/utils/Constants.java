package mobi.gastronomica.utils;

public class Constants {//constantes globales

    // Main
    public final static String NAME_APP = "gastronomica";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    // SplashScren
    public final static int SPLASH_SCREEN_DELAY = 2000;

    // ApiManager
    public final static String API_BASE_URL = "http://api.gastronomica.mobi/v1/";
    public final static String API_BASE_URL_2 = "http://admin.gastronomica.mobi/";
    public final static String API_BASE_URL_RESTAURANTE = "http://api.gastronomica.mobi/v1/restaurante?";
    public final static String API_ACCESS_TOKEN = "access_token=221c7b93a8261314688a39bac6d8cfe6";
    public final     static String ACCESS_TOKEN = "?access_token=221c7b93a8261314688a39bac6d8cfe6";
    public final static String API_BASE_URL_NOT_VERSION = "http://api.gastronomica.mobi";
    // Config
    public final static boolean IS_DOGFOOD_BUILD = false;

    // Fragment
    public final static String ID_FRAGMENT = "fragment";// clave para los ids de

    // los fragment
    public final static int FRAGMENT_BASE = 0;
    public final static int FRAGMENT_LIST_DONDE_IR = 1;
    public final static int FRAGMENT_LIST_EVENTOS = 2;
    public final static int FRAGMENT_DETAIL_IMG = 3;
    public final static int FRAGMENT_EVENTOS_DETAIL = 4;
    public final static int FRAGMENT_LIST_COLABORADORES = 5;
    public final static int FRAGMENT_PUBLICACION_DETAILS = 6;
    public final static int FRAGMENT_RESENA_DETAILS = 7;


    // SQLiteDBHelper
    public final static String SQL_DIR = "sql";
    public final static String CREATEFILE = "gastronomica.sql";
    public final static String UPGRADEFILE_PREFIX = "upgrade-";
    public final static String UPGRADEFILE_SUFFIX = ".sql";
    public final static String DATABASE_NAME = "Gastronomica";
    public final static int DATABASE_VERSION = 3;
    public final static String TABLE_RESTAURANTE = "Restaurante";
    public final static String TABLE_EVENTOS = "Eventos";
    public final static String TABLE_CERCA = "Cerca";
    public final static String TABLE_COLABORADORES = "Colaboradores";
    public final static String TABLE_PUBLICACIONES= "Publicaciones";
    public final static String TABLE_RESENA = "Resena";

    // Sql Fields
    public final static String ID = "id";
    public final static String TITLE = "title";
    public final static String IMAGE = "image";
    public final static String MENU_REGION = "menu_region";
    public final static String PRICE = "price";
    public final static String DATA = "responseData";
    public final static String DISTANCE = "distance";

    // Register User
    public final static String CLIENT = "client";
    public final static String FIRST_NAME = "first_name";
    public final static String LAST_NAME = "last_name";
    public final static String EMAIL = "email";
    public final static String ROLL_DEFAULT = "roll";
    public final static int ROLL = 1;
    public final static int LEVEL = 1;
    public final static String LEVEL_DEFAULT = "level";
    public final static String PASSWORD = "password";

    //PreferenceManager
    public final static String LOGIN_KEY = "login_key";

    //Register
    public final static String CLIENT_ID = "id";
    public final static String CLIENT_FIRST_NAME = "first_name";
    public final static String CLIENT_EMAIL = "email";
    public final static String CLIENT_LAST_NAME = "last_name";
    public final static String CLIENT_AVATAR = "avatar";
    public final static String CLIENT_ROLL = "roll";
    public final static String CLIENT_LEVEL = "level";


    //Colabporadores
    public final static String PUBLICACIONES_ID = "id";
    public final static String PUBLICACIONES_ID_COLABORADORES = "id_colaboradores";
    public final static String PUBLICACIONES_TITLE = "title";
    public final static String PUBLICACIONES_TYPE = "type";
    public final static String PUBLICACIONES_CONTENT = "content";
    public final static String PUBLICACIONES_LOCATION = "location";
    public final static String PUBLICACIONES_LATITIDE = "latitude";
    public final static String PUBLICACIONES_LONGITUDE = "longitude";
    public final static String PUBLICACIONES_IMAGEN = "imagen";
    public final static String PUBLICACIONES_DATE_PUBLICATE = "date_publicated";
    public final static String PARCELABLE = "parcelable";


    public final static String COLABORADORES_ID = "id";
    public final static String COLABORADORES_EMAIL = "email";
    public final static String COLABORADORES_FIRSTNANE = "first_name";
    public final static String COLABORADORES_LASTNAME = "last_name";
    public final static String COLABORADORES_AVATAR = "avatar";
    public final static String COLABORADORES_TITLES = "titles";
    public final static String COLABORADORES_DESCRIPTION = "description";

    //ListEventos
    public final static String EVENTOS_PARCELABLE = "parcelable";

    //Eventos
    public final static String EVENTOS_ID = "id";
    public final static String EVENTOS_TITLE = "title";
    public final static String EVENTOS_COLOR = "color";
    public final static String EVENTOS_CATEGORY = "category";
    public final static String EVENTOS_DATE = "fecha";
    public final static String EVENTOS_PLACE = "place";
    public final static String EVENTOS_IMAGE = "image";
    public final static String EVENTOS_DESCRIPTION = "description";
    public final static String EVENTOS_PRICE = "price";
    public final static String EVENTOS_CONFIRMATION = "confirmation";
    public final static String EVENTOS_ORG_NAME = "org_name";
    public final static String EVENTOS_ORG_DESCRIP = "org_descrip";

    //ResetPasswordEmail
    public final static String RESET_FLAT = "reset_save";
    public final static String RESET_MESSAGE = "message";
    public final static String RESET_CORREO_DESTINO = "correo_destino";
    public final static String RESET_RESET_PASSWORD_TOKEN = "reset_password_token";
    public final static String RESET_PASSWORD_RESET_SENT_AT = "password_reset_sent_at";

    //Extras
    //tipo de datos que pasa por el intent para el container activity
    public final static String SEND_DATA = "send_data";

    //Resena
    public final static String RESENA_ID  = "id";
    public final static String RESENA_RE_TITLE  = "re_title";
    public final static String RESENA_RE_CONTENT  = "re_content";
    public final static String RESENA_RE_START  = "re_start";
    public final static String RESENA_RESTAURANT_ID  = "restaurant_id";
    public final static String RESENA_CREATED_AT  = "created_at";
    public final static String RESENA_USER_NAME  = "user_name";
    public final static String RESENA_USER_LAST_NAME  = "user_last_name";
    public final static String RESENA_PROFILE_USER  = "profile_user";

}// end
package mobi.gastronomica;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

import mobi.gastronomica.SQLite.SQLite;
import mobi.gastronomica.components.TabPagerItemColaboradores;
import mobi.gastronomica.components.TabPagerItemEventos;
import mobi.gastronomica.components.TabPagerItemRestaurantes;
import mobi.gastronomica.model.Restaurantes;

public class UILApplication extends Application { //variables y metodos globales de la App

    public volatile static Context context = null;//contexto de la aplicacion global

    public volatile static ArrayList<Restaurantes> ITEMS = new ArrayList<>();//los objetos de las lista de manera global
    public volatile static SQLite SQL = null;

    //tab del viewpage
    public static List<TabPagerItemRestaurantes> TABS = new ArrayList<>();
    public static List<TabPagerItemEventos> TABS_EVENTOS = new ArrayList<>();
    public static List<TabPagerItemColaboradores> TABS_COLABORADORES = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();//asignar el cotexto de la aplicacion
        SQL = new SQLite();//instancia
        SQL.open();//abir la base de dato
        initImageLoader(getApplicationContext());//instancia de las imagenes y el tamaño de cache
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SQL.close();
    }


    public static void initImageLoader(Context context) {//control de cache para la imagenes
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }
}//endClass
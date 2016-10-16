package mobi.gastronomica.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nispok.snackbar.Snackbar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mobi.gastronomica.R;
import mobi.gastronomica.UILApplication;
import mobi.gastronomica.activity.ContainerActivity;
import mobi.gastronomica.activity.ViewPagerActivityRestaurantes;
import mobi.gastronomica.model.DetailsImagen;
import mobi.gastronomica.utils.Constants;
import mobi.gastronomica.utils.Functions;

import static mobi.gastronomica.utils.Logs.LOGI;

public class InfoDondeIrFragment extends BaseFragment implements View.OnClickListener {

    // TODO: Rename and change types and number of parameters
    public static InfoDondeIrFragment newInstance() {
        InfoDondeIrFragment fragment = new InfoDondeIrFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //var
    private ViewPagerActivityRestaurantes vp = null;
    //1
    @InjectView(R.id.img_header)
    ImageView img_header;
    @InjectView(R.id.text_heder)
    TextView text_heder;

    @InjectView(R.id.title_restaurante)
    TextView title;
    @InjectView(R.id.menu_region)
    TextView menu_region;
    @InjectView(R.id.price)
    ImageView price;

    //2
    @InjectView(R.id.phone)
    TextView phone;
    @InjectView(R.id.mobile_phone)
    TextView mobile_phone;
    @InjectView(R.id.email)
    TextView email;
    @InjectView(R.id.website)
    TextView website;
    @InjectView(R.id.ubicacion)
    TextView ubicacion;

    //4
    @InjectView(R.id.name_menu)
    TextView name_menu;
    @InjectView(R.id.name_regionales)
    TextView name_regionales;
    @InjectView(R.id.name_servicio)
    TextView name_servicio;
    @InjectView(R.id.name_local)
    TextView name_local;
    @InjectView(R.id.name_tipo)
    TextView name_tipo;
    @InjectView(R.id.name_especialidad)
    TextView name_especialidad;


    @InjectView(R.id.caracteristicas)
    TextView caracteristica;
    @InjectView(R.id.horarios)
    TextView horarios;
    @InjectView(R.id.name_servicios)
    TextView name_servicios;
    @InjectView(R.id.name_forma_pago)
    TextView name_forma_pago;

    @InjectView(R.id.menu)
    TextView menu;
    @InjectView(R.id.regionale)
    TextView regionale;
    @InjectView(R.id.servicio)
    TextView servicio;
    @InjectView(R.id.local)
    TextView local;
    @InjectView(R.id.tipo)
    TextView tipo;
    @InjectView(R.id.especialidad)
    TextView especialidad;

    //5
    @InjectView(R.id.desde_dia)
    TextView desde_dia;
    @InjectView(R.id.desde_hora)
    TextView desde_hora;
    @InjectView(R.id.hasta_dia)
    TextView hasta_dia;
    @InjectView(R.id.hasta_hora)
    TextView hasta_hora;

    @InjectView(R.id.tipo_servicios)
    TextView tipo_servicio;
    @InjectView(R.id.forma_pago)
    TextView forma_pago;

    private MapView mapView = null;
    private GoogleMap mMap = null;
   //@InjectView(R.id.img_map)
    //ImageView img_map;
    String latitud = "";
    String longitud = "";

    //Gone - Invisible
    @InjectView(R.id.linear_mobile_phone)
    LinearLayout linear_mobile_phone;
    @InjectView(R.id.linear_phone)
    LinearLayout linear_phone;
    @InjectView(R.id.linear_email)
    LinearLayout linear_email;
    @InjectView(R.id.linear_websites)
    LinearLayout linear_websites;
    @InjectView(R.id.cbz_2)
    LinearLayout cbz_2;

    private String web = "";
    private String mobile = "";
    private String mphone = "";

    String mUrl = "";
    String mTitle = "";

    //@InjectView(R.id.progress_img)
    //View progressWheel;
    DisplayImageOptions options = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vp = (ViewPagerActivityRestaurantes) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_donde_ir_informacion, container, false);
        ButterKnife.inject(this, view);

        int pos = 0;

        //Mapas
        if(Functions.checkPlayServices(getActivity())){
            mapView = (MapView) view.findViewById(R.id.map);
            mapView.onCreate(savedInstanceState);
            mMap = mapView.getMap();
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().isZoomControlsEnabled();
            mMap.getUiSettings().setScrollGesturesEnabled(false);
            mMap.setMyLocationEnabled(false);
            MapsInitializer.initialize(this.getActivity());
        }
        //control de cache
        options = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.ic_launcher).
                showImageOnFail(R.mipmap.ic_launcher).
                cacheInMemory(true).
                cacheOnDisk(true).
                considerExifParams(true).
                bitmapConfig(Bitmap.Config.RGB_565).
                build();

        ArrayList<TextView> style_light = new ArrayList<>();
        ArrayList<TextView> style_regular = new ArrayList<>();
        ArrayList<TextView> style_italic = new ArrayList<>();
        String font_light = "Roboto-Light";
        String font_regular = "Roboto-Regular";
        String font_italic = "Roboto-LightItalic";


        //cambiar estilo de letras
        style_light.add(title);
        style_light.add(menu_region);
        style_light.add(phone);
        style_light.add(mobile_phone);
        style_light.add(email);
        style_light.add(website);
        style_light.add(desde_dia);
        style_light.add(desde_hora);
        style_light.add(hasta_dia);
        style_light.add(hasta_hora);
        style_light.add(name_menu);
        style_light.add(name_regionales);
        style_light.add(name_servicio);
        style_light.add(name_local);
        style_light.add(name_tipo);
        style_light.add(name_especialidad);
        style_light.add(caracteristica);
        style_light.add(horarios);
        style_light.add(name_servicios);
        style_light.add(name_forma_pago);
        style_light.add(tipo_servicio);
        style_light.add(forma_pago);
        style_light.add(text_heder);

        style_regular.add(ubicacion);
        style_regular.add(menu);
        style_regular.add(regionale);
        style_regular.add(especialidad);
        style_regular.add(servicio);
        style_regular.add(local);
        style_regular.add(tipo);

        style_italic.add(caracteristica);
        style_italic.add(name_servicios);
        style_italic.add(horarios);
        style_italic.add(name_forma_pago);

        //Estilos a letras
        for (TextView letter : style_light) {
            letter.setTypeface(Functions.getTypeface(font_light));
        }

        for (TextView letter : style_regular) {
            letter.setTypeface(Functions.getTypeface(font_regular));
        }

        for (TextView letter : style_italic) {
            letter.setTypeface(Functions.getTypeface(font_italic));
        }

        pos = vp.dataInformation();
        LOGI("InfoDondeIrFragment POS", Integer.toString(pos));
        LOGI("InfoDondeIrFragment POS Array", Integer.toString(UILApplication.ITEMS.get(pos).getId()));
        if (pos != -1) {
            showInformation(pos);//bugs

        }
        return view;
    }


    @Override
    protected void showInformation(int i) {
        super.showInformation(i);
        String url = Constants.API_BASE_URL_2 + UILApplication.ITEMS.get(i).getImage();//iimagen
        this.mUrl = url;//bugs
        //Glide.with(getActivity()).load(url).into(img_header);// img

        if (Functions.validateField(UILApplication.ITEMS.get(i).getImage())) {
            img_header.setVisibility(View.GONE);
            text_heder.setVisibility(View.VISIBLE);
        } else {
            ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
            imageLoader.displayImage(mUrl, img_header, options);
        }

        title.setText(UILApplication.ITEMS.get(i).getTitle());
        this.mTitle = UILApplication.ITEMS.get(i).getTitle();

        menu_region.setText(UILApplication.ITEMS.get(i).getMenuRegion());

        //prices
        int prices = UILApplication.ITEMS.get(i).getPrice();
        switch (prices) {
            case 1:
                price.setImageDrawable(getDrawableResource(R.drawable.rango_precio_1));
                break;
            case 2:
                price.setImageDrawable(getDrawableResource(R.drawable.rango_precio_2));
                break;
            case 3:
                price.setImageDrawable(getDrawableResource(R.drawable.rango_precio_3));
                break;
            case 4:
                price.setImageDrawable(getDrawableResource(R.drawable.rango_precio_4));
                break;
            case 5:
                price.setImageDrawable(getDrawableResource(R.drawable.rango_precio_5));
                break;
            default:
                break;
        }

        //obtener los demas datos
        try {

            JSONObject json = new JSONObject(UILApplication.ITEMS.get(i).getData());
            Log.i("LOGLAT",json.getString("latitud"));

            if(Functions.checkPlayServices(getActivity())){
                if ((json.getString("latitud").equals("null")) && (json.getString("longitud").equals("null")) || (json.getString("latitud") == null) && (json.getString("longitud") == null)) {
                    //img_map.setVisibility(View.GONE);
                    mapView.setVisibility(View.GONE);
                    //progressWheel.setVisibility(View.GONE);
                } else {
                    addMaps(json.getDouble("latitud"), json.getDouble("longitud"), UILApplication.ITEMS.get(i).getTitle());
                }
            }else{
                //progressWheel.setVisibility(View.GONE);
                /**
                mapView.setVisibility(View.GONE);
                latitud = json.getString("latitud");
                longitud = json.getString("longitud");
                String api_map = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitud + "," + longitud + "&zoom=10&size=350x120&markers=icon:http://admin.gastronomica.mobi/iconmap.png|shadow:true+|+" + latitud + "," + longitud + "&sensor=false";
                Log.i("URL MAPS", api_map);
                loadImagen(api_map);
                 */
            }

            //verificar si los datos tlf,tlf movil, correo e email no eata disponible en ese caso no se muestra
            if (TextUtils.isEmpty(json.getString("phone")) && TextUtils.isEmpty(json.getString("mobile_phone")) && TextUtils.isEmpty(json.getString("email")) && TextUtils.isEmpty(json.getString("website"))) {
                cbz_2.setVisibility(View.GONE);
            }


            if (TextUtils.isEmpty(json.getString("phone")) || json.getString("phone").equals("")) {
                linear_phone.setVisibility(View.GONE);
                //phone.setText(getResources().getString(R.string.no_phone));
            } else {
                linear_phone.setVisibility(View.VISIBLE);
                mphone = json.getString("phone");
                phone.setText(json.getString("phone"));
            }

            if (TextUtils.isEmpty(json.getString("mobile_phone")) || json.getString("mobile_phone").equals("")) {
                linear_mobile_phone.setVisibility(View.GONE);
                //mobile_phone.setText(getResources().getString(R.string.no_mobil_phone));
            } else {
                linear_mobile_phone.setVisibility(View.VISIBLE);
                mobile = json.getString("mobile_phone");
                mobile_phone.setText(json.getString("mobile_phone"));
            }

            if (TextUtils.isEmpty(json.getString("email")) || json.getString("email").equals("")) {
                linear_email.setVisibility(View.GONE);
                email.setText(getResources().getString(R.string.no_email));
            } else {
                linear_email.setVisibility(View.VISIBLE);
                email.setText(json.getString("email"));
            }

            if (TextUtils.isEmpty(json.getString("website")) || json.getString("website").equals("")) {
                linear_websites.setVisibility(View.GONE);
                website.setText(getResources().getString(R.string.no_website));
            } else {
                web = json.getString("website");
                linear_websites.setVisibility(View.VISIBLE);
                website.setText(json.getString("website"));
            }


            phone.setOnClickListener(this);
            mobile_phone.setOnClickListener(this);
            email.setOnClickListener(this);
            website.setOnClickListener(this);

            StringBuilder sb = new StringBuilder();
            List<String> list = new ArrayList<>();

            if (!Functions.validateField(json.getString("avenue"))) {
                list.add(json.getString("avenue"));
            }
            if (!Functions.validateField(json.getString("neighbourhood"))) {
                list.add(json.getString("neighbourhood"));
            }
            if (!Functions.validateField(json.getString("parish"))) {
                list.add(json.getString("parish"));
            }
            if (!Functions.validateField(json.getString("landmark"))) {
                list.add(json.getString("landmark"));
            }
            if (!Functions.validateField(json.getString("country"))) {
                list.add(json.getString("country"));
            }

            if (list.size() == 1) {
                sb.append(list.get(0));
            } else {
                if (list.size() == 2) {
                    sb.append(list.get(0)).append(", ").append(list.get(1));
                } else {
                    if (list.size() == 3) {
                        sb.append(list.get(0)).append(", ").append(list.get(1)).append(", ").append(list.get(2));
                    } else {
                        if (list.size() == 4) {
                            sb.append(list.get(0)).append(", ").append(list.get(1)).append(", ").append(list.get(2)).append(", ").append(list.get(3));
                        } else {
                            if (list.size() == 5) {
                                sb.append(list.get(0)).append(", ").append(list.get(1)).append(", ").append(list.get(2)).append(", ").append(list.get(3)).append(", ").append(list.get(4));
                            }
                        }
                    }
                }
            }

            ubicacion.setText(sb.toString());//si existe una coma al pricipio quitarsela

            /** ------------------------- Caracterisitca ------------------------- **/
            if (TextUtils.isEmpty(json.getString("menu_type")) || json.getString("menu_type") == null) {
                menu.setText(getResources().getString(R.string.no_aplica));
            } else {
                menu.setText(json.getString("menu_type"));
            }

            if (TextUtils.isEmpty(json.getString("menu_region")) || json.getString("menu_region") == null) {
                regionale.setText(getResources().getString(R.string.no_aplica));
            } else {
                regionale.setText(Functions.upperCaseFirst(json.getString("menu_region")));
            }

            if (TextUtils.isEmpty(json.getString("menu_services")) || json.getString("menu_services") == null) {
                servicio.setText(getResources().getString(R.string.no_aplica));
            } else {
                servicio.setText(json.getString("menu_services"));
            }

            if (TextUtils.isEmpty(json.getString("local_number")) || json.getString("local_number") == null) {
                tipo.setText(getResources().getString(R.string.no_aplica));
            } else {
                tipo.setText(json.getString("local_number"));
            }

            if (TextUtils.isEmpty(json.getString("local")) || json.getString("local") == null) {
                local.setText(getResources().getString(R.string.no_aplica));
            } else {
                local.setText(json.getString("local"));
            }

            if (TextUtils.isEmpty(json.getString("consolidated")) || json.getString("consolidated") == null) {
                tipo.setText(getResources().getString(R.string.no_aplica));
            } else {
                tipo.setText(Functions.upperCaseFirst(json.getString("consolidated")));
            }

            if (TextUtils.isEmpty(json.getString("specializing_in")) || json.getString("specializing_in") == null) {
                especialidad.setText("No Aplica");
            } else {
                especialidad.setText(json.getString("specializing_in"));
            }

            /** ------------------------- Caracterisitca ------------------------- **/


            /** ------------------------- Hora ------------------------- **/

            desde_dia.setText(json.getString("open"));
            hasta_dia.setText(json.getString("to"));
            desde_hora.setText(Functions.upperCaseFirst(json.getString("from_time")));
            hasta_hora.setText(Functions.upperCaseFirst(json.getString("to_time")));

            /** ------------------------- Hora ------------------------- **/


            /** ------------------------- Servicio ------------------------- **/
            //Servicio
            StringBuilder servicio = new StringBuilder();
            List<String> m2list;
            m2list = new ArrayList<>();
            if (json.getBoolean("reservation")) {
                m2list.add("Reservaciones");
            }

            if (json.getBoolean("delivery")) {
                m2list.add("Entregas");
            }

            if (json.getBoolean("parking")) {
                m2list.add("Estacionamiento");
            }

            if (json.getBoolean("valet_parking")) {
                m2list.add("Servicio de estacionamiento");
            }

            if (TextUtils.isEmpty(servicio.toString()) || servicio.length() == 0) {
                tipo_servicio.setText("No tienes servicios actualmente.");
            } else {
                if (m2list.size() == 1) {
                    servicio.append(list.get(0));
                } else {
                    if (m2list.size() == 2) {
                        servicio.append(m2list.get(0)).append(", ").append(m2list.get(1));
                    } else {
                        if (m2list.size() == 3) {
                            servicio.append(m2list.get(0)).append(", ").append(m2list.get(1)).append(", ").append(m2list.get(2));
                        } else {
                            if (m2list.size() == 4) {
                                servicio.append(m2list.get(0)).append(", ").append(m2list.get(1)).append(", ").append(m2list.get(2)).append(", ").append(m2list.get(3));
                            }
                        }
                    }
                }
                tipo_servicio.setText(servicio.toString() + ".");
            }

            /** ------------------------- Servicio ------------------------- **/


            /** ------------------------- Forma de Pago ------------------------- **/
            StringBuilder pago = new StringBuilder();
            List<String> mlist;
            mlist = new ArrayList<>();

            if (json.getBoolean("cash")) {
                mlist.add("Efectivo");
            }
            if (json.getBoolean("checking")) {
                mlist.add("Cheque");
            }
            if (json.getBoolean("credict_card")) {
                mlist.add("T.Credito");
            }
            if (json.getBoolean("debit_card")) {
                mlist.add("T.Debito");
            }

            if (mlist.size() == 1) {
                pago.append(list.get(0));
            } else {
                if (mlist.size() == 2) {
                    pago.append(mlist.get(0)).append(", ").append(mlist.get(1));
                } else {
                    if (mlist.size() == 3) {
                        pago.append(mlist.get(0)).append(", ").append(mlist.get(1)).append(", ").append(mlist.get(2));
                    } else {
                        if (mlist.size() == 4) {
                            pago.append(mlist.get(0)).append(", ").append(mlist.get(1)).append(", ").append(mlist.get(2)).append(", ").append(mlist.get(3));
                        }
                    }
                }
            }

            forma_pago.setText(pago.toString() + ".");

            /** ------------------------- Forma de Pago ------------------------- **/

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        if(Functions.checkPlayServices(getActivity())){
            mapView.onResume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(Functions.checkPlayServices(getActivity())){
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(Functions.checkPlayServices(getActivity())){
            mapView.onLowMemory();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        super.onLowMemory();
        if(Functions.checkPlayServices(getActivity())){
            mapView.onPause();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent(Intent.ACTION_CALL);
        switch (id) {
            case R.id.phone:
                intent.setData(Uri.parse("tel:" + this.mphone));
                startActivity(intent);
                break;
            case R.id.mobile_phone:
                intent.setData(Uri.parse("tel:" + this.mobile));
                startActivity(intent);
                break;
            case R.id.email:
                Functions.SendEmail(getActivity());
                break;
            case R.id.website:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(this.web));
                startActivity(i);
                break;
        }
    }

    @OnClick(R.id.img_header)
    public void goToImg() {
        if (img_header != null) {
            Intent intent = new Intent(getActivity(), ContainerActivity.class);
            Bundle b = new Bundle();
            b.putInt(Constants.ID_FRAGMENT, Constants.FRAGMENT_DETAIL_IMG);
            b.putParcelable(Constants.SEND_DATA, new DetailsImagen(this.mTitle, this.mUrl));
            intent.putExtras(b);
            goToIntent(intent);
        }
    }

    /**
    @OnClick(R.id.img_map)
    public void goToMap() {
        if (img_map != null) {
            Log.i("Onclick", "map");
            Uri gmmIntentUri = Uri.parse("geo:" + latitud + "," + longitud + "?z=" + 15);
            //Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+latitud+","+longitud);
            //Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+latitud+","+"&daddr="+longitud);
            //Uri gmmIntentUri = Uri.parse(api_map);
            if (latitud.equals("") || longitud.equals("")) {
                Snackbar.with(getActivity()).text("No se encuentran disponible los datos de localización del restaurante").color(getResources().getColor(R.color.error)).show(getActivity());
            } else {
                if (Functions.isGoogleMapsInstalled()) {
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        getActivity().finish();
                        startActivity(mapIntent);
                    }
                } else {
                    Snackbar.with(getActivity()).text("No tiene instalado Google Maps").color(getResources().getColor(R.color.error)).show(getActivity());
                }
            }
        }
    }

    public void loadImagen(String api_map) {
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        imageLoader.displayImage(api_map, img_map, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressWheel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressWheel.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressWheel.setVisibility(View.GONE);
                if (loadedImage != null) {
                    img_map.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                img_map.setVisibility(View.VISIBLE);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {

            }
        });

    }
     */

    private void addMaps(final Double lat, final Double log, final String title) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, log), 10);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 1, null);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.animateCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, log)).icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_ubicacion));
        mMap.addMarker(marker);

        /**
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i("Onclick", "map");
                Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + log + "?z=" + 15);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    getActivity().finish();
                    startActivity(mapIntent);
                 }
            }

        });
        */
        /**
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.i("Onclick", "map");
                //Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + log + "?z=" + 15);
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=" + lat + "," + log);
                String name = "Nose";
                //Uri gmmIntentUri = Uri.parse("geo:0,0?q"+ lat + "," + log);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    getActivity().finish();
                    startActivity(mapIntent);
                }
                return false;
            }
        });
        */
    }

}//endClass

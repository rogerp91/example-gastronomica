package mobi.gastronomica.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Publicaciones implements Parcelable {

    int id;
    int id_colaboradores;
    String title;
    String type;
    String content;
    String location;
    double latitude;
    double longitude;
    String imagen;
    String date_publicated;

    public Publicaciones(int id, int id_colaboradores, String title, String type, String content, String location, double latitude, double longitude, String imagen, String date_publicated) {
        this.id = id;
        this.id_colaboradores = id_colaboradores;
        this.title = title;
        this.type = type;
        this.content = content;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imagen = imagen;
        this.date_publicated = date_publicated;
    }

    //getter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdColaboradores() {
        return id_colaboradores;
    }

    public void setIdColaboradores(int id_colaboradores) {
        this.id_colaboradores = id_colaboradores;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDatePublicated() {
        return date_publicated;
    }

    public void setDatePublicated(String date_publicated) {
        this.date_publicated = date_publicated;
    }

    public Publicaciones(Parcel parcel) {
        id = parcel.readInt();
        id_colaboradores = parcel.readInt();
        title = parcel.readString();
        type = parcel.readString();
        content = parcel.readString();
        location = parcel.readString();
        latitude = parcel.readDouble();
        longitude = parcel.readDouble();
        imagen = parcel.readString();
        date_publicated = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(id_colaboradores);
        parcel.writeString(title);
        parcel.writeString(type);
        parcel.writeString(content);
        parcel.writeString(location);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(imagen);
        parcel.writeString(date_publicated);
    }

    public static final Creator<Publicaciones> CREATOR = new Creator<Publicaciones>() {
        @Override
        public Publicaciones createFromParcel(Parcel parcel) {
            return new Publicaciones(parcel);
        }

        @Override
        public Publicaciones[] newArray(int size) {
            return new Publicaciones[size];
        }
    };
}

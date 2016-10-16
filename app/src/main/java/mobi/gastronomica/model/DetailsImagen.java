package mobi.gastronomica.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailsImagen implements Parcelable {

    private String title;
    private String url;

    public DetailsImagen(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public DetailsImagen(Parcel parcel){
        title = parcel.readString();
        url = parcel.readString();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(url);
    }

    public static final Creator<DetailsImagen> CREATOR = new Creator<DetailsImagen>() {
        @Override
        public DetailsImagen createFromParcel(Parcel parcel) {
            return new DetailsImagen(parcel);
        }

        @Override
        public DetailsImagen[] newArray(int size) {
            return new DetailsImagen[size];
        }
    };
}

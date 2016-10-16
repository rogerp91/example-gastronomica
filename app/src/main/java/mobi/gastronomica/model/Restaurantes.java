package mobi.gastronomica.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurantes implements Parcelable {// JavaBean

    // fields
    public int id;
    private String title;
    private String menu_region;
    private String image;
    private String data;
    private int price;
    private String distance;

    public Restaurantes(int id, String title, String menu_region, int price, String image, String data, String distance) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.title = title;
        this.image = image;
        this.data = data;
        this.menu_region = menu_region;
        this.price = price;
        this.distance = distance;
    }

    public Restaurantes(int id, String title, String image) {
        // TODO Auto-generated constructor stub
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public Restaurantes(Parcel parcel) {
        id = parcel.readInt();
        title = parcel.readString();
        image = parcel.readString();
        data = parcel.readString();
        menu_region = parcel.readString();
        price = parcel.readInt();
        distance = parcel.readString();
    }

    // getters
    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getMenuRegion() {
        return menu_region;
    }

    public String getImage() {
        return image;
    }

    public String getData() {
        return data;
    }

    public String getDistance() {
        return distance;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String setMenuRegion(String menu_region) {
        return this.menu_region = menu_region;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(data);
        parcel.writeString(menu_region);
        parcel.writeInt(price);
        parcel.writeString(distance);
    }

    public static final Creator<Restaurantes> CREATOR = new Creator<Restaurantes>() {
        @Override
        public Restaurantes createFromParcel(Parcel parcel) {
            return new Restaurantes(parcel);
        }

        @Override
        public Restaurantes[] newArray(int size) {
            return new Restaurantes[size];
        }
    };

}
package mobi.gastronomica.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Colaboradores implements Parcelable {

    int id;
    String email;
    String first_name;
    String last_name;
    String avatar;
    String titles;
    String description;

    public Colaboradores(int id, String email, String first_name, String last_name, String avatar, String titles, String description) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
        this.titles = titles;
        this.description = description;
    }

    //getter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Colaboradores(Parcel parcel) {
        id = parcel.readInt();
        email = parcel.readString();
        first_name = parcel.readString();
        last_name = parcel.readString();
        avatar = parcel.readString();
        titles = parcel.readString();
        description = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(email);
        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(avatar);
        parcel.writeString(titles);
        parcel.writeString(description);
    }

    public static final Creator<Colaboradores> CREATOR = new Creator<Colaboradores>() {
        @Override
        public Colaboradores createFromParcel(Parcel parcel) {
            return new Colaboradores(parcel);
        }

        @Override
        public Colaboradores[] newArray(int size) {
            return new Colaboradores[size];
        }
    };
}

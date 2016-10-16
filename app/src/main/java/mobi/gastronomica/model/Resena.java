package mobi.gastronomica.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Resena implements Parcelable {
    int id;
    String re_title;
    String re_content;
    String re_start;
    int restaurant_id;
    String created_at;
    String user_name;
    String user_last_name;
    String profile_user;

    public Resena(int id, String re_title, String re_content, String re_start, int restaurant_id, String created_at, String user_name, String user_last_name, String profile_user) {
        this.id = id;
        this.re_title = re_title;
        this.re_content = re_content;
        this.re_start = re_start;
        this.restaurant_id = restaurant_id;
        this.created_at = created_at;
        this.user_name = user_name;
        this.user_last_name = user_last_name;
        this.profile_user = profile_user;
    }

    public Resena(Parcel parcel) {
        id = parcel.readInt();
        re_title = parcel.readString();
        re_content = parcel.readString();
        re_start = parcel.readString();
        restaurant_id = parcel.readInt();
        created_at = parcel.readString();
        user_name = parcel.readString();
        user_last_name = parcel.readString();
        profile_user = parcel.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int re_title) {
        this.id = id;
    }

    public String getReIitle() {
        return re_title;
    }

    public void setReIitle(String re_title) {
        this.re_title = re_title;
    }

    public String getReContent() {
        return re_content;
    }

    public void setReContent(String re_content) {
        this.re_content = re_content;
    }

    public String getReStart() {
        return re_start;
    }

    public void setReStart(String re_start) {
        this.re_content = re_start;
    }

    public int getRestaurantId() {
        return restaurant_id;
    }

    public void setRestaurantId(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getUserLastName() {
        return user_last_name;
    }

    public void setUserLastName(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getProfileUser() {
        return profile_user;
    }

    public void setProfileUser(String profile_user) {
        this.profile_user = profile_user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(re_title);
        parcel.writeString(re_content);
        parcel.writeString(re_start);
        parcel.writeInt(restaurant_id);
        parcel.writeString(created_at);
        parcel.writeString(user_name);
        parcel.writeString(user_last_name);
        parcel.writeString(profile_user);
    }

    public static final Creator<Resena> CREATOR = new Creator<Resena>() {
        @Override
        public Resena createFromParcel(Parcel parcel) {
            return new Resena(parcel);
        }

        @Override
        public Resena[] newArray(int size) {
            return new Resena[size];
        }
    };
}
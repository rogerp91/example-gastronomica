package mobi.gastronomica.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EventosRecientes implements Parcelable {

	// fields
	public int id;
	private String title;
	private String color;
	private String category;
	private String fecha;
	private String place;
	private String image;
	private int price;
	private String description;
	private int confirmation;
	private String org_name;
	private String org_descrip;

	public EventosRecientes(int id, String title, String color, String category, String fecha, String place, String image, int price, String description, int confirmation, String org_name, String org_descrip) {

		this.id = id;
		this.title = title;
		this.color = color;
		this.category = category;
		this.fecha = fecha;
		this.place = place;
		this.image = image;
		this.price = price;
		this.description = description;
		this.confirmation = confirmation;
		this.org_name = org_name;
		this.org_descrip = org_descrip;
	}

	public EventosRecientes(String title, String image) {
		this.title = title;
		this.image = image;
	}

	public EventosRecientes(Parcel parcel) {
		id = parcel.readInt();
		title = parcel.readString();
		color = parcel.readString();
		category = parcel.readString();
		fecha = parcel.readString();
		place = parcel.readString();
		image = parcel.readString();
		price = parcel.readInt();
		description = parcel.readString();
		confirmation = parcel.readInt();
		org_name = parcel.readString();
		org_descrip = parcel.readString();
	}

	// setter and getter
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getFecha() {
		return this.fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getImagen() {
		return this.image;
	}

	public void setImagen(String image) {
		this.image = image;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return this.description;
	}

	public void setdescription(String description) {
		this.description = description;
	}

	public int getConfirmation() {
		return this.confirmation;
	}

	public void setConfirmation(int confirmation) {
		this.confirmation = confirmation;
	}

	public String getOrgName() {
		return this.org_name;
	}

	public void setOrgName(String org_name) {
		this.org_name = org_name;
	}

	public String getOrgDescrip() {
		return this.org_descrip;
	}

	public void setOrgDescrip(String org_descrip) {
		this.org_descrip = org_descrip;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(color);
		dest.writeString(category);
		dest.writeString(fecha);
		dest.writeString(place);
		dest.writeString(image);
		dest.writeInt(price);
		dest.writeString(description);
		dest.writeInt(confirmation);
		dest.writeString(org_name);
		dest.writeString(org_descrip);
	}

	public static final Creator<EventosRecientes> CREATOR = new Creator<EventosRecientes>() {
		@Override
		public EventosRecientes createFromParcel(Parcel parcel) {
			return new EventosRecientes(parcel);
		}

		@Override
		public EventosRecientes[] newArray(int size) {
			return new EventosRecientes[size];
		}
	};

}

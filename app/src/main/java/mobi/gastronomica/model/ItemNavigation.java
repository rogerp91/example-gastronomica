package mobi.gastronomica.model;

public class ItemNavigation {
    String mTitle;
    int mIcon;

    public ItemNavigation(String title, int icon) {
        this.mTitle = title;
        this.mIcon = icon;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getIcon() {
        return this.mIcon;
    }

    public void setIcon(int mIcon) {
        this.mIcon = mIcon;
    }

}

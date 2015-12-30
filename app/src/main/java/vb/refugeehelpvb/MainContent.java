package vb.refugeehelpvb;

public class MainContent {

    private String category;
    private int photoId;
    private int baseLineColor;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBaselineColor(int c){
        this.baseLineColor = c;
    }

    public int getBaseLineColor(){
        return baseLineColor;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}


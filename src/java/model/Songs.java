package model;
// Generated 07-Nov-2014 18:54:07 by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Songs generated by hbm2java
 */
public class Songs  implements java.io.Serializable {


     private Integer songId;
     private String name;
     private String artist;
     private Double latitude;
     private Double longitude;
     private String genere;
     private Date uploadedOn;
     private String path;
     private Integer uploadedBy;
     private Integer fav_count;
     boolean isFav;
    public Songs() {
    }

	
    public Songs(String path) {
        this.path = path;
    }
    public Songs(String name, String artist, Double latitude, Double longitude, String genere, Date uploadedOn, String path, Integer uploadedBy, Integer favCount) {
       this.name = name;
       this.artist = artist;
       this.latitude = latitude;
       this.longitude = longitude;
       this.genere = genere;
       this.uploadedOn = uploadedOn;
       this.path = path;
       this.uploadedBy = uploadedBy;
       this.fav_count = favCount;
    }
   
    public Integer getSongId() {
        return this.songId;
    }
    
    public void setSongId(Integer songId) {
        this.songId = songId;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getArtist() {
        return this.artist;
    }
    
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
     public Double getLongitude() {
        return this.longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public String getGenere() {
        return this.genere;
    }
    
    public void setGenere(String genere) {
        this.genere = genere;
    }
    public Date getUploadedOn() {
        return this.uploadedOn;
    }
    
    public void setUploadedOn(Date uploadedOn) {
        this.uploadedOn = uploadedOn;
    }
    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }

    public Integer getUploadedBy() {
        return this.uploadedBy;
    }
    
    public void setUploadedBy(Integer uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Integer getFav_count() {
        return this.fav_count;
    }
    
    public void setFav_count(Integer fav_count) {
        this.fav_count = fav_count;
    }
    
    public boolean getIsFav() {
        return this.isFav;
    }
    
    public void setIsFav(boolean isFav) {
        this.isFav = isFav;
    }
}



package videira.ifc.edu.br.georent.models;

import java.util.Date;

/**
 * Created by iuryk on 19/09/2016.
 */
public class Location {

    /**
     * Atributos
     */
    private Integer idLocation;
    private Double latitude;
    private Double longitude;
    private City idCity;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Construtor
     * @param idLocation
     * @param latitude
     * @param longitude
     * @param idCity
     * @param createdAt
     * @param updatedAt
     */
    public Location(Integer idLocation, Double latitude, Double longitude, City idCity, Date createdAt, Date updatedAt) {
        this.idLocation = idLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.idCity = idCity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Construtor
     */
    public Location(){

    }

    /**
     * Atributos
     */

    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public City getIdCity() {
        return idCity;
    }

    public void setIdCity(City idCity) {
        this.idCity = idCity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}

package videira.ifc.edu.br.georent.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by iuryk on 28/08/2016.
 */

public class User {

    /**
     * Constantes
     */
    public static Integer LOCATOR = 1;
    public static Integer OCCUPIER = 0;

    /**
     * Atributos
     */
    private java.lang.Integer idUser;

    private String name;

    private Date birthDate;

    private String email;

    private String phone;

    private String password;

    private Float credits;

    private Integer type;

    @SerializedName("distance")
    private Integer distance;

    @SerializedName("preference")
    private Preference idPreference;

    @SerializedName("city")
    private City idCity;

    @SerializedName("profileImage")
    private UserImage profileImage;

    private Date createdAt;

    private Date updatedAt;

    @SerializedName("user_images")
    private List<UserImage> userImages;
    /**
     * Construtor
     *
     * @param idUser
     * @param name
     * @param birthDate
     * @param email
     * @param phone
     * @param password
     * @param credits
     * @param type
     * @param distance
     * @param idPreference
     * @param idCity
     * @param createdAt
     * @param updatedAt
     */
    public User(java.lang.Integer idUser, String name, Date birthDate, String email, String phone, String password,
                Float credits, Integer type, java.lang.Integer distance, Preference idPreference, City idCity, Date createdAt, Date updatedAt) {
        this.idUser = idUser;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.credits = credits;
        this.type = type;
        this.distance = distance;
        this.idPreference = idPreference;
        this.idCity = idCity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Construtor
     */
    public User() {

    }

    /**
     * Getters e Setters
     */

    public java.lang.Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(java.lang.Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Float getCredits() {
        return credits;
    }

    public void setCredits(Float credits) {
        this.credits = credits;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public java.lang.Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Preference getIdPreference() {
        return idPreference;
    }

    public void setIdPreference(Preference idPreference) {
        this.idPreference = idPreference;
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

    public UserImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(UserImage profileImage) {
        this.profileImage = profileImage;
    }

    public List<UserImage> getUserImages() {
        return userImages;
    }

    public void setUserImages(List<UserImage> userImages) {
        this.userImages = userImages;
    }
}

package videira.ifc.edu.br.georent.models;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by iuryk on 28/08/2016.
 */

public class User {

    /**
     * Enumerador do tipo
     */
    public static enum Type{
        LOCATOR,
        OCCUPIER;
    }

    /**
     * Atributos
     */
    private Integer idUser;
    private String name;
    private Date birthDate;
    private String email;
    private String phone;
    private String password;
    private Float credits;
    private Type type;
    private Integer distance;
    private Preference idPreference;
    private City idCity;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Construtor
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
    public User(Integer idUser, String name, Date birthDate, String email, String phone, String password,
                Float credits, Type type, Integer distance, Preference idPreference, City idCity, Date createdAt, Date updatedAt) {
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
    public User(){

    }

    /**
     * Getters e Setters
     */

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getDistance() {
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
}

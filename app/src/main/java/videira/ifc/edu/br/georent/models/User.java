package videira.ifc.edu.br.georent.models;

import java.util.Date;

/**
 * Created by iuryk on 28/08/2016.
 */
public class User {

    /**
     * Atributos
     */
    private Integer idUser;
    private String name;
    private Date birthDate;
    private String email;
    private String phone;
    private String password;
    private Integer credits;
    private Integer photo;

    /**
     * Construtores
     */
    public User() {
    }

    public User(Integer idUser, String name, Date birthDate, String email, String phone,
                String password, Integer credits, Integer photo) {
        this.idUser = idUser;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.credits = credits;
        this.photo = photo;
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

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }
}

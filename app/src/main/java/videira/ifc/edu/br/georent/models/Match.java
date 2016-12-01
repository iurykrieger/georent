package videira.ifc.edu.br.georent.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by iuryk on 19/09/2016.
 */
public class Match {

    /**
     * Atributos
     */
    private Integer idMatch;

    @SerializedName("residence")
    private Residence idResidence;

    @SerializedName("user")
    private User idUser;

    private Integer like;

    private Date dateTime;

    private Date createdAt;

    private Date updatedAt;

    /**
     * Construtor
     *
     * @param idMatch
     * @param idResidence
     * @param idUser
     * @param like
     * @param dateTime
     * @param createdAt
     * @param updatedAt
     */
    public Match(Integer idMatch, Residence idResidence, User idUser, Integer like, Date dateTime, Date createdAt, Date updatedAt) {
        this.idMatch = idMatch;
        this.idResidence = idResidence;
        this.idUser = idUser;
        this.like = like;
        this.dateTime = dateTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Construtor
     */
    public Match() {

    }

    /**
     * Getters e Setters
     */
    public Integer getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Integer idMatch) {
        this.idMatch = idMatch;
    }

    public Residence getIdResidence() {
        return idResidence;
    }

    public void setIdResidence(Residence idResidence) {
        this.idResidence = idResidence;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

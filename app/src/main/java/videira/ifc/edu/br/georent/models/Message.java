package videira.ifc.edu.br.georent.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by iuryk on 19/09/2016.
 */
public class Message {

    /**
     * Atributos
     */

    private Integer idMessage;

    @SerializedName("from")
    private User from;

    @SerializedName("to")
    private User to;

    private String message;

    private Date dateTime;

    private Integer resource;

    private String path;

    private Date createdAt;

    private Date updatedAt;

    /**
     * Construtor
     *
     * @param idMessage
     * @param from
     * @param to
     * @param message
     * @param dateTime
     * @param resource
     * @param path
     * @param createdAt
     * @param updatedAt
     */
    public Message(Integer idMessage, User from, User to, String message, Date dateTime, Integer resource, String path, Date createdAt, Date updatedAt) {
        this.idMessage = idMessage;
        this.from = from;
        this.to = to;
        this.message = message;
        this.dateTime = dateTime;
        this.resource = resource;
        this.path = path;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Construtor
     */
    public Message() {

    }

    /**
     * Getters e Setters
     */
    public Integer getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

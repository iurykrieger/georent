package videira.ifc.edu.br.georent.models;

import java.util.Date;

/**
 * Created by iuryk on 19/09/2016.
 */
public class UserImage {

    /**
     * Atributos
     */
    private Integer idUserImage;
    private User idUser;
    private String path;
    private Integer resource;
    private Integer order;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Construtor
     *
     * @param idUserImage
     * @param idUser
     * @param path
     * @param resource
     * @param order
     * @param createdAt
     * @param updatedAt
     */
    public UserImage(Integer idUserImage, User idUser, String path, Integer resource, Integer order, Date createdAt, Date updatedAt) {
        this.idUserImage = idUserImage;
        this.idUser = idUser;
        this.path = path;
        this.resource = resource;
        this.order = order;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Construtor
     */
    public UserImage() {

    }

    /**
     * Getters e Setters
     */

    public Integer getIdUserImage() {
        return idUserImage;
    }

    public void setIdUserImage(Integer idUserImage) {
        this.idUserImage = idUserImage;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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
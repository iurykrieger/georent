package videira.ifc.edu.br.georent.models;

import java.util.Date;

/**
 * Created by iuryk on 19/09/2016.
 */
public class ResidenceImage {

    /**
     * Atributos
     */
    private Integer idResidenceImage;
    private Residence idResidence;
    private String path;
    private Integer resource;
    private Integer order;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Construtor
     *
     * @param idResidenceImage
     * @param idResidence
     * @param path
     * @param resource
     * @param order
     * @param createdAt
     * @param updatedAt
     */
    public ResidenceImage(Integer idResidenceImage, Residence idResidence, String path, Integer resource, Integer order, Date createdAt, Date updatedAt) {
        this.idResidenceImage = idResidenceImage;
        this.idResidence = idResidence;
        this.path = path;
        this.resource = resource;
        this.order = order;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Construtor
     */
    public ResidenceImage() {

    }

    /**
     * Getters e Setters
     */

    public Integer getIdResidenceImage() {
        return idResidenceImage;
    }

    public void setIdResidenceImage(Integer idResidenceImage) {
        this.idResidenceImage = idResidenceImage;
    }

    public Residence getIdResidence() {
        return idResidence;
    }

    public void setIdResidence(Residence idResidence) {
        this.idResidence = idResidence;
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

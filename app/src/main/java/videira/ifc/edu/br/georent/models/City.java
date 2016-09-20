package videira.ifc.edu.br.georent.models;

import java.util.Date;

/**
 * Created by iuryk on 19/09/2016.
 */
public class City {

    /**
     * Atributos
     */
    private Integer idCity;
    private String name;
    private String uf;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Construtor
     * @param idCity
     * @param name
     * @param uf
     * @param createdAt
     * @param updatedAt
     */
    public City(Integer idCity, String name, String uf, Date createdAt, Date updatedAt) {
        this.idCity = idCity;
        this.name = name;
        this.uf = uf;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Construtor
     */
    public City() {

    }

    /**
     * Getters e Setters
     */

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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

package videira.ifc.edu.br.georent.models;

import java.util.Date;

/**
 * Created by iuryk on 19/09/2016.
 */
public class Preference {

    /**
     * Atributos
     */
    private Integer idPreference;
    private Boolean sponsor; //FIADOR
    private Integer room;
    private Integer bathroom;
    private Integer vacancy;
    private Boolean laundry;
    private Float income;
    private Boolean condominium;
    private Boolean child;
    private Integer stay;
    private Boolean pet;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Construtor
     *
     * @param idPreference
     * @param sponsor
     * @param room
     * @param bathroom
     * @param vacancy
     * @param laundry
     * @param income
     * @param condominium
     * @param child
     * @param stay
     * @param pet
     * @param createdAt
     * @param updatedAt
     */
    public Preference(Integer idPreference, Boolean sponsor, Integer room, Integer bathroom, Integer vacancy,
                      Boolean laundry, Float income, Boolean condominium, Boolean child, Integer stay, Boolean pet, Date createdAt, Date updatedAt) {
        this.idPreference = idPreference;
        this.sponsor = sponsor;
        this.room = room;
        this.bathroom = bathroom;
        this.vacancy = vacancy;
        this.laundry = laundry;
        this.income = income;
        this.condominium = condominium;
        this.child = child;
        this.stay = stay;
        this.pet = pet;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Construtor
     */
    public Preference() {

    }

    /**
     * Getters e Setters
     */

    public Integer getIdPreference() {
        return idPreference;
    }

    public void setIdPreference(Integer idPreference) {
        this.idPreference = idPreference;
    }

    public Boolean getSponsor() {
        return sponsor;
    }

    public void setSponsor(Boolean sponsor) {
        this.sponsor = sponsor;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getBathroom() {
        return bathroom;
    }

    public void setBathroom(Integer bathroom) {
        this.bathroom = bathroom;
    }

    public Integer getVacancy() {
        return vacancy;
    }

    public void setVacancy(Integer vacancy) {
        this.vacancy = vacancy;
    }

    public Boolean getLaundry() {
        return laundry;
    }

    public void setLaundry(Boolean laundry) {
        this.laundry = laundry;
    }

    public Float getIncome() {
        return income;
    }

    public void setIncome(Float income) {
        this.income = income;
    }

    public Boolean getCondominium() {
        return condominium;
    }

    public void setCondominium(Boolean condominium) {
        this.condominium = condominium;
    }

    public Boolean getChild() {
        return child;
    }

    public void setChild(Boolean child) {
        this.child = child;
    }

    public Integer getStay() {
        return stay;
    }

    public void setStay(Integer stay) {
        this.stay = stay;
    }

    public Boolean getPet() {
        return pet;
    }

    public void setPet(Boolean pet) {
        this.pet = pet;
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

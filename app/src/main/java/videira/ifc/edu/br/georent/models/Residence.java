package videira.ifc.edu.br.georent.models;

/**
 * Created by iuryk on 19/09/2016.
 */
public class Residence {

    /**
     * Atributos
     */
    private Integer idResidence;
    private Location idLocation;
    private User idUser;
    private Preference idPreference;
    private String title;
    private String description;
    private String address;
    private String observation;
    private Float rent;

    /**
     * Construtor
     *
     * @param idResidence
     * @param idLocation
     * @param idUser
     * @param idPreference
     * @param title
     * @param description
     * @param observation
     * @param rent
     */
    public Residence(Integer idResidence, Location idLocation, User idUser, Preference idPreference, String title, String description, String address, String observation, Float rent) {
        this.idResidence = idResidence;
        this.idLocation = idLocation;
        this.idUser = idUser;
        this.idPreference = idPreference;
        this.title = title;
        this.description = description;
        this.address = address;
        this.observation = observation;
        this.rent = rent;
    }

    /**
     * Construtor
     */
    public Residence() {

    }

    /**
     * Getters e Setters
     */
    public Integer getIdResidence() {
        return idResidence;
    }

    public void setIdResidence(Integer id) {
        this.idResidence = id;
    }

    public Location getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Location idLocation) {
        this.idLocation = idLocation;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Preference getIdPreference() {
        return idPreference;
    }

    public void setIdPreference(Preference idPreference) {
        this.idPreference = idPreference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Float getRent() {
        return rent;
    }

    public void setRent(Float rent) {
        this.rent = rent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

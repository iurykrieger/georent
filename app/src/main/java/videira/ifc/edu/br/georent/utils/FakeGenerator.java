package videira.ifc.edu.br.georent.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import videira.ifc.edu.br.georent.models.City;
import videira.ifc.edu.br.georent.models.Location;
import videira.ifc.edu.br.georent.models.Preference;
import videira.ifc.edu.br.georent.models.Residence;
import videira.ifc.edu.br.georent.models.ResidenceImage;

/**
 * Created by iuryk on 23/10/2016.
 */

public class FakeGenerator {

    public static String[] RES = {"http://orig01.deviantart.net/c8ff/f/2010/111/4/d/bintaro_residence_by_ivanth.jpg",
            "https://www.residence.ualberta.ca/sites/default/files/graduate-residence-side.jpg",
            "https://www.residence.ualberta.ca/sites/default/files/graduate-residence-angle.jpg",
            "http://o.homedsgn.com/wp-content/uploads/2012/06/Lima-Residence-02.jpg"};

    private static FakeGenerator instance;
    private static List<ResidenceImage> residenceImages;
    private static List<Residence> residences;

    public static FakeGenerator getInstance() {
        if (instance == null) {
            instance = new FakeGenerator();
        }
        return instance;
    }

    public FakeGenerator() {
        residences = new ArrayList<>();
        residenceImages = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            City c = new City();
            c.setCreatedAt(new Date());
            c.setName("Fraiburgo");
            c.setUf("SC");
            c.setUpdatedAt(new Date());

            Location l = new Location();
            l.setIdCity(c);
            l.setIdLocation(new Random().nextInt());
            l.setLatitude(new Random().nextDouble());
            l.setLongitude(new Random().nextDouble());

            Preference p = new Preference();
            p.setUpdatedAt(new Date());
            p.setCreatedAt(new Date());
            p.setBathroom(new Random().nextInt(4));
            p.setChild(new Random().nextBoolean());
            p.setCondominium(new Random().nextBoolean());
            p.setIdPreference(new Random().nextInt());
            p.setIncome(new Random().nextFloat());
            p.setLaundry(new Random().nextBoolean());
            p.setPet(new Random().nextBoolean());
            p.setRoom(new Random().nextInt(8));
            p.setSponsor(new Random().nextBoolean());
            p.setStay(new Random().nextInt(24));
            p.setVacancy(new Random().nextInt(6));

            Residence r = new Residence();
            r.setAddress("Rua Nicolau Cavon, nº " + new Random().nextInt());
            r.setDescription("Descrição aleatória da residência nº " + new Random().nextInt());
            r.setIdResidence(new Random().nextInt());
            r.setObservation("Observação aleatória nº " + new Random().nextInt());
            r.setRent(new Random().nextFloat());
            r.setTitle("Residência bem localizada");
            r.setIdLocation(l);
            r.setIdPreference(p);

            ResidenceImage ri = new ResidenceImage();
            ri.setIdResidenceImage(new Random().nextInt());
            ri.setCreatedAt(new Date());
            ri.setOrder(i);
            ri.setUpdatedAt(new Date());
            ri.setResidence(r);
            ri.setPath(RES[i % 4]);

            residenceImages.add(ri);

            List<ResidenceImage> ris = new ArrayList<>();
            ris.add(ri);
            r.setResidenceImages(ris);

            residences.add(r);
        }
    }

    public static Residence getResidence(int idResidence) {
        for (Residence r : residences) {
            if (r.getIdResidence() == idResidence) {
                return r;
            }
        }
        return null;
    }

    public static List<ResidenceImage> getResidenceImages(int end) {
        return residenceImages.subList(0, end);
    }
}

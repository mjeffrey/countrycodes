package be.countries;

import com.google.common.collect.Maps;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class CountryMapper {
    private final Map<String, Country> isoAlpha2;
    private final Map<String, Country> isoNumeric3;
    private final Country belgium;


    public static CountryMapper fromStream(InputStream inputStream) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlCountryList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            XmlCountryList xmlCountryList = (XmlCountryList) unmarshaller.unmarshal(inputStream);
            return new CountryMapper(xmlCountryList.getCountryList());
        } catch (JAXBException e) {
            throw new IllegalStateException("Could not read countries", e);
        }
    }

    private CountryMapper(Collection<? extends Country> countries) {
        Map<String, Country> mapAlpha2 = Maps.newHashMapWithExpectedSize(countries.size());
        Map<String, Country> mapNumeric3 = Maps.newHashMapWithExpectedSize(countries.size());
        countries.forEach(c -> {
            mapAlpha2.put(c.getIsoAlpha2(), c);
            mapNumeric3.put(c.getIsoNumeric3(), c);
        });

        isoAlpha2 = Collections.unmodifiableMap(mapAlpha2);
        isoNumeric3 = Collections.unmodifiableMap(mapNumeric3);
        belgium = fromIsoAlpha2("BE").orElseThrow(() -> new IllegalArgumentException("Could not find Belgium in the Country Map " + isoAlpha2.toString() ));
    }


    public Optional<Country> fromIsoAlpha2(String code )  {
        return Optional.ofNullable(isoAlpha2.get(code));
    }

    public Optional<Country> fromIsoNumeric3(String code) {
        return Optional.ofNullable(isoNumeric3.get(code));
    }

    public String alphaToNumericDefaultBelgium(String code) {
        return Optional.ofNullable(isoAlpha2.get(code)).orElse(belgium).getIsoNumeric3();
    }

    public String numericToAlphaDefaultBelgium(String code) {
        return Optional.ofNullable(isoNumeric3.get(code)).orElse(belgium).getIsoAlpha2();
    }

}

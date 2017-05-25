package be.countries;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "geonames")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class XmlCountryList {

    @XmlElement(name = "country")
    private List<XmlCountry> countryList;

    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    @ToString
    private static class XmlCountry implements Country {
        @XmlElement(name = "countryCode")
        private String isoAlpha2;

        @XmlElement(name = "isoNumeric")
        private String isoNumeric3;

        @XmlElement(name = "isoAlpha3")
        private String isoAlpha3;

        @XmlElement(name = "countryName")
        private String name;

    }

}

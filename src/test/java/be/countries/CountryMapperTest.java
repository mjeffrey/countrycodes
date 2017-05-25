package be.countries;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;


public class CountryMapperTest {

    public static final String COUNTRIES_XML = "countries.xml";
    private CountryMapper countryMapper;

    @Before
    public void setUp() throws Exception {
        InputStream inputStream = XmlCountryList.class.getResourceAsStream(COUNTRIES_XML);
        assertThat( inputStream).describedAs("Cannot open resource " + COUNTRIES_XML).isNotNull();
        countryMapper = CountryMapper.fromStream(inputStream);

    }

    @Test
    public void testFindCountryAlpha2() throws Exception {
        assertThat(countryMapper.fromIsoAlpha2("BE")).map(Country::getIsoNumeric3).contains("056");
        assertThat(countryMapper.fromIsoAlpha2("NONE")).isNotPresent();
        assertThat(countryMapper.fromIsoAlpha2(null)).isNotPresent();
    }
    @Test
    public void testFindCountryNumeric3() throws Exception {
        assertThat(countryMapper.fromIsoNumeric3("056")).map(Country::getIsoAlpha2).contains("BE");
        assertThat(countryMapper.fromIsoNumeric3("NONE")).isNotPresent();
        assertThat(countryMapper.fromIsoNumeric3(null)).isNotPresent();
    }
    @Test
    public void testDefaultBelgiumAlpha2() throws Exception {
        assertThat(countryMapper.alphaToNumericDefaultBelgium("NZ")).isEqualTo("554");
        assertThat(countryMapper.alphaToNumericDefaultBelgium("NONE")).isEqualTo("056");
        assertThat(countryMapper.alphaToNumericDefaultBelgium(null)).isEqualTo("056");
    }
    @Test
    public void testDefaultBelgiumNumeric() throws Exception {
        assertThat(countryMapper.numericToAlphaDefaultBelgium("554")).isEqualTo("NZ");
        assertThat(countryMapper.numericToAlphaDefaultBelgium("NONE")).isEqualTo("BE");
        assertThat(countryMapper.numericToAlphaDefaultBelgium(null)).isEqualTo("BE");
    }

}

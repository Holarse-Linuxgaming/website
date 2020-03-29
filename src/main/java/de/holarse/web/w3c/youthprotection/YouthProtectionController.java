package de.holarse.web.w3c.youthprotection;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.holarse.backend.age.AgeBlockBasic;
import de.holarse.backend.age.AgeBlockCountry;
import de.holarse.backend.age.AgeBlockLabelType;
import de.holarse.backend.age.AgeBlockLabelTypeDefinition;
import de.holarse.backend.age.AgeDeclaration;
import de.holarse.backend.age.Label;
import de.holarse.backend.age.LabelTypeXmlFile;
import de.holarse.backend.miracle.Country;
import de.holarse.backend.miracle.Issuer;
import de.holarse.backend.miracle.Rating;
import de.holarse.backend.miracle.Scope;
import de.holarse.backend.miracle.ScopeUrl;

/**
 * Definiert Dateien, die Jugendschutzprogramme und Proxys auslesen können
 * und somit die Seiten clientseitig sperren können.
 */
@Controller
public class YouthProtectionController {

    @GetMapping(value = {"/age.xml", "/age-de.xml"}, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody String ageXml() throws Exception {
        AgeDeclaration ageDeclaration = new AgeDeclaration();
        
        AgeBlockBasic basic = new AgeBlockBasic();
        basic.setAgeIssuer("Bernd Ritter");
        basic.setLastChange("2016-02-15");
        basic.setCountry("de");
        basic.setVersion("2.0");
        basic.setRevisitAfter("7days");
        ageDeclaration.setBasic(basic);

        AgeBlockCountry country = new AgeBlockCountry();
        country.setCountryDefault("");
        ageDeclaration.setCountry(country);

        AgeBlockLabelType labelType = new AgeBlockLabelType();
        labelType.setHttpHeader("false");
        labelType.setHtmlMeta("false");
        labelType.setPhraseLabel("false");
        labelType.setAgeSubmit("false");
        labelType.setLabelZ("false");
        labelType.setAlternate("");
        labelType.setXmlFile("true");
        labelType.setDefaultAge("18");
        ageDeclaration.setLabelType(labelType);

        AgeBlockLabelTypeDefinition labelTypeDefinition = new AgeBlockLabelTypeDefinition();
        LabelTypeXmlFile xLabelTypeXmlFile = new LabelTypeXmlFile();
        de.holarse.backend.age.Label label = new de.holarse.backend.age.Label();
        label.setClazz("website");
        label.setAge("18");
        label.getScopes().add("holarse-linuxgaming.de/*");
        label.getScopes().add("*.holarse-linuxgaming.de/*");
        label.getScopes().add("*.holarse.de/*");
        xLabelTypeXmlFile.setLabel(label);
        labelTypeDefinition.setXmlfile(xLabelTypeXmlFile);

        ageDeclaration.setLabelTypeDefinition(labelTypeDefinition);

        final XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return xmlMapper.writeValueAsString(ageDeclaration);
    }

    @GetMapping("/miracle.xml")
    public @ResponseBody String miracle() throws Exception {
        de.holarse.backend.miracle.Label label = new de.holarse.backend.miracle.Label();

        de.holarse.backend.miracle.AgeDeclaration ageDeclaration = new de.holarse.backend.miracle.AgeDeclaration();
        
        Issuer issuer = new Issuer();
        issuer.setAgeIssuer("Bernd Ritter");
        issuer.setLastChange("2020-03-29");
        Country country = new Country();
        country.setCountryCode("all");
        issuer.setCountry(country);
        ageDeclaration.setIssuer(issuer);

        Scope scope = new Scope();
        List<ScopeUrl> scopeUrls = new ArrayList<>();
        scopeUrls.add(new ScopeUrl("holarse-linuxgaming.de/*", "web-url"));
        scopeUrls.add(new ScopeUrl("*.holarse-linuxgaming.de/*", "web-url"));
        scopeUrls.add(new ScopeUrl("*.holarse.de/*", "web-url"));        
        scope.setScopeUrls(scopeUrls);
        ageDeclaration.setScope(scope);

        Rating rating = new Rating();
        rating.setAge("18");
        ageDeclaration.setRating(rating);
        
        label.setAgeDeclaration(ageDeclaration);

        final XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return xmlMapper.writeValueAsString(label);        
    }

}
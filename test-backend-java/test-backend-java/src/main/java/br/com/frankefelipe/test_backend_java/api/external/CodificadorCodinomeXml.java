package br.com.frankefelipe.test_backend_java.api.external;

import br.com.frankefelipe.test_backend_java.api.exception.ProcessarXmlException;
import java.io.StringReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class CodificadorCodinomeXml {

    LigaDaJustica gerarLigaDaJusticaDeString(String xml) {
        LigaDaJustica ligaDaJustica = new LigaDaJustica();
        travessarXml(xml, ligaDaJustica);
        return ligaDaJustica;
    }

    private void travessarXml(String xml, LigaDaJustica ligaDaJustica) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(xml));
            while (reader.hasNext()) {
                int evento = reader.next();
                if (evento == XMLStreamReader.START_ELEMENT) {
                    if (reader.getLocalName().equals("codinome")) {
                        ligaDaJustica.getCodinomes().add(new Codinome(reader.getElementText()));
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new ProcessarXmlException("Erro ao processar XML");
        }
    }

}

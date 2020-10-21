import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Function;

public class ObfuscatorXMLHandler extends DefaultHandler {

    private String outputFile;

    private IndentingXMLStreamWriter xsw;

    private Function<String, String> secureFunction;

    public ObfuscatorXMLHandler(final Function<String, String> secureFunction,
                                final String outputFile) {
        this.outputFile = outputFile;
        this.secureFunction = secureFunction;
    }

    @Override
    public void startDocument() {
        try {
            xsw = new IndentingXMLStreamWriter(
                    XMLOutputFactory.newInstance()
                            .createXMLStreamWriter(new FileWriter(outputFile))
            );
            xsw.setIndentStep("    ");
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) {
        try {
            xsw.writeStartElement(qName);

            if (attributes.getLength() > 0) {
                for (int i = 0; i < attributes.getLength(); ++i)
                    xsw.writeAttribute(attributes.getLocalName(i),
                            secureFunction.apply(attributes.getValue(i)));
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String value = new String(ch, start, length).trim();

        if (!value.equals("")) {
            try {
                xsw.writeCharacters(secureFunction.apply(value));
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        try {
            xsw.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endDocument() {
        try {
            xsw.writeEndDocument();
            xsw.flush();
            xsw.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}

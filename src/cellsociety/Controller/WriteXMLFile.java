package cellsociety.Controller;

import cellsociety.Main;
import cellsociety.Model.Grid;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class for creating XML files based off of simulation configuration.
 *
 * Class based mainly on WriteXMLFile.java by mkyong
 * https://mkyong.com/java/how-to-create-xml-file-in-java-dom/
 */
public class WriteXMLFile {

    private Simulation mySim;
    private Grid myGrid;
    private static final ResourceBundle RESOURCES = Main.myResources;

    //FIXME: ADD COMMENTS
    public WriteXMLFile(Simulation sim, Grid grid) {
            mySim = sim;
            myGrid = grid;
    }

    public String writeSimulationXML() {
        String fileAddress = "C:\\file.xml";

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLException(RESOURCES.getString("XML_WRITE_CREATE"));
        }

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(RESOURCES.getString("Data"));
        Attr attr = doc.createAttribute(RESOURCES.getString("Type"));
        attr.setValue(mySim.getType().toString());
        rootElement.setAttributeNode(attr);
        doc.appendChild(rootElement);

        // staff elements
        Map<String, Integer> valueMap = mySim.getValueMap();
        for (String field: valueMap.keySet()) {
            Element e = doc.createElement(field);
            e.appendChild(doc.createTextNode(valueMap.get(field).toString()));
            rootElement.appendChild(e);
        }

        //NEED TO ADD GRID AND ROWS

        Element staff = doc.createElement("Staff");

        rootElement.appendChild(staff);

        // set attribute to staff element
        attr = doc.createAttribute("id");
        attr.setValue("1");
        staff.setAttributeNode(attr);


        // firstname elements
        Element firstname = doc.createElement("firstname");
        firstname.appendChild(doc.createTextNode("yong"));
        staff.appendChild(firstname);

        // lastname elements
        Element lastname = doc.createElement("lastname");
        lastname.appendChild(doc.createTextNode("mook kim"));
        staff.appendChild(lastname);

        // nickname elements
        Element nickname = doc.createElement("nickname");
        nickname.appendChild(doc.createTextNode("mkyong"));
        staff.appendChild(nickname);

        // salary elements
        Element salary = doc.createElement("salary");
        salary.appendChild(doc.createTextNode("100000"));
        staff.appendChild(salary);

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File(fileAddress));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("File saved!");
        return fileAddress;
        //} catch (
        //        ParserConfigurationException pce) {
        //    pce.printStackTrace();
        //} catch (TransformerException tfe) {
        //    tfe.printStackTrace();
        //}
        //return "";
    }
}

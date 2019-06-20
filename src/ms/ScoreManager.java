package ms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import enums.EMode;
//////////////////////////////////////////////////////////////////////////////////////////////
public class ScoreManager {
	
	
	private static final int MAX_ENTRIES = 10;
	
	//////////////////////////////////////////////////////////////////////////////////////////
	public static ScoreRecord[] getScore(EMode mode) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        String file = "src\\ms\\score_";
        
        if(mode == EMode.EASY)   {file += "easy.xml"  ;} else
        if(mode == EMode.NORMAL) {file += "normal.xml";} else
        if(mode == EMode.HARD)   {file += "hard.xml"  ;}
        
        Document document = builder.parse(new File(file));
        
        ScoreRecord[] scoreRecords = new ScoreRecord[MAX_ENTRIES];
        
        for(int i = 0; i < MAX_ENTRIES; i++) {
        	scoreRecords[i] = new ScoreRecord(" ", 9999);
        }
        
        
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                 Element elem = (Element) node;

                 String name  = elem.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
                 Integer time = Integer.parseInt(elem.getElementsByTagName("time").item(0).getChildNodes().item(0).getNodeValue());
                 
                 scoreRecords[i] = new ScoreRecord(name, time);
                 
            }
        }     
        
        return scoreRecords;
        
	}
	////////////////////////////////////////////////////////////////////////////////////////
	public static void writeScore(EMode mode, ScoreRecord newScoreRecord) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		
		ScoreRecord[] scoreRecords = insertRecord(getScore(mode),newScoreRecord);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        Document document = builder.newDocument();
        
        Element root = document.createElement("ScoreRecords");
        document.appendChild(root);

        
        for(int i  = 0 ; i < scoreRecords.length; i++) {
        	
        	Element scoreRecord = document.createElement("ScoreRecord");
            root.appendChild(scoreRecord);
            
            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(scoreRecords[i].getName()));
            scoreRecord.appendChild(name);
            
            Element time = document.createElement("time");
            time.appendChild(document.createTextNode(scoreRecords[i].getTime()+""));
            scoreRecord.appendChild(time);
        	
        }
           
        String file = "src\\ms\\score_";
        
        if(mode == EMode.EASY)   {file += "easy.xml"  ;} else
        if(mode == EMode.NORMAL) {file += "normal.xml";} else
        if(mode == EMode.HARD)   {file += "hard.xml"  ;}
        
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(file));
        
        transformer.transform(domSource, streamResult);
        
        System.out.println("Done creating XML File");
	}
	///////////////////////////////////////////////////////////////////////////////////////
	public static boolean isRecord(EMode mode, int time) throws ParserConfigurationException, SAXException, IOException {
		
		ScoreRecord[] scoreRecords = getScore(mode);
	
		for(ScoreRecord sr : scoreRecords) {
			if(time < sr.getTime() || sr.getTime() == 9999) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public static ScoreRecord[] insertRecord(ScoreRecord[] scoreRecords, ScoreRecord newScoreRecord) {
		
		
		
		ScoreRecord[] sR = new ScoreRecord[11];
		
		for(int i = 0; i < MAX_ENTRIES; i++ ) {
			sR[i] = scoreRecords[i];
		}
		
		sR[10] = newScoreRecord;
		 
		 
		 
		for (int i = sR.length-1; i > 0; i--)
			for (int j = 1; j <= i; j++) 
				if (sR[j-1].getTime() > sR[j].getTime())
				{
					ScoreRecord temp = sR[j - 1];
					sR[j - 1] = sR[j] ;
					sR[j] = temp ;
				}
		
		for(int i = 0; i < MAX_ENTRIES; i++ ) {
			scoreRecords[i] = sR[i];
		}
		
		
		return scoreRecords;
	}
	

	
	
}

package com.neo.msocial.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

public class ParseXml {
	public String getValueFromKey(String xmlString, String rootTag, String tagName, String key, String value){
		String ret = "";
		try {			
			javax.xml.parsers.DocumentBuilderFactory dbFactory = javax.xml.parsers.DocumentBuilderFactory
					.newInstance();
			javax.xml.parsers.DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(xmlString)));
			doc.getDocumentElement().normalize();

/*			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());
*/
			NodeList nList = doc.getElementsByTagName(rootTag);
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				//System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					if(eElement.getElementsByTagName(tagName).item(0).getTextContent().equals(key)){
						
						return eElement.getElementsByTagName(value).item(0).getTextContent();
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}

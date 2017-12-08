package com.polytech.Outils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ManipulationFichierXML {
	
	private NodeList list;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private File fileXML;
	private Document xml;
	private Element root;
	private XPathFactory xpf; 
	private XPath path;
	
	private int nbIndicateur;
	private int nbIndicateurTot;
	private int nbAlternative;
	private int nbCritere;
	
	public ManipulationFichierXML(String nomFichier){
		factory = DocumentBuilderFactory.newInstance();
		
		try{
			builder = factory.newDocumentBuilder();
			fileXML = new File(nomFichier);
			xml = builder.parse(fileXML);
			
			root = xml.getDocumentElement();
			
			xpf = XPathFactory.newInstance();
			path = xpf.newXPath();
			
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getNbElement(String typeElement){
        list = root.getElementsByTagName(typeElement);         
        return list.getLength();
	}
	
	public int getNbIndicateur(int numCritere){
		NodeList nbInd = root.getElementsByTagName("nbIndicateur");
		nbIndicateur = Integer.parseInt(nbInd.item(numCritere).getTextContent());
		
		return nbIndicateur;
	}
	
	public int getNbAlternative(){
		NodeList nbAlt = root.getElementsByTagName("nbAlternative");
		nbAlternative = Integer.parseInt(nbAlt.item(0).getTextContent());
		return nbAlternative;
	}
	
	public int getNbCritere(){
		NodeList nbCrit = root.getElementsByTagName("nbCritere");
		nbCritere = Integer.parseInt(nbCrit.item(0).getTextContent());
		return nbCritere;
	}
	
	public int getNbIndicateurTot(){
		NodeList nbIndTot = root.getElementsByTagName("nbIndicateurTot");
	//	NodeList alternatives = root.get
		
		nbIndicateurTot = Integer.parseInt(nbIndTot.item(0).getTextContent());
		return nbIndicateurTot;
	}
	
	public NodeList getList() {
		return list;
	}

	public DocumentBuilderFactory getFactory() {
		return factory;
	}

	public DocumentBuilder getBuilder() {
		return builder;
	}

	public File getFileXML() {
		return fileXML;
	}

	public Document getXml() {
		return xml;
	}

	public Element getRoot() {
		return root;
	}

	public XPathFactory getXpf() {
		return xpf;
	}

	public XPath getPath() {
		return path;
	}

	public int getNbIndicateur() {
		return nbIndicateur;
	}
}

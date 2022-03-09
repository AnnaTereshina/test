package ru.metaprime.testtask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ru.metaprime.testtask.entity.Organization;

public class DataParser {

	public ArrayList<Organization> receiveListOfOrganization(String tableName, ArrayList<String> namesOrganizations) {
		ArrayList<Organization> organizations = new ArrayList<>();
		NodeList organizationElements = receiveNodeListOfOrganizations(tableName);

		for (int i = 0; i < organizationElements.getLength(); i++) {
			Node organizationNode = organizationElements.item(i);
			Element elem = (Element) organizationNode;

			Node nameNode = elem.getElementsByTagName("name").item(0);
			String name = nameNode.getTextContent();

			if (!checkDouble(namesOrganizations, name)) {
				Node areaNode = elem.getElementsByTagName("area").item(0);
				String area = areaNode.getTextContent();

				Node addressNode = elem.getElementsByTagName("address").item(0);
				String address = addressNode.getTextContent();

				Node phoneNode = elem.getElementsByTagName("phone").item(0);
				String phone = phoneNode.getTextContent();

				Organization organization = new Organization(name, area, address, phone);
				organizations.add(organization);
			}
		}
		return organizations;
	}

	private NodeList receiveNodeListOfOrganizations(String tableName) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
		Document document = null;
		try {
			document = builder.parse(new File("src/main/resources/" + tableName));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		document.getDocumentElement().normalize();
		NodeList organizationElements = document.getElementsByTagName("organization");

		return organizationElements;
	}

	private boolean checkDouble(ArrayList<String> namesOrganizations, String name) {
		for (String nameOrganization : namesOrganizations) {
			if (nameOrganization.equals(name)) {
				return true;
			}
		}
		return false;
	}

}

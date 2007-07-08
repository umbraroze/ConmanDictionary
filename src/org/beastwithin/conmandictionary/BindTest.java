package org.beastwithin.conmandictionary;

import javax.xml.bind.*;
import java.io.*;

public class BindTest {
	public static void main(String varg[]) {
		try {
			Dictionary d = new Dictionary();
			
			d.setNotePad("Not really much of a notepad.");
			d.getDefinitions()[0].setLanguage("Frobnian");
			d.getDefinitions()[1].setLanguage("English");
			d.getDefinitions()[0].add(new Entry("froonax","fun"));
			d.getDefinitions()[0].add(new Entry("ittigittigitt","not so fun"));
			d.getDefinitions()[1].add(new Entry("fun","froonax"));
			d.getDefinitions()[1].add(new Entry("fun, not so","ittigittigitt"));
			
			JAXBContext jc = JAXBContext.newInstance(d.getClass());
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			m.marshal(d, System.out);
			System.out.println("<!-- The preceding was marshalled from programmatic definition -->");
			
			JAXBContext jc2 = JAXBContext.newInstance(Dictionary.class);
			Unmarshaller um = jc2.createUnmarshaller();
			Marshaller m2 = jc2.createMarshaller();
			Dictionary d2 = (Dictionary) um.unmarshal(new File("bindtest.xml"));
			m2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			m2.marshal(d2, System.err);
			System.err.println("<!-- The preceding was unmarshalled from bindtest.xml -->");
		} catch (JAXBException jaxbe) {
			jaxbe.printStackTrace();
		}
	}
}

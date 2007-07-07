package org.beastwithin.conmandictionary;

import javax.xml.bind.*;

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
		} catch (JAXBException jaxbe) {
			jaxbe.printStackTrace();
		}
	}
}

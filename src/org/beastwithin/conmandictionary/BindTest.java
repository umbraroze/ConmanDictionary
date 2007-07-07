package org.beastwithin.conmandictionary;

import javax.xml.bind.*;

public class BindTest {
	public static void main(String varg[]) {
		try {
			Dictionary d = new Dictionary();
			JAXBContext jc = JAXBContext.newInstance(d.getClass());
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			m.marshal(d, System.out);
		} catch (JAXBException jaxbe) {
			System.err.println("Bummm! " + jaxbe.getMessage());
		}
	}
}

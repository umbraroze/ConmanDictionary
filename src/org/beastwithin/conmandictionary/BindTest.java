package org.beastwithin.conmandictionary;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class BindTest {
	private static final String testFile = "bindtest.xml";
	public static void main(String varg[]) {
		try {
			Dictionary.validateFile(new File(testFile));
			System.out.println("\nFile "+testFile+" appears to be valid!");
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			System.out.println("\nCreating a document out of nothing...\n");
			
			Dictionary d = new Dictionary();
			
			d.setNotePad("Not really much of a notepad.");
			List<EntryList> l = d.getDefinitions();
			EntryList deflist1 = new EntryList(); 
			EntryList deflist2 = new EntryList();
			deflist1.setLanguage("Frobnian");
			deflist2.setLanguage("English");
			deflist1.add(new Entry("froonax","fun"));
			deflist1.add(new Entry("ittigittigitt","not so fun"));
			deflist2.add(new Entry("fun","froonax"));
			deflist2.add(new Entry("fun, not so","ittigittigitt"));
			l.add(deflist1);
			l.add(deflist2);
			
			JAXBContext jc = JAXBContext.newInstance(d.getClass());
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			System.out.println("\n\nMarshalled from programmatic definition:\n");
			m.marshal(d, System.out);
			
			
			JAXBContext jc2 = JAXBContext.newInstance(Dictionary.class);
			Unmarshaller um = jc2.createUnmarshaller();
			Marshaller m2 = jc2.createMarshaller();
			Dictionary d2 = (Dictionary) um.unmarshal(new File(testFile));
			System.out.println("\n\nUnmarshaled from "+testFile+":\n"+d2.toString()+"\n\nIn XML:\n\n");
			
			m2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			System.out.println("\n\nRe-marshalled:\n");
			m2.marshal(d2, System.out);
			
		} catch (JAXBException jaxbe) {
			jaxbe.printStackTrace();
		}
	}
}

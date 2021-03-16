package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import entities.Emitter;
import entities.Product;

public class Teste {
	
	
	public static void main(String[] args) throws Exception {
		File fXmlFile = new File(
				"src/35200367647412000199550020002631221100130511_678a11ce7b1634f8f98512759dbf16c7af51891f.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		NodeList key = doc.getElementsByTagName("chNFe");
		Element chave = (Element) key.item(0);
		String chaveNotaFiscal = chave.getTextContent();

		NodeList emit = doc.getElementsByTagName("emit");
		Element emissor = (Element) emit.item(0);
		String nomeEmissor = emissor.getElementsByTagName("xNome").item(0).getTextContent();
		Emitter emitter = new Emitter(nomeEmissor);
		
		NodeList prod = doc.getElementsByTagName("prod");
		List<Product> products = new ArrayList<>();

		for (int i = 0; i < prod.getLength(); i++) {
			Element product = (Element) prod.item(i);
			String nameProduct = product.getElementsByTagName("xProd").item(0).getTextContent();
			String quantity = product.getElementsByTagName("qCom").item(0).getTextContent();
			String value = product.getElementsByTagName("vUnCom").item(0).getTextContent();
			
			Product produto = new Product(nameProduct, quantity, value);
			products.add(produto);
		}
		
		try(PrintWriter writer = new PrintWriter(new File(chaveNotaFiscal+".csv"))){
			StringBuilder sb = new StringBuilder();
			sb.append(emitter.getName()+"\n");
			
			for(Product p: products) {
				sb.append(p.getName()+";");
				sb.append(p.getQuantity()+";");
				sb.append(p.getValue()+";");
				sb.append("\n");
			}
			writer.write(sb.toString());
		}
		catch (FileNotFoundException e) {
			System.out.println("Erro na abertura do Arquivo");
		}
	}

}

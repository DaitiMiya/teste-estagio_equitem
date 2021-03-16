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
import utilities.ListaDiretorio;

public class Teste {

	public static void main(String[] args) throws Exception {
		File raiz = new File("C:\\trabs\\ws-eclipse\\teste_equitem\\src");
		ListaDiretorio lista = new ListaDiretorio();
		List<File> listFile = lista.listaDiretorio(raiz);
		
		/*
		 * Com a lista do tipo File encontrada, percorremos cada arquivo .xml para extrair os dados necessarios
		 * e criar os arquivos .csv.
		 */
		
		for(File fXmlFile: listFile) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		NodeList key = doc.getElementsByTagName("chNFe"); //Filtrando as tags para encontrar a chave.
		Element chave = (Element) key.item(0);
		String chaveNotaFiscal = chave.getTextContent(); //Transformando o item da tag em string.

		NodeList emit = doc.getElementsByTagName("emit"); //Filtrando as tags para encontrar o emitente.
		Element emissor = (Element) emit.item(0);
		String nomeEmissor = emissor.getElementsByTagName("xNome").item(0).getTextContent(); //Transformando o item da tag em string.
		Emitter emitter = new Emitter(nomeEmissor); //Criando um objeto do tipo emitter
		
		NodeList prod = doc.getElementsByTagName("prod"); //Filtrando as tags para encontrar os produtos.
		List<Product> products = new ArrayList<>(); //Lista para adicionar os produtos do tipo Product.

		for (int i = 0; i < prod.getLength(); i++) {
			Element product = (Element) prod.item(i); //Percorrendo cada elemento da NodeList prod.
			/*
			 * Ao percorrer a lista de elementos produto, transformamos cada valor desejado em String.
			 */
			String nameProduct = product.getElementsByTagName("xProd").item(0).getTextContent();
			String quantity = product.getElementsByTagName("qCom").item(0).getTextContent();
			String value = product.getElementsByTagName("vUnCom").item(0).getTextContent();
			
			Product produto = new Product(nameProduct, quantity, value);
			products.add(produto); //Cada produto com as informações desejadas são inseridas na lista products.
		}
		
		/*
		 * Este bloco e responsavel pela criacao de um arquivo .csv com o nome chaveNotaFiscal, 
		 * sendo utilizado o Printwriter e StringBuilder para escrita desse novo arquivo.
		 */
		try(PrintWriter writer = new PrintWriter(new File(chaveNotaFiscal+".csv"))){
			StringBuilder sb = new StringBuilder();
			sb.append(emitter.getName()+"\n");
			
			/*
			 * Percorrendo um foreach na lista de products e escrevendo no arquivo csv.
			 */
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
}

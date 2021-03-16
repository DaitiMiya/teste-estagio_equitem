package utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListaDiretorio {

	/*
	 * Esse metodo e responsavel por criar uma lista de arquivos a partir de um diretorio passado como parametro,
	 * assim ele encontra os arquivos .xml e adiciona na lista do tipo File.
	 */
	public List<File> listaDiretorio(File raiz) {
		
		List<File> lista = new ArrayList<>();
		
		for(File f: raiz.listFiles()) {
			if(f.isFile() && f.getName().endsWith("xml")) {
				lista.add(f);
			}
		}
		return lista;
	}
}

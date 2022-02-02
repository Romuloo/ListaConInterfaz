/**
 Copyright [2022] [Javier Linares Castrillón]
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package domain.controlador;
import domain.modelo.*;
import java.util.*;
import java.io.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.MutableCell;

/**
 * Esta clase contiene todas las funcionalidades de la lista: annadir, eliminar, modificar y su carga
 * en un fichero o lectura de un .ods.
 *
 * @author Javier Linares Castrillón
 */

public class Tienda{

	private ArrayList<Libro> lista = new ArrayList();
	private String nombreLista = ".libros.txt";


	/**
	 * Constructor de la clase Tienda.
	 */
	public Tienda(){
		inicializarFichero();
	}

	/**
	 * @return la lista de libros.
	 */
	public ArrayList<Libro> getLista(){
		return lista;
	}

	/**
	 * Annade un objeto de Libro a la lista.
	 * @param t título del libro.
	 * @param p precio del libro.
	 */
	public void addLibro(String t, double p){
		lista.add(new Libro(t, p));
		actualizar();
	}

	/**
	 * Devuelve la concatenación de los toString() de cada objeto Libro presente en la lista.
	 */
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Libro l : lista)
			sb.append(l);
		return sb.toString();
	}

	private void inicializarFichero(){
	try {
		File f  = new File(nombreLista);
		f.createNewFile();
		Scanner sc = new Scanner(f);
		while(sc.hasNext()) {
			Libro l = new Libro();
			l.setTitulo(sc.nextLine());
			l.setPrecio(Double.parseDouble(sc.nextLine()));
			lista.add(l);
		}
	}catch(IOException ex)	{
		System.err.println(ex);
	}
	}


	private void actualizar() {
		try {
			FileWriter fw = new FileWriter(nombreLista);
			for(Libro l : lista) {
				fw.write(l.getTitulo() + "\n");
				fw.write(l.getPrecio() + "\n");
			}
			fw.close();
		}catch(IOException ex) {
			System.err.println(ex);
		}
	}

	/**
	 * Elimina un libro de la lista.
	 * @param n título del libro que se desea eliminar.
	 */
	public void removeLibro(String n){
		lista.remove(new Libro(){{setTitulo(n);}});
		actualizar();
	}

	/**
	 * Lee los valores de una hoja de cálculo.
	 * @param hoja el path de la hoja de cálculo.
	 * @return una lista de Libros.
	 */
	public ArrayList<Libro> leerHojaDeCalculo(String hoja){
	
		ArrayList<Libro> libros = new ArrayList<>();
		File file;
		Sheet sheet;
		try{
		      file = new File(hoja);
		      sheet = SpreadSheet.createFromFile(file).getSheet(0);
         	      int nRowCount = sheet.getRowCount();

			for(int i = 1; i < nRowCount; i++)
				libros.add(new Libro(sheet.getCellAt(0, i).getValue().toString(), Double.parseDouble(sheet.getCellAt(1,i)
								.getValue().toString())));

		}catch (IOException e) {
			 System.out.println("Ruta incorrecta");
        	}

		return libros;
	}

}

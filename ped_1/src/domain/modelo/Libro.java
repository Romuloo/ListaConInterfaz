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

package domain.modelo;

/**
 * Esta clase define las propiedades de un Libro.
 *
 * @author Javier Linares Castrillón
 */

public class Libro{

	private String titulo;
	private double precio;

	/**
	 * Constructor de la clase Libro.
	 * @param titulo título del libro.
	 * @param precio precio del libro.
	 */
	public Libro(String titulo, double precio){
		this.precio = precio;
		this.titulo = titulo;
	}

	/**
	 * Constructor sin parámetros de la clase Libro.
	 */
	public Libro(){
	}

	/**
	 * @return [Título]: + título + [Precio]: + precio.
	 */
	public String toString(){
		return "[Título]: " + titulo + ", [Precio]: " + precio;
	}

	/**
	 * Inicializa el atributo título de un libro.
	 * @param t título del libro.
	 */
	public void setTitulo(String t){
		this.titulo = t;
	}

	/**
	 * Inicializa el atributo precio de un libro.
	 * @param p precio del libro. 
	 */
	public void setPrecio(Double p){
		this.precio = p;
	}

	/**
	 * @return titulo del libro.
	 */
	public String getTitulo(){
		return titulo;
	}
	
	/**
	 * @return precio del libro.
	 */
	public Double getPrecio(){
		return precio;
	}

	/**
	 * Establece cuándo dos libros son iguales en función de su título.
	 * @return true si tienen el mismo título : false si no lo tienen.
	 */
	@Override
	public boolean equals(Object o){
		Libro l = (Libro) o;
		return this.titulo.equals(l.getTitulo());
	}

}

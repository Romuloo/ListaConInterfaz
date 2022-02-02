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

package domain.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import domain.controlador.*;
import domain.modelo.*;

/**
 * Esta clase se encarga del front de la aplicación.
 *
 * @author Javier Linares Castrillón
 */

public class App{
	
	 private static Tienda t = new Tienda();

	 private static JFrame f = new JFrame();
    	 private static Font font = new Font("Calibri", Font.BOLD, 25);
    	 private static JList list;
	 private static DefaultListModel listModel = new DefaultListModel();
	 private static JScrollPane listScrollPane;
	 private static JButton add = new JButton("+"), rm = new JButton("-"), change = new JButton("~"), upload = new JButton("u");
	 private static JLabel l1 = new JLabel("Libros");

	 /**
	  * Lanza la aplicación inicializando el frame y sus componentes.
	  */
	 public static void start(){
     		f.setTitle("Catálogo");
      		f.setBackground(new Color(39, 156, 207));
        	f.setSize(350,500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	f.setVisible(true);
        	f.setResizable(false);
        	f.setLayout(null);
        	f.setBackground(Color.orange);

		organizarComponentes();
		initLista();
	 }

	 private static void organizarComponentes(){
		f.add(l1);
		l1.setBounds(115, 0, 100,50);
        	l1.setFont(font);

		f.add(add);
		add.setBounds(15,50,30,25);
		add.setFont(font);

		f.add(rm);
		rm.setBounds(205,50,30,25);
		rm.setFont(font);

		f.add(change);
		change.setBounds(110, 50, 30, 25);
		change.setFont(font);

		f.add(upload);
		upload.setBounds(290, 50, 30, 25);
		upload.setFont(font);

		upload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
			limpiarModelo(listModel);
			SearchFrame s = new SearchFrame();
			}
		});

		add.addActionListener(new ActionListener() {
		 	@Override
            		public void actionPerformed(ActionEvent e) {
			AddFrame addF = new AddFrame("Añadir Libro", "+");
			} 
		});
		
		rm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
			int i = list.getSelectedIndex();
			try{
			Libro l = (Libro) listModel.get(i);
			t.removeLibro(l.getTitulo());
			listModel.remove(i);
			}catch(Exception ex){
			System.out.println("No has seleccionado ningún elemento que eliminar");
			}
			}
		});

		change.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				int i = list.getSelectedIndex();
				try{
					Libro l = (Libro) listModel.get(i);
					t.removeLibro(l.getTitulo());
					listModel.remove(i);
					AddFrame a = new AddFrame("Modificar Libro", l.getTitulo(), l.getPrecio()+"", "~");

				}catch(Exception ex){
					System.out.println("No has seleccionado ningún elemento que modificar");
				}
			}
		});

	 }

	 private static void addElementoLista(String ti, double p){
		t.addLibro(ti, p);
		listModel.addElement(new Libro(ti,p));
		
	 }

	 private static void initLista(){

      	 	for(Libro l : t.getLista())
       			listModel.addElement(l); 
       		list = new JList(listModel);
        	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        	list.setSelectedIndex(0);
        	list.setBackground(Color.WHITE);
        	listScrollPane = new JScrollPane(list);
        	f.add(listScrollPane);
        	list.setBackground(Color.getColor("blue", new Color(13, 175, 207)));
        	listScrollPane.setBackground(Color.blue);
        	listScrollPane.setBounds(0, 100, 350, 350);
	 }

	 private static void limpiarModelo(DefaultListModel lm){
		 for(Object o : lm.toArray()){
			 Libro l = (Libro) o;
			 t.removeLibro(l.getTitulo());
			 lm.remove(lm.indexOf(o));

		 } 
	 }

	 private static class SearchFrame extends JFrame{
		 
		 private JLabel r = new JLabel("Ruta");
		 private JTextField tr = new JTextField();
		 private Font font = new Font("Calibri", Font.BOLD, 15);
		 private JButton s = new JButton(">");

		public SearchFrame(){
			initFrame();
			initComponentes();
		}

		private void initFrame(){
			setTitle("Cargar");
      			setBackground(new Color(39, 156, 207));
        		setSize(150,100);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		setVisible(true);
        		setResizable(false);
        		setLayout(null);
        		setBackground(Color.orange);
		}

		private void initComponentes(){
			add(r);
			r.setBounds(10, 10, 60,25);
			r.setFont(font);

			add(tr);
			tr.setBounds(10, 42, 120, 20);

			add(s);
			s.setBounds(90, 10, 30, 25);
		       	s.setFont(font);

			s.addActionListener(new ActionListener() {
		 		@Override
            			public void actionPerformed(ActionEvent e) {
					try{
			
						if(tr.getText().length() > 0)
							for(Libro l : t.leerHojaDeCalculo(tr.getText()))
								addElementoLista(l.getTitulo(), l.getPrecio());
						
						dispose();
						
					}catch(Exception ex){
							System.out.println("No se ha añadido ninguna dirección");
					}

				}
			});	
			
		}
	 }



	private static class AddFrame extends JFrame{

		private JLabel n = new JLabel("Título"), p = new JLabel("Precio");
		private JTextField tn = new JTextField(), tp = new JTextField();
		private Font font = new Font("Calibri", Font.BOLD, 15);
		private JButton b;
		private String titulo = "", precio = "";

		public AddFrame(String t, String sb){
			initFrame(t);
			initComponentes(sb);
		}

		public AddFrame(String t, String tt, String p, String sb){
			initFrame(t);
			this.titulo = tt;
			this.precio = p;
			initComponentes(sb);
		}

		private void initFrame(String t){
			setTitle(t);
      			setBackground(new Color(39, 156, 207));
        		setSize(300,200);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		setVisible(true);
        		setResizable(false);
        		setLayout(null);
        		setBackground(Color.orange);
		}
		private void initComponentes(String sb){
		add(n);
		n.setBounds(10,25,120,20);
		n.setFont(font);

		add(p);
		p.setBounds(215,25,120,20);
		p.setFont(font);

		add(tn);
		tn.setText(titulo);
		tn.setBounds(10, 55, 185, 20);
		
		add(tp);
		tp.setText(precio);
		tp.setBounds(215, 55, 80, 20);

		b = new JButton(sb);
		add(b);
		b.setBounds(110, 80, 50, 50);
		b.setFont(font);
				
		b.addActionListener(new ActionListener() {
		 	@Override
            		public void actionPerformed(ActionEvent e) {
			try{
				if(tn.getText().length() > 0)
					addElementoLista(tn.getText(),Double.parseDouble(tp.getText()));
				dispose();
			}catch(Exception ex){
				System.out.println("No se ha añadido ningún registro");
			}
		}

		});


		}
	}
}

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.sound.sampled.*;

public class NotePad extends JFrame implements ActionListener, MenuListener {
	JTextArea nota;
	JFileChooser seleccion;
	JButton boton;
	JMenuBar barraMenu;
	JMenu archivo, salir;
	JMenuItem abrir, guardar;
	FileReader entrada = null;
	FileWriter salida = null;
	JScrollPane scroll;

	public NotePad(){
		//Creando la barra de menu
		barraMenu = new JMenuBar();
		
		//Agregando elementos a la barra
		archivo = new JMenu("Archivo");
		barraMenu.add(archivo); //agregar el elemento a la barra

		//Agregar el elemento de salida
		salir = new JMenu("Salir");
		salir.addMenuListener(this);
		barraMenu.add(salir);

		//Añadiendo elementos al menu
		abrir = new JMenuItem("Abrir un archivo ", new ImageIcon("imagenes/abrir.gif"));
		abrir.addActionListener(this); //Agrega un Action Listner al objeto
		archivo.add(abrir);

		guardar = new JMenuItem("Guardar un archivo ", new ImageIcon("imagenes/guardar.gif"));
		guardar.addActionListener(this);
		archivo.add(guardar);

		JPanel p = new JPanel();
		//Un contentPane contiene los componentes del JFrame
		setContentPane(p);
		p.setLayout(new BorderLayout()); //Prepara la distribución del Pane
		nota = new JTextArea();//asignar la variable nota a un text area
		setSize(600,400); //tamaño del frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setTitle("NotePad");
		this.setJMenuBar(barraMenu); //Agregar la barra de menu
		scroll = new JScrollPane(nota); //Agregar barra de desplazamiento a el text area
		p.add(scroll,BorderLayout.CENTER);
		setVisible(true); //Hace el JFrame visible, sin esto no se ve

	}
	public static void main(String[] args){
		NotePad d = new NotePad();
	}

	//Metodo de la interfaz ActionPerformed que requiere ser sobreescrito
	public void actionPerformed(ActionEvent e){
			seleccion = new JFileChooser();
			//Comprobar de donde viene el evento y luego ejecutar codigo
			if(e.getSource().equals(abrir)){
					int retorno = seleccion.showOpenDialog(this);
					//Si el usuario selecciona un archivo
					if(retorno == JFileChooser.APPROVE_OPTION){
						//Usa el File chooser para sleeccionar un archivo f
						File f = seleccion.getSelectedFile();
						try{
							entrada = new FileReader(f);
							BufferedReader leer = new BufferedReader(entrada);
							nota.read(leer,null);
							setTitle("NotePad - " + f.getName());
							leer.close();
						}catch(Exception ee){
							System.out.println("No puede leer");
						}
					}else{
						nota.append("Cancelado" + "\n");
					}
			}
			
			else if(e.getSource().equals(guardar)){
				int retorno = seleccion.showSaveDialog(this);
				if(retorno == JFileChooser.APPROVE_OPTION){
					File f = seleccion.getSelectedFile();
					nota.append(" "); //Para evitar error de guardado
					try{
						salida = new FileWriter(f);
						BufferedWriter escribir = new BufferedWriter( salida );
						nota.write(escribir);
						escribir.close();
						nota.setText("");
					}catch(Exception ee2){
						System.out.println("No se puede guardar");
					}
				}else{
					nota.append("Guardado cancelado");
				}
					nota.setCaretPosition(nota.getDocument().getLength());
			}
	}

	@Override //@Override indica que se va a sobreescribir un metodo, si el compilador no detecta una sobreescritura se genera un mensaje de error
	public void menuSelected(MenuEvent me) {
			//Si se selecciona salir, se cierra el programa
			if(me.getSource().equals(salir)){
				System.exit(0);
			}
	}

	@Override
	public void menuDeselected(MenuEvent me) {

	}

	@Override
	public void menuCanceled(MenuEvent me){

	}
}
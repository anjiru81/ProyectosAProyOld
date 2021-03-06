package proyectosAproyOld;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

public class Empresa extends ProyOldMutableTreeNode{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3852978401222324055L;

	//String rutaOrigen;
	String msg="";

	boolean existeEnOrigen;
	boolean existeEnDestino;
	boolean errorEnRutas;
	protected String rutaOrigen;
	protected String rutaDestino;
	protected String ruta_destino;

	protected LinkedList<String> rutas_origen;
	protected Empresa(String nombre){
		super(nombre);
	}

	public void find(){
		Iterator<String> rutas_it = rutas_origen.iterator();
		while(rutas_it.hasNext()){
			String ruta = rutas_it.next();
			if(!(new File(ruta+"\\"+this.getRuta()).exists())){
				msg=msg+"No existe en "+ruta+"; ";
				existeEnOrigen = false;
				errorEnRutas = true;
			}else{
				existeEnOrigen = true;
				errorEnRutas = false;
				msg=msg+"Existe en "+ruta+"; ";
				this.rutaOrigen = ruta+"\\"+this.getRuta();
				break;
			}
		}
		if(!(new File(ruta_destino+"\\"+this.getRuta()).exists())){
			msg=msg+"No existe en "+ruta_destino+"; ";
			existeEnDestino = false;
			errorEnRutas = true;
		}else{
			existeEnDestino = true;
			errorEnRutas = errorEnRutas | false;
			msg=msg+"Existe en destino";
			this.rutaDestino = ruta_destino+"\\"+this.getRuta();
		}
	}
	public void setRutas_Origen(LinkedList<String> rutas_origen) {
		this.rutas_origen = rutas_origen;
	}
	public void setRuta_destino(String ruta_destino) {
		this.ruta_destino = ruta_destino;
	}
	public boolean ExisteEnOrigen() {
		return existeEnOrigen;
	}
	public boolean ExisteEnDestino() {
		return existeEnDestino;
	}
	public String getMsg() {
		return msg;
	}
	public boolean isErrorEnRutas() {
		return errorEnRutas;
	}
	public String getRuta(){

		return (String)this.getUserObject();
	}
	public void move(){
		
		Enumeration<?> e = this.children();
		while(e.hasMoreElements()){
			Proyecto node = (Proyecto)e.nextElement();
			if(node.isEnabled()){
				node.move();
			}
		}

	}

	public void addProyecto(String nombre) {
		Proyecto p =new Proyecto(nombre, (String)this.getUserObject());
		p.setRutas_Origen(rutas_origen);
		p.setRuta_destino(ruta_destino);
		this.add(p);
		p.find();
	}

	public void checkIfProjectMoved() {
		boolean allDisabled = true;
		Enumeration<?> e = this.children();
		while(e.hasMoreElements()){
			allDisabled = allDisabled & !((Proyecto)e.nextElement()).isEnabled();
		}
		this.setEnabled(!allDisabled);
	}

	public Vector<String> getAllAvailablePaths() {
		Vector<String> availablePaths = new Vector<String>();
		Enumeration<?> e = this.children();

		while(e.hasMoreElements()){
			ProyOldMutableTreeNode node = (ProyOldMutableTreeNode)e.nextElement();
			if(node.isEnabled()){
				//System.out.println(node.getUserObject());
				availablePaths.addAll(node.getAllAvailablePaths());
			}

		}
		return availablePaths;
	}

}

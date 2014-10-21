package proyectosAproyOld;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

public class Empresa extends DefaultMutableTreeNode{
	//String rutaOrigen;
	String msg="";
	String ruta_destino;
	LinkedList<String> rutas_origen;
	boolean existeEnOrigen;
	boolean existeEnDestino;
	boolean errorEnRutas;
	public Empresa(String nombre){
		super(nombre);
	}
	public void addProyecto(String proyecto){
		this.add(new Proyecto(proyecto));
	}
	public void findEmpresa(){
		Iterator<String> rutas_it = rutas_origen.iterator();
		while(rutas_it.hasNext()){
			String ruta = rutas_it.next();
			if(!(new File(ruta+"\\"+this.getUserObject()).exists())){
				msg=msg+"No existe la empresa en "+ruta+" ";
				existeEnOrigen = false;
				errorEnRutas = true;
			}else{
				existeEnOrigen = true;
				errorEnRutas = false;
				msg=msg+"Empresa en "+ruta+" ";
				break;
			}
		}
		if(!(new File(ruta_destino+"\\"+this.getUserObject()).exists())){
			msg=msg+"No existe la empresa en "+ruta_destino+" ";
			existeEnDestino = false;
			errorEnRutas = true;
		}else{
			existeEnDestino = true;
			errorEnRutas = errorEnRutas | false;
			msg=msg+"La empresa existe en destino";
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

}

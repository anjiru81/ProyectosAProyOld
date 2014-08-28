package proyectosAproyOld;

import javax.swing.tree.DefaultMutableTreeNode;

public class Empresa extends DefaultMutableTreeNode{
	String rutaOrigen;
	String rutaDestino;
	boolean OK;
	public Empresa(String nombre){
		super(nombre);
	}
	public void addProyecto(String proyecto){
		this.add(new Proyecto(proyecto));
	}
	public boolean isOK() {
		return OK;
	}
	public void setOK(boolean oK) {
		OK = oK;
	}

}

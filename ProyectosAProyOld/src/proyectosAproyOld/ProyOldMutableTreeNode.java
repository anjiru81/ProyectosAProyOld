package proyectosAproyOld;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

public class ProyOldMutableTreeNode extends DefaultMutableTreeNode {

	private boolean enabled;

	public ProyOldMutableTreeNode() {
		// TODO Auto-generated constructor stub
		this.setEnabled(true);
	}

	public ProyOldMutableTreeNode(Object userObject) {
		super(userObject);
		this.setEnabled(true);
		// TODO Auto-generated constructor stub
	}

	public ProyOldMutableTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
		this.setEnabled(true);
		// TODO Auto-generated constructor stub
	}
	public void move(){
		Enumeration e = this.children();
		while(e.hasMoreElements()){
			Empresa node = (Empresa)e.nextElement();
			if(node.isEnabled()){
				node.move();
			}
		}
	}

	public void setEnabled(boolean enable){
		this.enabled = enable;
	}
	public boolean isEnabled(){
		return this.enabled;
	}

	public Vector<String> getAllAvailablePaths() {
		Vector<String> availablePaths = new Vector<String>();
		Enumeration e = this.children();
		while(e.hasMoreElements()){
			ProyOldMutableTreeNode node = (ProyOldMutableTreeNode)e.nextElement();
			if(node.isEnabled()){
				//availablePaths.add(((Proyecto)e.nextElement()).rutaOrigen+"\\"+((Proyecto)e.nextElement()).getRuta());
				availablePaths.addAll(((Empresa)node).getAllAvailablePaths());
			}
		}
		return availablePaths;
	}

}

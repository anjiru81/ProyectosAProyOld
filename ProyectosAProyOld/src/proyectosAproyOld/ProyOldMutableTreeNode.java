package proyectosAproyOld;

import java.util.Enumeration;

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
			((ProyOldMutableTreeNode)e.nextElement()).move();
		}
	}
	
	public void setEnabled(boolean enable){
		this.enabled = enable;
	}
	public boolean isEnabled(){
		return this.enabled;
	}

	public String[] getAllAvailablePaths() {
		// TODO Auto-generated method stub
		return null;
	}
}

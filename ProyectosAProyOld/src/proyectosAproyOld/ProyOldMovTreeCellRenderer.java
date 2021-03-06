package proyectosAproyOld;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

public class ProyOldMovTreeCellRenderer extends JLabel implements TreeCellRenderer {


	//    tree El �rbol sobre el que se dibujar� la foto del Component que devolvamos.
	//    value El nodo que tenemos que dibujar. Aqu� estar�n los datos que se quieran dibujar y normalmente ser� un TreeNode.
	//    selected Si est� o no seleccionado dicho nodo. Debemos tenerlo en cuenta si queremos, por ejemplo, que el color de fondo de los nodos seleccionados sea distinto de los nodos no seleccionados.
	//    expanded Si el nodo est� o no expandido (sus hijos son o no visibles)
	//    leaf Si el nodo es una hoja (no tiene nodos hijos)
	//    row Fila del �rbol en la que est� el nodo.
	//    hasFocus Si el nodo tiene el foco. Suele ser habitual que los elementos que tienen el foco se pinten con un peque�o cuadradito alrededor.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		// TODO Auto-generated method stub
		
		
		if(value instanceof Empresa ){
			//Si la empresa esta tanto en origen como en destino
			if(!((Empresa)value).isErrorEnRutas() && !(value instanceof Proyecto)){
				this.setOpaque(true);
				this.setForeground(Color.BLACK);
				this.setBackground(new Color(0,238,118));
				//this.setToolTipText(((Empresa)value).getMsg());
			//Si el proyecto no esta en origen pero si en destino, es decir que se emovio antes	
			}else if(value instanceof Proyecto && !((Proyecto)value).ExisteEnOrigen() && ((Proyecto)value).ExisteEnDestino()){
				this.setForeground(Color.MAGENTA);
				this.setOpaque(true);
				this.setBackground(Color.lightGray);
				//this.setToolTipText(((Empresa)value).getMsg());
			}
			//Si el proyecto existe en origen pero no en destino, pero la empresa si que existe en destino (es decir que se puede mover el proyecto pero aun no se hizo
			else if(value instanceof Proyecto && ((Proyecto)value).ExisteEnOrigen() && !((Proyecto)value).ExisteEnDestino() && ((Empresa)((Proyecto)value).getParent()).existeEnDestino){
				this.setForeground(Color.BLACK);
				this.setOpaque(true);
				this.setBackground(new Color(0,238,118));
			//	System.out.println(((DefaultMutableTreeNode)value).getUserObject().toString());
				//this.setToolTipText(((Empresa)value).getMsg());
			}else{
				this.setForeground(Color.RED);
				this.setOpaque(true);
				this.setBackground(Color.YELLOW);
				//this.setToolTipText(((Empresa)value).getMsg());
			}


		}else{
			this.setOpaque(false);
			//this.setBackground(Color.YELLOW);
			this.setForeground(Color.BLACK);
		}
		
		// Y el texto.
		this.setText(((DefaultMutableTreeNode) value).getUserObject().toString());
		return this;

		//	return null;
	}

}

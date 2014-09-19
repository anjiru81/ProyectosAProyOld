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

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		// TODO Auto-generated method stub

		// Se pone el icono adecuado
		//		   if (leaf)
		//		   {
		//		      botonIcono.setIcon(iconoHoja);
		//		   }
		//		   else if (expanded)
		//		   {
		//		      botonIcono.setIcon(iconoAbierto);
		//		   }
		//		   else
		//		   {
		//		      botonIcono.setIcon(iconoCerrado);
		//		   }
		//System.out.println(((DefaultMutableTreeNode)value)+ " " + String.valueOf((value instanceof Proyecto)));
		if(value instanceof Empresa || value instanceof Proyecto){

			this.setForeground(Color.GREEN);

		}else{
			this.setForeground(Color.BLACK);
		}
		//this.setForeground(Color.RED);
		// Y el texto.
		this.setText(((DefaultMutableTreeNode) value).getUserObject().toString());
		this.setToolTipText(value.toString());
		return this;

		//	return null;
	}

}

package proyectosAproyOld;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

public class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CheckTreeSelectionModel selectionModel; 
	private TreeCellRenderer delegate; 
	private TristateCheckBox checkBox = new TristateCheckBox(); 

	public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel){ 
		this.delegate = delegate; 
		this.selectionModel = selectionModel; 
		setLayout(new BorderLayout()); 
		setOpaque(false); 
		checkBox.setOpaque(false); 
		//  checkBox.setEnabled(false);
		checkBox.setState(TristateCheckBox.NOT_SELECTED); 
	} 


	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){ 
		Component renderer = delegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus); 

		TreePath path = tree.getPathForRow(row); 
		if(path!=null){ 
			if(selectionModel.isPathSelected(path, true)) 
				checkBox.setState(TristateCheckBox.SELECTED); 
			else 
				checkBox.setState(selectionModel.isPartiallySelected(path) ? TristateCheckBox.DONT_CARE : TristateCheckBox.NOT_SELECTED); 
		} 
		removeAll(); 
		add(checkBox, BorderLayout.WEST); 
		add(renderer, BorderLayout.CENTER); 
		if(value instanceof Proyecto){
			this.setToolTipText("Proyecto "+((Proyecto)value).getMsg());
		}else if(value instanceof Empresa){
			
			this.setToolTipText("Empresa "+((Empresa)value).getMsg());
		}
		if(((ProyOldMutableTreeNode)value).isEnabled()){
			checkBox.setEnabled(true);
		}else{
			checkBox.setEnabled(false);
		}
		return this; 
	} 
} 
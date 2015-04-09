package proyectosAproyOld;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class CheckTreeManager extends MouseAdapter implements TreeSelectionListener{ 
	private CheckTreeSelectionModel selectionModel; 
	private JTree tree = new JTree(); 
	int hotspot = new JCheckBox().getPreferredSize().width;
	private JPopupMenu popup = new JPopupMenu();
	private int lastx;
	private int lasty;

	public CheckTreeManager(JTree tree){ 
		this.tree = tree; 
		selectionModel = new CheckTreeSelectionModel(tree.getModel()); 
		tree.setCellRenderer(new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel)); 
		tree.addMouseListener(this); 
		selectionModel.addTreeSelectionListener(this);
		JMenuItem mi = new JMenuItem("Abrir Proyectos");
		JMenuItem mi2 = new JMenuItem("Abrir paraFacturar");
		JMenuItem mi3 = new JMenuItem("Abrir Proy_old");
		JMenuItem mi4 = new JMenuItem("Crear carpeta en Proy_old");
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// t.setText(((JMenuItem) e.getSource()).getText());
				EjecucionRuntime er = new EjecucionRuntime();
				if(e.getActionCommand().equals("Abrir Proyectos")){
					//  System.out.println("He pinchado en el popup "+e.getActionCommand());
					er.CommandExec("explorer \\\\nasr1\\proyectos");
				}else if(e.getActionCommand().equals("Abrir paraFacturar")){
					//	  System.out.println("He pinchado en el popup "+e.getActionCommand());
					er.CommandExec("explorer \\\\linguawss\\paraFacturar");
				}else if(e.getActionCommand().equals("Abrir Proy_old")){
					//  System.out.println("He pinchado en el popup "+e.getActionCommand());
					er.CommandExec("explorer \\\\nasr1\\proy_old");
				}else if(e.getActionCommand().equals("Crear carpeta en Proy_old")){
					//  System.out.println("He pinchado en el popup "+e.getActionCommand());
					crearCarpeta(er, "\\\\nasr1\\proy_old\\"+getNodeName());

				}
			}
		};
		mi.addActionListener(al);
		mi2.addActionListener(al);
		mi3.addActionListener(al);
		mi4.addActionListener(al);
		popup.add(mi);
		popup.add(mi2);
		popup.add(mi3);
		popup.add(mi4);

	} 
	public String getNodeName(){
		//System.out.println("prueba " +lastx+" "+lasty+" " );

		return tree.getPathForLocation(lastx, lasty).getLastPathComponent().toString();
	}
	public void crearCarpeta(EjecucionRuntime er, String ruta){
		int opcion = JOptionPane.showConfirmDialog(tree,"¿Desea crear la carpeta "+ruta+" ?", "Crear carpeta", JOptionPane.YES_NO_OPTION);
		if(opcion == JOptionPane.YES_OPTION){
			er.CommandExec("mkdir "+"\""+ruta+"\"");
			System.out.println("Se ha creado la carpeta "+ruta);
		}
	}
	public void mouseClicked(MouseEvent me){ 
		//	System.out.println(me.getButton());
		if(me.getButton()==MouseEvent.BUTTON1){
			TreePath path = tree.getPathForLocation(me.getX(), me.getY()); 
			if(path==null) 
				return; 
			if(me.getX()>tree.getPathBounds(path).x+hotspot) 
				return; 
			ProyOldMutableTreeNode node = (ProyOldMutableTreeNode)path.getLastPathComponent();
			if(!node.isEnabled())
				return;
			boolean selected = selectionModel.isPathSelected(path, true); 
			selectionModel.removeTreeSelectionListener(this); 
			//System.out.println(path +" "+(path.getLastPathComponent().getClass()).toString().equals("class proyectosAproyOld.Empresa"));
			//System.out.println(selected);
			try{ 
				if(selected) 
					selectionModel.removeSelectionPath(path);
				else
					selectionModel.addSelectionPath(path);
			} finally{
				selectionModel.addTreeSelectionListener(this);
				tree.treeDidChange();
			} 
		}else if(me.getButton()==MouseEvent.BUTTON3){
			//	System.out.println(me.getButton());
			lastx =  me.getX();
			lasty = me.getY();
			popup.show(tree, me.getX(), me.getY());


			//System.out.println((tree.getPathForLocation(me.getX(), me.getY())).getLastPathComponent().toString()+" "+me.getX()+" "+ me.getY());
		}
	} 

	public CheckTreeSelectionModel getSelectionModel(){ 
		return selectionModel; 
	} 

	public void valueChanged(TreeSelectionEvent e){ 
		tree.treeDidChange(); 
	} 
	public void forceValueChanged(){ 
		tree.treeDidChange(); 
	} 
}
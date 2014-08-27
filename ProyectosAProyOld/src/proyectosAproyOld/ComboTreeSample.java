package proyectosAproyOld;

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;

public class ComboTreeSample {
  public static void main(String args[]) {
	  try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		    	//System.out.println(info.getName());
		        if ("Windows".equals(info.getName())) {
		    	//System.out.println(info.getName());
		            UIManager.setLookAndFeel(info.getClassName());
		            //break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
    JFrame frame = new JFrame("Editable Tree");
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Raíz");
    DefaultMutableTreeNode rama1 = new DefaultMutableTreeNode("Rama1");
    DefaultMutableTreeNode hoja1 = new DefaultMutableTreeNode("Hoja1");
    DefaultMutableTreeNode hoja2 = new DefaultMutableTreeNode("Hoja2");
    root.add(rama1);
    rama1.add(hoja1);
    rama1.add(hoja2);
    JTree tree = new JTree(root);
    
    tree.setEditable(true);
    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree
        .getCellRenderer();
    String elements[] = { "Root", "chartreuse", "rugby", "sushi" };
    JComboBox comboBox = new JComboBox(elements);
    comboBox.setEditable(true);
    TreeCellEditor comboEditor = new DefaultCellEditor(comboBox);
    TreeCellEditor editor = new DefaultTreeCellEditor(tree, renderer,
        comboEditor);
    tree.setCellEditor(editor);
    JScrollPane scrollPane = new JScrollPane(tree);
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    frame.setSize(300, 150);
    frame.setVisible(true);
  }
}
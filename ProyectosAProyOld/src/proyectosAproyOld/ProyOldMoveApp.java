package proyectosAproyOld;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JTextPane;

import java.awt.Dimension;

import javax.swing.JLabel;

public class ProyOldMoveApp implements ActionListener, DocumentListener{

	private JFrame frame;
	private JTextPane editorPane;
	private Highlighter hl;
	private JButton btnGenerar;
	private JScrollPane scrollPane_arbol;
	private Parser p;
	private SimpleAttributeSet GreenAttr; 
	private SimpleAttributeSet BlackAttr;
	private JTextPane consola;
	private SimpleAttributeSet ConsolaAttr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Windows".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					// If Nimbus is not available, you can set the GUI to another look and feel.
				}
				try {
					ProyOldMoveApp window = new ProyOldMoveApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProyOldMoveApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		p = new Parser();
		GreenAttr = new SimpleAttributeSet(); 
		BlackAttr = new SimpleAttributeSet(); 
		ConsolaAttr = new SimpleAttributeSet();
		StyleConstants.setForeground(GreenAttr, new Color(0,169,39));
		StyleConstants.setForeground(BlackAttr, Color.BLACK);
		StyleConstants.setForeground(ConsolaAttr, new Color(0,169,39));
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel wrap_pane = new JPanel();
		frame.getContentPane().add(wrap_pane, BorderLayout.CENTER);
		wrap_pane.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		wrap_pane.add(scrollPane, BorderLayout.CENTER);

		editorPane = new JTextPane();
		editorPane.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		editorPane.getDocument().addDocumentListener(this);
		hl = editorPane.getHighlighter();
		scrollPane.setViewportView(editorPane);

		JPanel panel_botones = new JPanel();
		wrap_pane.add(panel_botones, BorderLayout.SOUTH);

		btnGenerar = new JButton("Generar");
		btnGenerar.addActionListener(this);
		btnGenerar.setEnabled(false);
		panel_botones.add(btnGenerar);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(3, 200));
		scrollPane_1.setMinimumSize(new Dimension(22, 100));
		panel.add(scrollPane_1);

		consola = new JTextPane();
		consola.setMinimumSize(new Dimension(600, 50));
		scrollPane_1.setViewportView(consola);

		JPanel panel_arbol = new JPanel();
		frame.getContentPane().add(panel_arbol, BorderLayout.EAST);
		panel_arbol.setLayout(new BorderLayout(0, 0));

		scrollPane_arbol = new JScrollPane();
		scrollPane_arbol.setPreferredSize(new Dimension(250, 3));
		panel_arbol.add(scrollPane_arbol);



	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//	this. revisarSintaxis();

		JTree tree;
		try {
			tree = new JTree(p.generarArbolEmpresas(p.getTextLines(this.editorPane.getDocument().getText(0,this.editorPane.getDocument().getLength()))));
			ToolTipManager.sharedInstance().registerComponent(tree);
			tree.setEditable(true);
			tree.setCellRenderer(new ProyOldMovTreeCellRenderer());
			//	DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();


			String elements[] = { "Root", "chartreuse", "rugby", "sushi" };
			JComboBox<String> comboBox = new JComboBox<String>(elements);
			comboBox.setEditable(true);
			//	TreeCellEditor comboEditor = new DefaultCellEditor(comboBox);
			//	TreeCellEditor editor = new DefaultTreeCellEditor(tree, renderer,
			//			comboEditor);
			//	tree.setCellEditor(editor);
			// scrollPane_arbol.add(tree);
			scrollPane_arbol.setViewportView(tree);

		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			consola.setText(consola.getText()+"\n"+e1.getMessage());
		}
	}


	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		//	this. revisarSintaxis();
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				revisarSintaxis();
				//((StyledDocument) editorPane.getDocument()).setCharacterAttributes(0,10, GreenAttr, true);
			}
		});

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				revisarSintaxis();
				//((StyledDocument) editorPane.getDocument()).setCharacterAttributes(0,10, GreenAttr, true);
			}
		});
	}
	public void revisarSintaxis(){

		try {
			hl.removeAllHighlights();
			Highlighter.HighlightPainter painter = new RedZigZagPainter();

			int offset = 0;
			String [] salida = p.getTextLines(this.editorPane.getDocument().getText(0,this.editorPane.getDocument().getLength()));

			for(int i = 0; i < salida.length; i++){
				((StyledDocument) editorPane.getDocument()).setCharacterAttributes(offset,offset + salida[i].length(), BlackAttr, false);
				if(p.esComentario(salida[i])){
				//	System.out.println("1: "+offset+" "+(offset + salida[i].length())+" "+salida[i]);
					((StyledDocument) editorPane.getDocument()).setCharacterAttributes(offset,offset + salida[i].length(), GreenAttr, false);
			//		System.out.println("2: "+offset+" "+(offset + salida[i].length()));

					offset+=salida[i].length() + 1;
					continue;
				}else if(p.esBlank(salida[i])){
					//offset+=salida[i].length() + 1;
				}else if(p.comprobarCaracteresIlegales(salida[i])){
					if(offset>0){
						hl.addHighlight(offset - 1,offset + salida[i].length() , painter );
					}else{
						hl.addHighlight(offset,offset + salida[i].length() , painter );
					}
				}


				offset+=salida[i].length() + 1;
			}

			Highlight[] hlh = hl.getHighlights();
			if( hlh == null || ( hlh.length == 0 && !p.esBlank(this.editorPane.getDocument().getText(0,this.editorPane.getDocument().getLength()) ))){
				this.btnGenerar.setEnabled(true);
			}else{

				this.btnGenerar.setEnabled(false);
			}
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			consola.setText(consola.getText()+"\n"+e1.getMessage());
		}
	}

}

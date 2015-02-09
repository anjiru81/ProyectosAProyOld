package proyectosAproyOld;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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
import javax.swing.tree.TreePath;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JTextPane;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.JSplitPane;

public class ProyOldMoveApp implements ActionListener, DocumentListener{

	private JFrame frame;
	private JTextPane editorPane;
	private Highlighter hl;
	private JButton btnGenerar;
	private JScrollPane scrollPane_arbol;
	private Parser p;
	private SimpleAttributeSet GreenAttr; 
	private SimpleAttributeSet BlackAttr;
	private static JTextPane consola;
	private SimpleAttributeSet ConsolaAttr;
	private JTextField textField;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static CheckTreeManager checkTreeManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
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
		frame.getContentPane().setPreferredSize(new Dimension(0, 500));
		frame.setBounds(100, 100, 818, 648);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel wrap_pane = new JPanel();
	//	frame.getContentPane().add(wrap_pane, BorderLayout.CENTER);
		wrap_pane.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(550, 300));
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

		JButton btnObtenerPath = new JButton("Obtener path");
		btnObtenerPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkTreeManager.getSelectionModel().getFullSelectedPaths();
				TreePath checkedPaths[] = checkTreeManager.getSelectionModel().getSelectionPaths(); 
				for(int i=0;i<checkedPaths.length;i++){
					checkedPaths[i].getPath();
					ProyOldMutableTreeNode selectedNode = ((ProyOldMutableTreeNode)checkedPaths[i].getLastPathComponent());			   
					selectedNode.move();
					checkTreeManager.forceValueChanged();
					//for(int j=0;j<objs.length;j++){
					//System.out.println(objs[j].toString());
					//	ProyOldMoveApp.consola.setText(ProyOldMoveApp.consola.getText()+"Path: "+checkedPaths[i].toString()+"\n");
					//	ProyOldMoveApp.writeInConsola("Path: "+checkedPaths[i].toString());
					//	}
					//ProyOldMoveApp.consola.setText(ProyOldMoveApp.consola.getText()+checkedPaths[i].toString()+"\n");
				}
				//System.out.println("availablePaths.size()" + availablePaths.size());
				//	Iterator<String> it = availablePaths.iterator();
				//while(it.hasNext()){
				//	ProyOldMoveApp.consola.setText(ProyOldMoveApp.consola.getText()+it.next()+"\n");
				//	ProyOldMoveApp.writeInConsola(it.next());
				//}
			}
		});
		panel_botones.add(btnObtenerPath);

		JPanel panel_arbol = new JPanel();
	//	frame.getContentPane().add(panel_arbol, BorderLayout.EAST);
		panel_arbol.setLayout(new BorderLayout(0, 0));

		scrollPane_arbol = new JScrollPane();
		scrollPane_arbol.setPreferredSize(new Dimension(250, 3));
		panel_arbol.add(scrollPane_arbol);

		JPanel panel_accesos = new JPanel();
		frame.getContentPane().add(panel_accesos, BorderLayout.NORTH);

		JLabel lblCredenciales = new JLabel("Credenciales");
		panel_accesos.add(lblCredenciales);

		JRadioButton rbLocal = new JRadioButton("Local");
		rbLocal.addActionListener(this);
		rbLocal.setSelected(true);
		buttonGroup.add(rbLocal);
		panel_accesos.add(rbLocal);

		JRadioButton rbLocalServer = new JRadioButton("Local Servidor");
		rbLocalServer.addActionListener(this);
		buttonGroup.add(rbLocalServer);
		panel_accesos.add(rbLocalServer);

		JRadioButton rdbtnLdap = new JRadioButton("LDAP");
		rdbtnLdap.addActionListener(this);
		buttonGroup.add(rdbtnLdap);
		panel_accesos.add(rdbtnLdap);

		JLabel lblUsuario = new JLabel("Usuario");
		panel_accesos.add(lblUsuario);

		textField = new JTextField();
		panel_accesos.add(textField);
		textField.setColumns(10);
		textField.setEnabled(false);
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		panel_accesos.add(lblContrasea);

		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		panel_accesos.add(passwordField);
		passwordField.setEnabled(false);

		
		JPanel panel = new JPanel();
	//	frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1);

		consola = new JTextPane();
		consola.setMinimumSize(new Dimension(600, 300));
		scrollPane_1.setViewportView(consola);
		//	runtime = new EjecucionRuntime(consola);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setLeftComponent(wrap_pane);
		splitPane_1.setRightComponent(panel_arbol);
		//frame.getContentPane().add(splitPane_1, BorderLayout.WEST);
		splitPane.setTopComponent(splitPane_1);
		splitPane.setBottomComponent(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//	this. revisarSintaxis();
		if(e.getActionCommand().equals("Generar")){
			this.insertarArbol();
			//	runtime.CommandExec("explorer \\\\nasr1\\proyectos");
		}else if(e.getActionCommand().equals("Local")){
			this.passwordField.setEnabled(false);
			this.textField.setEnabled(false);
		}else if(e.getActionCommand().equals("Local Servidor")){
			this.passwordField.setEnabled(true);
			this.textField.setEnabled(true);
			JOptionPane.showMessageDialog(frame,
					"Rellena los datos de conexión antes de introducir usuario y contraseña",
					"",
					JOptionPane.WARNING_MESSAGE);
		}else if(e.getActionCommand().equals("LDAP")){
			this.passwordField.setEnabled(true);
			this.textField.setEnabled(true);
		}	
	}


	@Override
	public void changedUpdate(DocumentEvent arg0) {

	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				revisarSintaxis();
			}
		});

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {


		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				revisarSintaxis();

			}
		});
	}
	public void insertarArbol(){
		JTree tree;
		try {
			tree = new JTree(p.generarArbolEmpresas(p.getTextLines(this.editorPane.getDocument().getText(0,this.editorPane.getDocument().getLength()))));
			ToolTipManager.sharedInstance().registerComponent(tree);
			//	tree.setEditable(true);
			tree.setCellRenderer(new ProyOldMovTreeCellRenderer());

			//	tree.setCellRenderer(new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel)); 
			checkTreeManager = new CheckTreeManager(tree); 
			//	DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
			TreeUtils.expandAll(tree, true);

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

	public static JTextPane getConsola() {
		// TODO Auto-generated method stub
		return consola;
	}
	public static void writeInConsola(String line){
		consola.setText(consola.getText()+line+"\n");
	}

}

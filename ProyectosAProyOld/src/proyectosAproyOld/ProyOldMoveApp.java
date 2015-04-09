package proyectosAproyOld;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

public class ProyOldMoveApp implements ActionListener, DocumentListener, Runnable{

	private JFrame frame;
	private JTextPane editorPane;
	private Highlighter hl;
	private JButton btnGenerar;
	private JScrollPane scrollPane_arbol;
	private Parser p;
	private SimpleAttributeSet GreenAttr; 
	private SimpleAttributeSet BlackAttr;
	private static JTextArea consola;
	private SimpleAttributeSet ConsolaAttr;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static CheckTreeManager checkTreeManager;
	private final PipedInputStream pin=new PipedInputStream();
	private final PipedInputStream pin2=new PipedInputStream();
	private boolean quit;
	private Thread reader;
	private Thread reader2;
	static public String ficheroLog;
//	private Thread errorThrower;
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

		JButton btnObtenerPath = new JButton("Mover");
		btnObtenerPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				
				Thread moveThread = new Thread() {
				      public void run() {
				    	  runMoves();
				      }
				    };
				    moveThread.start();
				  
						
			

				
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


		JPanel panel = new JPanel();
		//	frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1);

		consola = new JTextArea();
		consola.setMinimumSize(new Dimension(600, 300));
		DefaultCaret caret = (DefaultCaret)consola.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
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
		
		JPanel panelHerramientas = new JPanel();
		panel.add(panelHerramientas, BorderLayout.NORTH);
		panelHerramientas.setLayout(new BoxLayout(panelHerramientas, BoxLayout.X_AXIS));
		
		JButton botonGuardar = new JButton("");
		botonGuardar.setToolTipText("Guardar Log");
		botonGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarConsolaAFichero();
			}

			
		});
		botonGuardar.setIcon(new ImageIcon(ProyOldMoveApp.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		botonGuardar.setSelectedIcon(new ImageIcon(ProyOldMoveApp.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		panelHerramientas.add(botonGuardar);
		
		JButton botonBorrar = new JButton("");
		botonBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consola.setText("");
			}
		});
		botonBorrar.setToolTipText("Borrar la consola");
		botonBorrar.setIcon(new ImageIcon(ProyOldMoveApp.class.getResource("/proyectosAproyOld/ico-borrar.gif")));
		panelHerramientas.add(botonBorrar);
		int opcion = JOptionPane.showConfirmDialog(frame,"¿Desea crear fichero de log?", "Crear fichero de log", JOptionPane.YES_NO_OPTION);
		if(opcion == JOptionPane.YES_OPTION ){
			JFileChooser fileChooser = new JFileChooser("C:\\users\\becariosis\\desktop");
			fileChooser.setSelectedFile(new File("log.txt"));
			int seleccion = fileChooser.showSaveDialog(frame);
			
			if (seleccion == JFileChooser.APPROVE_OPTION)
			{ 
				 ficheroLog = fileChooser.getSelectedFile().getAbsolutePath();
				}
		}else{
			ficheroLog = "";
		}
		try
		{
			PipedOutputStream pout=new PipedOutputStream(this.pin);
			System.setOut(new PrintStream(pout,true));
		}
		catch (java.io.IOException io)
		{
			consola.append("Couldn't redirect STDOUT to this console\n"+io.getMessage());
		}
		catch (SecurityException se)
		{
			consola.append("Couldn't redirect STDOUT to this console\n"+se.getMessage());
		}

		try
		{
			PipedOutputStream pout2=new PipedOutputStream(this.pin2);
			System.setErr(new PrintStream(pout2,true));
		}
		catch (java.io.IOException io)
		{
			consola.append("Couldn't redirect STDERR to this console\n"+io.getMessage());
		}
		catch (SecurityException se)
		{
			consola.append("Couldn't redirect STDERR to this console\n"+se.getMessage());
		}

		quit=false; // signals the Threads that they should exit

		// Starting two separate threads to read from the PipedInputStreams
		//
		reader=new Thread(this);
		reader.setDaemon(true);
		reader.start();
		//
		reader2=new Thread(this);
		reader2.setDaemon(true);
		reader2.start();
		//System.out.println("Creada redirección");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//	this. revisarSintaxis();
		if(e.getActionCommand().equals("Generar")){
			this.insertarArbol();
			//	runtime.CommandExec("explorer \\\\nasr1\\proyectos");
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
			JComboBox comboBox = new JComboBox(elements);
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
public void runMoves(){
	checkTreeManager.getSelectionModel().getFullSelectedPaths();
	TreePath checkedPaths[] = checkTreeManager.getSelectionModel().getSelectionPaths(); 
	for(int i=0;i<checkedPaths.length;i++){
		checkedPaths[i].getPath();
		ProyOldMutableTreeNode selectedNode = ((ProyOldMutableTreeNode)checkedPaths[i].getLastPathComponent());			   
		selectedNode.move();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				checkTreeManager.forceValueChanged();
			}
		});
		
		
	}
	
}
	public static JTextArea getConsola() {
		// TODO Auto-generated method stub
		return consola;
	}
	public static void writeInConsola(final String line){

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{

				consola.setText(consola.getText()+line+"\n");
			}
		});

	}
	public synchronized void run()
	{
		try
		{
			while (Thread.currentThread()==reader)
			{
				try { this.wait(100);}catch(InterruptedException ie) {}
				if (pin.available()!=0)
				{
					String input=this.readLine(pin);
					consola.append(input);
					if(!ProyOldMoveApp.ficheroLog.equals("")){     
		                File archivo = new File(ProyOldMoveApp.ficheroLog);
		                BufferedWriter bw;
		                if(archivo.exists()) {
		                	bw = new BufferedWriter(new FileWriter(archivo,true));
		                    bw.write(input+"\r\n");
		                } else {
		                	bw = new BufferedWriter(new FileWriter(archivo));
		                    bw.write(input+"\r\n");
		                }
		                bw.close();
		            }
				}
				if (quit) return;
			}

			while (Thread.currentThread()==reader2)
			{
				try { this.wait(100);}catch(InterruptedException ie) {}
				if (pin2.available()!=0)
				{
					String input=this.readLine(pin2);
					consola.append(input);
					if(!ProyOldMoveApp.ficheroLog.equals("")){     
		                File archivo = new File(ProyOldMoveApp.ficheroLog);
		                BufferedWriter bw;
		                if(archivo.exists()) {
		                	bw = new BufferedWriter(new FileWriter(archivo,true));
		                    bw.write(input+"\r\n");
		                } else {
		                	bw = new BufferedWriter(new FileWriter(archivo));
		                    bw.write(input+"\r\n");
		                }
		                bw.close();
		            }
				}
				if (quit) return;
			}
		} catch (Exception e)
		{
			consola.append("\nConsole reports an Internal error.");
			consola.append("The error is: "+e);
		}

		// just for testing (Throw a Nullpointer after 1 second)
//		if (Thread.currentThread()==errorThrower)
//		{
//			try { this.wait(1000); }catch(InterruptedException ie){}
//			throw new NullPointerException("Application test: throwing an NullPointerException It should arrive at the console");
//		}

	}

	public synchronized String readLine(PipedInputStream in) throws IOException
	{
		String input="";
		do
		{
			int available=in.available();
			if (available==0) break;
			byte b[]=new byte[available];
			in.read(b);
			input=input+new String(b,0,b.length);
		}while( !input.endsWith("\n") &&  !input.endsWith("\r\n") && !quit);
		return input;
	}
	private void guardarConsolaAFichero() {
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser("C:\\users\\becariosis\\desktop");
		fileChooser.setSelectedFile(new File("log.txt"));
		int seleccion = fileChooser.showSaveDialog(consola);
		
		if (seleccion == JFileChooser.APPROVE_OPTION)
		{
			//System.out.println(fileChooser.getSelectedFile());
		 //  File fichero = fileChooser.getSelectedFile();
		   
		   try{

			      //Creamos un Nuevo objeto FileWriter dandole
			      //como parámetros la ruta y nombre del fichero
			      FileWriter fichero = new FileWriter(fileChooser.getSelectedFile());

			      //Insertamos el texto creado y si trabajamos
			      //en Windows terminaremos cada línea con "\r\n"
			      fichero.write(consola.getText());

			      //cerramos el fichero
			      fichero.close();

			    }catch(Exception ex){
			      ex.printStackTrace();
			    }
		   // Aquí debemos abrir el fichero para escritura
		   // y salvar nuestros datos.
		  
		}
	}
}

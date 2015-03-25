package proyectosAproyOld;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;


public class Parser {

	private LinkedList<String> rutas_origen;
	private String ruta_destino;
	public Parser(){
		Properties propiedades = new Properties();
	    InputStream entrada = null;
	    rutas_origen = new LinkedList<String>();
	    /*
	     * Aquí se carga desde el fichero de propiedades las rutas.
	     * 
	     * num_rutas es el número de rutas de origen
	     * 
	     * el formato de cada ruta será rutaN=<ruta>
	     * 
	     * donde N es un número natural de 0 a N...
	     * 
	     * ruta_destino es la ruta de destino
	     * 
	     * */
	     
	    try {
	        entrada = this.getClass().getResourceAsStream("configuration.properties");
	        // cargamos el archivo de propiedades
	        propiedades.load(entrada);
	        // obtenemos las propiedades y las imprimimos
	        int num_rutas=Integer.parseInt(propiedades.getProperty("num_rutas"));
	        for(int i=0;i<num_rutas;i++){
	        	rutas_origen.add(propiedades.getProperty("ruta"+i));
	        }
	        ruta_destino = propiedades.getProperty("ruta_destino");
//	       
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } finally {
	        if (entrada != null) {
	            try {
	                entrada.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	//Primero hay que haber pasado un trim() a linea
	public boolean esComentario(String linea){
		Pattern pat = Pattern.compile("%.*");
		Matcher mat = pat.matcher(linea);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}

	}
	public boolean esBlank(String linea){
		Pattern pat = Pattern.compile("\\s*\t*\n*");
		Matcher mat = pat.matcher(linea);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}

	}
	public Empresa parseaEmpresa(String linea) throws Exception{
		Empresa empresa = null;
		String [] campos = linea.split("\\s*:\\s*");

		if(campos.length !=2){
			throw new Exception("Error en el parseado. Ha introducido un símbolo : de más");			
		}else{
			String nombreEmpresa = campos[0].trim();
			String proyectos = campos[1].trim();
			
			String [] campos2 = proyectos.split("\\s*;\\s*");
			empresa = new Empresa(nombreEmpresa);
			empresa.setRutas_Origen(rutas_origen);
			empresa.setRuta_destino(ruta_destino);
			empresa.find();
			for(int i = 0;i < campos2.length; i++){
				empresa.addProyecto(campos2[i]);
			}
			empresa.checkIfProjectMoved();
		}
		
		return empresa;
	}

	public void limpiarCampos(String [] campos) throws Exception{
		for(int i=0;i<campos.length; i++){
			if(campos[i].trim().equals("")){
				throw new Exception("Error en la línea de designación de proyectos. Hay un proyecto en blanco o varios ;. Revíselo.");
			}
		}
	}
	public String [] getTextLines(String text){
		return text.split("\n");
		
	}
	public boolean comprobarCaracteresIlegales(String st){
		//Pattern pat = Pattern.compile(".*;.*|.*%.*");
		Pattern pat = Pattern.compile("[a-z0-9A-Z_\\-\\sñÑ&áéíóúàèìòùäëïöüçÇ']+\\s*:\\s*([0-9]+\\s*;\\s*)*([0-9]+\\s*(;\\s*)?)");
		//Pattern pat = Pattern.compile(";");
		Matcher mat = pat.matcher(st);
		if (mat.matches()) {
	        return false;
	     } else {
	        return true;
	     }
	}
	public DefaultMutableTreeNode generarArbolEmpresas(String [] lineas){
		Calendar c = Calendar.getInstance();
		String dia = Integer.toString(c.get(Calendar.DATE));
		String mes = Integer.toString(c.get(Calendar.MONTH) + 1);
		String annio = Integer.toString(c.get(Calendar.YEAR));
		ProyOldMutableTreeNode root = new ProyOldMutableTreeNode("Movimiento día "+dia+" / "+mes+" / "+annio);
		for (int i = 0; i < lineas.length; i++){
			if(this.esBlank(lineas[i])|this.esComentario(lineas[i])){
				continue;
				}else{
				try {
					root.add(this.parseaEmpresa(lineas[i]));
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return root;
	}
	public void lookForInFolder(String folder){
	//	 SmbFile archivo = null;
	//	UnidadRed ur = new UnidadRed();		
	//	archivo = new SmbFile(unidadRed.apuntarPath());
	}
	public static void main(String[] args) throws Exception {
		
		Parser p = new Parser();
		//String linea = "          Mutua Madrileña   : 130397; 130396;130428;";
//		String linea = "          Mutua Madrileña   : 130397; 130396";
//		System.out.println(p.comprobarCaracteresIlegales(linea));
//		String [] salida = p.getTextLines("kasjdklajsdkljasd\n jkasjd");
//		System.out.println("kasjdklajsdkljasd\n jkasjd");
//		for(int i = 0; i < salida.length; i++){
//		System.out.println(salida[i] + " " + i);
//		}
//		p.parseaEmpresa("hola:  123;   456   ;   ");
		String blank = "\n     \n \t";
		String comentario = "";
		System.out.println(blank+"blank : " + p.esBlank(blank));
		System.out.println("comentario : "+p.esComentario(comentario));
		boolean prueba = true;
		prueba = prueba | false;
		System.out.println(prueba);
		//		String [] campos = linea.split("\\s*:\\s*");
		//
		//		if(campos.length !=2){
		//			throw new Exception("Error en el parseado. Ha introducido un símbolo : de más");			
		//		}
		//String empresa = campos[0].trim();
		//		String empresa = campos[0];
		//		System.out.println(empresa);
		//		
		//		String [] proyectos = campos[1].split("\\s*;\\s*");
		//		p.limpiarCampos(proyectos);
		//		while(j<proyectos.length){
		//
		//			System.out.println(proyectos[j]);
		//
		//			j++;
		//
		//		}
		//		j = 0;
		//		while(j<campos.length){
		//
		//			System.out.println(campos[j]);
		//
		//			j++;
		//
		//		}
	}

}

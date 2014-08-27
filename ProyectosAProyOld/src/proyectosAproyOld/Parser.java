package proyectosAproyOld;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.tree.DefaultMutableTreeNode;

import jcifs.smb.SmbFile;

public class Parser {

	boolean OK = false;
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
	public Empresa parseaEmpresa(String linea) throws Exception{
		Empresa empresa = null;
		String [] campos = linea.split("\\s*:\\s*");

		if(campos.length !=2){
			throw new Exception("Error en el parseado. Ha introducido un s�mbolo : de m�s");			
		}else{
			String nombreEmpresa = campos[0].trim();
			String proyectos = campos[1].trim();
			
			String [] campos2 = proyectos.split("\\s*;\\s*");
			empresa = new Empresa(nombreEmpresa);
			for(int i = 0;i < campos2.length; i++){
				empresa.addProyecto(campos2[i]);
			}
		}
		empresa.setOK(OK);
		OK=!OK;
		return empresa;
	}

	public void limpiarCampos(String [] campos) throws Exception{
		for(int i=0;i<campos.length; i++){
			if(campos[i].trim().equals("")){
				throw new Exception("Error en la l�nea de designaci�n de proyectos. Hay un proyecto en blanco o varios ;. Rev�selo.");
			}
		}
	}
	public String [] getTextLines(String text){
		return text.split("\n");
		
	}
	public boolean comprobarCaracteresIlegales(String st){
		//Pattern pat = Pattern.compile(".*;.*|.*%.*");
		Pattern pat = Pattern.compile("[a-z0-9A-Z_\\-\\s��&�����������������']+\\s*:\\s*([0-9]+\\s*;\\s*)*([0-9]+\\s*(;\\s*)?)");
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
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Movimiento d�a "+dia+" / "+mes+" / "+annio);
		for (int i = 0; i < lineas.length; i++){
			if(lineas[i].equals("")|this.esComentario(lineas[i])){
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
		// TODO Auto-generated method stub
		Parser p = new Parser();
		int j = 0;

		//String linea = "          Mutua Madrile�a   : 130397; 130396;130428;";
		String linea = "          Mutua Madrile�a   : 130397; 130396";
		System.out.println(p.comprobarCaracteresIlegales(linea));
		String [] salida = p.getTextLines("kasjdklajsdkljasd\n jkasjd");
		System.out.println("kasjdklajsdkljasd\n jkasjd");
		for(int i = 0; i < salida.length; i++){
		System.out.println(salida[i] + " " + i);
		}
		p.parseaEmpresa("hola:  123;   456   ;   ");
		//		String [] campos = linea.split("\\s*:\\s*");
		//
		//		if(campos.length !=2){
		//			throw new Exception("Error en el parseado. Ha introducido un s�mbolo : de m�s");			
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

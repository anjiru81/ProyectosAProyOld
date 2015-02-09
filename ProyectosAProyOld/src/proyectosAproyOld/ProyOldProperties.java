package proyectosAproyOld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Properties;

public class ProyOldProperties {

	private LinkedList<String> rutas_origen;
	private String ruta_destino;
	public ProyOldProperties() {
		 rutas_origen = new LinkedList<String>();
		 setRuta_destino("");
	}
	public void loadParams() {
	    Properties propiedades = new Properties();
	    InputStream is = null;
	 
	    // First try loading from the current directory
	    try {
	        File f = new File("configuracion.properties");
	        is = new FileInputStream( f );
	    }
	    catch ( Exception e ) { is = null; }
	 
	    try {
	        if ( is == null ) {
	            // Try loading from classpath
	            is = getClass().getResourceAsStream("configuracion.properties");
	        }
	 
	        // Try loading properties from the file (if found)
	        propiedades.load( is );
	        int num_rutas=Integer.parseInt(propiedades.getProperty("num_rutas"));
	        for(int i=0;i<num_rutas;i++){
	        	rutas_origen.add(propiedades.getProperty("ruta"+i));
	        }
	        setRuta_destino(propiedades.getProperty("ruta_destino"));
	    }
	    catch ( Exception e ) { }
	   
	}
	public void saveParamChanges() {
	    try {
	        Properties props = new Properties();
	        String serverAddr = null;
			props.setProperty("ServerAddress", serverAddr);
	        String serverPort = null;
			props.setProperty("ServerPort", ""+serverPort);
	        String threadCnt = null;
			props.setProperty("ThreadCount", ""+threadCnt);
	        File f = new File("configuracion.properties");
	        OutputStream out = new FileOutputStream( f );
	        props.store(out, "This is an optional header comment string");
	    }
	    catch (Exception e ) {
	        e.printStackTrace();
	    }
	}
	public String getRuta_destino() {
		return ruta_destino;
	}
	public void setRuta_destino(String ruta_destino) {
		this.ruta_destino = ruta_destino;
	}
}

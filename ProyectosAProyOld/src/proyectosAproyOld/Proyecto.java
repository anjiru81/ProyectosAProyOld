package proyectosAproyOld;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public class Proyecto extends Empresa {

	private String empresa;


	public Proyecto(String nombre, String empresa) {
		super(nombre);
		this.empresa = empresa;
		// TODO Auto-generated constructor stub
	}
	public void find(){
		Iterator<String> rutas_it = rutas_origen.iterator();
		while(rutas_it.hasNext()){
			String ruta = rutas_it.next();
			if(!(new File(ruta+"\\"+this.getRuta()).exists())){
				msg=msg+"No existe en "+ruta+"; ";
				existeEnOrigen = false;
				errorEnRutas = true;
			}else{
				existeEnOrigen = true;
				errorEnRutas = false;
				msg=msg+"Existe en "+ruta+"; ";
				this.rutaOrigen = ruta+"\\"+this.getRuta();
				break;
			}
		}
		if(!(new File(ruta_destino+"\\"+this.getRuta()).exists())){
			msg=msg+"No existe en "+ruta_destino+"; ";
			existeEnDestino = false;
			errorEnRutas = true;
			if(((Empresa)this.getParent()).ExisteEnOrigen()){
				this.rutaDestino = ruta_destino+"\\"+this.getRuta();
			}
		}else{
			existeEnDestino = true;
			errorEnRutas = errorEnRutas | false;
			msg=msg+"Existe en destino";
			this.rutaDestino = ruta_destino+"\\"+this.getRuta();
			this.setEnabled(false);
		}
	}
	public void move(){
		if(!this.ExisteEnDestino() & this.ExisteEnOrigen()){
			String command = "robocopy.exe " +this.rutaOrigen +" "+this.rutaDestino +" /MOVE /MIR /XX /XC /XN /XO";
			ProyOldMoveApp.writeInConsola(command);
			//System.out.println(command);
			EjecucionRuntime exec = new EjecucionRuntime();
			exec.CommandExec(command);
			this.find();
		}else{
			ProyOldMoveApp.writeInConsola("No se ha movido el proyecto "+this.getRuta());
		}
	}
	public String getRuta(){

		return empresa+"\\"+(String)this.getUserObject();
	}
	public Vector<String> getAllAvailablePaths() {
		Vector<String> availablePaths = new Vector<String>();
		if(this.isEnabled()){
			availablePaths.add(this.rutaOrigen);
		}
		return availablePaths;
	}
}
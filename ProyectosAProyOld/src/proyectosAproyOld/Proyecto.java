package proyectosAproyOld;

public class Proyecto extends Empresa {

	private String empresa;


	public Proyecto(String nombre, String empresa) {
		super(nombre);
		this.empresa = empresa;
		// TODO Auto-generated constructor stub
	}


public String getRuta(){
	
	return empresa+"\\"+(String)this.getUserObject();
}
}
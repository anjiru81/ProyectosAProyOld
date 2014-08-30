package proyectosAproyOld;

import java.io.File;

import jcifs.smb.*;

public class UnidadRed 
{
	private String user;
	private String password;
	private String host;
	private String unidad;  
	private String ip;

	public UnidadRed(String host, String ip, String user, String password, String unidad)
	{

		this.setHost(host);
		this.setIp(ip);
		this.setUser(user);
		this.setPassword(password);
		this.setUnidad(unidad);
	}
	public UnidadRed(String unidad){
		this.setUnidad(unidad);
	}

	public void setIp (String ip)                 { this.ip = ip;  }
	public void setUser (String user)             { this.user = user; }
	public void setPassword (String password)     { this.password = password; }
	public void setHost (String host)             { this.host = host; }
	public void setUnidad (String unidad)         { this.unidad = unidad; }

	public void conectar()
	{
		try
		{
			// PropertyResourceBundle resb = (PropertyResourceBundle) 
			//               ResourceBundle.getBundle("properties.unidad");


			jcifs.Config.setProperty(this.host,this.ip);


		}
		catch (Exception e) {}
	}

	public String apuntarPath ()
	{
		conectar();
		return "smb://" + this.user + ":" + this.password + "@" + this.host + "/" + 
		this.unidad + "/";
	}
	public boolean copyFiles(SmbFile sFile, String fileContent, String fileName) {
		boolean successful = false;
		try{

			SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
			sfos.write(fileContent.getBytes());

			successful = true;
			System.out.println("Successful " + successful);
		} catch (Exception e) {
			successful = false;
			e.printStackTrace();
		}
		return successful;
	}
	public static void main(String[] args) {
		// UnidadRed ur = new UnidadRed();
		SmbFile archivo = null;
		File fichero = null;
		// String fichero = ...

		try
		{
			// PropertyResourceBundle resb = (PropertyResourceBundle) 
			//                                        ResourceBundle.getBundle("properties.unidad");
			//UnidadRed unidadRed = new UnidadRed();
			//  archivo = new SmbFile(unidadRed.apuntarPath());
			//  archivo = new SmbFile(unidadRed.apuntarPath()+ "/readme.txt");  
			// System.out.println(unidadRed.copyFiles(archivo,"Esto es un fichero de texto","readme.txt"));
			fichero = new File("C:\\Users\\Ángel\\git");
			String[]lista = fichero.list();
			// String[]lista = archivo.list();
			for(int i=0;i<lista.length;i++){
				System.out.println(lista[i]);
			}
			//	 .... aquí haz tus operaciones con ese archivo ... 
			// date cuenta que la llamada a unidadRed.apuntarPath() conecta con la unidad de red y apunta al path básico

		}

		catch (Exception e) 
		{

			if(e.getMessage().equalsIgnoreCase("Logon failure: unknown user name or bad password.")){
				System.out.println(e.getMessage());
			}else{
				e.printStackTrace();
			}
		}
	}


}


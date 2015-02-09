package proyectosAproyOld;

import java.io.*;

public class EjecucionRuntime {
    
   // private JTextPane consola;
	/** Creates a new instance of PruebaRuntime */
    public EjecucionRuntime() 
    {
     //  this.consola = consola;
    }
    public void CommandExec(String command){
    	 try
         {
             // Se lanza el ejecutable.
             Process p=Runtime.getRuntime().exec ("cmd /c "+command);
             
             // Se obtiene el stream de salida del programa
             InputStream is = p.getInputStream();
             InputStream es = p.getErrorStream();
             /* Se prepara un bufferedReader para poder leer la salida más comodamente. */
             BufferedReader br = new BufferedReader (new InputStreamReader (is));
             BufferedReader brerror = new BufferedReader (new InputStreamReader (es));
             // Se lee la primera linea
             String aux = br.readLine();
             String aux_error = brerror.readLine();
          
             // Mientras se haya leido alguna linea
             while (aux!=null)
             {
                 // Se escribe la linea en pantalla
                // System.out.println (aux);
            	 ProyOldMoveApp.writeInConsola(aux);
                // consola.setText(consola.getText()+aux+"\n");
                 
                 // y se lee la siguiente.
                 aux = br.readLine();
             }
             while (aux_error!=null)
             {
                 // Se escribe la linea en pantalla
                // System.out.println (aux);
            	 ProyOldMoveApp.writeInConsola(aux_error);
                // consola.setText(consola.getText()+aux+"\n");
                 
                 // y se lee la siguiente.
            	 aux_error = brerror.readLine();
             }
         } 
         catch (Exception e)
         {
             // Excepciones si hay algún problema al arrancar el ejecutable o al leer su salida.*/
             e.printStackTrace();
         }
    }
} 
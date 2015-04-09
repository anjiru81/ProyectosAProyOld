package proyectosAproyOld;

import java.io.*;

import javax.swing.JFileChooser;

public class EjecucionRuntime {
    
   private Process p;
// private JTextPane consola;

	/** Creates a new instance of PruebaRuntime */
 
    public EjecucionRuntime() 
    {
     
    }
   
    public void CommandExec(String command){
    	 try
         {
             // Se lanza el ejecutable.
           // 
    	//	  p=Runtime.getRuntime().exec ("cmd /k "+command+" > c:\\users\\becariosis\\desktop\\salida.txt");
    		  p=Runtime.getRuntime().exec ("cmd /c "+command);
             
             ReadStream s1 = new ReadStream("stdin", p.getInputStream ());
             ReadStream s2 = new ReadStream("stderr", p.getErrorStream ());
             s1.start ();
             s2.start ();
             p.waitFor();        
             } catch (Exception e) {  
             e.printStackTrace();  
             } finally {
                 if(p != null)
                     p.destroy();
             }
             
             
//             
//             // Se obtiene el stream de salida del programa
//             InputStream is = p.getInputStream();
//             InputStream es = p.getErrorStream();
//             /* Se prepara un bufferedReader para poder leer la salida más comodamente. */
//             BufferedReader br = new BufferedReader (new InputStreamReader (is));
//             BufferedReader brerror = new BufferedReader (new InputStreamReader (es));
//             // Se lee la primera linea
//             String aux = br.readLine();
//             String aux_error = brerror.readLine();
//          
//             // Mientras se haya leido alguna linea
//             while (aux!=null)
//             {
//                 // Se escribe la linea en pantalla
//                // System.out.println (aux);
//            	 ProyOldMoveApp.writeInConsola(aux);
//                // consola.setText(consola.getText()+aux+"\n");
//                 
//                 // y se lee la siguiente.
//                 aux = br.readLine();
//             }
//             System.out.println("Ha terminado el proceso");
////             while (aux_error!=null)
////             {
////                 // Se escribe la linea en pantalla
////                // System.out.println (aux);
////            	 ProyOldMoveApp.writeInConsola(aux_error);
////                // consola.setText(consola.getText()+aux+"\n");
////                 
////                 // y se lee la siguiente.
////            	 aux_error = brerror.readLine();
////             }
//         } 
//         catch (Exception e)
//         {
//             // Excepciones si hay algún problema al arrancar el ejecutable o al leer su salida.*/
//             e.printStackTrace();
//         }
    }
} 
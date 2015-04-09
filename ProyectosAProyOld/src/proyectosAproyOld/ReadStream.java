package proyectosAproyOld;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadStream implements Runnable {
    String name;
    InputStream is;
    Thread thread;
	     
    public ReadStream(String name, InputStream is) {
        this.name = name;
        this.is = is;
       
    }       
    public void start () {
        thread = new Thread (this);
        thread.start ();
    }       
    public void run () {
        try {
            InputStreamReader isr = new InputStreamReader (is);
            BufferedReader br = new BufferedReader (isr);   
            while (true) {
                String s = br.readLine ();
                if (s == null) break;
                System.out.println ("[" + name + "] " + s);
               // ProyOldMoveApp.writeInConsola ("[" + name + "] " + s);
                
           if(!ProyOldMoveApp.ficheroLog.equals("")){     
                File archivo = new File(ProyOldMoveApp.ficheroLog);
                BufferedWriter bw;
                if(archivo.exists()) {
                	bw = new BufferedWriter(new FileWriter(archivo,true));
                    bw.write(s+"\r\n");
                } else {
                	bw = new BufferedWriter(new FileWriter(archivo));
                    bw.write(s+"\r\n");
                }
                bw.close();
            }
                //Creada redirección

                
       //         FileWriter fichero = new FileWriter("C:\\users\\becariosis\\desktop\\logjava.txt");

			      //Insertamos el texto creado y si trabajamos
			      //en Windows terminaremos cada línea con "\r\n"
			//      fichero.write(s);

			      //cerramos el fichero
			//      fichero.close();
                
                
                
                
            }
            is.close ();    
        } catch (Exception ex) {
            System.out.println ("Problem reading stream " + name + "... :" + ex);
           // ProyOldMoveApp.writeInConsola("Problem reading stream " + name + "... :" + ex);
            ex.printStackTrace ();
        }
    }
}
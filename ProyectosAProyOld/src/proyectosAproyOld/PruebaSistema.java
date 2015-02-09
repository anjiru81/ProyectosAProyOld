package proyectosAproyOld;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;


public class PruebaSistema {
	static String compara (String st1, String [] st2){
		String salida = "";
		int comp_previa = 0;
		for(int i=0;i<st2.length;i++){
			if(i == 0){
				comp_previa = st1.compareToIgnoreCase(st2[i]);
				salida = st2[i];
			}else if(comp_previa > st1.compareToIgnoreCase(st2[i])){
				comp_previa = st1.compareToIgnoreCase(st2[i]);
				salida = st2[i];
			}

		}

		return salida;
	}
	static void selectionSort(String str, String [] a, int l, int r){

		for (int i=l;i<r;i++){
			int min=i;
			for(int j=i+1; j<=r; j++){
				if(computeLevenshteinDistance(a[j],str,3,3,6) < computeLevenshteinDistance(a[min],str,2,3,6)){
					min = j;
				}
				String temp = a[i];
				a[i] = a[min];
				a[min] = temp;
			}
		}
	}

	private static int minimum(int a, int b, int c) {
		if(a <= b && a <= c)
			return a;
		if(b <= a && b <= c)
			return b;
		return c;
	}

	public static int computeLevenshteinDistance(String str1, String str2, int add, int delete, int substitute) {
		return computeLevenshteinDistance(str1.toCharArray(), str2.toCharArray(), add, delete, substitute);
	}

	private static int computeLevenshteinDistance(char[] str1, char[] str2, int insert, int delete, int substitute) {
		int[][] distance = new int[str1.length+1][str2.length+1];
		for(int i = 0; i <= str1.length; i++)
			distance[i][0] = i * delete;
		for(int j = 0; j <= str2.length; j++)
			distance[0][j] = j * insert;
		for(int i = 1; i <= str1.length; i++) {
			for(int j = 1; j <= str2.length; j++) {
				distance[i][j]= minimum(distance[i-1][j] + delete, distance[i][j-1] + insert, distance[i-1][j-1] + ((str1[i-1] == str2[j-1]) ? 0 : substitute));
			}
		}
		return distance[str1.length][str2.length];
	}
	static LinkedList<String> listarDirectorios(String [] a, String path){
		LinkedList<String> salida = new LinkedList<String>(Arrays.asList(a));
		ListIterator<String> it = salida.listIterator();
		while(it.hasNext()){
			String st = it.next();
			System.out.println(path+st);
			File fl = new File(path+st);
			if(!fl.isDirectory()){
				it.remove();
			}
		}
		return salida;
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		File dir = new File("p:\\");
		//File dir = new File("//linguawss/paraFacturar/");
		System.out.println(dir.getPath());
		String[] ficheros = dir.list();
//		for(int i=0;i<ficheros.length;i++){
//			System.out.println(ficheros[i]);
//		}
		if (ficheros == null)
			System.out.println("No hay ficheros en el directorio especificado");
		else {
			//	String cmp = "Telefonica";

			//selectionSort(cmp,ficheros,0,ficheros.length-1);
			LinkedList<String> ll = listarDirectorios(ficheros,dir.getPath());
			// for (int x=0;x<ficheros.length;x++){
			//File f1 = new File(dir.getPath()+ficheros[x]);

			//System.out.println(ficheros[x]);


			//   System.out.println(computeLevenshteinDistance(ficheros[x],cmp,2,3,6)+"  "+ficheros[x]);

			// }
			System.out.println(ll.toString());
		}


		//	    String[] command =
		//	        {
		//	            "cmd",
		//	        };
		//	        Process p = Runtime.getRuntime().exec(command);
		//	      Thread therr = new Thread(new SyncPipe(p.getErrorStream(), System.err));
		//	      therr.start();
		//	      Thread thout = new Thread(new SyncPipe(p.getInputStream(), System.out));
		//	      thout.start();
		//	        PrintWriter stdin = new PrintWriter(p.getOutputStream());
		//	        stdin.println("robocopy");
		//	        // write any other commands you want here
		//	        stdin.close();
		//	        int returnCode = p.waitFor();
		//	       
		//	        System.out.println("Return code = " + returnCode);

	}
	public static void leerFichero(String fichero)
	{
		try{
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream(fichero);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console
				System.out.println (strLine);
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}


}
class SyncPipe implements Runnable
{
	public SyncPipe(InputStream istrm, OutputStream ostrm) {
		istrm_ = istrm;
		ostrm_ = ostrm;
	}
	public void run() {
		try
		{
			final byte[] buffer = new byte[1024];
			for (int length = 0; (length = istrm_.read(buffer)) != -1; )
			{
				ostrm_.write(buffer, 0, length);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	private final OutputStream ostrm_;
	private final InputStream istrm_;
}
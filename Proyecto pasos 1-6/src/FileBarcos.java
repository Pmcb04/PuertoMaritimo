import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/***
 * Class FileBarcos
 * @author Pedro Miguel Carmona Broncano
 *
 */
public class FileBarcos {
	
	private static FileWriter file;
	private static Date date;
	
	
	/***
	 * Contructor por defecto de la clase FileBarcos
	 * @throws IOException
	 */
	FileBarcos() throws IOException{
		file = new FileWriter(new File("barcos.log"));
		date = new Date();
		System.out.println("Fichero barcos.log creado...");
	}
	
	/***
	 * Metodo que escribe en el fichero barcos.log
	 * @param s
	 */
	public static synchronized void  print(String s) {
		try{
			file.write("[" + date.toString() + "] " + s + "\n");
		}catch(IOException e) {
			System.out.println("++++++++++++++++EXCEPTION FILE++++++++++++++++");
		};
		
	}
	
	/***
	 * Metodo para cerrar el fichero
	 */
	public static synchronized void closeFile() {
		try {
			file.close();
			System.out.println("Fichero barcos.log completado !!!");
		} catch (Exception e) {System.out.println("++++++++++++++++EXCEPTION CLOSE FILE++++++++++++++++");}
	}

}

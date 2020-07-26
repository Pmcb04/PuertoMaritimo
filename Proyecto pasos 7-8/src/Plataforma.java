import java.util.concurrent.SynchronousQueue;

/***
 * Class Plataforma
 * @author Pedro Miguel Carmona Broncano
 *
 */
public class Plataforma {

	private static SynchronousQueue<Integer> azucar;
	private static SynchronousQueue<Integer> sal;
	private static SynchronousQueue<Integer> harina;
	
	/***
	 * Constructor parametrizado de la clase Plataforma
	 * @param numGruas numero de gruas que va a tener la plataforma para sacar los productos
	 */
	Plataforma(int numGruas) {
		azucar = new SynchronousQueue<Integer>();
		sal = new SynchronousQueue<Integer>();
		harina = new SynchronousQueue<Integer>();

	}
	
	/***
	 * Metodo para poner element en la plataforma
	 * @param element elemento a poner en la plataforma
	 */
	public static void set(int element) {
			
			putElement(element);
			printSet(element);				
	}
	
	/***
	 * Metodo para sacar element de la plataforma
	 * @param element elemento a sacar de la plataforma 
	 */
	public static void get(int element) {
		
			takeElement(element);
			printGet(element);
	}
	
	/***
	 * Metodos para indicar que elemento se esta poniendo en la plataforma
	 * @param element elemento que se esta poniendo en la plataforma
	 */
	private static void printSet(int element) {
		
		switch (element) {
		case 0: FileBarcos.print("Poniendo en plataforma: Harina"); break;
		case 1: FileBarcos.print("Poniendo en plataforma: Sal"); break;
		case 2: FileBarcos.print("Poniendo en plataforma: Azucar"); break;
		}
			
	}
	
	/***
	 * Metodo para poner en la plataforma el element
	 * @param element elemento a poner en la plataforma
	 */
	private static void putElement(int element) {
		try {
			switch (element) {
			case 0: harina.put(element); break;
			case 1: sal.put(element); break;
			case 2: azucar.put(element); break;
			}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	/***
	 * Metodo para indicar que elemento se esta sacando de la plataforma
	 * @param element elemento que se esta sacando de la plataforma
	 */
	private static void printGet(int element) {
		
		switch (element) {
		case 0: FileBarcos.print("Sacando de la plataforma: Harina"); break;
		case 1: FileBarcos.print("Sacando de la plataforma: Sal"); break;
		case 2: FileBarcos.print("Sacando de la plataforma: Azucar"); break;
		}
	}
	
	
	/***
	 * Metodo para coger element de la plataforma
	 * @param element elemento a coger de la plataforma
	 */
	private static void takeElement(int element) {
		try {
			switch (element) {
			case 0: harina.take(); break;
			case 1: sal.take();  break;
			case 2: azucar.take(); break;
			}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
 }
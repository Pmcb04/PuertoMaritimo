import java.util.concurrent.locks.*;

/***
 * Class Plataforma
 * @author Pedro Miguel Carmona Broncano
 *
 */
public class Plataforma {
	
	private static Lock monitor = new ReentrantLock();
	private static int productoPlataforma = -1;
	private static Condition barco = monitor.newCondition();	
	private static Condition[] productos;
	
	/***
	 * Constructor parametrizado de la clase Plataforma
	 * @param numGruas numero de gruas que va a tener la plataforma para sacar los productos
	 */
	Plataforma(int numGruas) {
		productos = new Condition[numGruas]; // cada producto tendra su grua que lo recoga y por cada grua creamos una condicion
		for (int i = 0; i < productos.length; i++) 
			productos[i] = monitor.newCondition();
	}
	
	/***
	 * Metodo para poner element en la plataforma
	 * @param element elemento a poner en la plataforma
	 * @throws InterruptedException 
	 */
	public static void set(int element) throws InterruptedException {
		
		monitor.lock();	
		try {
			
			while ( !BarcoMercader.isEmpty() && productoPlataforma >= 0) barco.await();
					printSet(element);		
					productoPlataforma = element; // ponemos el producto en la plataforma
					productos[element].signal();
					
					
		} finally {
			monitor.unlock();
		}
	}
	
	/***
	 * Metodo para sacar element de la plataforma
	 * @param element elemento a sacar de la plataforma 
	 * @throws InterruptedException
	 */
	public static void get(int element) throws InterruptedException {
		
		monitor.lock();
		try {
			
			while(!BarcoMercader.isEmpty() && (productoPlataforma < 0 || productoPlataforma != element)) productos[element].await(); 
					if(productoPlataforma == element) printGet(element);
					productoPlataforma = -1; // quitamos de la plataforma el producto que estaba
					barco.signal();
					
				
		} finally {
			monitor.unlock();
		}
	}

	
	/***
	 * Metodo para despertar a todos las gruas
	 */
	public static void signalGruas() {
		for (int i = 0; i < productos.length; i++) 
			try {
				monitor.lock();
				productos[i].signal();
				monitor.unlock();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
 }
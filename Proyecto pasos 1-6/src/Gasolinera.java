import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import java.lang.Math;
/***
 * Class Gasolinera
 * @author Pedro Miguel Carmona Broncano
 *
 */
public class Gasolinera {
	
	private static int repostajeAgua; // repostaje estandar para agua
	private static int repostajeGasoil; // repostaje estandar para gasoil
	private static int[] vGasoil; // indicador para saber cuantos litros le quedan a cada repostaje
	private static int barcosBloqueados; // numero de barcos bloquedos para repostar
	private static int barcosGasolinera; // numero de barcos que han quedan en la gasolinera
	private static Semaphore [] sGasoil; // semaforos de cada repostaje
	private static Semaphore[] sBarcos; // semaforos de cada barco
	private static Semaphore sStop; // semaforo para esperar hasta que vengan todos los barcos
	private static Semaphore mutex; // exclusion mutua
	
	/***
	 * Constructor parametrizado de la clase Gasolinera (repostajeAgua y repostajeGasoil por defecto a 1000)
	 * @param numPetroleros numero de barcos petroleros que van a poder repostar
	 */
	Gasolinera(int numPetroleros){
		vGasoil = new int[numPetroleros];
		sGasoil = new Semaphore[numPetroleros];
		sBarcos = new Semaphore[numPetroleros];
		barcosBloqueados = 0;
		barcosGasolinera = numPetroleros;
		repostajeAgua = 1000;
		repostajeGasoil = 1000;
		mutex = new Semaphore(1); // para garantizar la exclusion mutua
		sStop = new Semaphore(0); // para hacer esperar a los barcos
		for (int i = 0 ; i< vGasoil.length; i++)  // iniciamos todos los repostajes a repostajeGasoil
			vGasoil[i] = repostajeGasoil;
		for (int i = 0; i <  sGasoil.length; i++) // iniciamos todos los semaforos de los barcos petroleros
			sGasoil[i] = new Semaphore(0);
		for (int i = 0; i <  sBarcos.length; i++)  // iniciamos todos los semaforos de los barcos petroleros
			sBarcos[i] = new Semaphore(0);
		
	}
	
	/***
	 * Constructor parametrizado de la clase Gasolinera
	 * @param numPetroleros numero de barcos petroleros que van a poder repostar
	 * @param repostajeAgua numero de litros que se van a poder llenar en un repostaje de agua
	 * @param repostajeGasoil numero de litros que se van a poder llenar en un repostaje de gasoil
	 */
	Gasolinera(int numPetroleros, int repostajeAgua, int repostajeGasoil){
		vGasoil = new int[numPetroleros];
		sGasoil = new Semaphore[numPetroleros];
		sBarcos = new Semaphore[numPetroleros];
		barcosBloqueados = 0;
		barcosGasolinera = numPetroleros;
		Gasolinera.repostajeAgua = repostajeAgua; // cambiamos la capacidad de repostaje del agua
		Gasolinera.repostajeGasoil = repostajeGasoil; // cambiamos la capacidad de repostaje del gasoil
		mutex = new Semaphore(1); // para garantizar la exclusion mutua
		sStop = new Semaphore(0); // para hacer esperar a los barcos
		for (int i = 0 ; i< vGasoil.length; i++)  // iniciamos todos los repostajes a repostajeGasoil
			vGasoil[i] = repostajeGasoil;
		for (int i = 0; i <  sGasoil.length; i++) // iniciamos todos los semaforos de los barcos petroleros
			sGasoil[i] = new Semaphore(0);
		for (int i = 0; i <  sBarcos.length; i++)  // iniciamos todos los semaforos de los barcos petroleros
			sBarcos[i] = new Semaphore(0);
		
	}
	
	/***
	 * Metodo para hacer esperar a todos los barcos, hasta que llegue el ultimo, para poder empezar a repostar
	 * @param b barco petrolero que llega a la gasolinera
	 */
	public static void llegar(BarcoPetrolero b) {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		barcosBloqueados++;
		FileBarcos.print("Barco con id " + b.getID() + " ha entrado en la gasolinera");
		FileBarcos.print("Número de barcos esperando a repostar: " + barcosBloqueados);
		mutex.release();
		if(barcosBloqueados < 5)
			try {
				sStop.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		else 
			for (int i = 0; i < barcosBloqueados-1; i++)
				sStop.release();
		
	}
	
	/***
	 * Metodo para poder hacer repostar "repostajeGasoil" de gasoil al barco b
	 * @param b barco petrolero que va a repostar gasoil
	 */
	public static void repostarGasoil(BarcoPetrolero b) {
			vGasoil[b.getID()-200] = 0; // id-200 por que barcosPetroleros id -> 20X
			if(vGasoil[b.getID()-200] == 0) {
				sGasoil[b.getID()-200].release(); // el barco i manifiesta que tiene que hechar gasoil
				try {
					FileBarcos.print("El barco " + b.getID() + " esta esperando a reponer gasoil...");
					sBarcos[b.getID()-200].acquire(); // el barco i se bloquea y espera para poder repostar
					FileBarcos.print("El barco " + b.getID() + " ha dejado de esperar para reponer gasoil...");
				} catch (Exception e) {}
			}
			FileBarcos.print("El barco " + b.getID() + " esta reponiendo gasoil...");
			b.addGasoil(repostajeGasoil); // añadimos en cada carga repostajeGasoil litros de gasoil
			if(b.gasoilRestante() > 0) FileBarcos.print("A el barco " + b.getID() +" le faltan por repostar " + b.gasoilRestante() + " litros de gasoil");
			else FileBarcos.print("Gasoil a tope en el barco " + b.getID());

	}
	
	/***
	 * Metodo para poder hacer repostar "repostajeAgua" de agua al barco b hasta llenarlo
	 * @param b barco petrolero que va a repostar agua
	 */
	public static void repostarAgua(BarcoPetrolero b) {
		try {
			mutex.acquire();
		} catch (Exception e) {}
		
		FileBarcos.print("El barco " + b.getID() + " esta reponiendo agua...");
		b.addAgua(repostajeAgua); // a�adimos en cada carga repostajeAgua litros de agua
		if(b.aguaRestante() > 0) FileBarcos.print("Le faltan por repostar " + b.aguaRestante() + " litros de agua");
		else FileBarcos.print("Agua a tope en el barco " + b.getID());
		
		mutex.release();
	}
	
	/***
	 * Notifica la salida de un barco de la gasolinera y comprueba que todos se hayan ido para, notificar al reponedor que puede terminar
	 * @param b barco petrolero que va a salir de la gasolinera
	 */
	public static void salir(BarcoPetrolero b) {
		
		FileBarcos.print("Barco con id " + b.getID() + " ha salido de la gasolinera");
		
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		barcosGasolinera--; // decrementamos el numero de barcos que hay en la gasolinera por que al repostar agua ya han acabado
		mutex.release();
		FileBarcos.print("Barcos que todavian quedan en la gasolinera " + barcosGasolinera);
		if(isFinReponedor()) releaseReponedor(); // si el barco que ha repuesto agua es el ultimo decimos al reponedor que se han ido todos los barcos
	}
	
	/***
	 * Indica si el reponedor ha acabado su trabajo por que ya no quedan mas barcos en la gasolinera
	 * @return barcosGasolinera == 0
	 */
	public static boolean isFinReponedor() {
		return barcosGasolinera == 0;
	}
	
	/***
	 * Libera los tanques de gasolina para que el reponedor pueda terminar
	 */
	public static void releaseReponedor() {
		for (int i = 0; i < sGasoil.length; i++) 
			   sGasoil[i].release(); // reponiendo el repostaje de gasoil
	}
	
	/***
	 * Metodo para reponer los tanques de gasoil
	 */
	public static void reponer() {
		
		for (int i = 0; i < sGasoil.length; i++) 
			try {
			   sGasoil[i].acquire(); // reponiendo el repostaje de gasoil
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		FileBarcos.print("Reponedor haciendo su trabajo...");
		
		for (int i = 0; i < sBarcos.length; i++) {
			vGasoil[i] = repostajeGasoil; // volvemos a reponer el repostaje i de gasoil
			FileBarcos.print("Reponiendo el repostaje " + i);
			sBarcos[i].release(); // le decimos al barco i que puede repostar
			FileBarcos.print("Ya puede repostar el barco " + i);
			FileBarcos.print("barcosGasolinera repostando gasoil:  " + barcosGasolinera);
		}
	}

}

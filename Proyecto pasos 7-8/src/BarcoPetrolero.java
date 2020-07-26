import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/***
 * Class BarcoPetrolero extends Barco
 * @author Pedro Miguel Carmona Broncano
 *
 */
public class BarcoPetrolero extends Barco{
	
	private final int capacidadAgua;
	private final int capacidadGasoil;
	private int contenedorGasoil;
	private int contenedorAgua;
	private final int TASKS = 2;
	private ThreadPoolExecutor executor;
	private Semaphore task;
	private Phaser p;
	
	/***
	 * Contructor parametrizado de la clase BarcoPetrolero
	 * @param id identificador del barco
	 * @param capacidadAgua capacidad del contenedor de agua
	 * @param capacidadGasoil capacidad del contenedor de gasoil
	 */
	BarcoPetrolero(int id, int capacidadAgua, int capacidadGasoil, Phaser p){
		super(id, true);
		contenedorGasoil = 0;
		contenedorAgua = 0;
		this.capacidadAgua = capacidadAgua;
		this.capacidadGasoil = capacidadGasoil;
		this.p = p;
		executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(TASKS);
		task = new Semaphore(0);
	}
	
	/***
	 * Devuelve la cantidad de agua que falta por llenar para el BarcoPetrolero
	 * @return CAPACIDAD_AGUA - contenedorAgua
	 */
	public int aguaRestante() {
		return capacidadAgua - contenedorAgua;
	}

	/***
	 * Devuelve la cantidad de gasoil que falta por llenar para el BarcoPetrolero
	 * @return CAPACIDAD_GASOIL - contenedorGasoil
	 */
	public int gasoilRestante() {
		return capacidadAgua - contenedorGasoil;
	}
		 
	/***
	 * Suma al contenedor de agua del BarcoMercader la cantidad de agua
	 * @param agua
	 */
	public void addAgua(int agua) {
		contenedorAgua += agua;
		if(contenedorAgua > capacidadAgua) contenedorAgua = capacidadAgua;
	}
	
	/***
	 * Suma al contenedor de gasoil del BarcoMercader la cantidad de gasoil
	 * @param gasoil
	 */
	public void addGasoil(int gasoil) {
		contenedorGasoil += gasoil;
		if(contenedorGasoil > capacidadGasoil) contenedorGasoil = capacidadGasoil;
	}
	
	/***
	 * Devuelve la capacidad maxima que posee el barco petrolero de agua
	 * @return capacidadAgua capacidad de agua del barco petrolero
	 */
	public int getAgua() {
		return capacidadAgua;
	}
	
	/***
	 * Devuelve la capacidad m�xima que posee el barco ptrolero de gasoil
	 * @return capacidadGasoil capacidad de gasoil del barco petrolero
	 */
	public int getGasoil() {
		return capacidadGasoil;
	}
	
	/***
	 * Metodo para despertar al barco para hacerle salir
	 */
	public void release() {
		task.release();
	}
	
	@Override
	public void run() {
		entrar(); // hacemos que entre el barco al puerto 
			Gasolinera.llegar(this); // vamos a la gasolinera
			p.arriveAndAwaitAdvance(); // PRIMERA FASE: Entrada en la gasolinera
			

					executor.execute(new TaskGasoil(this));
					executor.execute(new TaskAgua(this));
					executor.shutdown();
					
					try {
						task.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					p.arriveAndDeregister(); // SEGUNDA FASE: Reponer gasoil y agua
				
			Gasolinera.salir(this); // salimos de la gasolinera
		salir(); // hacemos que el barco salga del puerto
	}
	
}

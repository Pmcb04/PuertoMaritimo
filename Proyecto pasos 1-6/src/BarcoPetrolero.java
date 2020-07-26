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
	
	/***
	 * Contructor parametrizado de la clase BarcoPetrolero
	 * @param id identificador del barco
	 * @param capacidadAgua capacidad del contenedor de agua
	 * @param capacidadGasoil capacidad del contenedor de gasoil
	 */
	BarcoPetrolero(int id, int capacidadAgua, int capacidadGasoil){
		super(id, true);
		contenedorGasoil = 0;
		contenedorAgua = 0;
		this.capacidadAgua = capacidadAgua;
		this.capacidadGasoil = capacidadGasoil;
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
	
	@Override
	public void run() {
		entrar(); // hacemos que entre el barco al puerto 
			Gasolinera.llegar(this); // vamos a la gasolinera
			
				FileBarcos.print("Barco " + this.getID() + " empezando a reponer gasoil");
				while(gasoilRestante() > 0) 
					 Gasolinera.repostarGasoil(this); // repostamos gasoil  
				FileBarcos.print("Barco" + this.getID() + " ha finalizado de reponer gasoil");
				
				FileBarcos.print("Barco " + this.getID() + " empezando a reponer agua");
				while(aguaRestante() > 0) 
					Gasolinera.repostarAgua(this); // repostamos agua
				FileBarcos.print("Barco " + this.getID() + " ha finalizado de reponer agua");
				
			Gasolinera.salir(this); // salimos de la gasolinera
		salir(); // hacemos que el barco salga del puerto
	}
	
}

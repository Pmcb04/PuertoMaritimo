/**
 * Class Barco extends Thread
 * @author Pedro Miguel Carmona Broncano
 */
public class Barco extends Thread {
	
	private int id; // identificador del barco
	private boolean direccion; // true de entrada, false de salida
	private TorreDeControl control = new TorreDeControl();
	
	/***
	 * Contructor parametrizado de la clase Barco
	 * @param id
	 * @param direccion
	 */
	Barco(int id, boolean direccion){
		this.id = id;
		this.direccion = direccion;
	}
	
	/***
	 * Devuelve el id del barco
	 * @return id del barco
	 */
	public int getID() {
		return id;
	}
	
	/***
	 * Devuelve la torre de control del barco
	 * @return control del barco
	 */
	public TorreDeControl getTorre() {
		return control;
	}
	
	/***
	 * Provoca la salida del puerto del barco 
	 */
	public void salir() {
		control.permisoSalida(this.id); // protocolo de entrada
			Puerta.salir(id); // seccion critica
		control.finSalida(); // protocolo de salida
	}
	
	/***
	 * Provoca la entrada del barco al puerto
	 */
	public void entrar() {
		control.permisoEntrada(this.id); // protocolo de entrada
			Puerta.entrar(id); // seccion critica
		control.finEntrada(); // protocolo de salida
	}
	
	@Override
	public void run()  {
		if(direccion) entrar();
		else salir();
	}

}

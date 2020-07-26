/***
 * Class TorreDeControl
 * @author Pedro Miguel Carmona Broncano
 *
 */
public class TorreDeControl {
	
	private int entrando;
	private int saliendo;
	private int esperandoSalir;
	
	/***
	 * Contructor por deefecto de la clase TorreDeControl
	 */
	TorreDeControl(){
		
		entrando = 0;
		saliendo = 0;
		esperandoSalir = 0;
	}
	
	/***
	 * Metodo para hacer que un barco pida permiso de entrada a el puerto
	 * @param id identificador del barco que va a pedir entrada
	 */
	public synchronized void permisoEntrada(int id) {
		
		FileBarcos.print("El barco " + id + " esta pidiendo permiso de entrada");
		FileBarcos.print("saliendo: " + saliendo + " entrando: " + entrando + " esperandoSalir: " + esperandoSalir);

		while(saliendo > 0 || esperandoSalir > 0) {
			try {
				FileBarcos.print("El barco " + id + " se va a quedar esperando a entrar");
				wait();
			} catch (Exception e) {;}
		}

		entrando++;
		FileBarcos.print("El barco " + id + " se le da permiso de entrada");
	    FileBarcos.print("saliendo: " + saliendo + " entrando: " + entrando + " esperandoSalir: " + esperandoSalir);

		
	}
	
	/***
	 * Metodo para hacer que un barco pida permiso de salida del puerto
	 * @param id identificador del barco que va a pedir salida
	 */
	public synchronized void permisoSalida(int id) {
		
		FileBarcos.print("El barco " + id + " esta pidiendo permiso de salida");
		FileBarcos.print("saliendo: " + saliendo + " entrando: " + entrando + " esperandoSalir: " + esperandoSalir);

		while(entrando > 0) {
			try {
				FileBarcos.print("El barco " + id + " se va a quedar esperando para salir");
				esperandoSalir++;
				wait();
				esperandoSalir--;
			} catch (Exception e) {;}
		}
		
		saliendo++;
		FileBarcos.print("El barco " + id + " se le da permiso de salida");
		FileBarcos.print("saliendo: " + saliendo + " entrando: " + entrando + " esperandoSalir: " + esperandoSalir);

	}
	
	/***
	 * Metodo para finalizar la entrada de un barco
	 */
	public synchronized void finEntrada() {
		entrando--;
		if(entrando == 0) notifyAll();  // despertamos a todos los barcos esperando
	}
	
	/***
	 * Metodo para finalizar la salida de un barco
	 */
	public synchronized void finSalida() {
		saliendo--;
		if(saliendo == 0) notifyAll(); // despertamos a todos los barcos esperando 
	}
	
	
	

}

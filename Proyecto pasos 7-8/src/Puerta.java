/**-
 * Class Puerta
 * @author Pedro Miguel Carmona Broncano
 *
 */
public abstract class Puerta {
	
	private static final int NUM_MENSAJES = 3; // numeero de mensajes para mostrar
	
	/***
	 * Metodo para hacer entrar a un barco
	 * @param idBarco identificador del barco que esta entrando
	 */
	public synchronized static void entrar(int idBarco) {
		for (int i = 0; i < NUM_MENSAJES; i++)
		 FileBarcos.print("El barco " + idBarco + " esta entrando");
	}
	
	/***
	 * Metodo oara hacer salir a un barco
	 * @param idBarco identificador del barco que esta saliendo
	 */
	public synchronized static void salir(int idBarco) {
		for (int i = 0; i < NUM_MENSAJES; i++)
			FileBarcos.print("El barco " + idBarco + " esta saliendo");
	}
	
}

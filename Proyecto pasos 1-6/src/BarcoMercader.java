import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

/***
 * Class BarcoMercader extends Barco
 * @author Pedro Miguel Carmona Broncano
 *
 */
public class BarcoMercader extends Barco {
	
	private final int HARINA_ID = 0; // id del alimento harina
	private final int SAL_ID = 1; // id del alimento sal
	private final int AZUCAR_ID = 2; // id del alimento azucar
	private final int NUM_HARINA = 5; // numero de harina en el contenedor del barco
	private final int NUM_SAL = 20; // numero de sal en el contenedor del barco
	private final int NUM_AZUCAR = 12; // número de azucar en el contenedor del barco
	private static ArrayList<Integer> vContenedor = new ArrayList<>();
    
	/***
	 * Contructor parametrizado de la clase BarcoMercader
	 * @param id
	 */
	BarcoMercader(int id){
		super(id,true);
		llenarContenedor();
	}
	
	/***
	 * Metodo para llenar el contenedor del barco
	 */
	private void llenarContenedor(){
		
		IntStream.range(0, NUM_HARINA).forEach(i -> { vContenedor.add(HARINA_ID); }); // metemos los productos de harina en el contenedor	
		IntStream.range(0, NUM_SAL).forEach(i -> {  vContenedor.add(SAL_ID); }); // metemos los productos de sal en el contenedor
		IntStream.range(0, NUM_AZUCAR).forEach(i -> {  vContenedor.add(AZUCAR_ID); }); // metemos los productos de azucar en el contenedor
		
		Collections.shuffle(vContenedor);  // para que no salgan en orden y se vean las esperas de las gruas 
		FileBarcos.print(vContenedor.toString()); // imprimimos el contenedor inicial para ver el estado inicial
	}
	
	/***
	 * Devuelve true si el contenedor del Barco esta vacio, si no es el caso devuelve false
	 * @return vContenedor.isEmpty()
	 */
	public static boolean isEmpty() {
		return vContenedor.isEmpty();
	}
	
	@Override
	public void run() {
		entrar(); // el barco primero entra en el puerto 
			while (!vContenedor.isEmpty()) {
				try {
					Plataforma.set(vContenedor.get(0));
					vContenedor.remove(0);
					FileBarcos.print(vContenedor.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
			
			Plataforma.signalGruas(); // despertamos a todas las gruas para que no se queden bloqueadas y el programa finalize 
		salir(); // el barco sale del puerto
		
		
	}
}

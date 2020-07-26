/***
 * Class Grua extends Thread
 * @author Pedro Miguel Carmona Broncano
 *
 */
public class Grua extends Thread {
	
	private int element;
	
	/***
	 * Constructor parametrizado de la clase Grua
	 * @param element elemento que va a poder sacar de la plataforma la grua
	 */
	Grua(int element){
		this.element = element;
	}
	
	/***
	 * Metodo para indicar que la grua esta pidiendo element
	 * @param element elemento a pedir a la plataforma por la grua
	 */
	private void printPedir(int element) {
		
		if(element == 0) FileBarcos.print("Grua " + element + " pidiendo sacar harina");
		else if(element == 1) FileBarcos.print("Grua " + element + " pidiendo sacar sal");
		else if(element == 2) FileBarcos.print("Grua " + element + " pidiendo sacar azucar");
	}
	
	/***
	 * Metodo para indicar que la grua esta sacando element
	 * @param element elemento a sacar de la plataforma por la grua
	 */
	private void printSacar(int element) {
		if(element == 0) FileBarcos.print("Grua " + element + " ya a sacado harina");
		else if(element == 1) FileBarcos.print("Grua " + element + " ya a sacado sal");
		else if(element == 2) FileBarcos.print("Grua " + element + " ya a sacado azucar");
	}
	
	@Override
	public void run() {
		while (!BarcoMercader.isEmpty())
			try {
				printPedir(element);
				Plataforma.get(element);
				if(!BarcoMercader.isEmpty())printSacar(element);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			FileBarcos.print("La grua "+ element + " a terminado su jornada");
			//FileBarcos.closeFile();

	}

}

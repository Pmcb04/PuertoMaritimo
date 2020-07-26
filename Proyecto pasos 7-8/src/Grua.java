
/***
 * Class Grua extends Thread
 * @author Pedro Miguel Carmona Broncano
 *
 */
public class Grua extends Thread {
	
	private int element;
	private int numElement;
	
	/***
	 * Constructor parametrizado de la clase Grua
	 * @param element elemento que va a poder sacar de la plataforma la grua
	 */
	Grua(int element){
		this.element = element;
		element(element);
		
	}
	
	private void element(int element) {
		
		switch (element) {
		case 0:
			this.numElement = 5;
			break;
		case 1:
			this.numElement = 20;
			break;
		case 2:
			this.numElement = 12;
			break;
			
		default:
			break;
		}
		
		
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
			int i = 0;
			while (i < numElement) {
				printPedir(element);
				Plataforma.get(element);
				if(!BarcoMercader.isEmpty())printSacar(element);
				i++;
			}
			FileBarcos.print("La grua "+ element + " a terminado su jornada");

	}

}

import java.io.IOException;
import java.util.concurrent.Phaser;

public class Main {
	
	private static final int BARCOS_ENTRADA = 10; // numero de barcos de entrada
	private static final int BARCOS_SALIDA = 10; // numero de barcos de salida
	private static final int BARCOS_MERCADER = 1; // numero de barcos mercaderes
	private static final int BARCOS_PETROLEROS = 5; // numero de barcos petroleros
	private static final int PLATAFORMAS = 1; // numero de plataformas
	private static final int GASOLINERAS = 1; // numero de gasolineras
	private static final int REPONEDORES = 1; // numero de reponedores
	private static final int GRUAS = 3; // numero de gruas
		
	public static void main(String args[]) throws IOException {
		
		Barco vBarcosEntrada[] = new Barco[BARCOS_ENTRADA]; // id 1X
		Barco vBarcosSalida[] = new Barco[BARCOS_ENTRADA]; // id 2X
		Barco vBarcosMercader[] = new Barco[BARCOS_MERCADER]; // id 10X
		Barco vBarcosPetroleros[] = new Barco[BARCOS_PETROLEROS]; // id 20X
		Plataforma vPlataformas[] = new Plataforma[PLATAFORMAS];
		Gasolinera vGasolineras[] = new Gasolinera[GASOLINERAS];
		Reponedor vReponedores[] = new Reponedor[REPONEDORES];
		Grua vGruas[] = new Grua[GRUAS];
		FileBarcos f = new FileBarcos();
		Phaser p = new Phaser(5);
		
		
		for (int i = 0; i < PLATAFORMAS; i++) { // inicializacion de las plataformas
			vPlataformas[i] = new Plataforma(GRUAS);
		}
	
		for (int i = 0; i < BARCOS_ENTRADA; i++) {
			vBarcosEntrada[i] = new Barco(i+10, true); // barcos de salida
			vBarcosEntrada[i].start();
		}
		
		for (int i = 0; i < BARCOS_SALIDA; i++) { 
			vBarcosSalida[i] = new Barco(i+20, false); // barcos de entrada
			vBarcosSalida[i].start();
		}
		
	    for (int i = 0; i < BARCOS_MERCADER; i++) {
			vBarcosMercader[0] = new BarcoMercader(i+100); // barco mercader de entrada
			vBarcosMercader[0].start();
		}
		
		for (int i = 0; i < GRUAS; i++) {
			vGruas[i] = new Grua(i); // inicializaci�n de las gruas
			vGruas[i].start();
		}
	
		for (int i = 0; i < GASOLINERAS; i++) { // inicializacion de las gasolineras
			vGasolineras[i] = new Gasolinera(BARCOS_PETROLEROS);
		}
		
		for (int i = 0; i < BARCOS_PETROLEROS; i++) { // barcos petroleros de entrada
			vBarcosPetroleros[i] = new BarcoPetrolero(i+200,3000,5000, p);
			vBarcosPetroleros[i].start();
		}
			
		for (int i = 0; i < REPONEDORES; i++) { // inicializacion de los reponedores
			vReponedores[i] = new Reponedor();
			vReponedores[i].start();
		}
		
		
		try {
			for (Grua grua : vGruas) 
				grua.join();
			for(Barco barcos : vBarcosMercader)
				barcos.join();
			for(Barco barcos : vBarcosPetroleros)
				barcos.join();
			for(Barco barcos : vBarcosEntrada)
				barcos.join();
			for(Barco barcos : vBarcosSalida)
				barcos.join();
			for(Reponedor reponedor : vReponedores)
				reponedor.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
		FileBarcos.closeFile();
		
	}

}

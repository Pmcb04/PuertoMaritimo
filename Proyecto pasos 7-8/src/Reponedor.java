
/***
 * Class Reponedor extends Thread
 * @author Pedro Miguel Carmona Broncano
 *
 */
public class Reponedor extends Thread{
	
	@Override
	public void run() {
		while(!Gasolinera.isFinReponedor()) {
		  if(!Gasolinera.isFinReponedor())Gasolinera.reponer();
		}
		
		FileBarcos.print("Reponedor ha acabado su jornada...");
	}

}

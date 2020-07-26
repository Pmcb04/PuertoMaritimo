public class TaskGasoil implements Runnable {

	private BarcoPetrolero b;
	
	public TaskGasoil(BarcoPetrolero b) {
		this.b = b;
	}
	
	@Override
	public void run() {
		while(b.gasoilRestante() > 0)
			Gasolinera.repostarGasoil(b);
		
		FileBarcos.print("El barco petrolero " + b.getID() + " ha terminado de repostar gasoil");
		
		if(b.aguaRestante() == 0) {
			b.release();
			FileBarcos.print("El barco petrolero " + b.getID() + " ha terminado de repostar");
		}
			
	}
}

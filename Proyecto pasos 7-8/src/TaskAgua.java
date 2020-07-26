public class TaskAgua implements Runnable {

	private BarcoPetrolero b;
	
	public TaskAgua(BarcoPetrolero b) {
		this.b = b;
	}
	
	@Override
	public void run() {
		while(b.aguaRestante() > 0)
			Gasolinera.repostarAgua(b);
		
		FileBarcos.print("El barco petrolero " + b.getID() + " ha terminado de repostar agua");

		
		if(b.gasoilRestante() == 0) {
			b.release();
			FileBarcos.print("El barco petrolero " + b.getID() + " ha terminado de repostar");
		}
		
		
	}
}


//theworldisquiethere

public class Time extends Thread {

	private double lastDrop = System.currentTimeMillis();
	private int delay = 1;
	private Sorcery temp = (Sorcery)(Sorcery.box.getContentPane());
	
	public void run() {

		try {
	
			while(Sorcery.active && !Sorcery.done) {
				
				if (System.currentTimeMillis() - lastDrop > (delay * 1000)) {
	
					temp.forceDrop();
					lastDrop = System.currentTimeMillis();

				} else

					Thread.sleep(Math.abs((int)( (delay * 1000) - (System.currentTimeMillis() - lastDrop) )));

			}

			temp.repaint();

		} catch (Exception e) {

			System.out.println("[keeper] failed.");

			e.printStackTrace();
		
		}

	}

	public int getDelay() {

		return delay;

	}

}


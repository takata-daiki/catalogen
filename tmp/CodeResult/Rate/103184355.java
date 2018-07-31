package biometric;

import java.util.Date;
import java.util.LinkedList;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.timer.Timer;

public abstract class Rate {

	private static final double NS_TO_MINUTE = 6 * Math.pow(10, 10);
	private static final int MAX_BEATS = 5; // plus current
	private static final long MS_PER_BEAT_CHECK = Timer.ONE_SECOND * 3 / 2;
	
	private double rate = 0.0F;
	private LinkedList<Long> measurements = new LinkedList<Long>();
	private LinkedList<Boolean> beats = new LinkedList<Boolean>();
	
	private Timer countdown = new Timer();
	private NotificationListener listener = new NotificationListener() {
		
		@Override
		public void handleNotification(Notification notification, Object ignore) {
			// THERE's only one notification
			updateRate(false);
		}
	};
	
	public Rate() {
		this(0.0F);
	}
	
	public Rate(float initialRate) {
		reset(initialRate);
		countdown.addNotificationListener(listener, null, null);
		countdown.addNotification("", "", null, new Date(), MS_PER_BEAT_CHECK);
	}
	
	public void reset(float initialRate) {
		measurements.clear();
		beats.clear();
		rate = initialRate;
		countdown.stop();
		countdown.setSendPastNotifications(false);
	}
	
	public void start() {
		if (!countdown.isActive()) {
			updateRate(true);
		}
	}
	
	public double rate() {
		return rate;
	}
	
	protected void updateRate(boolean isBeat) {
		if (isBeat) {
			countdown.stop();	//reset?
			countdown.start();
		}
		measurements.addLast(System.nanoTime());
		beats.addLast(isBeat);
		
		if (measurements.size() == 0) {
			rate = 0.0;
		} else {
			long firstBeat = measurements.getFirst();
			long lastBeat = measurements.getLast();
			int numBeats = 0;
			
			for (Boolean beat : beats) {
				if (beat && beat.booleanValue()) {
					numBeats++;
				}
			}
			
			if (numBeats > 0 && measurements.size() > 2) {
				if (lastBeat - firstBeat == 0) {
					throw new NullPointerException("OH NO");
				}
				
				double rateNS = ((double) numBeats) / (lastBeat - firstBeat);
				rate = rateNS * NS_TO_MINUTE;
			//} else {
			//	rate = 0.0;
			}
		}
		
		if (isBeat) {
			while (measurements.size() > MAX_BEATS) {
				measurements.pollFirst();
				beats.pollFirst();
			}
		}
		
		updateStatus();
	}
	
	public synchronized double rateNow() {
		if (measurements.size() == 0)
			return 0.0;
		
		long firstBeat = measurements.getFirst();
		long lastBeat = System.nanoTime();
		int numBeats = 0;
			
		for (Boolean beat : beats) {
			if (beat && beat.booleanValue()) {
				numBeats++;
			}
		}
			
		if (numBeats > 0 && measurements.size()+1 > 2) {				
			double rateNS = ((double) numBeats) / (lastBeat - firstBeat);
			return rateNS * NS_TO_MINUTE;
		}
		
		return rate;
	}
	
	protected abstract void updateStatus();
}

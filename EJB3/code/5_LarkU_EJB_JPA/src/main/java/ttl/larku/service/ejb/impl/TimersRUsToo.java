package ttl.larku.service.ejb.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Singleton
@Startup
public class TimersRUsToo {
	
	@Resource
	private TimerService timerService;
	
	@PostConstruct
	public void init() {
		TimerConfig timerConfig = new TimerConfig();
		timerService.createSingleActionTimer(5000, timerConfig);
	}
	
	
	public void createATimer() {
		TimerConfig timerConfig = new TimerConfig();
		timerService.createSingleActionTimer(5000, timerConfig);
	}
	
	@Timeout
	public void timeOut(Timer t) {
		System.out.println("Timer went off");
	}

	public static void main(String[] args) {
		TimersRUsToo trt = new TimersRUsToo();
		trt.createATimer();
	}
}

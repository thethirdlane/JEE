package ttl.larku.service.ejb.impl;

import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

//@Lock(LockType.READ)
@Singleton
@Startup
public class TimersRUs {
	
	@Resource
	private TimerService timerService;
	
	//@Schedule(second="*/10", minute="*", hour="*", persistent=false)
	public void everyTwoMinutes(Timer timer) {
		System.out.println("A ten second egg");
		timer.cancel();
	}

	private int count = 10;

	//@Lock(LockType.WRITE)
	//@Schedule(second="*/20", minute="*", hour="*", persistent=false)
	public void cancelAfter10Times(Timer timer) {
		System.out.println("A twenty second egg with " + count + " lives left");
		if(count == 10) {
			createATimer();
		}
		if(count-- == 0) {
			System.out.println("Twenty second egg is bye bye");
			timer.cancel();
		}
	}

	
	public void createATimer() {
		ScheduleExpression se = new ScheduleExpression();
		se.minute("*/1");
		se.hour("*");
		se.dayOfMonth("23-4");
		TimerConfig config = new TimerConfig("", false);
		timerService.createCalendarTimer(se, config);
	}
	
	@Timeout
	public void everMinuteOfEveryHourFrom23thTo4th() {
		System.out.println("everMinuteOfEveryHourFrom23thTo4th");
	}
}
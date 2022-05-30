package com.app.MessageScheduler;

import com.app.MessageScheduler.apiCall.Scheduler;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.util.Timer;

@SpringBootApplication
@ComponentScan({"com.*"})
public class MessageSchedulerApplication {
	@Autowired
	public Timer timer;

	@Autowired
	public Scheduler scheduler;

	public static void main(String[] args) throws InterruptedException, IOException {
		SpringApplication.run(MessageSchedulerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void startScheduler(){
		timer.schedule(scheduler,1000,60000);
	}

}

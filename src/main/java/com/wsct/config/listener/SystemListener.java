package com.wsct.config.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class SystemListener extends ContextLoaderListener {

	// private static Logger logger = LoggerFactory
	// .getLogger(SystemListener.class);
	// private static ApplicationContext applicationContext;

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		/******** jul to slf4j *********/

		/************* spring *********/
		// applicationContext = super.getCurrentWebApplicationContext();
	}

	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}

}

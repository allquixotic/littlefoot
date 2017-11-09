package com.pathfinder.littlefoot

import org.springframework.beans.factory.annotation.Autowired

import com.vaadin.annotations.Theme
import com.vaadin.server.VaadinRequest
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.Label
import com.vaadin.ui.UI

@Theme("valo")
@SpringUI
public class MyUI extends UI {
	
	@Autowired
	private StatsBean stats;

	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new Label(stats.sayHello()))
    }
}
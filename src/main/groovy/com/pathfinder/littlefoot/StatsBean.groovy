package com.pathfinder.littlefoot

import com.vaadin.spring.annotation.SpringComponent
import com.vaadin.spring.annotation.VaadinSessionScope

@SpringComponent
@VaadinSessionScope
class StatsBean {

	public String sayHello() {
		return "Hello from bean " + getClass().getName() + " : " +  toString()
	}
}

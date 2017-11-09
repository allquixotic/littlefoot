package com.pathfinder.littlefoot.tests

import static org.assertj.core.api.Assertions.*
import static org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import com.pathfinder.littlefoot.BadCommandException
import com.pathfinder.littlefoot.CliBuilder
import com.pathfinder.littlefoot.CommandType

class CliBuilderTests {

	private CliBuilder builder
	
	@Rule
	public ExpectedException ex = ExpectedException.none()
	
	@Before
	public void setUp() throws Exception {
		builder = new CliBuilder()
	}

	@Test
	public void testEmptyInstanceName() {
		builder.withProgram("arkmanager").withCommand(CommandType.START).withArgs("--saveworld")
		ex.expect(BadCommandException.class)
		System.out.println(builder.getCommandLine())
	}
	
	@Test
	public void testBadInstanceName() {
		ex.expect(BadCommandException.class)
		builder.withInstance("command injection attempt`foo`")
	}
	
	@Test
	public void testBadArgumentName() {
		builder.withInstance("main").withProgram("arkmanager").withCommand(CommandType.STOP)
		ex.expect(BadCommandException.class)
		builder.withArgs('--foo-bar#={!')
	}
	
	@Test
	public void testMissingCommand() {
		builder.withInstance("main").withProgram("arkmanager").withArgs("--saveworld")
		ex.expect(BadCommandException.class)
		
		System.out.println(builder.getCommandLine())
	}

}

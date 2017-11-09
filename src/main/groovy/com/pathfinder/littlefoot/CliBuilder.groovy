package com.pathfinder.littlefoot;

import java.util.regex.Pattern

public class CliBuilder {

	public static final String DEFAULT_PROGRAM  = 'arkmanager'
	public static final String DEFAULT_INSTANCE = 'main'
	public static final Pattern instRx = Pattern.compile('^[0-9A-Za-z]+$')
	public static final Pattern argRx = Pattern.compile('^[0-9A-Za-z\\-=]+$')

	private final List<String> args
	private final List<String> unmodArgs
	private String command
	private String program
	private String instance

	CliBuilder() {
		args = new ArrayList<String>()
		unmodArgs = Collections.unmodifiableList(args)
		program  = DEFAULT_PROGRAM
		instance = DEFAULT_INSTANCE
	}

	public List<String> getArgs() {
		return unmodArgs
	}

	public String getCommand() {
		return command
	}

	public String getCommandLine() {
		return getCommandLineValidateOptional(true)
	}

	public CliBuilder withProgram(String prog) {
		program = prog?.trim() ?: null
		return this
	}

	public CliBuilder withCommand(CommandType ct) {
		command = ct?.name()?.trim()?.toLowerCase() ?: null
		return this
	}

	public CliBuilder withInstance(String instanceName) {
		if(!instanceName?.trim()) {
			throw new BadCommandException("Must supply an instance name; string so far: ${toString()}")
		}

		instanceName = instanceName.trim()

		int subst = 0

		if(instanceName.startsWith('@')) {
			subst = 1
		}

		if(!instRx.matcher(instanceName.substring(subst)).matches()) {
			throw new BadCommandException("Bad instance name; must be alphanumeric and optionally contain an @ symbol at the beginning; string so far: ${toString()}")
		}

		this.instance = instanceName

		return this
	}

	public CliBuilder withArgs(String ... argz) {
		for(String arg : argz) {
			String tmp = arg.replaceFirst('--', '')
			if(argRx.matcher(tmp).matches()) {
				args.add("--${tmp}".trim())
			}
			else {
				throw new BadCommandException("Bad argument name; must be alphanumeric and can only contain hyphens and equals signs; string so far: ${toString()}")
			}
		}
	}


	@Override
	public String toString() {
		return String.join("\n", "Command: ${command}",
				"Program: ${program}",
				"Args: ${String.join(' ', unmodArgs)}",
				"Instance: ${instance}",
				"Total command line: ${getCommandLineValidateOptional(false)}")
	}

	private CliBuilder validateRequired() {
		if((!program?.trim() || !command?.trim() || !instance.trim())) {
			throw new BadCommandException("Required parameter missing from command line: ${toString()}")
		}

		return this
	}

	private String getCommandLineValidateOptional(boolean doValidate = true) {
		if(doValidate) {
			validateRequired()
		}
		List<String> j = new ArrayList<String>()
		j.add(command)
		j.add(program)
		j.addAll(unmodArgs)
		j.add(instance)
		return String.join(" ", j)
	}
}

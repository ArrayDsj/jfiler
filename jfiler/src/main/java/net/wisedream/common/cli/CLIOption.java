package net.wisedream.common.cli;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CLIOption {
	public boolean hasArg() default false;

	public boolean hasArgs() default false;

	public boolean isRequired() default false;

	public String opt() default "";

	public String longOpt() default "";

	public String desc() default "";

	public String argName() default "";
}

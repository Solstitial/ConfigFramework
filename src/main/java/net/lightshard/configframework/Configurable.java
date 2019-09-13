package net.lightshard.configframework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) //Fields only
public @interface Configurable
{
	
	String path();

	DataType type();

	char colorCharLocale() default '&';
	
}

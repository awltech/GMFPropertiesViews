package com.worldline.gmf.propertysections.core.internal;

import java.util.ResourceBundle;

import java.text.MessageFormat;

/**
 * Messages class, for internationalization purpose
 * 
 * @author mvanbesien
 *
 */
public enum Messages {
	ERROR_CHANGE_STARTED, ERROR_NOT_IN_CHANGE;
	
	/*
	 * ResourceBundle instance
	 */
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("Messages");
	
	/*
	 * Returns value of the message
	 */ 
	public String value() {
		if (Messages.resourceBundle == null || !Messages.resourceBundle.containsKey(this.name()))
			return "!!"+this.name()+"!!";
		
		return Messages.resourceBundle.getString(this.name());
	}
	
	/*
	 * Returns value of the formatted message
	 */ 
	public String value(Object... args) {
		if (Messages.resourceBundle == null || !Messages.resourceBundle.containsKey(this.name()))
			return "!!"+this.name()+"!!";
		
		return MessageFormat.format(Messages.resourceBundle.getString(this.name()), args);
	}
	
}

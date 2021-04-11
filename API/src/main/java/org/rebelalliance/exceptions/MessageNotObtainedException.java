package org.rebelalliance.exceptions;

public class MessageNotObtainedException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1748950400747640321L;

	public MessageNotObtainedException() {
        super("Secret message could not be obtained.");
    }
}

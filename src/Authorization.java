
//**************************** DO NOT MODIFY THIS INTERFACE**********************************//

public interface Authorization {
	
	public static final String ERROR = "-- Authorization Failed --";	// default error message
	
	
	/**
	 * Uses for authorizing this object
	 * @return true if this object has been authorized, otherwise return false
	 */
	public abstract boolean authorize();
}

//*****************************************************************************//
	
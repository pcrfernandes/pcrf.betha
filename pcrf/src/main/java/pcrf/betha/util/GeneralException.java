package pcrf.betha.util;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class GeneralException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public GeneralException(String msg) {
		super(msg);
	}

}

package pcrf.betha.app;

import javax.inject.Inject;

import org.jboss.seam.international.status.Messages;
import org.jboss.solder.exception.control.CaughtException;
import org.jboss.solder.exception.control.Handles;
import org.jboss.solder.exception.control.HandlesExceptions;

import pcrf.betha.util.GeneralException;

@HandlesExceptions
public class ExceptionHandler {
	@Inject
	private Messages messages;
	
	public void geralExceptionHandler(@Handles CaughtException<Throwable> evt) {
		this.messages.error("ERRO: " + evt.getException().getMessage());
		evt.getException().printStackTrace();
		evt.handled();
    }

	public void totalExceptionHandler(@Handles CaughtException<GeneralException> evt) {
		messages.error(evt.getException().getMessage());
		evt.handled();
    }

}

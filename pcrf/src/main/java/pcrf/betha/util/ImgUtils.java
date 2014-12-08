package pcrf.betha.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
public class ImgUtils implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String saveImg(byte[] dados) {
		String filename = String.valueOf(UUID.randomUUID());
		
		try {
			FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + filename);// substituir a var do openshift por "/tmp/" para teste locais
			os.write(dados);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return filename;
	}
	
	public static String showImg(String filename) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

		String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI().substring(0), "") + request.getContextPath();
		
		return baseUrl + "/rest/compras/item/img/" + filename;
	}

}

package pcrf.betha.rest.img;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

@Path("/compras/item/img")
public class ImgResources {
	private String filename;
	
	private boolean setParams(String filename) {
		try {
			this.filename = filename;
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	@GET
	@Path("{filename}")
	@Produces("image/*")
	public Response getImg(@PathParam("filename") String fname) {
		if (!setParams(fname))
			return Response.serverError().build();

        StreamingOutput stream = new StreamingOutput() {
    	    public void write(OutputStream out) throws IOException, WebApplicationException {
    			File file = new File(System.getenv("OPENSHIFT_DATA_DIR") + filename);// substituir a var do openshift por "/tmp/" para teste locais
    		    InputStream input = new FileInputStream(file);

    		    byte[] bytes = new byte[4096];
    		    int read = 0;
    		    while ((read = input.read(bytes, 0, 4096)) != -1) {
    		        out.write(bytes, 0, read);
    		        out.flush();
    		    }
    		 
    		    input.close();
    		    out.close();
    	    }
        };
        
        ResponseBuilder response = Response.ok(stream);
        response.header("Content-Disposition", "inline");
        return response.build();
	}
	
}

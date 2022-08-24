
package fr.m2i.javaapirest.tpuser.resource;

import fr.m2i.javaapirest.annuaireapi.*;
import fr.m2i.javaapirest.tpuser.util.SessionHelper;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("/users")
public class UserResource {

   

    // URI : /
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Personne> getUsers(@Context HttpServletRequest request) {
        System.out.println("Endpoint : getPersonnes");
        SessionHelper.getEntityManager();

        return null;
    }
    
     

}









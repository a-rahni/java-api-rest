
package fr.m2i.javaapirest.tpuser.resource;

import fr.m2i.javaapirest.annuaireapi.*;
import fr.m2i.javaapirest.tpuser.dao.UserDAO;
import fr.m2i.javaapirest.tpuser.exception.UserDaoErrorException;
import fr.m2i.javaapirest.tpuser.exception.UserNotFoundException;
import fr.m2i.javaapirest.tpuser.model.User;
import fr.m2i.javaapirest.tpuser.util.SessionHelper;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/users")
public class UserResource {
    
    UserDAO userDao = new UserDAO();

   

    // URI : /
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getUsers(@Context HttpServletRequest request) {
        try{
            List<User> list = userDao.findAll();
            return Response.status(Response.Status.OK)
                    .entity(list).build();
        }catch(UserNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }catch(UserDaoErrorException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
    

//    @POST
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public User createUser(User user) {
//        // Ajout user dans la liste
//        User created = userDao.create(user);
//
//        return created;
//    }
    
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response getUsers(@PathParam("id") int id) {
        try{
            User user = userDao.findById(id);
            return Response.status(Response.Status.OK)
                        .entity(user).build();
        
        }catch(UserNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }catch(UserDaoErrorException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createUser(User user) {
        
        if(false == userDao.checkValidityUserData(user)){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("données user invalides").build();
        }
        // to do: check if meail is already used
        try{
            // Ajout user dans la liste
            User created = userDao.create(user);

            if(created == null){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("user non créé").build();
            }

            return Response.status(Response.Status.CREATED)
                        .entity(created).build();
        }catch(UserNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }catch(UserDaoErrorException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateUser(@PathParam("id") int id, User user) {
        
        if(user.getEmail()!= null && !"".equals(user.getEmail())){
            // si le nouveua email est déja utilisé par d'autre user (email unique)
            // on peut ne pas le gerer ici, update en bdd ne passera si email n'est pas unique
            User userWithEmail = userDao.findByEmail(user.getEmail());
            if(userWithEmail != null && userWithEmail.getId()!=id){
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("the new email is already used by another user").build();
            }
        }
        
        try{

            User userToUpdate= userDao.update(id, user);

            if(userToUpdate == null){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("user was not updates").build();
            }
            return Response.status(Response.Status.OK)
                        .entity(userToUpdate).build();
            
        }catch(UserNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }catch(UserDaoErrorException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
    
    
    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteUser(@PathParam("id") Integer id) {
        
        try{
            if(userDao.delete(id)){
                return Response.status(Response.Status.OK)
                        .entity("User successfully deleted").build();
            }
            return Response.status(Response.Status.BAD_REQUEST)
                        .entity("An error occurred").build();
            
        }catch(UserNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND)
                .entity(e.getMessage()).build();
        }catch(UserDaoErrorException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/search")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> SerachByEmailOrLastName(@QueryParam("q") String str, @DefaultValue("1") @QueryParam("count") int count ) {
        return userDao.findByEmailOrLastname(str, count);
        
    }
        
    
}









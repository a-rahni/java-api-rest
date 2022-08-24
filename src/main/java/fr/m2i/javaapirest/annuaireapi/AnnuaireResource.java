
package fr.m2i.javaapirest.annuaireapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/personnes")
public class AnnuaireResource {

    // URI : /
    @POST
    //@Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Personne createPersonne(Personne personne, @Context HttpServletRequest request) {
        System.out.println("Endpoint : createPersonne");

        // to do: check que personne est != null et au moins 1 attribut non null
        //      sinon reponse badRequest
        
        // Récupérer l'annuaire stocké dans les attributs de la Session
        AnnuaireDAO annuaire = (AnnuaireDAO) request.getSession().getAttribute("annuaire");

        // Dans le cas où mon annuaire est null, je l'instancie
        if (annuaire == null) {
            annuaire = new AnnuaireDAO();
        }

        // Ajout de la personne dans la liste
        Personne created = annuaire.create(personne);

        // Créer / met à jour mon annuaire en Session
        request.getSession().setAttribute("annuaire", annuaire);

        return created;
    }

    // URI : /
    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<Personne> getPersonnes(@Context HttpServletRequest request) {
        System.out.println("Endpoint : getPersonnes");

        // Récupérer l'annuaire stocké dans les attributs de la Session
        AnnuaireDAO annuaire = (AnnuaireDAO) request.getSession().getAttribute("annuaire");

        if (annuaire == null) {
            return new ArrayList();
        }

        return annuaire.getPersonnes();
        //tri
        //return Collections.sort(annuaire.getPersonnes(),(p1,p2)->{return (p1.getId()- p2.getId());});
    }
    
     // URI : /1
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonneById(@PathParam("id") Long id, @Context HttpServletRequest request) {

        // Vérifie le paramètre 'id' -> bad request si invalide
        if (id == null || id < 1L) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Le paramètre id est invalide").build();
        }

        AnnuaireDAO annuaire = (AnnuaireDAO) request.getSession().getAttribute("annuaire");
        String notFoundError = String.format("La personne avec l'id: %d n'existe pas", id);

        // L'annuaire n'est pas encore créer -> personne not found
        if (annuaire == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(notFoundError).build();
        }

        Personne personne = annuaire.getPersonneById(id);

        // Personne non trouvé dans la liste -> personne not found
        if (personne == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(notFoundError).build();
        }

        return Response.status(Response.Status.OK).entity(personne).build();
    }
    
    // URI : /1
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Personne personne, @Context HttpServletRequest request) {
        System.out.println("Endpoint : update");
        // to do: check que personne est != null et au moins 1 attribut non null
        //      sinon reponse badRequest
        
        // Vérifie le paramètre 'id' -> bad request si invalide
        if (id == null || id < 1L) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Le paramètre id est invalide").build();
        }

        AnnuaireDAO annuaire = (AnnuaireDAO) request.getSession().getAttribute("annuaire");
        String notFoundError = String.format("La personne avec l'id: %d n'existe pas", id);

        // L'annuaire n'est pas encore créer -> personne not found
        if (annuaire == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(notFoundError).build();
        }

        boolean success = annuaire.update(id, personne);

        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).entity(notFoundError).build();
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
     // URI : /1
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePersonne(@PathParam("id") Long id, @Context HttpServletRequest request) {

        // Vérifie le paramètre 'id' -> bad request si invalide
        if (id == null || id < 1L) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Le paramètre id est invalide").build();
        }

        AnnuaireDAO annuaire = (AnnuaireDAO) request.getSession().getAttribute("annuaire");
        String notFoundError = String.format("La personne avec l'id: %d n'existe pas", id);

        // L'annuaire n'est pas encore créer -> personne not found
        if (annuaire == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(notFoundError).build();
        }

        boolean success = annuaire.delete(id);

        if (!success) {
            return Response.status(Response.Status.NOT_FOUND).entity(notFoundError).build();
        }

        return Response.status(Response.Status.OK).entity("La personne a bien été supprimé").build();
    }

}


















/*
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/annuaire")
public class AnnuaireResource {
    
    
    // URI: annuaire/personnes
    @GET
    @Path("/personnes")
    public List<Personne> findAll(@Context HttpServletRequest request){
        AnnuaireDAO annuaire =  getAnnuaire(request);
        return annuaire.findAll();     
    }
    
    // URI: annuaire/personnes/id
    @GET
    @Path("/personnes/{id}")
    public Personne findById(@Context HttpServletRequest request, @PathParam("id") long id){
        
        AnnuaireDAO annuaire =  getAnnuaire(request);
        return annuaire.findById(id);
   
    }
    
    // URI: annuaire/personnes/new
    @POST
    @Path("/personnes/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //@Consumes(MediaType.APPLICATION_JSON)
    public void create(@Context HttpServletRequest request,@FormParam("nom") String nom,@FormParam("prenom") String prenom){
        
        AnnuaireDAO annuaire =  getAnnuaire(request);  
        Personne personne = new Personne(nom,prenom);
        annuaire.save(personne);
         
    }
    
//    //TO DO
//    @POST  
//    @Path("/personnes/new")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Personne create(Personne personne){
//        return null;     
//    }
    
    
    // URI: annuaire/personnes/new
    @PUT
    @Path("/personnes")
    //@Path("/personnes/{id}")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@Context HttpServletRequest request,Personne personne){   
        AnnuaireDAO annuaire =  getAnnuaire(request);      
        annuaire.update(personne); 
    }
    
    // URI: annuaire/personnes/new
    @DELETE
    @Path("/personnes/{id}")
    //@Path("/personnes/{id}")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(@Context HttpServletRequest request,@PathParam("id") long id){   
        AnnuaireDAO annuaire =  getAnnuaire(request);      
        annuaire.delete(id);
    }
    
    
    
     public AnnuaireDAO getAnnuaire(@Context HttpServletRequest request){
        
        //AnnuaireDao annuaire = (AnnuaireDAO)request.getServletContext().getAttribute("annuaire");
        HttpSession session = request.getSession();
        AnnuaireDAO annuaire = (AnnuaireDAO) session.getAttribute("annuaire");
        
        if(annuaire == null)
        {
            annuaire = new AnnuaireDAO();
            session.setAttribute("annuaire", annuaire);
        }
        return annuaire;
            
     }
    
}

*/
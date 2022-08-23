
package fr.m2i.javaapirest.annuaireapi;

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
        AnnuaireDao annuaire =  getAnnuaire(request);
        return annuaire.findAll();     
    }
    
    // URI: annuaire/personnes/id
    @GET
    @Path("/personnes/{id}")
    public Personne findById(@Context HttpServletRequest request, @PathParam("id") long id){
        
        AnnuaireDao annuaire =  getAnnuaire(request);
        return annuaire.findById(id);
   
    }
    
    // URI: annuaire/personnes/new
    @POST
    @Path("/personnes/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //@Consumes(MediaType.APPLICATION_JSON)
    public void create(@Context HttpServletRequest request,@FormParam("nom") String nom,@FormParam("prenom") String prenom){
        
        AnnuaireDao annuaire =  getAnnuaire(request);  
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
        AnnuaireDao annuaire =  getAnnuaire(request);      
        annuaire.update(personne); 
    }
    
    // URI: annuaire/personnes/new
    @DELETE
    @Path("/personnes/{id}")
    //@Path("/personnes/{id}")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Consumes(MediaType.APPLICATION_JSON)
    public void delete(@Context HttpServletRequest request,@PathParam("id") long id){   
        AnnuaireDao annuaire =  getAnnuaire(request);      
        annuaire.delete(id);
    }
    
    
    
     public AnnuaireDao getAnnuaire(@Context HttpServletRequest request){
        
        //AnnuaireDao annuaire = (AnnuaireDao)request.getServletContext().getAttribute("annuaire");
        HttpSession session = request.getSession();
        AnnuaireDao annuaire = (AnnuaireDao) session.getAttribute("annuaire");
        
        if(annuaire == null)
        {
            annuaire = new AnnuaireDao();
            session.setAttribute("annuaire", annuaire);
        }
        return annuaire;
            
     }
    
}

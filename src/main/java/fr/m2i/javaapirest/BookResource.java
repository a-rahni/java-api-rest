
package fr.m2i.javaapirest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author rahni
 */
@Path("/books") 
public class BookResource {

    // URI:  /books/
    @GET
    //@Produces("text/plain")  // type de la réponse
    public String getBooks() {
        System.out.println("Endpoint : getBooks");
        return "JAVA 8 docs / Angular pour les nuls / les voyages les plus fou";
    }
    
    // URI /books/borrowed/
    @GET
    //@Produces("text/plain")  // type de la réponse
     @Path("/borrowed") 
    public String getBorrowedBooks() {
        System.out.println("Endpoint : getBorrowedBooks");
        return "Angular pour les nuls / les voyages les plus fou";
    }
    
    // URI /books/id
    @GET
    @Path("/{id}")   // ca peut fonctinner sans preciser / au debut (mieux de l'ajouter)
    public String getBookById(@PathParam("id") int id) {
        System.out.println("Endpoint : getBookById");
        return "Livre avec id: "+ id + " - Java 8 docs";
    }
    
    // URI : /books/name-angular-editor-google
    @GET
    @Path("/name-{name}-editor-{editor}")
    public String getBookByNameAndEditor(@PathParam("name") String name, @PathParam("editor") String editor) {
        System.out.println("Endpoint : getBookByNameAndEditor");
        return "Livre avec le nom : " + name + ", l'editeur : " + editor + " - Angular pour les nuls";
    }
    
    // URI : /books/query-parameters?name=harry&isbn=1-111111-11&isExtended=true
    //pour des valeur avec caratères spéciaux, utiliser l'encodage url  (exp & --> %26
    @GET
    @Path("/query-parameters")
    public String getQueryParametersBook(@DefaultValue("default") @QueryParam("name") String name,
            @DefaultValue("?-??????-??") @QueryParam("isbn") String isbn,
            @DefaultValue("false") @QueryParam("isExtended") boolean isExtended) {

        return "Livre recherché - nom : " + name + ", isbn : " + isbn + ", version étendue : " + isExtended;
    }
    
    // URI : /books/header-parameters
    @GET
    @Path("/header-parameters")       // les params value sont envoyé dans le header
    public String geHeaderParametersBook(@DefaultValue("default") @HeaderParam("name") String name,
            @DefaultValue("?-??????-??") @HeaderParam("isbn") String isbn,
            @DefaultValue("false") @HeaderParam("isExtended") boolean isExtended) {

        System.out.println("Endpoint : getHeaderParametersBook");
        return "Livre recherché via headers - nom : " + name + ", isbn : " + isbn + ", version étendue : " + isExtended;
    }
    
    // URI : /books/create-from-form
    @POST   // passage de données par le body de la requete (non testable via navigateur, utiliser Postman
    @Path("/create-from-form")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Consumes("application/x-www-form-urlencoded")
    public String createFromForm(@FormParam("name") String name) {
        System.out.println("Endpoint : create from form");
        return ("Le nom du livre créé : " + name);
    }
    
     // URI : /context-uri-info/harry
    @GET
    @Path("/context-uri-info/{name}")
    public String geContextUriInfo(@Context UriInfo uriInfo, @PathParam("name") String name,
            @QueryParam("queryParam") String queryParam) {
        System.out.println("Endpoint : geContextUriInfo");

        StringBuilder result = new StringBuilder();
        result.append("getPath(): ")
                .append(uriInfo.getPath())
                .append("\n")
                .append("getPathSegments(): ");

        for (PathSegment p : uriInfo.getPathSegments()) {
            result.append(p.getPath()).append(" ");
        }

        result.append("\n")
                .append("getPathParameters(): ");

        for (String p : uriInfo.getPathParameters().keySet()) {
            result.append(p).append(" ");
        }

        result.append("\n")
                .append("getQueryParameters(): ");

        for (String p : uriInfo.getQueryParameters().keySet()) {
            result.append(p).append(" ");
        }

        result.append("\n")
                .append("getAbsolutePath(): ").append(uriInfo.getAbsolutePath()).append("\n")
                .append("getBaseURI(): ").append(uriInfo.getBaseUri()).append("\n")
                .append("getRequestURI(): ").append(uriInfo.getRequestUri()).append("\n");

        return result.toString();
    }
    
    // URI : /context-headers
    @GET
    @Path("/context-headers")
    public String geContextHeaders(@Context HttpHeaders headers) {
        System.out.println("Endpoint : geContextHeaders");

        StringBuilder result = new StringBuilder();
        result.append("Cookies: ");

        for (String key : headers.getCookies().keySet()) {
            result.append(key).append("\n");
        }

        result.append("RequestHeaders: ");

        MultivaluedMap<String, String> requestHeaders = headers.getRequestHeaders();

        for (String key : requestHeaders.keySet()) {
            result.append(key).append(" : ").append(requestHeaders.get(key)).append("\n");
        }
        
        return result.toString();
    }

    


}
    


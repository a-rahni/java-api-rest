
package fr.m2i.javaapirest.annuaireapi;

// par defaut Jakson utilise JSON. 

import javax.xml.bind.annotation.XmlRootElement;

//on ajouter des annotation pour autre format serialisation/deserialisation xml
@XmlRootElement(name="personne")
public class Personne {
    private Long id;
    
    private String nom;
    
    private String prenom;

    public Personne( String nom, String prenom) {
        //this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    // necessaire pour Jakson deserialisation
    public Personne() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    
    
}


package fr.m2i.javaapirest.annuaireapi;

public class Personne {
    private Long id;
    
    private String nom;
    
    private String prenom;

    public Personne( String nom, String prenom) {
        //this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    // necessaire pour Jax
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

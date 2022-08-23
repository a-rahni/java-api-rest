
package fr.m2i.javaapirest.annuaireapi;

import java.util.ArrayList;
import java.util.List;

public class AnnuaireDao {
    private List<Personne> personnes;
    
    public AnnuaireDao(){
        personnes = new ArrayList<Personne>();    
    }
    
    public List<Personne> findAll(){
        return personnes;
    }
    
    public Personne findById(Long id){
        if(id == null)  return null;
        for(Personne p:personnes){
            if(p.getId()==id){
                return p;
            }
        }
        return null;
    }
    
    public void save(Personne personne){
        if(personne == null){
            System.out.println("the object to save is null");
            return;
        }
        
        personne.setId(Long.valueOf(personnes.size()+1));
        personnes.add(personne);
    }
    
     public void update(Personne personne){
        if(personne == null){
            System.out.println("the object to update is null");
            return;
        }
        
        int index = personnes.size();
        for(int i=0; i<personnes.size(); i++){
            if(personnes.get(i).getId()==personne.getId()){
                index =i;
                break;
            }
        }
        
        if(index>=personnes.size()){
            System.out.println("the object to update is not found");
            return;
        }
        
       personnes.set(index, personne);
    }
     
     public void delete(Personne personne){
        if(personne == null){
            System.out.println("the object to delete is null");
            return;
        }      
        personnes.remove(personne);       
     }
     
      public void delete(Long id){
        if(id == null){
            System.out.println("the object to delete is null");
            return;
        }
        Personne pToDelete = null;
        for(Personne p:personnes){
            if(p.getId()==id){
                pToDelete = p;
                break;
            }
        }
        personnes.remove(pToDelete);       
     }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.picod.statBeans;

import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.picod.dao.DeclarationFacadeLocal;
import org.picod.entities.Declaration;
import org.primefaces.event.SlideEndEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Anatole ABE
 */
@ManagedBean
@SessionScoped
public class StatDeclarationsBean {
    private CartesianChartModel diagrammeHF;
    private CartesianChartModel diagrammeTranAge;
    private Integer hauteurDiagram = 100;
    private Integer etatAfficher = 1; // diagramme par defaut à afficher (diagramme HF), 2 pour les tranches d'age
    private Integer annee = new Integer(new Date().getYear()+1900);
    
    @EJB
    private DeclarationFacadeLocal declarationFacadeLocal;
    
    /**
     * Creates a new instance of StatDeclarationsBean
     */
    public StatDeclarationsBean() {
    }
    
    /**
     * genère le diagramme des déclarations
     * @return 
     */
    public CartesianChartModel genererDiagrammeHF(){
        diagrammeHF = new CartesianChartModel();
        ChartSeries hommes = new ChartSeries("Hommes");
        ChartSeries femmes = new ChartSeries("Femmes");
        String req = "SELECT COUNT(id_declaration) FROM declaration d WHERE YEAR(DATE) = "+this.annee+" and (MONTH (date) = ";
        
        for (int i= 1; i<=12 ;i++){
            hommes.set(intTomois(i), declarationFacadeLocal.countWithCriteria(req+ i +" ) and sexe ='Homme'"));  //on compte le nombre de déclartions des hommes pour ce mois
            femmes.set(intTomois(i), declarationFacadeLocal.countWithCriteria(req+ i +" ) and sexe ='Femme'"));  //on compte le nombre de déclartions des femmes pour ce mois
        }
        diagrammeHF.addSeries(hommes);
        diagrammeHF.addSeries(femmes);
        return diagrammeHF;
    }
    
    /**
     * genère le diagramme des déclarations en fonction des tranches d'ages
     * @return 
     */
    public CartesianChartModel genererDiagrammetranAge(){
        diagrammeTranAge = new CartesianChartModel();
        ChartSeries hommes = new ChartSeries("Hommes");
        ChartSeries femmes = new ChartSeries("Femmes");
        String req = "SELECT COUNT(id_declaration) FROM declaration d WHERE YEAR(DATE) = "+this.annee+" and age = ";
        
        for (int i= 1; i<=5 ;i++){  //on parcour toutes les index des ages
            hommes.set(intToTrangeAge(i), declarationFacadeLocal.countWithCriteria(req+ i +"  and sexe ='Homme'"));  //on compte le nombre de déclartions des hommes pour ce mois
            femmes.set(intToTrangeAge(i), declarationFacadeLocal.countWithCriteria(req+ i +"  and sexe ='Femme'"));  //on compte le nombre de déclartions des femmes pour ce mois
        }
        diagrammeTranAge.addSeries(hommes);
        diagrammeTranAge.addSeries(femmes);
        return diagrammeTranAge;
    }
    
    /**
     * Fonction qui fait correspondre un entien a un mois
     * @param i index du mois 
     * @return moi : le nom du mois
     */
    public String intTomois(int i){
        
        if (i==1) return "Jan";
        if (i==2) return "Fev";
        if (i==3) return "Mars";
        if (i==4) return "Avril";
        if (i==5) return "Mai";
        if (i==6) return "Juin";
        if (i==7) return "Juil";
        if (i==8) return "Aout";
        if (i==9) return "Sept";
        if (i==10) return "Oct";
        if (i==11) return "Nov";
        if (i==12) return "Dec";
        return "unknow";
    }
    /**
     * Fonction qui fait correspondre un entien a la tranche d'age
     * @param i index du mois 
     * @return tranche : la tranche d'age
     */
    public String intToTrangeAge(int i){
        
        if (i==1) return "Inf à 1 ans";
        if (i==2) return "1-4 ans";
        if (i==3) return "5-14 ans";
        if (i==4) return "15-45";
        if (i==5) return "sup 45 ans";
        return "unknow";
    }

    
    public void onSlideEnd(SlideEndEvent event) {
        
        hauteurDiagram = new Integer(event.getValue());
        System.out.println("la hauteur est = "+hauteurDiagram);
    }
    
//    ------------------------------------------------- getter and setters -----------------------------------------------
    
    
    
    public CartesianChartModel getDiagrammeHF() {
        return genererDiagrammeHF();
    }

    public void setDiagrammeHF(CartesianChartModel diagrammeHF) {
        this.diagrammeHF = diagrammeHF;
    }

    public Integer getHauteurDiagram() {
        return hauteurDiagram;
    }

    public void setHauteurDiagram(Integer hauteurDiagram) {
        this.hauteurDiagram = hauteurDiagram;
    }

    public CartesianChartModel getDiagrammeTranAge() {
        return genererDiagrammetranAge();
    }

    public void setDiagrammeTranAge(CartesianChartModel diagrammeTranAge) {
        this.diagrammeTranAge = diagrammeTranAge;
    }

    public Integer getEtatAfficher() {
        
        return etatAfficher;
    }

    public void setEtatAfficher(Integer etatAfficher) {
        this.etatAfficher = etatAfficher;
    }

    public Integer getAnnee() {
        System.out.println("la annee est = "+annee);
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }
    
    
    
    
}

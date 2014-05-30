/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.fema.view;

import br.edu.fema.controller.MarcaJpa;
import br.edu.fema.controller.MarcaJpaController;
import br.edu.fema.model.Marca;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import util.JPAUtil;

/**
 *
 * @author dvillela
 */
@ManagedBean
@ViewScoped
public class MarcaMB {

    private List<Marca> marcas;
    private Marca marca;

    /**
     * Creates a new instance of MarcaMB
     */
    public MarcaMB() {
        this.marca = new Marca();
    }
    
    public void save(Marca marca){
        MarcaJpa marcaJpa = new MarcaJpaController(JPAUtil.getEmf());
        marcaJpa.create(marca);
        this.marca = new Marca();
    }

    public List<Marca> getMarcas() {
        MarcaJpa marcaJpa = new MarcaJpaController(JPAUtil.getEmf());
        this.marcas = marcaJpa.findMarcaEntities();
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

}

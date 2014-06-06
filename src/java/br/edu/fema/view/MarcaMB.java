/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.fema.view;

import br.edu.fema.controller.MarcaJpa;
import br.edu.fema.controller.MarcaJpaController;
import br.edu.fema.controller.exceptions.IllegalOrphanException;
import br.edu.fema.controller.exceptions.NonexistentEntityException;
import br.edu.fema.model.Marca;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import util.JPAUtil;

/**
 *
 * @author dvillela
 */
@ManagedBean(name = "marcaMB")
@SessionScoped
public class MarcaMB {

    // Nova marca
    private Marca marca;
    // Edição de Marca
    private Marca marcaEd;
    private List<Marca> marcas;

    /**
     * Creates a new instance of MarcaMB
     */
    public MarcaMB() {
        this.marca = new Marca();
        this.marcaEd = null;
        this.marcas = null;
    }
    
    public void delMarca(Marca marca) {
        System.out.println("Apagando " + marca.getNome());
        this.marcaEd = marca;
        Map<String,Object> options = new HashMap<String, Object>();
        //options.put("modal", true);
        //options.put("draggable", false);
        //options.put("resizable", false);
        //options.put("contentHeight", 320);
        options.put("contentWidth", 100);
        RequestContext.getCurrentInstance().openDialog("delMarca");
    }
    
    public void altMarca(Marca marca) {
        System.out.println("Apagando " + marca.getNome());
        this.marcaEd = marca;
        Map<String,Object> options = new HashMap<String, Object>();
        //options.put("modal", true);
        //options.put("draggable", false);
        //options.put("resizable", false);
        //options.put("contentHeight", 320);
        //options.put("contentWidth", 100);
        RequestContext.getCurrentInstance().openDialog("altMarca");
    }

    public void save(Marca marca) {
        System.out.println("Salvando a marca " + marca.getNome());
        FacesMessage.Severity severity;
        String message;
        MarcaJpa marcaJpa = new MarcaJpaController(JPAUtil.getEmf());
        marcaJpa.create(marca);
        message = marca.getNome() + " - Salva com sucesso";
        severity = FacesMessage.SEVERITY_INFO;
        FacesMessage msg = new FacesMessage(severity, message, null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.marca = new Marca();
    }

    public void apagar() {
        System.out.println("Apagando " + this.marca.getNome());
        //FacesMessage.Severity severity;
        //String message;
        //MarcaJpa marcaJpa = new MarcaJpaController(JPAUtil.getEmf());
        //try {
        //    marcaJpa.destroy(marca.getId());
        //    message = marca.getNome() + " - Deletado";
        //    severity = FacesMessage.SEVERITY_INFO;
        //} catch (IllegalOrphanException ex) {
        //    message = marca.getNome() + " - Esta marca tem modelo ligado a ela";
        //    severity = FacesMessage.SEVERITY_ERROR;
        //} catch (NonexistentEntityException ex) {
        //    message = marca.getNome() + " - Não exixte esta marca";
        //    severity = FacesMessage.SEVERITY_ERROR;
        //}
        //FacesMessage msg = new FacesMessage(severity, message, null);
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        RequestContext.getCurrentInstance().closeDialog("delMarca");
    }

    public void atualizar() {
        System.out.println("Atualizando " + marcaEd.getNome());
        FacesMessage.Severity severity = null;
        String message = null;
        MarcaJpa marcaJpa = new MarcaJpaController(JPAUtil.getEmf());
        Marca marcaAtu;
        try {
            marcaAtu = marcaJpa.findMarca(this.marcaEd.getId());
            marcaAtu.setNome(this.marcaEd.getNome());
            marcaJpa.edit(marcaAtu);
            message = marcaEd.getNome() + " - Alterada";
            severity = FacesMessage.SEVERITY_INFO;
        } catch (IllegalOrphanException ex) {
            message = marcaEd.getNome() + " - Esta marca tem modelo ligado a ela";
            severity = FacesMessage.SEVERITY_ERROR;
        } catch (NonexistentEntityException ex) {
            message = marcaEd.getNome() + " - Esta marca não existe";
            severity = FacesMessage.SEVERITY_ERROR;
        } catch (Exception ex) {
            message = marcaEd.getNome() + " - Exception";
            severity = FacesMessage.SEVERITY_ERROR;
        }

        FacesMessage msg = new FacesMessage(severity, message, null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void edit(Marca marca) {
        System.out.println("Editando parametro ===> " + marca.getNome());
        this.marcaEd = new Marca();
        this.marcaEd.setId(marca.getId());
        this.marcaEd.setNome(marca.getNome());
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Marca> getMarcas() {
        MarcaJpa marcaJpa = new MarcaJpaController(JPAUtil.getEmf());
        this.marcas = marcaJpa.findMarcaEntities();
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public Marca getMarcaEd() {
        return marcaEd;
    }

    public void setMarcaEd(Marca marcaEd) {
        this.marcaEd = marcaEd;
    }
}

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import util.JPAUtil;

/**
 *
 * @author dvillela
 */
@ManagedBean(name = "marcaMB")
@SessionScoped
public class MarcaMB {

    // Action - realiza ações e navegações.
    // ActionListener - altera o estado de um objeto (componentes de tela ou suas classes), geralmente com ajax. 
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
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 100);
        options.put("contentWidth", 250);
        RequestContext.getCurrentInstance().openDialog("delMarca", options, null);
    }

    public void altMarca(Marca marca) {
        System.out.println("Apagando " + marca.getNome());
        this.marcaEd = marca;
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 110);
        options.put("contentWidth", 280);
        RequestContext.getCurrentInstance().openDialog("altMarca", options, null);
    }

    public void save(Marca marca) {
        MarcaJpa marcaJpa = new MarcaJpaController(JPAUtil.getEmf());
        marcaJpa.create(marca);
        System.out.println("Salvando a marca " + marca.getNome() + " - " + FacesContext.getCurrentInstance());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Marca salva", "Id:" + marca.getId() + " - " + marca.getNome());
        FacesContext.getCurrentInstance().addMessage(null, message);
        this.marca = new Marca();
    }

    public void apagar(Marca marca) {
        System.out.println("Apagando " + marca.getNome() + " - " + marca.getId());
        FacesMessage.Severity severity = null;
        String message = "???";
        MarcaJpa marcaJpa = new MarcaJpaController(JPAUtil.getEmf());
        try {
            marcaJpa.destroy(marca.getId());
            message = marca.getNome() + " - Deletado";
            severity = FacesMessage.SEVERITY_INFO;
        } catch (IllegalOrphanException ex) {
            message = marca.getNome() + " - Esta marca tem modelo ligado a ela";
            severity = FacesMessage.SEVERITY_ERROR;
        } catch (NonexistentEntityException ex) {
            message = marca.getNome() + " - Não exixte esta marca";
            severity = FacesMessage.SEVERITY_ERROR;
        } finally {
            FacesMessage msg = new FacesMessage(severity, message, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().closeDialog("delMarca");
        }
    }

    public void apagarCancel(ActionEvent event) {
        RequestContext.getCurrentInstance().closeDialog("delMarca");
    }

    public void atualizar(Marca marca) {
        System.out.println("Atualizando " + marcaEd.getNome());
        FacesMessage.Severity severity = null;
        String message = null;
        MarcaJpa marcaJpa = new MarcaJpaController(JPAUtil.getEmf());
        Marca marcaAtu;
        try {
            marcaAtu = marcaJpa.findMarca(marcaEd.getId());
            marcaAtu.setNome(marcaEd.getNome());
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
        } finally {
            FacesMessage msg = new FacesMessage(severity, message, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().closeDialog("altMarca");
        }
    }

    public void atualizarCancel(ActionEvent event) {
        RequestContext.getCurrentInstance().closeDialog("altMarca");
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

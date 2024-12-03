/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package br.jsf;

import br.ejb.EJBOlaMundo;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

/**
 *
 * @author a2419890
 */

// JSF --> EJB --> Payara

@Named(value = "jSFOi")
@RequestScoped
public class JSFOi {

    @EJB
    EJBOlaMundo ejb = new EJBOlaMundo();
    
    /**
     * Creates a new instance of JSFOi
     */
    public JSFOi() {
    }
    public String getOi() {
        return ejb.getOi();
    }
}

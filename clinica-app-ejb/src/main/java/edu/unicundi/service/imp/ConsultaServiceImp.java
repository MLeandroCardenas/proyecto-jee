/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unicundi.service.imp;

import edu.unicundi.entity.Consulta;
import edu.unicundi.entity.DetalleConsulta;
import edu.unicundi.exception.NotFoundModelException;
import edu.unicundi.repo.IConsultaRepo;
import edu.unicundi.service.IConsultaService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ASUS-PC
 */
@Stateless
public class ConsultaServiceImp implements IConsultaService {
    
    @EJB
    private IConsultaRepo repo;

    @Override
    public List<Consulta> listarTodos() {
        return repo.listarTodos();
    }

    @Override
    public Consulta listar(int id) throws NotFoundModelException {
        Consulta consulta = repo.listar(id);
        if (consulta == null) {
            throw new NotFoundModelException("Objeto consulta no encontrado");
        }

        Consulta consultaAux = new Consulta();
        consultaAux.setId(consulta.getId());
        for (DetalleConsulta dc : consulta.getDetalleConsulta()) {
            dc.setConsulta(consultaAux);
        }
        return consulta;
    }

    @Override
    public void guardar(Consulta consulta) {
        if (consulta.getDetalleConsulta() != null) {
            for (DetalleConsulta dc : consulta.getDetalleConsulta()) {
                dc.setConsulta(consulta);
            }
        }        
        repo.guardar(consulta);
    }

    @Override
    public void eliminar(int id) throws NotFoundModelException {
        Consulta consulta = repo.listar(id);
        if (consulta == null) 
            throw new NotFoundModelException("Objeto consulta no encontrado");  
        repo.eliminar(consulta);
    }

    @Override
    public void editar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

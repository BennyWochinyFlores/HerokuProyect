package bwf.idat.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bwf.idat.app.models.entity.Cliente;


@Repository("clienteDaoJPA")
public class ClienteDaoImpl implements IClienteDao {

	//Encargado del Mapeo:
	@PersistenceContext
	private EntityManager em;
	
	 //Salto de Advertencias:
	@SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList(); //LEER-SELECT
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
	    if(cliente.getId()!=null && cliente.getId()>0) {
	        em.merge(cliente);
	    } else {
			em.persist(cliente);
	    }
	}

    @Override
    @Transactional(readOnly = true)
    public Cliente editarCliente(Long id) {        
        return em.find(Cliente.class, id);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        em.remove(editarCliente(id));
    }

	

}

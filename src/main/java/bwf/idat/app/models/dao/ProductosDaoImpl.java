package bwf.idat.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bwf.idat.app.models.entity.Productos;



@Repository("productosDaoJpa")
public class ProductosDaoImpl implements ProductoDao{
	
	@PersistenceContext
	private EntityManager pem ;
	
	@SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
	@Override
	public List<Productos> findAll() {
		return pem.createQuery("from Productos").getResultList();}
	
	@Override
	@Transactional
	public void save(Productos productos) {
	    if(productos.getCodigo()!=null && productos.getCodigo()>0) {
	        pem.merge(productos);
	    } else {
			pem.persist(productos);
	    }

	}
	
	@Override
    @Transactional(readOnly = true)
    public Productos editarProductos(Long id) {        
        return pem.find(Productos.class, id);
    }

	@Override
	@Transactional
	public void eliminarProductos(Long id) {
		pem.remove(editarProductos(id));

		
	}
	
}





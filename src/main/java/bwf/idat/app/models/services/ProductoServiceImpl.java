package bwf.idat.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bwf.idat.app.models.dao.ProductosDaoImpl;
import bwf.idat.app.models.entity.Productos;



@Service
public class ProductoServiceImpl implements IProductosService {

	 @Autowired
	    private ProductosDaoImpl productoDao;
	    
	    @Override
	    @Transactional(readOnly = true)
	    public List<Productos> findAll() {
	        return (List<Productos>) productoDao.findAll();
	    }
}

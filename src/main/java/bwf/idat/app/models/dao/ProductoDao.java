package bwf.idat.app.models.dao;

import java.util.List;

import bwf.idat.app.models.entity.Productos;

public interface ProductoDao {

	public List<Productos> findAll(); 
	
	//Crear método guardar Productos:
	public void save(Productos productos);
	
	//Editar Productos:
	public Productos editarProductos(Long id);
	
	//Eliminar Productos:
	public void eliminarProductos(Long id);

}

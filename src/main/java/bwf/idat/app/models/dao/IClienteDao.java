package bwf.idat.app.models.dao;

import java.util.List;

import bwf.idat.app.models.entity.Cliente;



public interface IClienteDao {
	//Método para retornar toda la lista de clientes:
	public List<Cliente> findAll(); 
	
	//Crear método guardar:
	public void save(Cliente cliente);
	
	//Editar cliente:
	public Cliente editarCliente(Long id);
	
	//Eliminar Cliente:
	public void eliminarCliente(Long id);
}

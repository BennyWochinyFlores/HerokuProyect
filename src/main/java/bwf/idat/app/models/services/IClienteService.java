package bwf.idat.app.models.services;
import java.util.List;

import bwf.idat.app.models.entity.Cliente;



public interface IClienteService {
    public List<Cliente> findAll();
}

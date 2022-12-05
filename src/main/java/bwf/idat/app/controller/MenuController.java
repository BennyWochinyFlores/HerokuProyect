package bwf.idat.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import bwf.idat.app.models.dao.IClienteDao;
import bwf.idat.app.models.entity.Cliente;



@Controller
public class MenuController {

	//Traer la clase IClienteDao:
	
	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;
	
	@GetMapping(value={"/","/inicio"})
	public String detalles(Model model) {
		model.addAttribute("titulo","Pagina Principal");
		return "inicio";
	}
	
	//Métodos:	
	@GetMapping(value={"/listar"})
	public String listarClientes(Model model) {
		model.addAttribute("titulo","Listado de Empleados");
		model.addAttribute("clientes",clienteDao.findAll());
		return "listar";
	}
	
	@GetMapping(value="/form")
	public String crear(Model model) {
		model.addAttribute("titulo","Formulario de Cliente");
		Cliente cliente = new Cliente();
		model.addAttribute("cliente",cliente);
		return "form";
	}
	
	//Post:
	@PostMapping(value="/form")
	public String guardar(@Validated Cliente cliente, BindingResult result, Model model,
	        @RequestParam("file") MultipartFile foto){
		
		if(result.hasErrors()) {			
			return "form";
		}
		
		if(!foto.isEmpty()) {
		    Path directorioImagenes = Paths.get("src//main//resources//static/imagenes"); //Accedo al directorio
		    String imagen = directorioImagenes.toFile().getAbsolutePath(); //Accedo a la imagen
		    
		    try {
		        byte[] bytes= foto.getBytes();
		        Path rutaCompleta = Paths.get(imagen+"//"+foto.getOriginalFilename());
		        Files.write(rutaCompleta, bytes);
		        cliente.setFoto(foto.getOriginalFilename());
		    } catch(IOException e) {
		        e.printStackTrace();
		    }
		}
		clienteDao.save(cliente);
		return "redirect:listar";
	}
	
	//Método para ver la imagen en el navegador:
	
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Model model) {
	    Cliente cliente = clienteDao.editarCliente(id);	    
	    if(cliente==null) {
	        return "redirect://listar";
	    }
	    model.addAttribute("cliente",cliente);
	    model.addAttribute("titulo","Detalle del Cliente "+ cliente.getNombre());
	    return "ver";
	}
	
	//Método para Editar:
	
	@GetMapping(value="/form/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String,Object> model) {
	    Cliente cliente=null;
	    if(id>0) {
	        cliente=clienteDao.editarCliente(id);
	    } else {
	        return "redirect:/listar";
	    }
	    model.put("titulo", "Editar Cliente");
	    model.put("cliente",cliente);
	    return "form";
	}
	
	//Método para eliminar:
	@GetMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
	    if(id>0) { clienteDao.eliminarCliente(id);}
	    return "redirect:/listar";
	}
}

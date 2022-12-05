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

import bwf.idat.app.models.dao.ProductoDao;
import bwf.idat.app.models.entity.Productos;

@Controller
public class ProductoController {
	
	@Autowired
	@Qualifier("productosDaoJpa")
	private ProductoDao productosDao;
	
	
	@GetMapping(value={"/productos"})
	public String listarProductos(Model model) {
		model.addAttribute("titulo","Listado de Productos");
		model.addAttribute("producto",productosDao.findAll());
		return "productos";
	}
	
	@GetMapping(value="/detalle")
	public String crear(Model model) {
		model.addAttribute("titulo","Formulario de Producto");
		Productos productos = new Productos();
		model.addAttribute("productos",productos);
		return "detalle";
	}
	@PostMapping(value="/detalle")
	public String guardar(@Validated Productos productos, BindingResult result, Model model,
	        @RequestParam("file") MultipartFile foto){
		
		if(result.hasErrors()) {			
			return "detalle";
		}
		
		if(!foto.isEmpty()) {
		    Path directorioImagenes = Paths.get("src//main//resources//static/imagenes"); //Accedo al directorio
		    String imagen = directorioImagenes.toFile().getAbsolutePath(); //Accedo a la imagen
		    
		    try {
		        byte[] bytes= foto.getBytes();
		        Path rutaCompleta = Paths.get(imagen+"//"+foto.getOriginalFilename());
		        Files.write(rutaCompleta, bytes);
		        productos.setFoto(foto.getOriginalFilename());
		    } catch(IOException e) {
		        e.printStackTrace();
		    }
		}
		productosDao.save(productos);
		return "redirect:productos";
		}
	
	@GetMapping(value="/ver2/{codigo}")
	public String ver(@PathVariable(value="codigo") Long id, Model model) {
	    Productos productos = productosDao.editarProductos(id);	    
	    if(productos==null) {
	        return "redirect://productos";
	    }
	    model.addAttribute("productos",productos);
	    model.addAttribute("titulo","Detalle del Producto "+ productos.getNombre());
	    return "ver2";
	}
	
	
	
	
	
	@GetMapping(value="/detalle/{codigo}")
	public String editar(@PathVariable(value="codigo") Long id, Map<String,Object> model) {
	    Productos productos=null;
	    if(id>0) {
	        productos=productosDao.editarProductos(id);
	    } else {
	        return "redirect:/productos";
	    }
	    model.put("titulo", "Editar Productos");
	    model.put("productos",productos);
	    return "detalle";
	}
	@GetMapping(value="/borrar/{codigo}")
	public String eliminar(@PathVariable(value="codigo") Long id) {
	    if(id>0) { productosDao.eliminarProductos(id);}
	    return "redirect:/productos";
	}
	
	
	

}


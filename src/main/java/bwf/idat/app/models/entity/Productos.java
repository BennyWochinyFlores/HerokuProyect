package bwf.idat.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name= "producto")
public class Productos implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message = "Debes especificar el nombre")
	@Size(min = 2, max = 50, message = "El nombre debe tener al menos 2 caracteres")
	private String nombre;
	
	@NotBlank(message = "Debes especificar el nombre")
	@Size(min = 2, max = 50, message = "El nombre debe tener al menos 2 caracteres")
	private String marca;
	
	@NotBlank(message = "Debes especificar el nombre")
	@Size(min = 2, max = 50, message = "El nombre debe tener al menos 2 caracteres")
	private String precio;
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE) //
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createAt;
	
	private String foto;
	
	@OneToMany(mappedBy = "productos",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Stocks> stock;
	

	public List<Stocks> getStock() {
		return stock;
	}
	public void setStock(List<Stocks> stock) {
		this.stock = stock;
	}
	
	public void addStocks(Stocks stocks) {
        stock.add(stocks);
    }
	
	public Productos() {
        stock= new ArrayList<Stocks>();
    }
	
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	@PrePersist  
	public void prePersist() {
		createAt= new Date();
	}

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	

}









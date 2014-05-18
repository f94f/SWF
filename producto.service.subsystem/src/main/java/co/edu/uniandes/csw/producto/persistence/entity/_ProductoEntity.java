
package co.edu.uniandes.csw.producto.persistence.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class _ProductoEntity {

	@Id
	@GeneratedValue(generator = "Producto")
	private Long id;
	private String name;
	private String tipo;
	private Boolean esPerecedero;
	private Double precioPromedio;
	private Double tiempoPromedioEspera;
	private Double cantidadPromedioAPedir;
	private Double minimoNivelInventario;

	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	public Boolean getEsPerecedero(){
		return esPerecedero;
	}
	
	public void setEsPerecedero(Boolean esPerecedero){
		this.esPerecedero = esPerecedero;
	}
	public Double getPrecioPromedio(){
		return precioPromedio;
	}
	
	public void setPrecioPromedio(Double precioPromedio){
		this.precioPromedio = precioPromedio;
	}
	public Double getTiempoPromedioEspera(){
		return tiempoPromedioEspera;
	}
	
	public void setTiempoPromedioEspera(Double tiempoPromedioEspera){
		this.tiempoPromedioEspera = tiempoPromedioEspera;
	}
	public Double getCantidadPromedioAPedir(){
		return cantidadPromedioAPedir;
	}
	
	public void setCantidadPromedioAPedir(Double cantidadPromedioAPedir){
		this.cantidadPromedioAPedir = cantidadPromedioAPedir;
	}
	public Double getMinimoNivelInventario(){
		return minimoNivelInventario;
	}
	
	public void setMinimoNivelInventario(Double minimoNivelInventario){
		this.minimoNivelInventario = minimoNivelInventario;
	}
}
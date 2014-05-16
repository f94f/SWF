
package co.edu.uniandes.csw.ordenreaprovicionamiento.logic.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public abstract class _OrdenReaprovicionamientoDTO {

	private Long id;
	private String name;
	private Date fecha;
	private Integer cantidad;
	private String nombreProducto;

	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
	public Date getFecha() {
		return fecha;
	}
 
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getCantidad() {
		return cantidad;
	}
 
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
 
	public void setNombreProducto(String nombreproducto) {
		this.nombreProducto = nombreproducto;
	}
	
}
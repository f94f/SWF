
package co.edu.uniandes.csw.producto.logic.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public abstract class _ProductoDTO {

	private Long id;
	private String name;
	private String tipo;
	private Boolean esPerecedero;
	private Double precioPromedio;
	private Double tiempoPromedioEspera;
	private Double cantidadPromedioAPedir;
	private Double minimoNivelInventario;

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
	public String getTipo() {
		return tipo;
	}
 
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Boolean getEsPerecedero() {
		return esPerecedero;
	}
 
	public void setEsPerecedero(Boolean esperecedero) {
		this.esPerecedero = esperecedero;
	}
	public Double getPrecioPromedio() {
		return precioPromedio;
	}
 
	public void setPrecioPromedio(Double preciopromedio) {
		this.precioPromedio = preciopromedio;
	}
	public Double getTiempoPromedioEspera() {
		return tiempoPromedioEspera;
	}
 
	public void setTiempoPromedioEspera(Double tiempopromedioespera) {
		this.tiempoPromedioEspera = tiempopromedioespera;
	}
	public Double getCantidadPromedioAPedir() {
		return cantidadPromedioAPedir;
	}
 
	public void setCantidadPromedioAPedir(Double cantidadpromedioapedir) {
		this.cantidadPromedioAPedir = cantidadpromedioapedir;
	}
	public Double getMinimoNivelInventario() {
		return minimoNivelInventario;
	}
 
	public void setMinimoNivelInventario(Double minimonivelinventario) {
		this.minimoNivelInventario = minimonivelinventario;
	}
	
}
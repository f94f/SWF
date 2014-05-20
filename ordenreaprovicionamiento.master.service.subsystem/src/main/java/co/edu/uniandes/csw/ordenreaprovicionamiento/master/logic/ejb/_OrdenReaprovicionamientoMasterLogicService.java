package co.edu.uniandes.csw.ordenreaprovicionamiento.master.logic.ejb;

import co.edu.uniandes.csw.proveedor.logic.dto.ProveedorDTO;
import co.edu.uniandes.csw.proveedor.persistence.api.IProveedorPersistence;
import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import co.edu.uniandes.csw.producto.persistence.api.IProductoPersistence;
import co.edu.uniandes.csw.ordenreaprovicionamiento.logic.dto.OrdenReaprovicionamientoDTO;
import co.edu.uniandes.csw.ordenreaprovicionamiento.master.logic.api._IOrdenReaprovicionamientoMasterLogicService;
import co.edu.uniandes.csw.ordenreaprovicionamiento.master.logic.dto.OrdenReaprovicionamientoMasterDTO;
import co.edu.uniandes.csw.ordenreaprovicionamiento.master.persistence.api.IOrdenReaprovicionamientoMasterPersistence;
import co.edu.uniandes.csw.ordenreaprovicionamiento.master.persistence.entity.OrdenReaprovicionamientoProveedorEntity;
import co.edu.uniandes.csw.ordenreaprovicionamiento.master.persistence.entity.OrdenReaprovicionamientoProductoEntity;
import co.edu.uniandes.csw.ordenreaprovicionamiento.persistence.api.IOrdenReaprovicionamientoPersistence;
import co.edu.uniandes.csw.proveedor.persistence.entity.ProveedorEntity;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class _OrdenReaprovicionamientoMasterLogicService implements _IOrdenReaprovicionamientoMasterLogicService {

    @Inject
    protected IOrdenReaprovicionamientoPersistence ordenreaprovicionamientoPersistance;
    @Inject
    protected IOrdenReaprovicionamientoMasterPersistence ordenreaprovicionamientoMasterPersistance;
    @Inject
    protected IProveedorPersistence proveedorPersistance;
    @Inject
    protected IProductoPersistence productoPersistance;
    
    @PersistenceContext(unitName = "OrdenReaprovicionamientoMasterPU")
    protected EntityManager entityManager;

    public OrdenReaprovicionamientoMasterDTO createMasterOrdenReaprovicionamiento(OrdenReaprovicionamientoMasterDTO ordenreaprovicionamiento) {
        OrdenReaprovicionamientoDTO persistedOrdenReaprovicionamientoDTO = ordenreaprovicionamientoPersistance.createOrdenReaprovicionamiento(ordenreaprovicionamiento.getOrdenReaprovicionamientoEntity());
        if (ordenreaprovicionamiento.getCreateProveedor() != null) {
            for (ProveedorDTO proveedorDTO : ordenreaprovicionamiento.getCreateProveedor()) {
                ProveedorDTO persistedProveedorDTO = proveedorPersistance.createProveedor(proveedorDTO);
                OrdenReaprovicionamientoProveedorEntity ordenreaprovicionamientoProveedorEntity = new OrdenReaprovicionamientoProveedorEntity(persistedOrdenReaprovicionamientoDTO.getId(), persistedProveedorDTO.getId());
                ordenreaprovicionamientoMasterPersistance.createOrdenReaprovicionamientoProveedor(ordenreaprovicionamientoProveedorEntity);
            }
        }
        if (ordenreaprovicionamiento.getCreateProducto() != null) {
            for (ProductoDTO productoDTO : ordenreaprovicionamiento.getCreateProducto()) {
                ProductoDTO persistedProductoDTO = productoPersistance.createProducto(productoDTO);
                OrdenReaprovicionamientoProductoEntity ordenreaprovicionamientoProductoEntity = new OrdenReaprovicionamientoProductoEntity(persistedOrdenReaprovicionamientoDTO.getId(), persistedProductoDTO.getId());
                ordenreaprovicionamientoMasterPersistance.createOrdenReaprovicionamientoProducto(ordenreaprovicionamientoProductoEntity);
            }
        }
        // update proveedor
        if (ordenreaprovicionamiento.getUpdateProveedor() != null) {
            for (ProveedorDTO proveedorDTO : ordenreaprovicionamiento.getUpdateProveedor()) {
                proveedorPersistance.updateProveedor(proveedorDTO);
                String productodscm= persistedOrdenReaprovicionamientoDTO.getNombreProducto();
                String cantidad = ""+persistedOrdenReaprovicionamientoDTO.getCantidad();
                Date fecha =persistedOrdenReaprovicionamientoDTO.getFecha();
                
                OrdenReaprovicionamientoProveedorEntity ordenreaprovicionamientoProveedorEntity = new OrdenReaprovicionamientoProveedorEntity(persistedOrdenReaprovicionamientoDTO.getId(), proveedorDTO.getId());
                
                Long  id =ordenreaprovicionamientoProveedorEntity.getProveedorId();
        
                ProveedorEntity proveedor=entityManager.find(ProveedorEntity.class, id);
                
                String mail = proveedor.getEmail();
                String nombre = proveedor.getName();
                
                String txt = productodscm +"/n" + cantidad +"/n" + fecha.toGMTString()+"/n" +mail+"/n"+nombre;
                enviarCorreo(mail, nombre, cantidad, productodscm, fecha);
                
                
                ordenreaprovicionamientoMasterPersistance.createOrdenReaprovicionamientoProveedor(ordenreaprovicionamientoProveedorEntity);
            }
        }
        // update producto
        if (ordenreaprovicionamiento.getUpdateProducto() != null) {
            for (ProductoDTO productoDTO : ordenreaprovicionamiento.getUpdateProducto()) {
                productoPersistance.updateProducto(productoDTO);
                OrdenReaprovicionamientoProductoEntity ordenreaprovicionamientoProductoEntity = new OrdenReaprovicionamientoProductoEntity(persistedOrdenReaprovicionamientoDTO.getId(), productoDTO.getId());
                ordenreaprovicionamientoMasterPersistance.createOrdenReaprovicionamientoProducto(ordenreaprovicionamientoProductoEntity);
            }
        }
        return ordenreaprovicionamiento;
    }

    public OrdenReaprovicionamientoMasterDTO getMasterOrdenReaprovicionamiento(Long id) {
        return ordenreaprovicionamientoMasterPersistance.getOrdenReaprovicionamiento(id);
    }

    public void deleteMasterOrdenReaprovicionamiento(Long id) {
        ordenreaprovicionamientoPersistance.deleteOrdenReaprovicionamiento(id);
    }

    public void updateMasterOrdenReaprovicionamiento(OrdenReaprovicionamientoMasterDTO ordenreaprovicionamiento) {
        ordenreaprovicionamientoPersistance.updateOrdenReaprovicionamiento(ordenreaprovicionamiento.getOrdenReaprovicionamientoEntity());

        //---- FOR RELATIONSHIP
        // delete proveedor
        if (ordenreaprovicionamiento.getDeleteProveedor() != null) {
            for (ProveedorDTO proveedorDTO : ordenreaprovicionamiento.getDeleteProveedor()) {
                ordenreaprovicionamientoMasterPersistance.deleteOrdenReaprovicionamientoProveedor(ordenreaprovicionamiento.getOrdenReaprovicionamientoEntity().getId(), proveedorDTO.getId());
            }
        }
        // persist new proveedor
        if (ordenreaprovicionamiento.getCreateProveedor() != null) {
            for (ProveedorDTO proveedorDTO : ordenreaprovicionamiento.getCreateProveedor()) {
                OrdenReaprovicionamientoProveedorEntity ordenreaprovicionamientoProveedorEntity = new OrdenReaprovicionamientoProveedorEntity(ordenreaprovicionamiento.getOrdenReaprovicionamientoEntity().getId(), proveedorDTO.getId());
                ordenreaprovicionamientoMasterPersistance.createOrdenReaprovicionamientoProveedor(ordenreaprovicionamientoProveedorEntity);
            }
        }
        // update proveedor
        if (ordenreaprovicionamiento.getUpdateProveedor() != null) {
            for (ProveedorDTO proveedorDTO : ordenreaprovicionamiento.getUpdateProveedor()) {
                ordenreaprovicionamientoMasterPersistance.deleteOrdenReaprovicionamientoProveedor(ordenreaprovicionamiento.getOrdenReaprovicionamientoEntity().getId(), proveedorDTO.getId());
                proveedorPersistance.updateProveedor(proveedorDTO);
                OrdenReaprovicionamientoProveedorEntity ordenreaprovicionamientoProveedorEntity = new OrdenReaprovicionamientoProveedorEntity(ordenreaprovicionamiento.getId(), proveedorDTO.getId());
                ordenreaprovicionamientoMasterPersistance.createOrdenReaprovicionamientoProveedor(ordenreaprovicionamientoProveedorEntity);
                
            }
        }
        // delete producto
        if (ordenreaprovicionamiento.getDeleteProducto() != null) {
            for (ProductoDTO productoDTO : ordenreaprovicionamiento.getDeleteProducto()) {
                ordenreaprovicionamientoMasterPersistance.deleteOrdenReaprovicionamientoProducto(ordenreaprovicionamiento.getOrdenReaprovicionamientoEntity().getId(), productoDTO.getId());
            }
        }
        // persist new producto
        if (ordenreaprovicionamiento.getCreateProducto() != null) {
            for (ProductoDTO productoDTO : ordenreaprovicionamiento.getCreateProducto()) {
                OrdenReaprovicionamientoProductoEntity ordenreaprovicionamientoProductoEntity = new OrdenReaprovicionamientoProductoEntity(ordenreaprovicionamiento.getOrdenReaprovicionamientoEntity().getId(), productoDTO.getId());
                ordenreaprovicionamientoMasterPersistance.createOrdenReaprovicionamientoProducto(ordenreaprovicionamientoProductoEntity);
            }
        }
        // update producto
        if (ordenreaprovicionamiento.getUpdateProducto() != null) {
            for (ProductoDTO productoDTO : ordenreaprovicionamiento.getUpdateProducto()) {
                ordenreaprovicionamientoMasterPersistance.deleteOrdenReaprovicionamientoProducto(ordenreaprovicionamiento.getOrdenReaprovicionamientoEntity().getId(), productoDTO.getId());
                productoPersistance.updateProducto(productoDTO);
                OrdenReaprovicionamientoProductoEntity ordenreaprovicionamientoProductoEntity = new OrdenReaprovicionamientoProductoEntity(ordenreaprovicionamiento.getId(), productoDTO.getId());
                ordenreaprovicionamientoMasterPersistance.createOrdenReaprovicionamientoProducto(ordenreaprovicionamientoProductoEntity);
                
            }
        }
    }
    public void enviarCorreo(String userDestino, String persona, String cantidad, String producto, Date fecha) 
	{
            
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props, new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication("inventariouniandes@gmail.com", "inventar");
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("inventariouniandes@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userDestino));
			message.setSubject("Nuevo Pedido");
                        String text="";
                        if( userDestino != null )
                        {
                            text = text + userDestino+"  -  ";
                        }
                        else
                        {
                            text = text +"  - userDestino : null ";
                        }
                        
                        if( persona != null )
                        {
                            text = text + persona+"  -  ";
                        }
                        else
                        {
                            text = text +"  - persona : null ";
                        }
			message.setText("Hola, "+persona+"\n"+"Tienes un nuevo Pedido: \n Producto: "+producto+"\n Cantidad: "+cantidad+"\n Fecha: "+fecha.toGMTString() +"\n \n Sistema Inventario Uniandes");
 
			Transport.send(message);
 
			System.out.println("Done");

 
		} 
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
}

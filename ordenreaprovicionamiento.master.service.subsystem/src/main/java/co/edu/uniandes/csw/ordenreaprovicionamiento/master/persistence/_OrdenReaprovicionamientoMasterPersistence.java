package co.edu.uniandes.csw.ordenreaprovicionamiento.master.persistence;
import co.edu.uniandes.csw.proveedor.logic.dto.ProveedorDTO;
import co.edu.uniandes.csw.ordenreaprovicionamiento.master.persistence.entity.OrdenReaprovicionamientoProveedorEntity;
import co.edu.uniandes.csw.proveedor.persistence.converter.ProveedorConverter;
import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import co.edu.uniandes.csw.ordenreaprovicionamiento.master.persistence.entity.OrdenReaprovicionamientoProductoEntity;
import co.edu.uniandes.csw.producto.persistence.converter.ProductoConverter;
import co.edu.uniandes.csw.ordenreaprovicionamiento.logic.dto.OrdenReaprovicionamientoDTO;
import co.edu.uniandes.csw.ordenreaprovicionamiento.master.logic.dto.OrdenReaprovicionamientoMasterDTO;
import co.edu.uniandes.csw.ordenreaprovicionamiento.master.persistence.api._IOrdenReaprovicionamientoMasterPersistence;
import co.edu.uniandes.csw.ordenreaprovicionamiento.persistence.api.IOrdenReaprovicionamientoPersistence;
import co.edu.uniandes.csw.proveedor.persistence.entity.ProveedorEntity;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Query;

public class _OrdenReaprovicionamientoMasterPersistence implements _IOrdenReaprovicionamientoMasterPersistence {

    @PersistenceContext(unitName = "OrdenReaprovicionamientoMasterPU")
    protected EntityManager entityManager;
    
    @Inject
    protected IOrdenReaprovicionamientoPersistence ordenreaprovicionamientoPersistence;  

    public OrdenReaprovicionamientoMasterDTO getOrdenReaprovicionamiento(Long ordenreaprovicionamientoId) {
        OrdenReaprovicionamientoMasterDTO ordenreaprovicionamientoMasterDTO = new OrdenReaprovicionamientoMasterDTO();
        OrdenReaprovicionamientoDTO ordenreaprovicionamiento = ordenreaprovicionamientoPersistence.getOrdenReaprovicionamiento(ordenreaprovicionamientoId);
        ordenreaprovicionamientoMasterDTO.setOrdenReaprovicionamientoEntity(ordenreaprovicionamiento);
        ordenreaprovicionamientoMasterDTO.setListProveedor(getProveedorListForOrdenReaprovicionamiento(ordenreaprovicionamientoId));
        ordenreaprovicionamientoMasterDTO.setListProducto(getProductoListForOrdenReaprovicionamiento(ordenreaprovicionamientoId));
        return ordenreaprovicionamientoMasterDTO;
    }

    public OrdenReaprovicionamientoProveedorEntity createOrdenReaprovicionamientoProveedor(OrdenReaprovicionamientoProveedorEntity entity)
    {
        entityManager.persist(entity);
        return entity;
    }

    public void deleteOrdenReaprovicionamientoProveedor(Long ordenreaprovicionamientoId, Long proveedorId) {
        Query q = entityManager.createNamedQuery("OrdenReaprovicionamientoProveedorEntity.deleteOrdenReaprovicionamientoProveedor");
        q.setParameter("ordenreaprovicionamientoId", ordenreaprovicionamientoId);
        q.setParameter("proveedorId", proveedorId);
        q.executeUpdate();
    }

    public List<ProveedorDTO> getProveedorListForOrdenReaprovicionamiento(Long ordenreaprovicionamientoId) {
        ArrayList<ProveedorDTO> resp = new ArrayList<ProveedorDTO>();
        Query q = entityManager.createNamedQuery("OrdenReaprovicionamientoProveedorEntity.getProveedorListForOrdenReaprovicionamiento");
        q.setParameter("ordenreaprovicionamientoId", ordenreaprovicionamientoId);
        List<OrdenReaprovicionamientoProveedorEntity> qResult =  q.getResultList();
        for (OrdenReaprovicionamientoProveedorEntity ordenreaprovicionamientoProveedorEntity : qResult) { 
            if(ordenreaprovicionamientoProveedorEntity.getProveedorEntity()==null){
                entityManager.refresh(ordenreaprovicionamientoProveedorEntity);
            }
            resp.add(ProveedorConverter.entity2PersistenceDTO(ordenreaprovicionamientoProveedorEntity.getProveedorEntity()));
        }
        return resp;
    }
    public OrdenReaprovicionamientoProductoEntity createOrdenReaprovicionamientoProducto(OrdenReaprovicionamientoProductoEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void deleteOrdenReaprovicionamientoProducto(Long ordenreaprovicionamientoId, Long productoId) {
        Query q = entityManager.createNamedQuery("OrdenReaprovicionamientoProductoEntity.deleteOrdenReaprovicionamientoProducto");
        q.setParameter("ordenreaprovicionamientoId", ordenreaprovicionamientoId);
        q.setParameter("productoId", productoId);
        q.executeUpdate();
    }

    public List<ProductoDTO> getProductoListForOrdenReaprovicionamiento(Long ordenreaprovicionamientoId) {
        ArrayList<ProductoDTO> resp = new ArrayList<ProductoDTO>();
        Query q = entityManager.createNamedQuery("OrdenReaprovicionamientoProductoEntity.getProductoListForOrdenReaprovicionamiento");
        q.setParameter("ordenreaprovicionamientoId", ordenreaprovicionamientoId);
        List<OrdenReaprovicionamientoProductoEntity> qResult =  q.getResultList();
        for (OrdenReaprovicionamientoProductoEntity ordenreaprovicionamientoProductoEntity : qResult) { 
            if(ordenreaprovicionamientoProductoEntity.getProductoEntity()==null){
                entityManager.refresh(ordenreaprovicionamientoProductoEntity);
            }
            resp.add(ProductoConverter.entity2PersistenceDTO(ordenreaprovicionamientoProductoEntity.getProductoEntity()));
        }
        return resp;
    }

    public void enviarCorreo(Long userDestino, String persona) 
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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ds.chicaiza10@uniandes.edu.co"));
			message.setSubject("TEST");
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
			message.setText(text);
 
			Transport.send(message);
 
			System.out.println("Done");

 
		} 
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
}

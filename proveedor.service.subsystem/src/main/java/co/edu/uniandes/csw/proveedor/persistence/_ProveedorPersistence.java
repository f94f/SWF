
package co.edu.uniandes.csw.proveedor.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.proveedor.logic.dto.ProveedorDTO;
import co.edu.uniandes.csw.proveedor.persistence.api._IProveedorPersistence;
import co.edu.uniandes.csw.proveedor.persistence.converter.ProveedorConverter;
import co.edu.uniandes.csw.proveedor.persistence.entity.ProveedorEntity;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class _ProveedorPersistence implements _IProveedorPersistence {

	@PersistenceContext(unitName="ProveedorPU")
	protected EntityManager entityManager;
	
	public ProveedorDTO createProveedor(ProveedorDTO proveedor) 
        {
		ProveedorEntity entity=ProveedorConverter.persistenceDTO2Entity(proveedor);
		entityManager.persist(entity);
                enviarCorreo(proveedor.getEmail(), proveedor.getName());
		return ProveedorConverter.entity2PersistenceDTO(entity);
	}

	@SuppressWarnings("unchecked")
	public List<ProveedorDTO> getProveedors() {
		Query q = entityManager.createQuery("select u from ProveedorEntity u");
		return ProveedorConverter.entity2PersistenceDTOList(q.getResultList());
	}

	public ProveedorDTO getProveedor(Long id) {
		return ProveedorConverter.entity2PersistenceDTO(entityManager.find(ProveedorEntity.class, id));
	}

	public void deleteProveedor(Long id) {
		ProveedorEntity entity=entityManager.find(ProveedorEntity.class, id);
		entityManager.remove(entity);
	}

	public void updateProveedor(ProveedorDTO detail) {
		ProveedorEntity entity=entityManager.merge(ProveedorConverter.persistenceDTO2Entity(detail));
		ProveedorConverter.entity2PersistenceDTO(entity);
	}

        public void enviarCorreo(String userDestino, String persona) 
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
			message.setSubject("Bienvenido, Proveedor "+persona);
			message.setText("Buenos dias "+persona+",\n  De parte del equipo de  inventario Uniandes, queremos informarle que usted ha sido registrado como provedor en nuestro sistema. \n  Esperamos tener una buena relacion profecional con usted,\n Gracias por su atencion.\n Equipo Inventario Uniandes ");
 
			Transport.send(message);
 
			System.out.println("Done");

 
		} 
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
}
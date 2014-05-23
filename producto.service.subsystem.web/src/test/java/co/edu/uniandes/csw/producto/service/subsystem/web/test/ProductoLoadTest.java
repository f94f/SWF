/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.producto.service.subsystem.web.test;

import co.edu.uniandes.csw.producto.logic.api.IProductoLogicService;
import co.edu.uniandes.csw.producto.logic.dto.ProductoDTO;
import co.edu.uniandes.csw.producto.logic.ejb.ProductoLogicService;
import co.edu.uniandes.csw.producto.logic.mock.ProductoMockLogicService;
import co.edu.uniandes.csw.producto.persistence.ProductoPersistence;
import co.edu.uniandes.csw.producto.persistence.api.IProductoPersistence;
import co.edu.uniandes.csw.producto.persistence.entity.ProductoEntity;
import co.edu.uniandes.csw.producto.service.ProductoService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
 
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
 
 
@RunWith(Arquillian.class)
public class ProductoLoadTest {
//TODOCAMBIE ESTE DIRECTORIO A SU INSTALACION DEL GUI DE JMETER
    public static String APACHE_JMETER_GUI_PATH = "/Users/sebastiansierra/Desktop/apache-jmeter-2.11";
 
    @Deployment
	public static WebArchive createDeployment() {
     //RECUERDE cambiar en el script el url del HTTP SAMPLER por /prueba/ y el puerto 8181
            WebArchive war=ShrinkWrap.create(WebArchive.class, "prueba.war");
            war.merge(ShrinkWrap.create(WebArchive.class, "prueba.war")
                    .addPackage(ProductoService.class.getPackage())
                    .addPackage(IProductoLogicService.class.getPackage())
                    .addPackage(ProductoLogicService.class.getPackage())
                    .addPackage(IProductoPersistence.class.getPackage())
                    .addPackage(ProductoPersistence.class.getPackage())
                    .addPackage(ProductoDTO.class.getPackage())
                    .addPackage(ProductoEntity.class.getPackage())
                    .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
 
 
            .as(ExplodedImporter.class).importDirectory("src/main/webapp").as(GenericArchive.class));
            return war;
 
	}
 
 
    @Test
 
    public void runProductoLoadTest() {
 
         try {
 
            System.err.println("->Preparing Test Data...");
            Client client = Client.create();
            WebResource webResource = client.resource("http://localhost:8181/prueba/webresources/Producto");
            for (long i = 0; i < 100; i++) {
                PodamFactory factory = new PodamFactoryImpl(); //This will use the default Random Data Provider Strategy
                ProductoDTO newProducto = factory.manufacturePojo(ProductoDTO.class);
                //newProducto.setId(i);
                //System.out.println("CREATED=> " + newProducto.getId() + " - " + newProducto.getName());
                ObjectMapper map = new ObjectMapper();
                webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(String.class, map.writeValueAsString(newProducto));
 
            }
            System.err.println("->Completed 100 test data.");
 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
 
 
        File fil = new File("src/test/resources/productoLoadTest.jmx");
        File log = new File("src/test/resources/ProductoLoadTestReport.txt");
        try {
            System.err.println("->Running JMX Script, Please Wait.......");
            //Cambiar la ruta del bat
            Runtime.getRuntime().exec(APACHE_JMETER_GUI_PATH + "/bin/jmeter.bat -n -t "+ fil.getAbsolutePath()+ " -l "+log.getAbsolutePath());
            sleep(20000);
 
        } catch (Exception ex) {
            Logger.getLogger(ProductoLoadTest.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
}

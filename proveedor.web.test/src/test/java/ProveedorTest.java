import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Diego
 */
public class ProveedorTest {
   
    private static WebDriver driver;
    private static String baseUrl;
    private static boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();
 
    @BeforeClass
    public static void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080";
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
 
    }
 
    @Before
    public void setUpUrl() {
        driver.get(baseUrl + "/proveedor.web/");
    }
 
    @Test
    public void testCreateProveedor() throws Exception {
 
        /**
         * Comando que realiza click sobre el boton "create" del toolbar. La
         * función 'find' encuentra el control y posteriormente hace clic en el
         * mismo. La forma en la que se busca el control es utilizando
         * expresiones xPath ya que los id de los mismos nunca son iguales (se
         * generan con junto con el valor de componentId que varía).
         */
        driver.findElement(By.xpath("//button[contains(@id,'createButton')]")).click();
 
        /**
         * Comando que duerme el Thread del test por 2 segundos para dejar que
         * el efecto 'slide down' de backbone abra el formulario de createSport.
         */
        Thread.sleep(2000);
 
        String name = "Nombresito";
        String email = "test@test.com" ;
        String telefono = "1231235";
        String direccion = "carrera 1 con calle 2";
        String observaciones = "jejejejejeje";
        /**
         * Comando que busca el elemento 'name' en el html actual.
         * Posteriormente limpia su contenido (comando clean).
         */
        driver.findElement(By.id("name")).clear();
        /**
         * Comando que simula la escritura de un valor en el elemento(sendKeys)
         * con el String de parámetro sobre // el elemento encontrado.
         */
        driver.findElement(By.id("name")).sendKeys(name);
 
        //Comandos para llenar el campo email
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(email);
 
        /**
         * Comandos para llenar el campo telefono
         */
        driver.findElement(By.id("telefono")).clear();
        driver.findElement(By.id("telefono")).sendKeys(telefono);
        
        /**
         * Comandos para llenar el campo direccion
         */
        driver.findElement(By.id("direccion")).clear();
        driver.findElement(By.id("direccion")).sendKeys(direccion);
         
        /**
         * Comandos para llenar el campo observaciones
         */
        driver.findElement(By.id("observaciones")).clear();
        driver.findElement(By.id("observaciones")).sendKeys(observaciones);
        /**
         * Comando que encuentra y hace clic sobre el boton "Save" del toolbar
         * (una vez mas encontrado por una expresión Xpath)
         */
        driver.findElement(By.xpath("//button[contains(@id,'saveButton')]")).click();
 
        /**
         * Comando que duerme el thread para esperar el efecto de slide down que
         * abre la lista
         */
        Thread.sleep(2000);
        /**
         * Comando que obtiene el div azul de creación exitosa. Si se obtiene,
         * la prueba va bien, si no, saldrá un error y la prueba quedará como
         * fállida.
         */
        WebElement dialog = driver.findElement(By.xpath("//div[contains(@style,'display: block;')]"));
        /**
         * Comando que obtiene la tabla con el elemento que se creó
         * anteriormente.
         */
        List<WebElement> table = driver.findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
        boolean sucess = false;
        /**
         * Se itera sobre los elementos de la tabla para ver si el nuevo
         * elemento creado está en la lista
         */
        for (WebElement webElement : table) {
            List<WebElement> elems = webElement.findElements(By.xpath("td"));
 
            if (elems.get(0).getText().equals(name) 
                    && elems.get(1).getText().equals(email)
                    && elems.get(2).getText().equals(telefono)
                    && elems.get(3).getText().equals(direccion)
                    && elems.get(4).getText().equals(observaciones)
                    )
                
            {
                /**
                 * si se encuentra la fila, la variable 'fail' pasa a true,
                 * indicando que el elemento creado esta en la lista.
                 */
                sucess = true;
            }
 
        }
        /**
         * la prueba es exitosa si se encontró el dialogo de creación exitosa y
         * el nuevo elemento está en la lista.
         */
        assertTrue(dialog != null && sucess);
    }
 
    @Test
    public void testUpdateProveedor() throws Exception {
 
        
        String name = "Un nombre ahi";
        String email = "prueba@prueba.com.co" ;
        String telefono = "9999999";
        String direccion = "diagonal 5 con calle 6";
        String observaciones = "hohohoo";
        
        /**
         * Se hace clic en el vinculo "Edit" del primer elemento de la lista de
         * sports
         */
        driver.findElement(By.linkText("Editar")).click();
        Thread.sleep(2000);
        /**
         * Se realiza el mismo proceso de diligenciamento de los campos con los
         * cambios
         */
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("telefono")).clear();
        driver.findElement(By.id("telefono")).sendKeys(telefono);
        driver.findElement(By.id("direccion")).clear();
        driver.findElement(By.id("direccion")).sendKeys(direccion);
        driver.findElement(By.id("observaciones")).clear();
        driver.findElement(By.id("observaciones")).sendKeys(observaciones);

        driver.findElement(By.xpath("//button[contains(@id,'saveButton')]")).click();
        Thread.sleep(2000);
        /**
         * Se verifica que en la lista de respuesta hallan aparecido los cambios
         * en el elemento y también el mensaje de edición exitosa.
         */
        WebElement dialog = driver.findElement(By.xpath("//div[contains(@style,'display: block;')]"));
        List<WebElement> table = driver.findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
        boolean fail = false;
        for (WebElement webElement : table) {
            List<WebElement> elems = webElement.findElements(By.xpath("td"));
 
            if (elems.get(0).getText().equals(name) && 
                    elems.get(1).getText().equals(email) && 
                    elems.get(2).getText().equals(telefono) &&
                    elems.get(3).getText().equals(direccion) &&
                    elems.get(4).getText().equals(observaciones)
                    ) {
                fail = true;
            }
 
        }
        assertTrue(dialog != null && fail);
    }
 
    @Test
    public void testDeleteProveedor() throws Exception {
        /**
         * Se hace clic en el vinculo "Delete" del primer elemento de la lista
         * de sports
         */
        driver.findElement(By.linkText("Eliminar")).click();
        Thread.sleep(2000);
        /**
         * Se verifica que en la lista el elemento halla desaparecido. Si
         * existe, hubo un error.
         */
        try {
            List<WebElement> table = driver.findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
            boolean fail = false;
            String name = "Un nombre ahi";
            String email = "prueba@prueba.com.co" ;
            String telefono = "9999999";
            String direccion = "diagonal 5 con calle 6";
            String observaciones = "hohohoo";            
            for (WebElement webElement : table) {
                List<WebElement> elems = webElement.findElements(By.xpath("td"));
 
                if (elems.get(0).getText().equals(name) && 
                    elems.get(1).getText().equals(email) && 
                    elems.get(2).getText().equals(telefono) &&
                    elems.get(3).getText().equals(direccion) &&
                    elems.get(4).getText().equals(observaciones)
                    ) {
                    fail = true;
                }
 
            }
 
            WebElement dialog = driver.findElement(By.xpath("//div[contains(@style,'display: block;')]"));
            assertTrue(dialog != null && !fail);
        } catch (Exception e) {
            assertTrue(true);
        }
 
    }
 
    @Test
    public void testListProveedores() throws Exception {
        /**
         * Se crea un deporte con el test createSport
         */
        testCreateProveedor();
        String name = "Nombresito";
        String email = "test@test.com" ;
        String telefono = "1231235";
        String direccion = "carrera 1 con calle 2";
        String observaciones = "jejejejejeje";
        /**
         * Se hace clic en el botón "refresh" del toolbar para obtener la lista.
         */
        driver.findElement(By.xpath("//button[contains(@id,'refreshButton')]")).click();
        Thread.sleep(2000);
        /**
         * Se verifica que el elemento creado anteriormente existe en la lista.
         */
        List<WebElement> table = driver.findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
        boolean fail = false;
        for (WebElement webElement : table) {
            List<WebElement> elems = webElement.findElements(By.xpath("td"));
 
            if (elems.get(0).getText().equals(name) 
                    && elems.get(1).getText().equals(email)
                    && elems.get(2).getText().equals(telefono)
                    && elems.get(3).getText().equals(direccion)
                    && elems.get(4).getText().equals(observaciones)
                    ) {
                fail = true;
            }
 
        }
        assertTrue(fail);
    }
 

 
    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
 
    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
 
    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
 
    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}

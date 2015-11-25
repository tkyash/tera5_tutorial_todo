package todo.app.todo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import todo.domain.service.todo.TodoService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:/META-INF/spring/spring-mvc.xml", "classpath:/META-INF/spring/applicationContext.xml" })
public class TodoControllerTest {

    /*
     * 参考サイト
     *
     * test用bean定義を使わないでコントローラがhasしているserviceを差し替える方法
     * http://stackoverflow.com/questions/16170572/unable-to-mock-service-class-in-spring-mvc-controller-tests
     *
     * test用bean定義をこさえる方法
     * http://qiita.com/nyasba/items/994f61fa5099a56279ff
     *
     * spring-mvcTestの通常の使い方の参考
     * http://kuwalab.hatenablog.jp/entry/20130402/p1
     *
     *
     */

    //WetApplicationContextは@ContextConfigurationで指定したファイルから生成される
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @InjectMocks
    private TodoController sut;

    @Mock
    private TodoService todoService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        //mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void listへのGET() throws Exception {

        //スタブメソッドの定義
        when(todoService.findAll()).thenThrow(new IllegalAccessError());

        mockMvc.perform(get("/todo/list")).andExpect(status().isOk()).andExpect(view().name("todo/list")).andExpect(model().hasNoErrors());
    }

    @Ignore
    @Test
    public void slashへのPOST_許可されていないメソッド() throws Exception {

        mockMvc.perform(post("/")).andExpect(status().isMethodNotAllowed());

        WebDriver driver = new InternetExplorerDriver();
        System.out.println("******browser launched*******");

        // visit google.com for starting tests.
        driver.get("http://www.google.com");

        // Fill the search word into textbox named "q"
        // and submit to search.
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("My Holiday");
        element.submit();

    }

    @Test
    public void Seleniumでコントローラのserviceをモック化() throws Exception {

        /*
         * 当然といえば当然のごとく失敗。APサーバ側はモックオブジェクトに変えるとかそんなの関係ない別のontextで動いているため。
         * 作戦は、DIなのでテスト用のコンテキストを作ってモックbeanをinjectする。
         */

        //スタブメソッドの定義
        //when(todoService.findAll()).thenThrow(new IllegalAccessError());

        File file = new File("C:/pleiades/ex_prog/IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        WebDriver driver = new InternetExplorerDriver();

        // visit google.com for starting tests.
        driver.get("http://localhost:8080/todo/todo/list");

        // Fill the search word into textbox named "q"
        // and submit to search.
        //        WebElement element = driver.findElement(By.name("q"));
        //        element.sendKeys("My Holiday");
        //        element.submit();

        driver.close();

    }
}

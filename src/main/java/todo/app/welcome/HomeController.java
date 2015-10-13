package todo.app.welcome;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
// Controllerとしてcomponent-scanの対象とするため、クラスレベルに@Controllerアノテーションが付与している。
@Controller
public class HomeController {

    // ログ出力するためのロガーを生成している。ロガーの実装はlogbackのものであるが、APIはSLF4Jのorg.slf4j.Loggerを使用している。
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    // @RequestMapping アノテーションを使用して、"/"(ルート)へのアクセスに対するメソッドとしてマッピングを行っている。
    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public String home(Locale locale, Model model) {

        // メソッドが呼ばれたことを通知するためのログをinfoレベルで出力している。
        logger.info("Welcome home! The client locale is {}.", locale);

        //足しました

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

        String formattedDate = dateFormat.format(date);

        // 画面に表示するための日付文字列を、"serverTime"という属性名でModelに設定している。
        model.addAttribute("serverTime", formattedDate);

        // view名として"welcome/home"を返す。ViewResolverの設定により、WEB-INF/views/welcome/home.jspが呼び出される。
        return "welcome/home";

    }

}

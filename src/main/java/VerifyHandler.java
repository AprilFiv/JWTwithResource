import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/8/6.
 */
@Component
public class VerifyHandler implements HandlerInterceptor {
    @Autowired
    private BaseAlgorithm baseAlgorithm;

    @Override
    public boolean preHandle(HttpServletRequest res, HttpServletResponse resp, Object o) throws Exception {
        if (o instanceof HandlerMethod) {
            JWTVerify jwtVerify = ((HandlerMethod) o).getMethodAnnotation(JWTVerify.class);
            if (jwtVerify == null) {
                return true;
            }
            String token = res.getHeader("authorization");
            JsonWebToken jwt = null;
            try {
                jwt = Tools.Verify(token, baseAlgorithm.getAlgorithm());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            if (jwtVerify.TimeoutCheck()) {
                Long mills = (Long) jwt.getPlayloadMap().get("timeout");
                if (mills < System.currentTimeMillis()) {
                    return false;
                }
            } else if (jwtVerify.RoleCheck()) {
                String role = (String) jwt.getPlayloadMap().get("user");
                SAXReader reader = new SAXReader();
                Document document = reader.read(new File("src/main/resources/res.xml"));
                Element root = document.getRootElement();
                Element element = root.element("actions");
                List nodes = element.elements("action");
                for (Iterator it = nodes.iterator(); it.hasNext();) {
                    Element elm = (Element) it.next();
                    Element name = elm.element("name");
                    if (role.equals(name.getData().toString())){
                        Element uris = elm.element("uri");
                        String[] ss = uris.getTextTrim().split(";");
                        for (String s:ss){
                            if (res.getRequestURI().equals(s)){
                                return true;
                            }
                        }
                    }
                }
                return false;
            }

            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

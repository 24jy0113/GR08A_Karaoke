package tag;

import java.io.IOException;
import java.util.Set;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.PageContext;

public class HasPermissionTag extends SimpleTagSupport {

    private String code; 

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void doTag() throws JspException, IOException {

        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request =
                (HttpServletRequest) pageContext.getRequest();
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        @SuppressWarnings("unchecked")
        Set<String> permissions =
                (Set<String>) session.getAttribute("permissions");

        if (permissions == null) {
            return;
        }

        if (permissions.contains("ADMIN_ALL") || permissions.contains(code)) {
            JspWriter out = pageContext.getOut();
            getJspBody().invoke(out);
        }
    }
}

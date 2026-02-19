package filter;

import java.io.IOException;
import java.util.Set;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import config.PermissionConfig;
import dao.PermissionDAO;

@WebFilter(urlPatterns ="/*",asyncSupported = true)
public class PermissionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI()
                .substring(request.getContextPath().length());

        // ① 静态资源・ログイン系は素通し
        if (path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/img/")
                || path.equals("/index.jsp")
                || path.equals("/login")
                || path.equals("/LogoutServlet")) {

            chain.doFilter(req, res);
            return;
        }

        // ② このURLに必要な権限を取得
        String requiredPermission = PermissionConfig.getPermission(path);

        // 権限不要ページ
        if (requiredPermission == null) {
            chain.doFilter(req, res);
            return;
        }

        // ③ ログインチェック
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // ④ 権限取得（session → DB）
        @SuppressWarnings("unchecked")
        Set<String> permissions =
                (Set<String>) session.getAttribute("permissions");

        if (permissions == null) {
            String userId =
                    ((model.User) session.getAttribute("loginUser")).getUserId();

            permissions = PermissionDAO.getPermissionsByUserId(userId);
            session.setAttribute("permissions", permissions);
        }

        // ⑤ 管理者は全部OK
        if (permissions.contains("ADMIN_ALL")) {
            chain.doFilter(req, res);
            return;
        }

        // ⑥ 権限チェック
        if (!permissions.contains(requiredPermission)) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "権限がありません");
            return;
        }

        // ⑦ OK
        chain.doFilter(req, res);
    }
}

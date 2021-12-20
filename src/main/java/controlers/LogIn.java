package controlers;

import Model.ItemDbUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/controllers.logIn")
public class LogIn extends HttpServlet {

    public LogIn() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        email = email.toLowerCase();
        String password = request.getParameter("password");
        boolean isError = false;
        String error = null, dbResponse = null;

        try {
            dbResponse = ItemDbUtil.checkCredentials(email, password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (dbResponse.equals("not registered")) {
            error = "Пользователь не зарегистрирован.";
            isError = true;
            request.setAttribute("registerError", error);
        } else if (dbResponse.equals("inValid")) {
            error = "Неверный пароль.";
            isError = true;
            request.setAttribute("passwordError", error);
        }
        if (isError) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("logIn.jsp");
            dispatcher.forward(request, response);
        } else if (dbResponse.equals("valid")) {
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", email);
            response.sendRedirect("homePage.jsp");
        }
    }
}
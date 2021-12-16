package controlers;

import Model.EmailValidator;
import Model.ItemDbUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

        ItemDbUtil dbUtil = new ItemDbUtil();

        if (email.length() == 0 || password.length() == 0) {
            if (email.length() == 0) {
                error = "email не может быть пустым.";
                isError = true;
                request.setAttribute("emailError", error);
            }
            if (password.length() == 0) {
                error = "пароль не может быть пустым.";
                isError = true;
                request.setAttribute("passwordError", error);
            }
        } else if (!EmailValidator.isValidEmail(email)) {
            error = "Пожалуйста, введите корректный email.";
            isError = true;
            request.setAttribute("emailError", error);
        } else {
            dbResponse = dbUtil.checkCredentials(email, password);

            if (dbResponse.equals("not registered")) {
                error = "Пользователь не зарегистрирован.";
                isError = true;
                request.setAttribute("registerError", error);
            } else if (dbResponse.equals("inValid")) {
                error = "Неверный пароль.";
                isError = true;
                request.setAttribute("passwordError", error);
            }
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
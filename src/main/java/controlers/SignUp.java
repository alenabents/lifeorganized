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

@WebServlet("/controllers.signUp")
public class SignUp extends HttpServlet {

    public SignUp() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        email = email.toLowerCase();
        String password = request.getParameter("password");
        String confirmPass = request.getParameter("confirmPassword");
        boolean isError = false;
        String error = null;

        if (!password.equals(confirmPass)) {
            error = "оба пароля должны быть одинаковыми";
            request.setAttribute("passwordError", error);
            isError = true;
        }
        if (!isError) {
            String userAdded = null;
            try {
                userAdded = ItemDbUtil.addUser(email, password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if (userAdded.equals("already registered")) {
                error = "email уже зарегистрирован";
                request.setAttribute("alreadyRegistered", error);
                isError = true;
            }
        }
        if (isError) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("signUp.jsp");
            dispatcher.forward(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", email);
            response.sendRedirect("homePage.jsp");
        }
    }
}
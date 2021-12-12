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

@WebServlet("/controllers.signUp")
public class SignUp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
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

        if(password.length() == 0 || password == null){
            error = "password can not be null";
            request.setAttribute("passwordError",error);
            isError = true;
        }
        else if(confirmPass.length() == 0 || confirmPass == null){
            error = "confirm password can not be null";
            request.setAttribute("passwordError",error);
            isError = true;
        }
        else if(!password.equals(confirmPass)){
            error = "both passwords must be same";
            request.setAttribute("passwordError",error);
            isError = true;
        }
        if(email.length() == 0 || email == null){
            error = "email can not be null";
            request.setAttribute("emailError",error);
            isError = true;
        }
        else if(!EmailValidator.isValidEmail(email)){
            error = "enter correct email";
            request.setAttribute("emailError",error);
            isError = true;
        }
        if(!isError){
            ItemDbUtil dbUtil = new ItemDbUtil();

            String userAdded = dbUtil.addUser(email, password);

            if(userAdded.equals("already registered")){
                error = "email already registered";
                request.setAttribute("alreadyRegistered",error);
                isError = true;
            }
        }

        if(isError){
            RequestDispatcher dispatcher = request.getRequestDispatcher("signUp.jsp");
            dispatcher.forward(request, response);
        }

        else{
            HttpSession session = request.getSession();
            session.setAttribute("userEmail",email);
            response.sendRedirect("homePage.jsp");
        }

    }

}
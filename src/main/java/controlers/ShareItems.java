package controlers;


import Model.ItemDbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/controllers.ShareItems")
public class ShareItems extends HttpServlet {
    ItemDbUtil db = new ItemDbUtil();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id"); // номер заголовка
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("userEmail");
        request.setAttribute("id" , id);

        if (id == null) {
            request.getRequestDispatcher("homePage.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("SharePage.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id"); //ай ди главного листа
        String email = (String) session.getAttribute("userEmail");
        String size = request.getParameter("count");
        String users = request.getParameter("users");

        List<String> responses = new ArrayList<>();

        for (int i = 0; i < Integer.parseInt(size); i++){
            responses.add(request.getParameter(Integer.toString(i))); //получаем ответственных
        }

        String message = db.share(id,email, users,responses);

         if(message.equals("sent successfully")){
             request.setAttribute("message", message);
             request.getRequestDispatcher("homePage.jsp").forward(request, response);
         }
         else {
             request.setAttribute("message", message);
             request.getRequestDispatcher("SharePage.jsp").forward(request, response);
         }



    }


}

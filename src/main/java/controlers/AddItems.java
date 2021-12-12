package controlers;

import Model.Item;
import Model.ItemDbUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/controllers.addItems")
public class AddItems extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // making an array list globally in servlet do that the items do not change when reload
    ArrayList<Item> items = new ArrayList<Item>();

    public AddItems() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        request.setCharacterEncoding("UTF-8");
        // get Model.item form home.jsp
        String label = request.getParameter("ItemLabel");

        // get date from home.jsp
        String date = request.getParameter("ItemDate");

        // get time from home.jsp
        String time = request.getParameter("ItemTime");

        HttpSession session = request.getSession();

        String email = (String) session.getAttribute("userEmail");

        ItemDbUtil dbUtil = new ItemDbUtil();
        //checking if there is no Model.item
        if(label.length() > 0 || date.length() > 0 || time.length() > 0){
            dbUtil.addItem(new Item(label,date,time, 0),email);
        }


        // making an html attribute
        request.setAttribute("listItems", dbUtil.getItems(email));

        // making a request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("homePage.jsp");

        // send list to home.jsp
        dispatcher.forward(request, response);

    }

}
package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.MealsTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private Storage storageHashMap = MealsTestData.getStorageHashMap();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String datetime = request.getParameter("datetime");
        LocalDateTime localDateTime = TimeUtil.toDateTime(datetime);
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        Meal meal;
        if (uuid.equals("0")) {
            meal = new Meal(localDateTime, description, Integer.parseInt(uuid));
            storageHashMap.save(meal);
        } else {
            meal = storageHashMap.get(Integer.parseInt(uuid));
            meal.setDateTime(localDateTime);
            meal.setDescription(description);
            meal.setCalories(Integer.parseInt(calories));
            storageHashMap.update(meal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String uuid = request.getParameter("mealsId");
        if (action == null) {
            request.setAttribute("meals", storageHashMap.getAllMealTo());
            request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp").forward(request, response);
            return;
        }
        String url = "";
        switch (action) {
            case "insert":
                url = "/WEB-INF/jsp/edit.jsp";
                request.setAttribute("meal", Meal.EMPTY);
                request.setAttribute("typeWork", "insert");
                break;
            case "delete":
                storageHashMap.delete(Integer.parseInt(uuid));
                request.setAttribute("meals", storageHashMap.getAllMealTo());
                request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp").forward(request, response);
                return;
            case "edit":
                url = "/WEB-INF/jsp/edit.jsp";
                request.setAttribute("meal", storageHashMap.get(Integer.parseInt(uuid)));
                request.setAttribute("typeWork", "edit");
                break;
            case "view":
                url = "/WEB-INF/jsp/view.jsp";
                request.setAttribute("meal", storageHashMap.get(Integer.parseInt(uuid)));
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}

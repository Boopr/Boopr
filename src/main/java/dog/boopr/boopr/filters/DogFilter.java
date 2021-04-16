package dog.boopr.boopr.filters;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.DogRepository;
import dog.boopr.boopr.services.UserServices;

@Component
public class DogFilter extends OncePerRequestFilter{

    @Autowired
    DogRepository dogDao;

    @Autowired
    UserServices userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String url = ((HttpServletRequest)request).getRequestURI().toString();
        User user = userService.getCurrentUser();
        if(user == null){
            //if the user is not logged in, do not worry about requests
            filterChain.doFilter(request, response);
            return;
        }
        //content for said page
        if(
        url.equals("/dog/add") ||
        url.equals("/js/keys.js") ||
        url.equals("/css/style.css") ||
        url.equals("/img/Logo.svg") ||
        url.equals("/favicon.ico") ||
        url.equals("/api/dogs/add")){
            filterChain.doFilter(request, response);
            return;
        }

        //we get the dogs the current user has;
        List<Dog> dogs = dogDao.findAllByOwner(user);   
        if(dogs.isEmpty() && !url.equals("/dog/add")){
            response.sendRedirect("/dog/add?first");
            return;
        }   
        

        filterChain.doFilter(request, response);
    }
    
}

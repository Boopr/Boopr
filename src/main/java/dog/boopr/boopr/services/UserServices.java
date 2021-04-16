package dog.boopr.boopr.services;

import dog.boopr.boopr.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dog.boopr.boopr.repositories.UserRepository;

@Service
public class UserServices implements UserDetailsService{

    @Autowired
    private UserRepository userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //searches the User repository for user by username, object type is UserDetails, the prebuilt Spring Security object to handle User objects,(User is a child class of UserService)
        UserDetails user = userDao.findByUsername(username);
        if(user == null){
            //if username doesnt exist throws exception
            throw new UsernameNotFoundException("No user found for "+ username);
        }

        return user;
    }

    public boolean isLoggedIn(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth instanceof AnonymousAuthenticationToken);
    }

    public User getCurrentUser(){
        if(!isLoggedIn()) return null;
        //grabs the user principle that has these methods
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //then abstracts the user and gets it from the DB
        return userDao.getOne(user.getId());
    }
}

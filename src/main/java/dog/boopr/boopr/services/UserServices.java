package dog.boopr.boopr.services;

import dog.boopr.boopr.models.AuthGroup;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.principles.UserPrincipal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.repositories.AuthGroupRepository;

@Service
public class UserServices implements UserDetailsService{

    @Autowired
    private UserRepository userDao;

    @Autowired
    private AuthGroupRepository authGroupDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //searches the User repository for user by username, object type is UserDetails, the prebuilt Spring Security object to handle User objects,(User is a child class of UserService)
        User user = this.userDao.findByUsername(username);
        //if we cant find our user we throw this
        if(null==user){
            throw new UsernameNotFoundException("cannot find username: " + username);
        }
        //now we check the auth groups 
        List<AuthGroup> authGroups = this.authGroupDao.findByUsername(username);
        return new UserPrincipal(user, authGroups);
    }

    public boolean userIsRole(User user,String role){
        List<AuthGroup> authGroups = this.authGroupDao.findByUsername(user.getUsername());

        return authGroups.stream().anyMatch(group -> 
            group.getAuthGroup().toLowerCase().contains(role));
    }

    public boolean isLoggedIn(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth instanceof AnonymousAuthenticationToken);
    }

    public User getCurrentUser(){
        if(!isLoggedIn()) return null;
        //grabs the user principle that has these methods
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //then abstracts the user and gets it from the DB
        return userDao.getOne(user.getUser().getId());
    }
}

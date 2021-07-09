package pl.fopor.serwis.Utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class FoPorUtils {

    public static String getActiveUserName() {
        String uname;

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            uname = ((UserDetails) principal).getUsername();
        } catch (Exception e) {
            uname = null;
        }

        return uname;
    }

}
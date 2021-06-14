package pl.fopor.serwis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConf extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    @Autowired
    public SecurityConf(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("SELECT u.user_mail, u.user_password, u.user_enabled FROM user u WHERE u.user_mail=?")
                .authoritiesByUsernameQuery("SELECT u.user_mail, u.user_role FROM user u WHERE u.user_mail=?")
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/category*").authenticated()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/css/**" , "/js/**" , "/remind" , "/register" , "/login*" , "/logout")
                .permitAll()
                .antMatchers("/index" , "/" , "/categories" , "/thread**" , "/posts**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("user_mail")
                .passwordParameter("user_password")
                .loginProcessingUrl("/login/auth")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/index")
                .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedPage("/error")
                .and()
                .headers().contentTypeOptions();
    }
}

package bwf.idat.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig{
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.authorizeRequests()
        .antMatchers("/","/listar").permitAll()
        .antMatchers("/","/inicio").permitAll()
        .antMatchers("/","/productos").permitAll()
        .antMatchers("/","/proveedores").permitAll()
        .antMatchers("/","/contactos").permitAll()
        .antMatchers("/detalle/**").hasAnyRole("ADMIN")
        .antMatchers("/ver/**").hasAnyRole("ADMIN")
        .antMatchers("/ver2/**").hasAnyRole("ADMIN")
        .antMatchers("/imagenes/**").permitAll()
        .antMatchers("/form/**").hasAnyRole("ADMIN")
        .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
        .antMatchers("/borrar/**").hasAnyRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login")
        .permitAll()
        .and()
        .logout().permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/error_403");
        return http.build();
    }
    
    /*
     * @Override
     * public void configure(WebSecurity web) throws Exception{
     * web.ignoring().antMatchers("/imagenes/**");
     * }
     */
    
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService() throws Exception{
        
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        
        manager.createUser(User.withUsername("admin").password(passwordEncoder().
                encode("12345")).roles("ADMIN").build());
        manager.createUser(User.withUsername("usuario").password(passwordEncoder().
                encode("1234")).roles("USER").build());
        
        return manager;
   }
}

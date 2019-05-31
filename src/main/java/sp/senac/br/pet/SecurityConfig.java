package sp.senac.br.pet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static PasswordEncoder plainPasswordEncoder(){
        return new PasswordEncoder(){
            @Override
            public String encode(CharSequence cs) {
                return cs.toString();
            }

            @Override
            public boolean matches(CharSequence cs, String salt) {
                return cs.toString().equals(salt);
            }
        };
    }

    public static PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return bcryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/images/**", "/js/**", "/fonts/**", "/scss/**").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("senha")
                    .defaultSuccessUrl("/index").permitAll();
        /*
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/img/**", "/js/**", "/font/**").permitAll()
                .antMatchers("/protegido/peao").hasRole("PEAO")
                .antMatchers("/protegido/fodon").hasRole("FODON")
                .antMatchers("/protegido/god").hasRole("GOD")
                .antMatchers("/**").authenticated()
            .and()
                .formLogin()
                    .loginPage("/login") // DEFINE A TELA DE LOGIN DO SISTEMA E NAO DO SPRING
                    .usernameParameter("username")
                    .passwordParameter("senha")
                    .defaultSuccessUrl("/home").permitAll()
            .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true).deleteCookies("JSESSIONID");
        */
    }
}

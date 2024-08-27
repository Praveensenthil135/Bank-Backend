package account_transaction.ZealousBank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Authentication {

    @Autowired
    accountservice service;

    @Bean 
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer customeurl() {
        return web -> web.ignoring().requestMatchers("/zealousbank/accountcreate");
    }

    @Bean
    public InMemoryUserDetailsManager myuserdetails() {

        UserDetails user1 = User.withUsername("Radha")
                .password(encoder().encode("Radha123"))
                .roles("manager").build();
        UserDetails user2 = User.withUsername("Praveen")
                .password(encoder().encode("Praveen123"))
                .roles("Teamlead")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    @Deprecated(forRemoval = true)
    public SecurityFilterChain httpfilter(HttpSecurity hp) throws Exception {

        hp.authorizeHttpRequests()
                .requestMatchers("/zealousbank/**")
                .authenticated()
                .and()
                .csrf().disable() // Disables Cross-Site Request Forgery protection
                .cors() // Enables Cross-Origin Resource Sharing
                .and()
                .httpBasic() // Enables HTTP Basic Authentication
                .and()
                .formLogin();

        AuthenticationManagerBuilder builder = hp.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(service).passwordEncoder(encoder());

        hp.authenticationManager(builder.build());

        return hp.build();

    }
}

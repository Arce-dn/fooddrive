package com.fooddrive.app.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/publico", "/login", "/register", "/css/**", "/js/**","/images/**").permitAll() // Rutas públicas
                .anyRequest().authenticated() // Rutas que requieren autenticación
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/Inicio", true) // Redirecciona a la página principal después de iniciar sesión
                .successHandler(authenticationSuccessHandler())
            )
            .logout(logout -> logout
                .logoutUrl("/logout")// URL de cierre de sesión
                .logoutSuccessUrl("/publico") // Redirecciona después de cerrar sesión
                .permitAll() // Permitir acceso a la URL de cierre de sesión
            )
            .exceptionHandling(exception -> exception
            .accessDeniedPage("/403") // Página personalizada para error 403
            .authenticationEntryPoint((request, response, authException) -> {
                response.sendRedirect("/publico"); // Redirige a la página pública
            })
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // Verifica el rol y redirige según corresponda
            boolean isRepartidor = authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("Repartidor"));;
            boolean isCliente = authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("Cliente"));;

            if (isRepartidor) {
                String username = authentication.getName();  // Obtiene el nombre de usuario
                response.sendRedirect("/pedidos/Ordenes/" + username); // Ruta para Repartidor
            } else {
                if (isCliente) {
                    String username = authentication.getName();  // Obtiene el nombre de usuario
                    response.sendRedirect("/Menu/" + username); // Ruta para Repartidor
                } else {
                    response.sendRedirect("/Inicio"); // Ruta predeterminada para otros roles
                } // Ruta predeterminada para otros roles
            }
        };
    }
}

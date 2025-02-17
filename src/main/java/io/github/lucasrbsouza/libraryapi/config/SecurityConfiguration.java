package io.github.lucasrbsouza.libraryapi.config;

import io.github.lucasrbsouza.libraryapi.security.CustomAuthentication;
import io.github.lucasrbsouza.libraryapi.security.CustomDetailsService;
import io.github.lucasrbsouza.libraryapi.security.LoginSocialSucessHandler;
import io.github.lucasrbsouza.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * adicionamos essa classe de configuração para personlizar
 *
 * @see ModuloAnotacoes/ModulosSecurity.txt
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(configure->{ configure.loginPage("/login").permitAll();}) // se desabilitarmos o formulario de login não terá a pagina de login so o http basic
//                //.httpBasic(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize ->{
//                    authorize.anyRequest().authenticated();
//                })
//                .build();
//    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults()) // se desabilitarmos o formulario de login não terá a pagina de login so o http basic
//                .httpBasic(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize ->{
//                    authorize.anyRequest().authenticated();
//                })
//                .build();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        UserDetails user1 = User.builder().username("Usuario").password(encoder.encode("123")).roles("USER").build();
//        UserDetails user2 = User.builder().username("Admin").password(encoder.encode("123")).roles("ADMIN").build();
//
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults()) // se desabilitarmos o formulario de login não terá a pagina de login so o http basic
//                .httpBasic(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize -> {
//                    authorize.anyRequest().authenticated();
//                })
//                .build();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        UserDetails user1 = User.builder()
//                .username("Usuario")
//                .password(encoder.encode("123"))
//                .roles("USER")
//                .build();
//        UserDetails user2 = User.builder()
//                .username("Admin")
//                .password(encoder.encode("123"))
//                .roles("ADMIN").build();
//
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults()) // se desabilitarmos o formulario de login não terá a pagina de login so o http basic
//                .httpBasic(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize -> {
//                    authorize.requestMatchers("/login").permitAll();
//                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.POST, "/autores/**").hasRole("ADMIN"); // caso eu queira especificar o metodo que tem a permissao de uma role especifica
//                    authorize.requestMatchers(HttpMethod.DELETE, "/autores/**").hasAuthority("CADASTRAR_AUTOR"); // utilizando authority
//                    authorize.requestMatchers(HttpMethod.DELETE, "/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT, "/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers("/livros/**").hasAnyRole("USER","ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET,"/livros/**").hasAnyRole("USER","ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET, "/livros/**").hasAnyAuthority("OBTER_DETALHES", "SALVAR_LIVROS");
//                    authorize.anyRequest().authenticated(); //qualquer um abaixo disso sera ignorado, Any request tem que ficar por ultimo
//                })
//                .build();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(UsuarioService usarioService) {
//
//        return new CustomDetailsService(usarioService);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults()) // se desabilitarmos o formulario de login não terá a pagina de login so o http basic
//                .httpBasic(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize -> {
//                    authorize.requestMatchers("/login/**").permitAll();
//                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
//                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");
//                    authorize.anyRequest().authenticated(); //qualquer um abaixo disso sera ignorado, Any request tem que ficar por ultimo
//                })
//                .build();
//    }


//    @Bean
//    public UserDetailsService userDetailsService(UsuarioService usarioService) {
//
//        return new CustomDetailsService(usarioService);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, LoginSocialSucessHandler loginSocialSucessHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults()) // se desabilitarmos o formulario de login não terá a pagina de login so o http basic
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    authorize.anyRequest().authenticated(); //qualquer um abaixo disso sera ignorado, Any request tem que ficar por ultimo
                })
                .oauth2Login(oathu2->{
                    oathu2.successHandler(loginSocialSucessHandler);
                })
                .build();
    }
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }
}

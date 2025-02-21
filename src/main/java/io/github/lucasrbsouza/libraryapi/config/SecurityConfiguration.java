package io.github.lucasrbsouza.libraryapi.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.github.lucasrbsouza.libraryapi.security.CustomAuthentication;
import io.github.lucasrbsouza.libraryapi.security.CustomDetailsService;
import io.github.lucasrbsouza.libraryapi.security.JwtCustomAuthenticationFilter;
import io.github.lucasrbsouza.libraryapi.security.LoginSocialSucessHandler;
import io.github.lucasrbsouza.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

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


//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder(10);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http, LoginSocialSucessHandler successHandler, JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                //.httpBasic(Customizer.withDefaults())
                .formLogin(configurer -> {
                    configurer.loginPage("/login");
                })
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();

                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            .loginPage("/login")
                            .successHandler(successHandler);
                })
                .oauth2ResourceServer(oauth2RS -> oauth2RS.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }

    //conifgura o prefixo role
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    //configura no token jwt o prefixo scope
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var auhtoritesConverter = new JwtGrantedAuthoritiesConverter();
        auhtoritesConverter.setAuthorityPrefix("");
        var converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(auhtoritesConverter);

        return converter;
    }

    // JWK --> json web key
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws Exception {
        RSAKey rsaKey = gerarChaveRSA();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    //Gerar Par de Chaves RSA
    private RSAKey gerarChaveRSA() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        RSAPublicKey chavePublica = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey chavePrivada = (RSAPrivateKey) keyPair.getPrivate();


        return new RSAKey
                .Builder(chavePublica)
                .privateKey(chavePrivada)
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    //decodificar jwt
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> {
           web.ignoring().requestMatchers(
                  "/v2/api-docs/**",
                  "/v3/api-docs/**",
                  "/swagger-resources/**",
                  "swagger-ui.html",
                  "swagger-ui/**",
                  "/webjars/**",
                   "/actuator/**"
          );
        };
    }
}

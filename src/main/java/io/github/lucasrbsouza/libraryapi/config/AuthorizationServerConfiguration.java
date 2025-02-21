package io.github.lucasrbsouza.libraryapi.config;

import io.github.lucasrbsouza.libraryapi.security.CustomAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain authSecurityFilterChain(HttpSecurity http)throws Exception{
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http.oauth2ResourceServer(oAuth2Rs ->
                oAuth2Rs
                        .jwt(Customizer.withDefaults()));

        http.formLogin(configure ->configure.loginPage("/login"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public TokenSettings tokenSettings(){
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                //acces_token: token utilizado nas requisicoes
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                //refresh_token: token utilizado para renovar o acces_token
                .refreshTokenTimeToLive(Duration.ofMinutes(90))
                .build();
    }

    @Bean
    public ClientSettings clientSettings(){
        return ClientSettings.builder()
                .requireAuthorizationConsent(false)
                .build();
    }
    //
    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder()
                //obter o token
                .tokenEndpoint("/oauth2/token")
                //utilizado para consultar o status do token
                .tokenIntrospectionEndpoint("/oauth2/instrospect")
                //revogar o token
                .tokenRevocationEndpoint("/oauth2/revoke")
                //authorization endpoint
                .authorizationEndpoint("/oauth2/authorize")
                //informacoes do usuario OPEN ID CONNECT
                .oidcUserInfoEndpoint("oauth2/userinfo")
                // obter chave public para verificar a assinatura do token
                .jwkSetEndpoint("/oauth2/jwks")
                //endpoint para frazer logaout
                .oidcLogoutEndpoint("/oauth2/logout")
                .build();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(){
        return context -> {
            var principal = context.getPrincipal();
            if (principal instanceof CustomAuthentication authentication){
                OAuth2TokenType tipoToken = context.getTokenType();

                if (OAuth2TokenType.ACCESS_TOKEN.equals(tipoToken)){

                    Collection<GrantedAuthority> authorities = authentication.getAuthorities();

                    List<String> authoritesList = authorities.stream().map(GrantedAuthority::getAuthority).toList();

                    context.getClaims()
                            .claim("authorities", authoritesList)
                            .claim("email", authentication.getUsuario().getEmail());
                }
            }
        };
    }
}

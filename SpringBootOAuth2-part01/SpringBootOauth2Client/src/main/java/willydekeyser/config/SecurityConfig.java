package willydekeyser.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests().requestMatchers("/createUser").permitAll()
                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login();
        return http.build();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, ClientRegistrationRepository clientRegistrationRepository) throws Exception {

        String base_uri = OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;
        DefaultOAuth2AuthorizationRequestResolver resolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, base_uri);
        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());

        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> {
                    oauth2Login.loginPage("/oauth2/authorization/myoauth2");
                    oauth2Login.authorizationEndpoint().authorizationRequestResolver(resolver);
                    oauth2Login.userInfoEndpoint(userInfo -> userInfo
                            .oidcUserService(this.oidcUserService()));
                })
                .oauth2Client(withDefaults());
        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return (userRequest) -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);
            OAuth2AccessToken accessToken = userRequest.getAccessToken();
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            try {
                JWT jwt = JWTParser.parse(accessToken.getTokenValue());
                JWTClaimsSet claimSet = jwt.getJWTClaimsSet();
                Collection<String> userAuthorities = claimSet.getStringListClaim("authorities");
                mappedAuthorities.addAll(userAuthorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList());
            } catch (ParseException e) {
                System.err.println("Error OAuth2UserService: " + e.getMessage());
            }
            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            return oidcUser;
        };
    }
}
    /*
    @Bean
    @Order(2)
    public SecurityFilterChain appsecurityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/new").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("")
                .authenticated().and().formLogin().and().build();
    }
    */

    /*
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();
        return http.build();
    }
    */





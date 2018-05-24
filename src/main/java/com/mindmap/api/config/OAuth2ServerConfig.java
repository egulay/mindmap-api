package com.mindmap.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@Slf4j
public class OAuth2ServerConfig {
    private static final String RESOURCE_ID = "mindmap-api";

    @Configuration
    @EnableResourceServer
    @Slf4j
    protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {
        private final TokenStore tokenStore;

        @Autowired
        public ResourceServerConfig(TokenStore tokenStore) {
            this.tokenStore = tokenStore;
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID).tokenStore(tokenStore);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/**").authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    @Slf4j
    protected static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
        private final AuthenticationService authenticationService;
        private final JwtAccessTokenConverter jwtAccessTokenConverter;
        private final TokenStore tokenStore;
        private final AuthenticationManager authenticationManager;

        @Autowired
        public AuthorizationServerConfig(AuthenticationService authenticationService
                , JwtAccessTokenConverter jwtAccessTokenConverter
                , TokenStore tokenStore
                , AuthenticationManager authenticationManager) {
            this.authenticationService = authenticationService;
            this.jwtAccessTokenConverter = jwtAccessTokenConverter;
            this.tokenStore = tokenStore;
            this.authenticationManager = authenticationManager;
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
                    .accessTokenConverter(jwtAccessTokenConverter)
                    .userDetailsService(authenticationService);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("mindmap")
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("USER")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .secret("secret");
        }
    }

    @Bean
    public FilterRegistrationBean customCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}


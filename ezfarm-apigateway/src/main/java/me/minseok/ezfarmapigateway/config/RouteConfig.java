package me.minseok.ezfarmapigateway.config;

import java.time.ZonedDateTime;
import me.minseok.ezfarmapigateway.filter.AuthorizationFilter;
import me.minseok.ezfarmapigateway.filter.AuthorizationFilter.Config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class RouteConfig {

  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder builder,
      AuthorizationFilter authorizationFilter) {
    //TODO: Ezfarm-user 다중 서버 환경 -> Load Balancing 필요
    return builder.routes()
        .route("ezfarm-user",
            r -> r.path("/login", "/actuators/**")
                .filters(f -> f.removeRequestHeader("Cookie"))
                .uri("http://127.0.0.1:8080")
        ).route("ezfarm-user",
            r -> r.path("/users")
                .and()
                .method(HttpMethod.POST)
                .filters(f -> f.removeRequestHeader("Cookie"))
                .uri("http://127.0.0.1:8080")
        ).route("ezfarm-user",
            r -> r.path("/users/**")
                .filters(f -> f.removeRequestHeader("Cookie")
                    .filter(authorizationFilter.apply(new Config())))
                .uri("http://127.0.0.1:8080")
        ).build();

//        return builder.routes()
//        .route("ezfarm-user-high",
//            r -> r.path("/users")
//                .and()
//                .weight("group-order", 9)
//                .and()
//                .between(ZonedDateTime.now().minusDays(5), ZonedDateTime.now().plusDays(5))
//                .filters(f -> f.removeRequestHeader("Cookie"))
//                .uri("http://127.0.0.1:8080")
//        )
//        .route("ezfarm-user-low",
//            r -> r.path("/users")
//                .and()
//                .weight("group-order", 1)
//                .and()
//                .between(ZonedDateTime.now().plusDays(5), ZonedDateTime.now().plusDays(30))
//                .filters(f -> f.removeRequestHeader("Cookie"))
//                .uri("http://127.0.0.1:8080")
//        )
//        .build();
  }
}

//package pl.example.components.validation;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//
//import reactor.core.publisher.Mono;
//
//@Component
//public class SpaWebFilter implements WebFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        String path = exchange.getRequest().getURI().getPath();
//        if (!path.startsWith("/api") && path.matches("[^\\\\.]*")) {
//            return chain.filter(
//                exchange.mutate().request(exchange.getRequest().mutate().path("/index.html").build()
//                ).build());
//        }
//        return chain.filter(exchange);
//    }
//}

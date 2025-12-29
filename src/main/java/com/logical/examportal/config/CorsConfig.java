package com.logical.examportal.config;
//
//import java.util.Arrays;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // allow cookies/auth
//        config.setAllowedOrigins(Arrays.asList(
////            "http://localhost:5500",
//            "https://fastidious-parfait-a42fa2.netlify.app/"
//        ));
//        config.addAllowedOrigin("https://fastidious-parfait-a42fa2.netlify.app/"); // Add your frontend origin
////        config.addAllowedOrigin("http://localhost:5500");
//        //        config.addAllowedOrigin("https://examportalbackend-production.up.railway.app");
//        config.addAllowedOrigin("http://localhost:8099");
//        config.addAllowedHeader("*"); // Allow all headers
//        config.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, etc.)
//        config.setAllowCredentials(true); // Allow credentials like cookies
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config); // Apply to all endpoints
//
//        return new CorsFilter(source);
//    }
//}


//package com.logical.examportal.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//
//        // exact origins, no trailing slash
//        config.setAllowedOrigins(Arrays.asList(
//                "https://fastidious-parfait-a42fa2.netlify.app",
//                "http://localhost:8099"
//        ));
//
//        config.setAllowCredentials(true);
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        return source;
//    }
//}

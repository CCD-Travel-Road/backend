package YeoroProject.Yeoro.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화 (운영 환경에서는 활성화 권장)
//            .authorizeHttpRequests(authorize -> authorize
//                .requestMatchers("/api/users/login", "/api/users/create").permitAll()  // 로그인, 회원가입 경로 허용
//                .anyRequest().authenticated()  // 나머지 요청 인증 필요
//            )
//            .formLogin(AbstractHttpConfigurer::disable)  // 기본 폼 로그인 비활성화
//            .httpBasic(AbstractHttpConfigurer::disable);  // HTTP Basic 인증 비활성화
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화 (운영 환경에서는 활성화 권장)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/public/**").permitAll()  // /api/public/ 이하의 모든 경로는 인증 없이 허용
                .anyRequest().permitAll()  // 나머지 모든 요청은 인증 없이 허용
            )
            .formLogin(AbstractHttpConfigurer::disable)  // 기본 폼 로그인 비활성화
            .httpBasic(AbstractHttpConfigurer::disable);  // HTTP Basic 인증 비활성화

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

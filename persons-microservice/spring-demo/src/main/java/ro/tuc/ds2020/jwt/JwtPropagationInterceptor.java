//package ro.tuc.ds2020.jwt;
//
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import java.io.IOException;
//
//public class JwtPropagationInterceptor implements ClientHttpRequestInterceptor {
//
//    private final String token;
//
//    public JwtPropagationInterceptor(String token) {
//        this.token = token; // Tokenul JWT este injectat la crearea interceptorului
//    }
//
//    @Override
//    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//        request.getHeaders().set("Authorization", "Bearer " + token);
//        return execution.execute(request, body);
//    }
//}

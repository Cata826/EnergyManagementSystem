package ro.tuc.ds2020.dtos;

import java.util.UUID;

//public class LoginResponseDTO {
//    private String token;
//
//    public LoginResponseDTO(String token) {
//        this.token = token;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//}
public class LoginResponseDTO {
    private String token;
    private UUID id;
    private boolean isAdmin;

    public LoginResponseDTO(String token, UUID id, boolean isAdmin) {
        this.token = token;
        this.id = id;
        this.isAdmin = isAdmin;
    }

    public String getToken() {
        return token;
    }

    public UUID getId() {
        return id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

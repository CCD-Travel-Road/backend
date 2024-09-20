package YeoroProject.Yeoro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResisterRequest {

    private String email;       // 이메일 주소
    private String password;    // 비밀번호
    private String name;        // 회원 이름
    private String age;         // 회원 나이

}

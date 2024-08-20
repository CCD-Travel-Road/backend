package YeoroProject.Yeoro.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")

public class User {

    @Id
    private String id;  // 회원 아이디

    private String email;       // 이메일 주소
    private String password;    // 비밀번호
    private String name;        // 회원 이름
}



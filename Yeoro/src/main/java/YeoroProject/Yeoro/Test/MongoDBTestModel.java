package YeoroProject.Yeoro.Test;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")

public class MongoDBTestModel {

    @Id
    private String Userid;  // 유저 아이디

    private String email;    // 이메일 주소
    private String password;    // 비밀번호
}



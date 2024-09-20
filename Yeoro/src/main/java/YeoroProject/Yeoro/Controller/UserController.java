package YeoroProject.Yeoro.Controller;

import YeoroProject.Yeoro.Service.UserService;
import YeoroProject.Yeoro.Model.User;
import YeoroProject.Yeoro.dto.LoginRequest;
import YeoroProject.Yeoro.dto.ResisterRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUser();
    }
    /**
     * 회원가입
     * @param resisterRequest 저장할 유저 객체
     * @return  로그인 성공 여부
     */
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody ResisterRequest resisterRequest) {
        String response = userService.addUser(resisterRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        String response = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(String email) {
        String response = userService.checkUserEmail(email);
        return ResponseEntity.ok(response);
    }
}

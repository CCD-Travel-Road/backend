package YeoroProject.Yeoro.Service;

import YeoroProject.Yeoro.Model.User;
import YeoroProject.Yeoro.Repository.UserRepository;
import YeoroProject.Yeoro.dto.ResisterRequest;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    public String addUser(ResisterRequest resisterRequest) {
        // 비밀번호 해싱
        resisterRequest.setPassword(passwordEncoder.encode(resisterRequest.getPassword()));

        // 유저 정보 저장
        User user = new User();
        user.setEmail(resisterRequest.getEmail());
        user.setPassword(passwordEncoder.encode(resisterRequest.getPassword()));
        user.setName(resisterRequest.getName());
        user.setAge(resisterRequest.getAge());

        userRepository.save(user);

        return "회원가입 성공";
    }

    // 로그인
    public String authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        // 사용자가 존재하고 비밀번호가 일치하는지 확인
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return "로그인 성공";
        }

        return "이메일이나 비밀번호를 다시 한 번 확인해주세요.";
    }
    
    // 이메일 중복 확인
    public String checkUserEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return "중복된 이메일입니다.";
        }

        return "사용 가능한 이메일입니다.";
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * 유저 존재 확인 및 ID 반환
     *
     * @param email 유저의 이메일 주소
     * @return 사용자의 ID값을 반환합니다.
     */
    public String findById(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new RuntimeException("해당 이메일을 가진 회원을 찾을 수 없습니다.");
        }
        return user.get().getId();
    }




}

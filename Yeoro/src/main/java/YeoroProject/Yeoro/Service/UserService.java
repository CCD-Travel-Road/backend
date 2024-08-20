package YeoroProject.Yeoro.Service;

import YeoroProject.Yeoro.Model.User;
import YeoroProject.Yeoro.Repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}

package YeoroProject.Yeoro.Test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class MongoDBTestController {

    private final MongoDBTestService mongoDBTestService;

    @Autowired
    public MongoDBTestController(MongoDBTestService mongoDBTestService) {
        this.mongoDBTestService = mongoDBTestService;
    }

    @GetMapping
    public List<MongoDBTestModel> getUsers() {
        return mongoDBTestService.getAllUser();
    }
}

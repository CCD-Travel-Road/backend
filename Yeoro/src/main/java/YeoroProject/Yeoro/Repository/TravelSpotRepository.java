package YeoroProject.Yeoro.Repository;

import YeoroProject.Yeoro.Model.TravelSpot;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TravelSpotRepository extends MongoRepository<TravelSpot, String> {

    Optional<TravelSpot> findByCourseIdAndTravelSpotName(String existingCourseID, String travelSpotName);

}

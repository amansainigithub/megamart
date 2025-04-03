package com.coder.springjwt.services.publicService.ratingService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.productRatings.Ratings;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.services.publicService.ratingService.RatingService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RatingServiceImple implements RatingService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;


    @Override
    public ResponseEntity<?> unratedDeliveredProduct() {
        log.info("<-- unratedDeliveredProduct Flying -->");
        try {

            List<Object[]> allWithoutRatingsByUserId = this.orderItemsRepository
                                            .findAllWithoutRatingsWithDetails("16");

            for (Object[] row : allWithoutRatingsByUserId) {
                CustomerOrderItems orderItem = (CustomerOrderItems) row[0];
                Ratings rating = (Ratings) row[1]; // This will be null
            }
            return ResponseGenerator.generateSuccessResponse(allWithoutRatingsByUserId , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }
}

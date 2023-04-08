package com.example.restorentmanagementsystem.Controllers;

import com.example.restorentmanagementsystem.DTO.BasicResponseDTO;
import com.example.restorentmanagementsystem.DTO.InsertFeedbackRequest;
import com.example.restorentmanagementsystem.DTO.InsertOrderDetailsRequest;
import com.example.restorentmanagementsystem.Models.Feedback;
import com.example.restorentmanagementsystem.Repositories.FeedbackDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/feedback")
public class FeedbackController {

    @Autowired
    FeedbackDAO feedbackDAO;

    @PostMapping("/InsertFeedback")
    //@Operation( security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BasicResponseDTO> InsertFeedback(@RequestBody InsertFeedbackRequest request) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Send Feedback Successfully.");
        try{

            Feedback setData = new Feedback();
            setData.setOrderID(request.getOrderID());
            setData.setUserName(request.getUserName());
            setData.setFeedback(request.getFeedback());
            feedbackDAO.save(setData);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/GetFeedback")
    //@Operation( security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BasicResponseDTO> GetFeedback(@RequestParam int PageNumber, int RecordPerPage) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Get Feedback Successfully.");
        try{

            List<Feedback> list = new ArrayList<>();
            list = feedbackDAO
                    .findAll()
                    .stream()
                    .skip((PageNumber-1)*RecordPerPage)
                    .limit(RecordPerPage)
                    .toList();

            if(list.stream().count()==0){
                response.setSuccess(false);
                response.setMessage("Feedback List Not Found");
                return ResponseEntity.ok(response);
            }
            response.setData(list);

            double TotalRecord = feedbackDAO
                    .findAll()
                    .stream().count();

            int TotalPage = (int)Math.ceil((double)(TotalRecord/RecordPerPage));
            response.setTotalPage(TotalPage);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }


}

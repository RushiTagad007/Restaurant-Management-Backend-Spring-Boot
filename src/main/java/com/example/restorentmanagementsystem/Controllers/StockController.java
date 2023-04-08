package com.example.restorentmanagementsystem.Controllers;

import com.example.restorentmanagementsystem.DTO.*;
import com.example.restorentmanagementsystem.Models.Stock;
import com.example.restorentmanagementsystem.Repositories.StockDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/stock")
public class StockController {

    @Autowired
    StockDAO stockDAO;

    @PostMapping("/InsertStockDetails")
    public ResponseEntity<BasicResponseDTO> InsertStockDetails(@RequestBody InsertStockDetailsRequest request) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Insert Stock Details Successfully.");
        try{

            Stock setOrder = new Stock();
            setOrder.setInsertionDate(new Date().toString());
            setOrder.setQuentity(request.getQuentity());
            setOrder.setStockName(request.getStockName());
            stockDAO.save(setOrder);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/GetStockDetails")
    public ResponseEntity<BasicResponseDTO> GetStockDetails(@RequestParam int PageNumber, int RecordPerPage) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Get Stock Details Successfully.");
        try{

            List<Stock> list = new ArrayList<>();
            list = stockDAO
                    .findAll()
                    .stream()
                    .skip((PageNumber-1)*RecordPerPage)
                    .limit(RecordPerPage)
                    .toList();

            if(list.stream().count()==0){
                response.setSuccess(false);
                response.setMessage("UserOrder List Not Found");
                return ResponseEntity.ok(response);
            }
            response.setData(list);
//
            double TotalRecord = stockDAO
                    .findAll()
                    .stream().count();
//
            int TotalPage = (int)Math.ceil((double)(TotalRecord/RecordPerPage));
            response.setTotalPage(TotalPage);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/GetAllStockDetails")
    public ResponseEntity<BasicResponseDTO> GetAllStockDetails() {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Get Stock Details Successfully.");
        try{

            List<Stock> list = new ArrayList<>();
            list = stockDAO
                    .findAll()
                    .stream()
                    .toList();

            if(list.stream().count()==0){
                response.setSuccess(false);
                response.setMessage("UserOrder List Not Found");
                return ResponseEntity.ok(response);
            }
            response.setData(list);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/UpdateStockDetails")
    public ResponseEntity<BasicResponseDTO> UpdateStockDetails(@RequestBody UpdateStockDetailsRequest request) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Update Booking Details Successfully.");
        try{

            Optional<Stock> setData = stockDAO
                    .findAll()
                    .stream()
                    .filter(X->X.getStockId()== request.getStockID())
                    .findFirst();
            if(setData.isEmpty()){
                response.setSuccess(false);
                response.setMessage("UserOrder ID Not Found");
                return ResponseEntity.ok(response);
            }
            Stock setOrder = setData.get();
            setOrder.setQuentity(request.getQuentity());
            setOrder.setStockName(request.getStockName());
            stockDAO.save(setOrder);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }


}

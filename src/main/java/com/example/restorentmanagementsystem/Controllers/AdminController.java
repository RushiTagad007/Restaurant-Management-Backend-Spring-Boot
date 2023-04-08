package com.example.restorentmanagementsystem.Controllers;

import com.example.restorentmanagementsystem.DTO.*;
import com.example.restorentmanagementsystem.Models.Menu;
import com.example.restorentmanagementsystem.Models.Stock;
import com.example.restorentmanagementsystem.Models.UserOrder;
import com.example.restorentmanagementsystem.Repositories.FeedbackDAO;
import com.example.restorentmanagementsystem.Repositories.MenuDAO;
import com.example.restorentmanagementsystem.Repositories.OrderDAO;
import com.example.restorentmanagementsystem.Repositories.StockDAO;
import com.example.restorentmanagementsystem.Services.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/Admin")
public class AdminController {

    private final FileUploadService fileUploadService;

    @Autowired
    MenuDAO menuDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    StockDAO stockDAO;

    // Menu
    //@PostMapping("/InsertMenuDetails")
    @RequestMapping(value = "/InsertMenuDetails", method = RequestMethod.POST, consumes="multipart/form-data")
    public ResponseEntity<BasicResponseDTO> InsertMenuDetails(@ModelAttribute InsertMenuDetailsRequest request) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Insert Menu Details Successfully.");
        try{

            Menu setData = new Menu();
            setData.setInsertionDate(new Date().toString());
            setData.setMenuName(request.getMenuName());
            setData.setMenuDescription(request.getMenuDescription());
            setData.setPrice(request.getPrice());
            setData.setMenuType(request.getMenuType());
            setData.setMenuSubType(request.getMenuSubType());
            setData.setStockName_1(request.getStockName_1());
            setData.setStockName_2(request.getStockName_2());
            setData.setStockName_3(request.getStockName_3());
            setData.setStockQuantity_1(request.getStockQuantity_1());
            setData.setStockQuantity_2(request.getStockQuantity_2());
            setData.setStockQuantity_3(request.getStockQuantity_3());
            setData.setIsActive(request.getIsActive());
            setData.setImageUrl(fileUploadService.uploadFile(request.getFile()));
            menuDAO.save(setData);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/GetMenuDetails")
    //@Operation( security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BasicResponseDTO> GetMenuDetails(@RequestParam int PageNumber, int RecordPerPage) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Get Menu Details Successfully.");
        try{
            PageNumber= PageNumber==0?1:PageNumber;
            List<Menu> list = new ArrayList<>();
            list = menuDAO
                    .findAll()
                    .stream()
//                    .skip((PageNumber-1)*RecordPerPage)
//                    .limit(RecordPerPage)
                    .toList();
//
            if(list.stream().count()==0){
                response.setSuccess(false);
                response.setMessage("Menu List Not Found");
                return ResponseEntity.ok(response);
            }
            response.setData(list);
//
            double TotalRecord = menuDAO
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

    //@PatchMapping("/UpdateMenuDetails")
    @RequestMapping(value = "/UpdateMenuDetails", method = RequestMethod.PATCH, consumes="multipart/form-data")
    public ResponseEntity<BasicResponseDTO> UpdateMenuDetails(@ModelAttribute UpdateMenuDetailsRequest request) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Update Menu Details Successfully.");
        try{

            Optional<Menu> getData = menuDAO.findAll().stream().filter(X->X.getMenuId()== request.getMenuID()).findFirst();
            if(getData.isEmpty()){
                response.setSuccess(false);
                response.setMessage("No Data Found");
                return ResponseEntity.ok(response);
            }

            Menu setData = getData.get();
            setData.setMenuName(request.getMenuName());
            setData.setMenuDescription(request.getMenuDescription());
            setData.setPrice(request.getPrice());
            setData.setMenuType(request.getMenuType());
            setData.setMenuSubType(request.getMenuSubType());
            setData.setStockName_1(request.getStockName_1());
            setData.setStockName_2(request.getStockName_2());
            setData.setStockName_3(request.getStockName_3());
            setData.setStockQuantity_1(request.getStockQuantity_1());
            setData.setStockQuantity_2(request.getStockQuantity_2());
            setData.setStockQuantity_3(request.getStockQuantity_3());
            setData.setIsActive(request.getIsActive());
            menuDAO.save(setData);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/DeleteMenuDetails")
    //@Operation( security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BasicResponseDTO> DeleteMenuDetails(@RequestParam Long MenuID) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Delete Employee Details Successfully.");
        try{

            Optional<Menu> data = menuDAO
                    .findAll()
                    .stream()
                    .filter(X->X.getMenuId()==MenuID)
                    .findFirst();

            if(!data.isPresent()){
                response.setSuccess(false);
                response.setMessage("InValid Menu ID.");
                return ResponseEntity.ok(response);
            }

            menuDAO.delete(data.get());

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    // Payment

    @PostMapping("/GetMenuRecord")
    //@Operation( security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BasicResponseDTO> GetMenuRecord(@RequestBody GetMenuRecordRequest request) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Get Payment Details Successfully.");
        try{

            List<Menu> list = new ArrayList<>();
            List<Menu> list1 = new ArrayList<>();
            if(request.getIsVeg()){
                list1 = menuDAO.findAll().stream().filter(X->X.getMenuType().equalsIgnoreCase("veg")).toList();
            }

            if(list1.stream().count()!=0){
                for (Menu var : list1)
                {
                    Optional<Menu> data = list.stream().filter(X->X.getMenuName().equalsIgnoreCase(var.getMenuName())).findFirst();
                    if(!data.isPresent())
                    {
                        list.add(var);
                    }
                }
            }

            list1 = new ArrayList<>();
            if(request.getIsNonVeg()){
                list1 = menuDAO.findAll().stream().filter(X->X.getMenuType().equalsIgnoreCase("nonveg")).toList();
            }

            if(list1.stream().count()!=0){
                for (Menu var : list1)
                {
                    Optional<Menu> data = list.stream().filter(X->X.getMenuName().equalsIgnoreCase(var.getMenuName())).findFirst();
                    if(!data.isPresent())
                    {
                        list.add(var);
                    }
                }
            }

            list1 = new ArrayList<>();
            if(request.getIsStarter()){
                list1 = menuDAO.findAll().stream().filter(X->X.getMenuSubType().equalsIgnoreCase("starter")).toList();
            }

            if(list1.stream().count()!=0){
                for (Menu var : list1)
                {
                    Optional<Menu> data = list.stream().filter(X->X.getMenuName().equalsIgnoreCase(var.getMenuName())).findFirst();
                    if(!data.isPresent())
                    {
                        list.add(var);
                    }
                }
            }

            list1 = new ArrayList<>();
            if(request.getIsDesert()){
                list1 = menuDAO.findAll().stream().filter(X->X.getMenuSubType().equalsIgnoreCase("desert")).toList();
            }

            if(list1.stream().count()!=0){
                for (Menu var : list1)
                {
                    Optional<Menu> data = list.stream().filter(X->X.getMenuName().equalsIgnoreCase(var.getMenuName())).findFirst();
                    if(!data.isPresent())
                    {
                        list.add(var);
                    }
                }
            }


            list1 = new ArrayList<>();
            if(request.getIsMainCourse()){
                list1 = menuDAO.findAll().stream().filter(X->X.getMenuSubType().equalsIgnoreCase("maincourse")).toList();
            }

            if(list1.stream().count()!=0){
                for (Menu var : list1)
                {
                    Optional<Menu> data = list.stream().filter(X->X.getMenuName().equalsIgnoreCase(var.getMenuName())).findFirst();
                    if(!data.isPresent())
                    {
                        list.add(var);
                    }
                }
            }


            list1 = new ArrayList<>();
            if(request.getIsBeverages()){
                list1 = menuDAO.findAll().stream().filter(X->X.getMenuSubType().equalsIgnoreCase("beverages")).toList();
            }

            if(list1.stream().count()!=0){
                for (Menu var : list1)
                {
                    Optional<Menu> data = list.stream().filter(X->X.getMenuName().equalsIgnoreCase(var.getMenuName())).findFirst();
                    if(!data.isPresent())
                    {
                        list.add(var);
                    }
                }
            }

            if(list.stream().count()==0){
                response.setSuccess(false);
                response.setMessage("Menu List Not Found");
                return ResponseEntity.ok(response);
            }
            response.setData(list);


        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/UpdateActiveMenuStatus")
    //@Operation( security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BasicResponseDTO> UpdateActiveMenuStatus(@RequestBody UpdateActiveMenuStatusRequest request) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Update Menu Details Successfully.");
        try{

            Optional<Menu> getData = menuDAO.findAll().stream().filter(X->X.getMenuId()== request.getMenuID()).findFirst();
            if(getData.isEmpty()){
                response.setSuccess(false);
                response.setMessage("No Data Found");
                return ResponseEntity.ok(response);
            }

            Menu setData = getData.get();
            setData.setIsActive(request.getIsActive());
            menuDAO.save(setData);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/UpdateOrderStatus")
    //@Operation( security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BasicResponseDTO> UpdateOrderStatus(@RequestBody UpdateOrderStatusRequest request) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Update Menu Details Successfully.");
        try{

            Optional<UserOrder> getData = orderDAO.findAll().stream().filter(X->X.getId()== request.getOrderID()).findFirst();
            if(getData.isEmpty()){
                response.setSuccess(false);
                response.setMessage("No Data Found");
                return ResponseEntity.ok(response);
            }

            UserOrder setData = getData.get();
            setData.setStatus(request.getStatus());
            orderDAO.save(setData);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }


    @GetMapping("/GetAllOrderDetails")
    public ResponseEntity<BasicResponseDTO> GetAllOrderDetails(@RequestParam int PageNumber, int RecordPerPage) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Get Order Details Successfully.");
        try{

            List<UserOrder> list = new ArrayList<>();
            list = orderDAO
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

            double TotalRecord = orderDAO
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

package com.example.restorentmanagementsystem.Controllers;

import com.example.restorentmanagementsystem.DTO.*;
import com.example.restorentmanagementsystem.Models.Menu;
import com.example.restorentmanagementsystem.Models.Stock;
import com.example.restorentmanagementsystem.Models.UserOrder;
import com.example.restorentmanagementsystem.Repositories.MenuDAO;
import com.example.restorentmanagementsystem.Repositories.OrderDAO;
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
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    MenuDAO menuDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    StockDAO stockDAO;

    // InsertOrderDetails, GetOrderDetails, UpdateOrderDetails, DeleteOrderDetails

    @PostMapping("/InsertOrderDetails")
    public ResponseEntity<BasicResponseDTO> InsertOrderDetails(@RequestBody InsertOrderDetailsRequest request) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Insert Order Details Successfully.");
        try{

            Optional<Menu> menuDetails = menuDAO.findAll().stream().filter(X->X.getMenuId()== request.getMenuID()).findFirst();
            if(menuDetails.isEmpty()){
                response.setSuccess(false);
                response.setMessage("Menu ID Not Present. Something Went Wrong.");
                return ResponseEntity.ok(response);
            }

            Optional<Stock> stockData1 = stockDAO
                    .findAll()
                    .stream()
                    .filter(X->X.getStockName().equalsIgnoreCase(menuDetails.get().getStockName_1()))
                    .findFirst();

            if(stockData1.isPresent()){
                Stock getData = stockData1.get();
                Long Quentity = getData.getQuentity();
                if((Quentity- menuDetails.get().getStockQuantity_1())<0)
                {
                    response.setSuccess(false);
                    response.setMessage("Quantity 1 Less Than : "+Quentity);
                    return ResponseEntity.ok(response);
                }

                stockData1.get().setQuentity(Quentity- menuDetails.get().getStockQuantity_1());
            }



            Optional<Stock> stockData2 = stockDAO
                    .findAll()
                    .stream()
                    .filter(X->X.getStockName().equalsIgnoreCase(menuDetails.get().getStockName_2()))
                    .findFirst();

            if(stockData2.isPresent()){
                Stock getData = stockData2.get();
                Long Quentity = getData.getQuentity();
                if((Quentity- menuDetails.get().getStockQuantity_2())<0)
                {
                    response.setSuccess(false);
                    response.setMessage("Quantity 2 Less Than : "+Quentity);
                    return ResponseEntity.ok(response);
                }

                stockData2.get().setQuentity(Quentity- menuDetails.get().getStockQuantity_2());
            }

            Optional<Stock> stockData3 = stockDAO
                    .findAll()
                    .stream()
                    .filter(X->X.getStockName().equalsIgnoreCase(menuDetails.get().getStockName_3()))
                    .findFirst();

            if(stockData3.isPresent()){
                Stock getData = stockData3.get();
                Long Quentity = getData.getQuentity();
                if((Quentity- menuDetails.get().getStockQuantity_3())<0)
                {
                    response.setSuccess(false);
                    response.setMessage("Quantity 3 Less Than : "+Quentity);
                    return ResponseEntity.ok(response);
                }

                stockData3.get().setQuentity(Quentity- menuDetails.get().getStockQuantity_3());
            }

            stockDAO.save(stockData1.get());
            stockDAO.save(stockData2.get());
            stockDAO.save(stockData3.get());


            UserOrder setUserOrder = new UserOrder();
            setUserOrder.setInsertionDate(new Date().toString());
            setUserOrder.setUserName(request.getUserName());
            setUserOrder.setPhoneNumber(request.getMobileNumber());
            setUserOrder.setUserID(request.getUserID());
            setUserOrder.setMenuID(request.getMenuID());
            setUserOrder.setTableID(request.getTableID());
            Optional<Menu> menuData = menuDAO.findAll().stream().filter(X->X.getMenuId()==request.getMenuID()).findFirst();
            setUserOrder.setPrice(menuData.isPresent()?menuData.get().getPrice() : 0);
            setUserOrder.setStatus("Received");
            setUserOrder.setIsActive(true);
            orderDAO.save(setUserOrder);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/GetOrderDetails")
    public ResponseEntity<BasicResponseDTO> GetOrderDetails(@RequestParam Long UserID, int PageNumber, int RecordPerPage) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Get Order Details Successfully.");
        try{

            List<UserOrder> list = new ArrayList<>();
            List<GetOrderDetailsResponseDTO> dataList = new ArrayList<>();
            if(UserID==-1)
            {
                list = orderDAO
                        .findAll()
                        .stream()
                        .filter(X->X.getStatus().equalsIgnoreCase("bill"))
                        .skip((PageNumber - 1) * RecordPerPage)
                        .limit(RecordPerPage)
                        .toList();

                if(list.stream().count()==0){
                    response.setSuccess(false);
                    response.setMessage("UserOrder List Not Found");
                    return ResponseEntity.ok(response);
                }

                list.forEach(X->{
                    Optional<GetOrderDetailsResponseDTO> getData = dataList.stream().filter(X1->X1.TableID==X.getTableID()).findFirst();
                    if(getData.isPresent()){
                        getData.get().Price +=X.getPrice();
                    }else{
                        GetOrderDetailsResponseDTO data = new GetOrderDetailsResponseDTO();
                        data.UserID = X.getUserID();
                        data.MenuID = X.getMenuID();
                        data.TableID = X.getTableID();
                        data.UserName = X.getUserName();
                        data.PhoneNumber = X.getPhoneNumber();
                        data.Status = X.getStatus();
                        data.Price = X.getPrice();
                        dataList.add(data);
                    }
                });
            }
            else {
                list = orderDAO
                        .findAll()
                        .stream()
                        .filter(x -> x.getUserID() == UserID)
                        .skip((PageNumber - 1) * RecordPerPage)
                        .limit(RecordPerPage)
                        .toList();

                if(list.stream().count()==0){
                    response.setSuccess(false);
                    response.setMessage("UserOrder List Not Found");
                    return ResponseEntity.ok(response);
                }

                list.forEach(X->{
                    Optional<GetOrderDetailsResponseDTO> getData = dataList.stream().filter(X1->X1.TableID==X.getTableID()).findFirst();
                    if(getData.isPresent()){
                        getData.get().Price +=X.getPrice();
                    }else{
                        GetOrderDetailsResponseDTO data = new GetOrderDetailsResponseDTO();
                        data.UserID = X.getUserID();
                        data.MenuID = X.getMenuID();
                        data.TableID = X.getTableID();
                        data.UserName = X.getUserName();
                        data.PhoneNumber = X.getPhoneNumber();
                        data.Status = X.getStatus();
                        data.Price = X.getPrice();
                        dataList.add(data);
                    }
                });

            }



            response.setData(dataList);

            double TotalRecord = dataList.stream().filter(x->x.getUserID()==UserID).count();

            int TotalPage = (int)Math.ceil((double)(TotalRecord/RecordPerPage));
            response.setTotalPage(TotalPage);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/GetSubOrderDetails")
    public ResponseEntity<BasicResponseDTO> GetSubOrderDetails(@RequestParam Long UserID, int TableID, int PageNumber, int RecordPerPage) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Get Order Details Successfully.");
        try{

            List<UserOrder> list = new ArrayList<>();
            list = orderDAO
                    .findAll()
                    .stream()
                    .filter(x->x.getUserID()==UserID && x.getTableID()==TableID)
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
                    .stream().filter(x->x.getUserID()==UserID).count();

            int TotalPage = (int)Math.ceil((double)(TotalRecord/RecordPerPage));
            response.setTotalPage(TotalPage);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }


    @PatchMapping("/UpdateOrderDetails")
    public ResponseEntity<BasicResponseDTO> UpdateOrderDetails(@RequestBody UpdateOrderDetailsRequest request) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Update Order Details Successfully.");
        try{

            Optional<UserOrder> setData = orderDAO.findAll().stream().filter(X->X.getId()== request.getOrderID()).findFirst();
            if(setData.isEmpty()){
                response.setSuccess(false);
                response.setMessage("UserOrder ID Not Found");
                return ResponseEntity.ok(response);
            }
            UserOrder setUserOrder = setData.get();
            setUserOrder.setUserName(request.getUserName());
            setUserOrder.setPhoneNumber(request.getMobileNumber());
            setUserOrder.setUserID(request.getUserID());
            setUserOrder.setMenuID(request.getMenuID());
            setUserOrder.setTableID(request.getTableID());
            orderDAO.save(setUserOrder);

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/DeleteOrderDetails")
    public ResponseEntity<BasicResponseDTO> DeleteOrderDetails(@RequestParam Long OrderID) {
        BasicResponseDTO response = new BasicResponseDTO();
        response.setSuccess(true);
        response.setMessage("Delete Order Details Successfully.");
        try{

            Optional<UserOrder> setData = orderDAO
                    .findAll()
                    .stream()
                    .filter(X->X.getId()== OrderID)
                    .findFirst();

            if(setData.isEmpty()){
                response.setSuccess(false);
                response.setMessage("Order ID Not Found");
                return ResponseEntity.ok(response);
            }
            orderDAO.delete(setData.get());

        }catch (Exception ex){
            response.setSuccess(false);
            response.setMessage("Exception Message : "+ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/GetMenuDetails")
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
                    .toList();
//
            if(list.stream().count()==0){
                response.setSuccess(false);
                response.setMessage("Menu List Not Found");
                return ResponseEntity.ok(response);
            }

            List<Menu> MainMenu = new ArrayList<>();

            list.forEach(X->{
                Optional<Stock> stockData1 = stockDAO
                        .findAll()
                        .stream()
                        .filter(X1->X1.getStockName().equalsIgnoreCase(X.getStockName_1()))
                        .findFirst();
                Boolean IsAvailable=true;
                Stock getData = stockData1.get();
                Long Quentity = getData.getQuentity();
                if((Quentity - X.getStockQuantity_1())<0)
                {
                    IsAvailable=false;
                }

                Optional<Stock> stockData2 = stockDAO
                        .findAll()
                        .stream()
                        .filter(X1->X1.getStockName().equalsIgnoreCase(X.getStockName_2()))
                        .findFirst();

                getData = stockData2.get();
                Quentity = getData.getQuentity();
                if((Quentity- X.getStockQuantity_2())<0)
                {
                    IsAvailable=false;
                }


                Optional<Stock> stockData3 = stockDAO
                        .findAll()
                        .stream()
                        .filter(X1->X1.getStockName().equalsIgnoreCase(X.getStockName_3()))
                        .findFirst();

                 getData = stockData3.get();
                 Quentity = getData.getQuentity();
                if((Quentity- X.getStockQuantity_3())<0)
                {
                    IsAvailable=false;
                }

                if(IsAvailable){
                    MainMenu.add(X);
                }

            });


            response.setData(MainMenu);
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

}

package com.templatexuv.apresh.customerapp.webacces;



import com.templatexuv.apresh.customerapp.model.AllAddress;
import com.templatexuv.apresh.customerapp.model.AllCart;
import com.templatexuv.apresh.customerapp.model.AllCategory;
import com.templatexuv.apresh.customerapp.model.AllCheckoutResponse;
import com.templatexuv.apresh.customerapp.model.AllComments;
import com.templatexuv.apresh.customerapp.model.AllPaymentResponse;
import com.templatexuv.apresh.customerapp.model.AllProduct;
import com.templatexuv.apresh.customerapp.model.AllProperty;
import com.templatexuv.apresh.customerapp.model.AllSeller;
import com.templatexuv.apresh.customerapp.model.BaseModel;
import com.templatexuv.apresh.customerapp.model.User;

import org.codehaus.jackson.map.ObjectMapper;



public class ResponseHandler {

    public Object parseResponse(int method,String readString,Response response){

        switch (method){
            case ServiceMethods.WS_GETALL_CATEGORIES:
               return parseCategories(readString, response);
            case ServiceMethods.WS_GET_PRODUCTS_BY_ID:
                return parseAllProducts(readString, response);
            case ServiceMethods.WS_GET_PRODUCTS_BY_FILTER:
                return parseAllProducts(readString, response);
            case ServiceMethods.WS_ADD_PRODUCT_TO_CART:
                return parseAllProducts(readString, response);
            case ServiceMethods.WS_GELL_ALL_CART_PRODUCTS:
                return parseAllCartProducts(readString, response);
            case ServiceMethods.WS_DELETE_PRODUCT:
                return parseAllCartProducts(readString, response);
            case ServiceMethods.WS_GET_NO_OF_PRODUCTS:
                return parseAllProducts(readString, response);
            case ServiceMethods.WS_GET_SELLERS:
                return parseAllSellers(readString, response);
            case ServiceMethods.WS_GET_SELLER_COMMENTS:
                return parseSellerComments(readString, response);
            case ServiceMethods.WS_REGISTRATION:
                return parseUserResponse(readString, response);
            case ServiceMethods.WS_LOGIN:
                return parseUserResponse(readString, response);
            case ServiceMethods.WS_GET_PROPERTIES:
                return parseProperties(readString, response);
            case ServiceMethods.WS_GET_ALL_ADDRESS:
                return parseAllAddress(readString, response);
            case ServiceMethods.WS_ADD_ADDRESS:
                return parseAllAddress(readString, response);
            case ServiceMethods.WS_EDIT_ADDRESS:
                return parseAllAddress(readString, response);
            case ServiceMethods.WS_DELETE_ADDRESS:
                return parseAllAddress(readString, response);
            case ServiceMethods.WS_CHECK_OUT:
                return parseAllCartProducts(readString, response);
            case ServiceMethods.WS_CONFIRM_ORDER:
                return parseOrdersResponse(readString, response);
            case ServiceMethods.WS_UNLOCK_PRODUCTS:
                return parseOrdersResponse(readString, response);
            case ServiceMethods.WS_GET_ALL_ORDERS:
                return parseOrdersResponse(readString, response);


        }

        return null;
    }


    public Object parseCategories(String readString,Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        AllCategory allCategory = null;
        try {
            allCategory = objectMapper.readValue(readString, AllCategory.class);
            response.isError = false;
            response.message = allCategory.getMessage();

        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            allCategory.setMessage("Unable to read Data.");
        }
        if(allCategory!=null)
        response.data = allCategory;

        return response;

    }
    public Object parseAllProducts(String readString,Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        AllProduct allProduct = null;
        try {
            allProduct = objectMapper.readValue(readString, AllProduct.class);
            response.message = allProduct.getMessage();
            response.isError = false;

        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            response.message="Unable to read Data.";

        }
        if(allProduct!=null)
            response.data = allProduct;
        return response;
    }
    public Object parseAllCartProducts(String readString,Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        AllCart allCart = null;
        try {
            allCart = objectMapper.readValue(readString, AllCart.class);
            response.message = allCart.getMessage();
            response.isError = false;

        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            response.message="Unable to read Data.";

        }
        if(allCart!=null)
            response.data = allCart;
        return response;
    }

    public Object parseAllSellers(String readString,Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        AllSeller allSeller = null;
        try {
            allSeller = objectMapper.readValue(readString, AllSeller.class);
            response.message = allSeller.getMessage();
            response.isError = false;

        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            response.message="Unable to read Data.";
        }
        if(allSeller!=null)
        response.data = allSeller;
        return response;
    }

    public Object parseAllAddress(String readString,Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        AllAddress allAddress = null;
        try {
            allAddress = objectMapper.readValue(readString, AllAddress.class);
            response.message = allAddress.getMessage();
            response.isError = false;

        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            response.message="Unable to read Data.";
        }
        if(allAddress!=null)
            response.data = allAddress;
        return response;
    }

    public Object parseSellerComments(String readString,Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        AllComments allComments = null;
        try {
            allComments = objectMapper.readValue(readString, AllComments.class);
            response.message=allComments.getMessage();
            response.isError = false;

        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            response.message="Unable to read Data.";
        }
        if(allComments!=null)
            response.data = allComments;
        return response;
    }


    public Object parseUserResponse(String readString, Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        try {
            user = objectMapper.readValue(readString, User.class);
            response.message=user.getMessage();
            response.isError = false;

        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            response.message="Unable to read Data.";

        }
        if(user!=null)
            response.data = user;
        return response;
    }

    public Object parseProperties(String readString,Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        AllProperty allProperty = null;
        try {
            allProperty = objectMapper.readValue(readString, AllProperty.class);
            response.message= allProperty.getMessage();
            response.isError = false;
        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            response.message="Unable to read Data.";
        }
        if(allProperty!=null)
            response.data = allProperty;
        return response;
    }


    public Object commonParser(String readString,Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        BaseModel baseModel = null;
        try {
            baseModel = objectMapper.readValue(readString, BaseModel.class);
            response.message=baseModel.getMessage();
            response.isError = false;
        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            response.message="Unable to read Data.";
        }
        if(baseModel!=null)
            response.data = baseModel;
        return response;
    }

    public Object parseCheckOutResponse(String readString,Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        AllCheckoutResponse allCheckoutResponse = null;
        try {
            allCheckoutResponse = objectMapper.readValue(readString, AllCheckoutResponse.class);
            response.message = allCheckoutResponse.getMessage();
            response.isError = false;

        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            response.message="Unable to read Data.";
        }
        if(allCheckoutResponse!=null)
            response.data = allCheckoutResponse;
        return response;
    }

    public Object parseOrdersResponse(String readString,Response response) {
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        AllPaymentResponse paymentResponse = null;
        try {
            paymentResponse = objectMapper.readValue(readString, AllPaymentResponse.class);
            response.message = paymentResponse.getMessage();
            response.isError = false;

        } catch (Exception e) {
            e.printStackTrace();
            response.isError = true;
            response.message="Unable to read Data.";
        }
        if(paymentResponse!=null)
            response.data = paymentResponse;
        return response;
    }

}
// {"result":"success","categories":[{"categoryId":"1","categoryName":"Mens"},{"categoryId":"2","categoryName":"Womens"},{"categoryId":"3","categoryName":"Kids"}]}
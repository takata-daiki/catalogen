package com.mlsdev.zavpivcom.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mlsdev.zavpivcom.model.Good;
import com.mlsdev.zavpivcom.model.Order;
import com.mlsdev.zavpivcom.model.Person;
import com.mlsdev.zavpivcom.util.HttpHelper;
import com.mlsdev.zavpivcom.util.JsonParser;
import com.mlsdev.zavpivcom.util.StreamHelper;

public class RestDataManager implements IDataManager {

    private static final String TAG = "RestDataManager";
    
    private static final String URL_API_GENERAL = "http://fierce-mountain-9771.heroku.com/api";
    private static final String URL_API_PERSONS = "/people";
    private static final String URL_API_GOODS = "/goods";
    private static final String URL_API_ORDERS = "/people_orders";
    
    public RestDataManager() {
    }
    
    @Override
    public List<Person> getPersons() {
	List<Person> persons = new ArrayList<Person>();
	
	try {
	    InputStream is = HttpHelper.getResponse(new URL(URL_API_GENERAL + URL_API_PERSONS));
	    String response = StreamHelper.makeString(is);
	    
	    JSONArray entries = new JSONArray(response);
	    for (int i = 0; i < entries.length(); i++) {
		JSONObject jsonObject = entries.getJSONObject(i);
		persons.add(JsonParser.parsePerson(jsonObject));
	    }
	} catch (IOException ex) {
	    Log.e(TAG, "Error in http request/response while getting persons", ex);
	} catch (JSONException ex) {
	    Log.e(TAG, "Persons json parsing error", ex);
	}
	
	return persons;
    }
    
    public List<Good> getGoods() {
	List<Good> goods = new ArrayList<Good>();
	
	try {
	    InputStream is = HttpHelper.getResponse(new URL(URL_API_GENERAL + URL_API_GOODS));
	    String response = StreamHelper.makeString(is);
	    

	    JSONArray entries = new JSONArray(response);
	    for (int i = 0; i < entries.length(); i++) {
		JSONObject jsonObject = entries.getJSONObject(i);
		goods.add(JsonParser.parseGood(jsonObject));
	    }
	} catch (IOException ex) {
	    Log.e(TAG, "Error in http request/response while getting goods", ex);
	} catch (JSONException ex) {
	    Log.e(TAG, "Goods json parsing error", ex);
	}
	
	return goods;
    }
    
    public List<Order> getOrders() {
	List<Order> orders = new ArrayList<Order>();
	
	try {
	    InputStream is = HttpHelper.getResponse(new URL(URL_API_GENERAL + URL_API_ORDERS));
	    String response = StreamHelper.makeString(is);
	    

	    JSONArray entries = new JSONArray(response);
	    for (int i = 0; i < entries.length(); i++) {
		JSONObject jsonObject = entries.getJSONObject(i);
		
		for (Order order : JsonParser.parsePersonOrder(jsonObject)) {
		    orders.add(order);
		}
	    }
	} catch (IOException ex) {
	    Log.e(TAG, "Error in http request/response while getting goods", ex);
	} catch (JSONException ex) {
	    Log.e(TAG, "Goods json parsing error", ex);
	}
	
	return orders;
    }
}

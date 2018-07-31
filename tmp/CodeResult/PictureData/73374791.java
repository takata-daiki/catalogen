/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tooon.dynamiclivewallpaper;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ConfigDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.tooon.dynamiclivewallpaper.model");

        addPictureData(schema);
        addCustomerOrder(schema);

        new DaoGenerator().generateAll(schema, "../DynamicLiveWallpaper/src-gen");
    }

    private static void addPictureData(Schema schema) {
    	Entity rule = schema.addEntity("Rule");
    	
        Entity pictureData = schema.addEntity("PictureData");
        pictureData.addIdProperty();
        pictureData.addStringProperty("filename").notNull();
        pictureData.addBooleanProperty("local");
        pictureData.addLongProperty("reloadTime");
        Property ruleId = pictureData.addLongProperty("ruleId").getProperty();
        
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }

}

# takehome-test-microservice

# List of Service
1. discovery-server : Service Eureka Discovery
2. api-gateway : Untuk Routing services
3. product-service : Service untuk menambahkan product dan melihat daftar product
4. order-service : Service untuk membuat order
5. inventory-service : Service untuk cek inventory

# Stack
1. Java JDK 8
2. Spring Boot 2.7.18
3. PostgreSQL
4. MongoDB
5. Kafka

# Step to Run
1. Jalankan query berikut pada postgreSQL local/server
   - Create Database inventory-service
     ```bash
		CREATE DATABASE "inventory-service"
	  	WITH 
      	OWNER = postgres
    		ENCODING = 'UTF8'
    		LC_COLLATE = 'English_United States.1252'
    		LC_CTYPE = 'English_United States.1252'
    		TABLESPACE = pg_default
    		CONNECTION LIMIT = -1;
	   ```
   - Create table t_inventory pada database inventory-service
     ```bash
			CREATE TABLE IF NOT EXISTS public.t_inventory
			(
			    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
			    quantity integer,
			    sku_code character varying(255) COLLATE pg_catalog."default",
			    CONSTRAINT t_inventory_pkey PRIMARY KEY (id)
			)
			
			TABLESPACE pg_default;
			
			ALTER TABLE IF EXISTS public.t_inventory
			    OWNER to postgres;
	   ```
   - Create Database order-service
     ```bash
		CREATE DATABASE "order-service"
		    WITH 
		    OWNER = postgres
		    ENCODING = 'UTF8'
		    LC_COLLATE = 'English_United States.1252'
		    LC_CTYPE = 'English_United States.1252'
		    TABLESPACE = pg_default
		    CONNECTION LIMIT = -1;
	   ```
2. Jalankan script berikut pada MongoDB local/server
   ```bash
	 	> use product-service
   	> db.createCollection('product')
	  ```
4. Run local Zookeeper dan Kafka pada local dengan port default 9092
5. Run secara terurut dari discovery-server, api-gateway, product-service, inventory-service, order-service
6. discovery-server running pada port 8761 & api-gateway pada port 8000

# List API
1. Create product (stock pada inventory akan terisi 100 secara default)
   ```http
   POST http://localhost:8000/api/product 
    {
        "name": "Samsung S24",
        "description": "Samsung S24 desc",
        "price": 1300,
        "skuCode": "samsung_s24"
    }
   ```
2. Get All Product
   ```http
   GET http://localhost:8000/api/product 
   ```
4. Place Order (terdapat validasi untuk cek stock pada inventory berasarkan skuCode)
   ```http
   POST http://localhost:8091/api/order
    {
        "orderLineItemsDtoList": [
            {
                "skuCode": "samsung_s24",
                "price": 1200,
                "quantity": 2
            }
        ]
    }
   ```
5. Get stock in inventory by skuCode
   ```http
   GET http://localhost:8000/api/inventory?skuCode=samsung_s24 
   ```

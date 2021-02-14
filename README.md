# oms-admin-service
Services related to OMS Admin functionalities 

1. Start the MariaDB docker container - 
`docker run --name db -e MYSQL_ROOT_PASSWORD=my-secret-pw -p 3306:3306 mariadb:10.5.8`
   
2. Initialize with [schema.sql](./src/test/resources/schema.sql) file

# Lead

****************************************************************************************************
HOW TO START LEAD ADMIN
****************************************************************************************************

1. UNZIP lead-on-jetty-9.4.2.v20170220.zip in your C:\ drive

2. launch start.bat in the main (jetty-distribution-9.4.2.v20170220) folder

service is running on localhost:8080


***************************************************************************************************
HOW TO STOP LEAD ADMIN
***************************************************************************************************

1. close the batch window

service is stopped


***************************************************************************************************
HOW TO CHANGE THE DEFAULT PORT
***************************************************************************************************

1. open start.bat with a text editor

2. change last 4 digits (by default 8080) to a desired port

3. save the start.bat file

4. restart the service

port is changed


***************************************************************************************************
SERVICE DETAILS
***************************************************************************************************

1. Lead Admin pulls data from 4 tables in the datasource Control Panel: LeadContact, 
LeadOrganization, LeadRemoteAddress and leadSession

2. Data is collected by Logger (https://github.com/nailyusupov/Logger)

3. Before deploying/making changes make sure the datasource package has connection string
credentials

# takehome



## Setup

 1. Clone the repo
	 `git clone https://github.com/sourabhbhavsar/takehome.git`
 2.  Build the docker image	
	 `docker build --tag=takehome:latest .`
 3. Run the container
	 `docker run -p8080:8080 takehome:latest`
	  
	  

## Testing
#### Unsecured Endpoint: **Rate limited by 5 requests per second**

 `curl -X POST \
  http://localhost:8080/unsecured_countries_in_same_continent \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 8ec880dd-fdf6-06eb-32e6-c0b26df37c10' \
  -d '{
"country_codes": ["IN", "MX"]
}'`

#### Secured Endpoint: **Rate limited by 20 requests per second**

`curl -X POST \
  http://localhost:8080/secured_countries_in_same_continent \
  -H 'authorization: Basic dGVzdDp0ZXN0' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 920a9387-dc43-a051-585b-b130c5374b92' \
  -d '{
"country_codes": ["CA", "US"]
}'`

## NOTE
The API is secured by spring security using `http basic` and the user = test and password = test and can be changed in `resources/application.yml` file. 

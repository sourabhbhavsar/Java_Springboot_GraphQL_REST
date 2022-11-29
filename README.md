# A Java Springboot based REST API project that interfaces with a public GraphQL [API](https://github.com/trevorblades/countries) for country data.

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/sourabhbhavsar/takehome/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/sourabhbhavsar/takehome/tree/main)
[![Known Vulnerabilities](https://snyk.io/test/github/sourabhbhavsar/takehome/badge.svg)](https://snyk.io/test/github/sourabhbhavsar/takehome)
[![Maintainability](https://api.codeclimate.com/v1/badges/165a69f8f7a5136e1db5/maintainability)](https://codeclimate.com/github/sourabhbhavsar/takehome/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/165a69f8f7a5136e1db5/test_coverage)](https://codeclimate.com/github/sourabhbhavsar/takehome/test_coverage)

## Setup

 1. Clone the repo
	 `git clone https://github.com/sourabhbhavsar/takehome.git`
 2.  Build the docker image	
	 `docker build --tag=takehome:latest .`
 3. Run the container
	 `docker run -p8080:8080 takehome:latest`
	  
	  

## Testing
#### Unsecured Endpoint: **Rate limited by 5 requests per second**

 `curl -X POST  http://localhost:8080/unsecured_countries_in_same_continent  -H 'content-type: application/json'  -d '{ "country_codes": ["IN", "MX"] }'`

Response
```
[
    {
        "name": "Asia",
        "countries": [
            "IN"
        ],
        "otherCountries": [
            "AE",
            "AF",
            "AM",
            "AZ",
            "BD",
            "BH",
            "BN",
            "BT",
            "CC",
            "CN",
            "CX",
            "GE",
            "HK",
            "ID",
            "IL",
            "IO",
            "IQ",
            "IR",
            "JO",
            "JP",
            "KG",
            "KH",
            "KP",
            "KR",
            "KW",
            "KZ",
            "LA",
            "LB",
            "LK",
            "MM",
            "MN",
            "MO",
            "MV",
            "MY",
            "NP",
            "OM",
            "PH",
            "PK",
            "PS",
            "QA",
            "SA",
            "SG",
            "SY",
            "TH",
            "TJ",
            "TM",
            "TR",
            "TW",
            "UZ",
            "VN",
            "YE"
        ]
    },
    {
        "name": "North America",
        "countries": [
            "MX"
        ],
        "otherCountries": [
            "AG",
            "AI",
            "AW",
            "BB",
            "BL",
            "BM",
            "BQ",
            "BS",
            "BZ",
            "CA",
            "CR",
            "CU",
            "CW",
            "DM",
            "DO",
            "GD",
            "GL",
            "GP",
            "GT",
            "HN",
            "HT",
            "JM",
            "KN",
            "KY",
            "LC",
            "MF",
            "MQ",
            "MS",
            "NI",
            "PA",
            "PM",
            "PR",
            "SV",
            "SX",
            "TC",
            "TT",
            "US",
            "VC",
            "VG",
            "VI"
        ]
    }
]
```


#### Secured Endpoint: **Rate limited by 20 requests per second**

`curl -X POST  http://localhost:8080/secured_countries_in_same_continent -H 'authorization: Basic dGVzdDp0ZXN0' -H 'content-type: application/json' -d '{ "country_codes": ["CA", "US"] }'`

Response
```
[
    {
        "name": "North America",
        "countries": [
            "CA",
            "US"
        ],
        "otherCountries": [
            "AG",
            "AI",
            "AW",
            "BB",
            "BL",
            "BM",
            "BQ",
            "BS",
            "BZ",
            "CR",
            "CU",
            "CW",
            "DM",
            "DO",
            "GD",
            "GL",
            "GP",
            "GT",
            "HN",
            "HT",
            "JM",
            "KN",
            "KY",
            "LC",
            "MF",
            "MQ",
            "MS",
            "MX",
            "NI",
            "PA",
            "PM",
            "PR",
            "SV",
            "SX",
            "TC",
            "TT",
            "VC",
            "VG",
            "VI"
        ]
    }
]
```

## NOTE
The API is secured by spring security using `http basic` and the user = test and password = test and can be changed in `resources/application.yml` file. 

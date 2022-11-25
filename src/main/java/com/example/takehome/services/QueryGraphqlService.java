    package com.example.takehome.services;

    import com.example.takehome.models.Continent;
    import com.example.takehome.models.GraphqlRequestBody;
    import com.example.takehome.utils.GraphqlSchemaReaderUtil;
    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;
    import org.springframework.beans.factory.annotation.Value;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.web.reactive.function.client.WebClient;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    @Service
    @Slf4j
    public class QueryGraphqlService {
        private final String url;

        public QueryGraphqlService(@Value("https://countries.trevorblades.com/") String url) {
            this.url = url;
        }


        public List<Continent> getCountryDetails(JSONObject countriesJson) throws IOException, JSONException {

            WebClient webClient = WebClient.builder().build();

            GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

            final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getCountries");
            final String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("getCountries_variables");

            String var = getContinents(countriesJson.toString());
            graphQLRequestBody.setQuery(query);
            //graphQLRequestBody.setVariables(variables.replace("countryCode", countryCode));
            graphQLRequestBody.setVariables(var);

            String response =  webClient.post()
                    .uri(url)
                    .bodyValue(graphQLRequestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject responseJson = new JSONObject(response);
            List<Continent> continentList = new ArrayList<>();
            JSONArray continentsJsonArray = responseJson.getJSONObject("data").getJSONArray("continents");
            JSONArray requestCountriesJsonArray = countriesJson.getJSONArray("country_codes");
            Set<String> countriesInRequestSet = new HashSet<>();
            for (int k = 0; k < requestCountriesJsonArray.length(); k++) {
                countriesInRequestSet.add(requestCountriesJsonArray.getString(k));
            }

            for (int i = 0; i < continentsJsonArray.length(); i++) {
                JSONObject continentJson = continentsJsonArray.getJSONObject(i);
                Continent continent = new Continent();
                continent.setName(continentJson.getString("name"));

                JSONArray countriesJsonArray = continentJson.getJSONArray("countries");
                for (int j = 0; j < countriesJsonArray.length(); j++) {
                    JSONObject countryJsonObject = countriesJsonArray.getJSONObject(j);
                    String currentCountry = countryJsonObject.getString("code");
                    if (countriesInRequestSet.contains(currentCountry)) {
                        continent.addCountry(currentCountry);
                    }
                    else {
                        continent.addOtherCountry(currentCountry);
                    }
                }

                continentList.add(continent);
            }

            return continentList;
        }

        public String getContinents(final String countryCodes) throws IOException, JSONException {

            WebClient webClient = WebClient.builder().build();

            GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();
            log.info("countryCodes = " + countryCodes);
            final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getContinents");
            final String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("getContinents_variables");

            graphQLRequestBody.setQuery(query);
            graphQLRequestBody.setVariables(countryCodes);

            String resp =  webClient.post()
                    .uri(url)
                    .bodyValue(graphQLRequestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JSONObject responseJson = new JSONObject(resp);
            JSONObject ret = new JSONObject();
            List<String> continents = new ArrayList<>();
            JSONArray respContinentArray = responseJson.getJSONObject("data").getJSONArray("countries");
            for (int i = 0; i < respContinentArray.length(); i++) {
                JSONObject continentObject = respContinentArray.getJSONObject(i);
                continents.add(continentObject.getJSONObject("continent").getString("code"));
            }

            ret.put("code", continents);
            return ret.toString();
        }
    }

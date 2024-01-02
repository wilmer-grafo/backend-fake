package bo.gob.segip.micro_servicios.controllers;

import bo.gob.segip.micro_servicios.dao.CountryDAO;
import bo.gob.segip.micro_servicios.model.Country;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryAPIController {

    private final CountryDAO countryDao;

//    http://localhost:9090/api/countries?search=Virgin&continent=North%20America

    @GetMapping
    public ResponseEntity<?> getCountries(
            @RequestParam(name = "search", required = false) String searchTerm,
            @RequestParam(name = "continent", required = false) String continent,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "pageNo", required = false) Integer pageNo
    ) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("search", searchTerm);
            params.put("continent", continent);
            params.put("region", region);
            if (pageNo != null) {
                params.put("pageNo", pageNo.toString());
            }

            List<Country> countries = countryDao.getCountries(params);
            Map<String, Object> response = new HashMap<>();
            response.put("list", countries);
            response.put("count", countryDao.getCountriesCount(params));
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            System.out.println("Error while getting countries" + ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while getting countries");
        }
    }

    @PostMapping(value = "/{countryCode}",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editCountry(
            @PathVariable String countryCode, @Valid @RequestBody Country country) {
        try {
            countryDao.editCountryDetail(countryCode, country);
            Country countryFromDb = countryDao.getCountryDetail(countryCode);
            return ResponseEntity.ok(countryFromDb);
        } catch (Exception ex) {
            System.out.println("Error while editing the country: {} with data: {}" + countryCode + country + ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while ediiting the country");
        }
    }

}

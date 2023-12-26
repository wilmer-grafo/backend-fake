package bo.gob.segip.micro_servicios.controller;

import bo.gob.segip.micro_servicios.dao.CityDAO;
import bo.gob.segip.micro_servicios.model.City;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityAPIController {

    private final CityDAO cityDao;

    @GetMapping("/{countryCode}")
    public ResponseEntity<?> getCities(@PathVariable String countryCode,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        try {
            return new ResponseEntity<>(cityDao.getCities(countryCode, pageNo),
                    HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println("Error while getting cities for country: {}" +
                    countryCode + ex);
            return new ResponseEntity<>("Error while getting cities",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/{countryCode}")
    public ResponseEntity<?> addCity(@PathVariable String countryCode,
                                     @Valid @RequestBody City city) {
        try {
            cityDao.addCity(countryCode, city);
            return new ResponseEntity<>(city, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println("Error while adding city: {} to country: {}" +
                    city + countryCode + ex);
            return new ResponseEntity<>("Error while adding city",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable Long cityId) {
        try {
            cityDao.deleteCity(cityId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            System.out.println("Error occurred while deleting city : {}" + cityId + ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the city: " + cityId);
        }
    }

}

package bo.gob.segip.micro_servicios.controllers;

import bo.gob.segip.micro_servicios.dao.CountryLanguageDAO;
import bo.gob.segip.micro_servicios.model.CountryLanguage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class CountryLanguageAPIController {

    private final CountryLanguageDAO cLanguageDao;

    @GetMapping("/{countryCode}")
    public ResponseEntity<?> getLanguages(@PathVariable String countryCode,
                                          @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        try {
            return ResponseEntity.ok(cLanguageDao.getLanguages(countryCode, pageNo));
        } catch (Exception ex) {
            System.out.println("Error while getting languages for country: {}" +
                    countryCode + ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while languages cities");
        }

    }

    @PostMapping("/{countryCode}")
    public ResponseEntity<?> addLanguage(@PathVariable String countryCode,
                                         @Valid @RequestBody CountryLanguage language) {
        try {
            if (cLanguageDao.languageExists(countryCode, language.getLanguage())) {
                return ResponseEntity.badRequest()
                        .body("Language already exists for country");
            }
            cLanguageDao.addLanguage(countryCode, language);
            return ResponseEntity.ok(language);
        } catch (Exception ex) {
            System.out.println("Error while adding language: {} to country: {}" +
                    language + countryCode + ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while adding language");
        }
    }

    @DeleteMapping("/{countryCode}/language/{language}")
    public ResponseEntity<?> deleteLanguage(@PathVariable String countryCode,
                                            @PathVariable String language) {
        try {
            cLanguageDao.deleteLanguage(countryCode, language);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            System.out.println("Error occurred while deleting language : {}, for country: {}" +
                    language + countryCode + ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the language");
        }
    }

}

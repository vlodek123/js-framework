package cz.vt.jsframeworks.web;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cz.vt.jsframeworks.entity.Framework;

import cz.vt.jsframeworks.service.FrameworkService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/framework")
public class FrameworkController {

    FrameworkService frameworkService;

    @GetMapping("/{id}")
    public ResponseEntity<Framework> getFramework(@PathVariable Long id) {
        return new ResponseEntity<>(frameworkService.getFramework(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Framework>> getFrameworks() {
        return new ResponseEntity<>(frameworkService.getFrameworks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Framework> saveFramework(@Valid @RequestBody Framework framework) {
        return new ResponseEntity<>(frameworkService.saveFramework(framework), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Framework> updateFramework(@Valid @RequestBody Framework framework, @PathVariable Long id) {       
        return new ResponseEntity<>(frameworkService.updateFramework(framework, id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Framework> deleteFramework(@PathVariable Long id) {
        frameworkService.deleteFramework(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/hl/{id}")
    public ResponseEntity<Framework> updateHypeLevel(@PathVariable Long id, @Valid @RequestParam("hypelevel") Long hypeLevel) {        
        return new ResponseEntity<>(frameworkService.updateHypeLevel(id, hypeLevel), HttpStatus.OK);
    }

    @PatchMapping("/dd/{id}")
    public ResponseEntity<Framework> updateDeprecationDate(@PathVariable Long id, @RequestParam("deprecationdate") String deprecationDate) {                
        return new ResponseEntity<>(frameworkService.updateDeprecationDate(id, LocalDate.parse(deprecationDate)), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Framework> updateHypeLevelAndDeprecationDate(@PathVariable Long id, @Valid @RequestParam("hypelevel") Long hypeLevel, @RequestParam("deprecationdate") String deprecationDate) {
        return new ResponseEntity<>(frameworkService.updateHypeLevelAndDeprecationDate(id, hypeLevel, LocalDate.parse(deprecationDate)), HttpStatus.OK);
    }

    @GetMapping("/searchstring")
    public ResponseEntity<List<Framework>> getFrameworkByString(@RequestParam("infix") String infix) {
        return new ResponseEntity<>(frameworkService.getFrameworkByString(infix), HttpStatus.OK);
    }

    @GetMapping("/searchhlgte")
    public ResponseEntity<List<Framework>> getByHypeLevelGreaterThanEqual(@Valid @RequestParam("hypelevel") Long hypeLevel) {    
        return new ResponseEntity<>(frameworkService.getByHypeLevelGreaterThanEqual(hypeLevel), HttpStatus.OK);
    }

    @GetMapping("/searchhllte")
    public ResponseEntity<List<Framework>> getByHypeLevelLessThanEqual(@Valid @RequestParam("hypelevel") Long hypeLevel) {    
        return new ResponseEntity<>(frameworkService.getByHypeLevelLessThanEqual(hypeLevel), HttpStatus.OK);
    }
    
}

package com.prototype.restdocopenapi;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moo")
public class MooController {
	
    @Autowired
    MooRepository repository;

    @GetMapping
    public ResponseEntity<List<Moo>> getAllMoos() {
        List<Moo> mooList = (List<Moo>) repository.findAll();
        if (mooList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mooList, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Moo> getMooById(@PathVariable("id") Long id) {

        Optional<Moo> moo = repository.findById(id);
        return moo.isPresent() ? new ResponseEntity<>(moo.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Moo> addMoo(@RequestBody @Valid Moo moo) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(MooController.class).slash(moo.getId())
            .toUri());
        Moo savedMoo;
        try {
        	savedMoo = repository.save(moo);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(savedMoo, httpHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMoo(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moo> updateMoo(@PathVariable("id") long id, @RequestBody Moo moo) {
        boolean isMooPresent = repository.existsById(Long.valueOf(id));

        if (!isMooPresent) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Moo updatedMoo = repository.save(moo);

        return new ResponseEntity<>(updatedMoo, HttpStatus.OK);
    }
}

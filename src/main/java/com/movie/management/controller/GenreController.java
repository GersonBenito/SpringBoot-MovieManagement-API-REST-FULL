package com.movie.management.controller;

import com.movie.management.controller.DTO.GenreDTO;
import com.movie.management.entity.Genre;
import com.movie.management.service.IGenreService;
import com.movie.management.util.ValidFieldUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/genre")
public class GenreController {

    @Autowired
    private IGenreService genreService;

    @Autowired
    private ValidFieldUtil validFieldUtil;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> findAll(){
        Map<String, Object> response = new HashMap<>();
        List<GenreDTO> genreList = genreService.findAll()
                .stream().map(genre -> GenreDTO.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .build()
                ).toList();
        response.put("Data", genreList);
        response.put("message", "Datos obtenidos");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("message", "id no proporcionado");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Genre> genreOptional = genreService.findById(id);
        if(genreOptional.isPresent()){
            Genre genre = genreOptional.get();
            GenreDTO genreDTO = GenreDTO.builder()
                    .id(genre.getId())
                    .name(genre.getName())
                    .build();

            response.put("Data", genreDTO);
            response.put("message", "Registro obtenido");

            return ResponseEntity.ok(response);
        }

        response.put("message", "Genero con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>save(@RequestBody GenreDTO genreDTO){
        Map<String, Object> response = new HashMap<>();
        try {
            if(validFieldUtil.isInvalidField(genreDTO)){
                response.put("message", "Uno o varios campos estan vacios");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

            genreService.save(
                    Genre.builder()
                            .name(genreDTO.getName())
                            .build()
            );

            response.put("message", "Registro creado correctamente");
            response.put("url", new URI("/api/v1/genre"));

            return ResponseEntity.ok(response);
        }catch (URISyntaxException e){
            response.put("message", "Ocurrio un error interno");
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        Map<String,Object>response = new HashMap<>();
        if(id == null){
            response.put("message", "Id no proporcionado");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Genre> genreOptional = genreService.findById(id);
        if(genreOptional.isPresent()){
            genreService.deleteById(id);
            response.put("message", "Registro eliminado");
            return ResponseEntity.ok(response);
        }

        response.put("message", "Registro con "+id+" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>update(@RequestBody GenreDTO genreDTO, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();

        if(id == null){
            response.put("message", "id no proporcionado");
            new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        if(validFieldUtil.isInvalidField(genreDTO)){
            response.put("message", "Uno o varios campos estan vacios");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Genre> genreOptional = genreService.findById(id);

        if(genreOptional.isPresent()){
            Genre genre = genreOptional.get();
            genre.setName(genreDTO.getName());

            genreService.save(genre);

            response.put("message", "Actualizado correctamente");

            return ResponseEntity.ok(response);
        }

        response.put("message", "Genero con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }
}

package com.movie.management.controller;

import com.movie.management.controller.DTO.MovieDTO;
import com.movie.management.entity.Movie;
import com.movie.management.persistence.IMovieDAO;
import com.movie.management.service.IMovieService;
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
@RequestMapping("/api/v1/movie")
public class MovieController {

    @Autowired
    private IMovieService movieService;
    
    @Autowired
    private IMovieDAO movieDAO;

    @Autowired
    private ValidFieldUtil validFieldUtil;

    @GetMapping // esta ruta debe de ser publico debido a que se les presentara a los usuarios
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public ResponseEntity<Map<String, Object>>findAll(){
        Map<String, Object>response = new HashMap<>();
        List<MovieDTO> movieList = movieService.findAll();

        response.put("message", "Registros obtenidos");
        response.put("Data", movieList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}") // esta ruta debe de ser publico debido a que se les presentara a los usuarios
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'INVITED')")
    public ResponseEntity<?>findById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("message", "Id no proporcionado");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<MovieDTO> movieOptional = movieService.findById(id);

        if(movieOptional.isPresent()){
            response.put("message", "Registro obtenido");
            response.put("Data", movieOptional.get());

            return ResponseEntity.ok(response);
        }

        response.put("message", "Movie con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>save(@RequestBody MovieDTO movieDTO){
        Map<String, Object> response = new HashMap<>();
        try{
            if(validFieldUtil.isInvalidField(movieDTO)){
                response.put("message", "Uno o varios campos estan vacios");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

			movieService.save(movieDTO);

            response.put("message", "Registro guardado");
            response.put("url", new URI("/api/v1/movie"));
            return ResponseEntity.ok(response);

        }catch (URISyntaxException e){
            response.put("message", "Error interno");
            response.put("error", e.getStackTrace());
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("message", "Id no proporcionado");
            new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> movieOptional = movieDAO.findById(id);

        if(movieOptional.isPresent()){
            movieService.deleteById(id);

            response.put("message", "Registro eliminado");
            return ResponseEntity.ok(response);
        }

        response.put("message", "Registro con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMovie(@RequestBody MovieDTO movieDTO, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("message", "Id no proporcionado");
            new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> movieOptional = movieDAO.findById(id);
        
        if(movieOptional.isPresent()){
            Movie movie = MovieDTO.Movie(movieDTO, movieOptional.get());
            movieDAO.save(movie);
            
            response.put("message", "Registro actualizado");
            return ResponseEntity.ok(response);
        }

        response.put("message", "Registro con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    // TODO: Agregar busqueda por titulo, popularidad, etc, y filtrar por categoria

}

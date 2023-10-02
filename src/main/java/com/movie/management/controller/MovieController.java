package com.movie.management.controller;

import static com.movie.management.util.StringUtil.DATA;
import static com.movie.management.util.StringUtil.MESSAGE;
import static java.util.Collections.singletonMap;

//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.aspectj.util.Reflection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie.management.controller.DTO.MovieDTO;
import com.movie.management.controller.DTO.MultiparamMovieDTO;
import com.movie.management.entity.Movie;
import com.movie.management.persistence.IMovieDAO;
import com.movie.management.service.IMovieService;
import com.movie.management.util.ValidFieldUtil;


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

        response.put(MESSAGE, "Registros obtenidos");
        response.put(DATA, movieList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}") // esta ruta debe de ser publico debido a que se les presentara a los usuarios
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'INVITED')")
    public ResponseEntity<?>findById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put(MESSAGE, "Id no proporcionado");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<MovieDTO> movieOptional = movieService.findById(id);

        if(movieOptional.isPresent()){
            response.put(MESSAGE, "Registro obtenido");
            response.put(DATA, movieOptional.get());

            return ResponseEntity.ok(response);
        }

        response.put(MESSAGE, "Movie con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>save(@RequestBody MovieDTO movieDTO){
        Map<String, Object> response = new HashMap<>();
        try{
            if(validFieldUtil.isInvalidField(movieDTO)){
                response.put(MESSAGE, "Uno o varios campos estan vacios");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

			movieService.save(movieDTO);

            response.put(MESSAGE, "Registro guardado");
            response.put("url", new URI("/api/v1/movie"));
            return ResponseEntity.ok(response);

        }catch (URISyntaxException e){
            response.put(MESSAGE, "Error interno");
            response.put("error", e.getStackTrace());
            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put(MESSAGE, "Id no proporcionado");
            new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> movieOptional = movieDAO.findById(id);

        if(movieOptional.isPresent()){
            movieService.deleteById(id);

            response.put(MESSAGE, "Registro eliminado");
            return ResponseEntity.ok(response);
        }

        response.put(MESSAGE, "Registro con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateMovie(@RequestBody MovieDTO movieDTO, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put(MESSAGE, "Id no proporcionado");
            new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }
        
        Optional<Movie> movieOptional = movieDAO.findById(id);
        
        if(movieOptional.isPresent()){
            Movie movie = MovieDTO.Movie(movieDTO, movieOptional.get());
            movieDAO.save(movie);
            
            response.put(MESSAGE, "Registro actualizado");
            return ResponseEntity.ok(response);
        }

        response.put(MESSAGE, "Registro con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }
    
    
    @GetMapping("/custom")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'INVITED')")
    public ResponseEntity<?> findByCustom(@RequestParam(required=false) Map<String, String> map,
    		@RequestParam(required = false) Boolean any) {
    	
//    	System.out.println(any);
    	map.remove("any");
//    	System.out.println(map.size());
//    	if(!noString(title))
//    		paramCount++;
    	if(map.isEmpty())
			return new ResponseEntity<Object>(singletonMap(MESSAGE, "You must provide at least one query param"),
					HttpStatus.BAD_REQUEST);
    	
    	if(map.size()>1 && any==null)
    		return new ResponseEntity<Object>(singletonMap(MESSAGE, "You must provide any=true or false for params>1"),
					HttpStatus.BAD_REQUEST);
    	
    	if(map.size()==1 && map.get("id")!=null)
    		return new ResponseEntity<Object>(singletonMap(MESSAGE, "Use findBy API"),
					HttpStatus.BAD_REQUEST);
    	
    	map.put("any", String.valueOf(any));
 
    	return ResponseEntity.ok(singletonMap(DATA,movieService.getMoviesCustom(map)));
    }


    // TODO: Agregar busqueda por titulo, popularidad, etc, y filtrar por categoria

}

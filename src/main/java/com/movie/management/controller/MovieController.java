package com.movie.management.controller;

import com.movie.management.controller.DTO.MovieDTO;
import com.movie.management.entity.Movie;
import com.movie.management.service.IMovieService;
import com.movie.management.util.EType;
import com.movie.management.util.RangePrice;
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
    private ValidFieldUtil validFieldUtil;

    @GetMapping // esta ruta debe de ser publico debido a que se les presentara a los usuarios
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public ResponseEntity<Map<String, Object>>findAll(){
        Map<String, Object>response = new HashMap<>();
        List<MovieDTO> movieList = movieService.findAll()
                .stream().map(movie -> MovieDTO.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .description(movie.getDescription())
                        .posterImage(movie.getPosterImage())
                        .backgroundImage(movie.getBackgroundImage())
                        .popularity(movie.getPopularity())
                        .rentalPrice(movie.getRentalPrice())
                        .purchasePrice(movie.getPurchasePrice())
                        .availability(movie.isAvailability()) // lombok agrega el get de esta forma isAvailability
                        .stock(movie.getStock())
                        .genres(movie.getGenres())
                        .build()
                ).toList();

        response.put("message", "Registros obtenidos");
        response.put("Data", movieList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public ResponseEntity<?> searchByParam(@RequestParam String param){
        Map<String, Object> response = new HashMap<>();
        if(param.isBlank()){
            response.put("message","Param search is blank");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }
        List<MovieDTO> movieList = movieService.searchByParam(param)
                .stream().map(movie -> MovieDTO.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .description(movie.getDescription())
                        .posterImage(movie.getPosterImage())
                        .backgroundImage(movie.getBackgroundImage())
                        .popularity(movie.getPopularity())
                        .rentalPrice(movie.getRentalPrice())
                        .purchasePrice(movie.getPurchasePrice())
                        .availability(movie.isAvailability()) // lombok agrega el get de esta forma isAvailability
                        .stock(movie.getStock())
                        .genres(movie.getGenres())
                        .build()
                ).toList();
        response.put("message", "Records obtained");
        response.put("Data", movieList);
        response.put("filter_type", EType.SEARCH);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/filter-price")
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public ResponseEntity<?> filterByRangePrice(@RequestBody RangePrice rangePrice){
        Map<String, Object> response = new HashMap<>();
        if(rangePrice.minPrice == null || rangePrice.maxPrice == null){
            response.put("message","Params min price or max price is blank");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        List<MovieDTO>movieList = movieService.filterByRangePrice(rangePrice.minPrice, rangePrice.maxPrice, rangePrice.type)
                .stream().map(movie -> MovieDTO.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .description(movie.getDescription())
                        .posterImage(movie.getPosterImage())
                        .backgroundImage(movie.getBackgroundImage())
                        .popularity(movie.getPopularity())
                        .rentalPrice(movie.getRentalPrice())
                        .purchasePrice(movie.getPurchasePrice())
                        .availability(movie.isAvailability()) // lombok agrega el get de esta forma isAvailability
                        .stock(movie.getStock())
                        .genres(movie.getGenres())
                        .build()
                ).toList();

        EType typeFiler = rangePrice.type == EType.PURCHASE ? EType.PURCHASE : EType.RENTAL;

        response.put("message", "Records obtained");
        response.put("Data", movieList);
        response.put("filter_type", typeFiler);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN','USER','INVITED')")
    public ResponseEntity<?> filterByGenre(@RequestParam String genre){
        Map<String, Object> response = new HashMap<>();
        if(genre.isBlank()){
            response.put("message", "Param genre is blank");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        List<MovieDTO>movieList = movieService.filterByGenre(genre)
                .stream().map(movie -> MovieDTO.builder()
                        .id(movie.getId())
                        .title(movie.getTitle())
                        .description(movie.getDescription())
                        .posterImage(movie.getPosterImage())
                        .backgroundImage(movie.getBackgroundImage())
                        .popularity(movie.getPopularity())
                        .rentalPrice(movie.getRentalPrice())
                        .purchasePrice(movie.getPurchasePrice())
                        .availability(movie.isAvailability()) // lombok agrega el get de esta forma isAvailability
                        .stock(movie.getStock())
                        .genres(movie.getGenres())
                        .build()
                ).toList();

        response.put("message", "Records obtained");
        response.put("Data", movieList);
        response.put("filter_type", EType.SEARCH);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}") // esta ruta debe de ser publico debido a que se les presentara a los usuarios
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'INVITED')")
    public ResponseEntity<?>findById(@PathVariable Long id){
        Map<String, Object>response = new HashMap<>();
        if(id == null){
            response.put("message", "Id no proporcionado");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> movieOptional = movieService.findById(id);

        if(movieOptional.isPresent()){
            Movie movie = movieOptional.get();
            MovieDTO movieDTO = MovieDTO.builder()
                    .id(movie.getId())
                    .title(movie.getTitle())
                    .description(movie.getDescription())
                    .posterImage(movie.getPosterImage())
                    .backgroundImage(movie.getBackgroundImage())
                    .popularity(movie.getPopularity())
                    .rentalPrice(movie.getRentalPrice())
                    .purchasePrice(movie.getPurchasePrice())
                    .availability(movie.isAvailability())
                    .stock(movie.getStock())
                    .genres(movie.getGenres())
                    .build();

            response.put("message", "Registro obtenido");
            response.put("Data", movieDTO);

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
            if(validFieldUtil.isValidField(movieDTO)){
                response.put("message", "Uno o varios campos estan vacios");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

            movieService.save(
                    Movie.builder()
                            .title(movieDTO.getTitle())
                            .description(movieDTO.getDescription())
                            .posterImage(movieDTO.getPosterImage())
                            .backgroundImage(movieDTO.getBackgroundImage())
                            .popularity(movieDTO.getPopularity())
                            .rentalPrice(movieDTO.getRentalPrice())
                            .purchasePrice(movieDTO.getPurchasePrice())
                            .availability(movieDTO.isAvailability())
                            .stock(movieDTO.getStock())
                            .genres(movieDTO.getGenres())
                            .build()
            );

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

        Optional<Movie> movieOptional = movieService.findById(id);

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

        Optional<Movie> movieOptional = movieService.findById(id);
        if(movieOptional.isPresent()){

            Movie movie = getMovie(movieDTO, movieOptional);

            movieService.save(movie);

            response.put("message", "Registro actualizado");

            return ResponseEntity.ok(response);
        }

        response.put("message", "Registro con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    // TODO: Agregar busqueda por titulo, popularidad, etc, y filtrar por categoria


    /**
     * Method for set values each field to entity Movie
     * @param movieDTO DTO
     * @param movieOptional Entity Movie
     * @return Entity Movie
     */
    private static Movie getMovie(MovieDTO movieDTO, Optional<Movie> movieOptional) {

        Movie movie = movieOptional.get();

        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setPosterImage(movieDTO.getPosterImage());
        movie.setBackgroundImage(movieDTO.getBackgroundImage());
        movie.setPopularity(movieDTO.getPopularity());
        movie.setRentalPrice(movieDTO.getRentalPrice());
        movie.setPurchasePrice(movieDTO.getPurchasePrice());
        movie.setAvailability(movieDTO.isAvailability());
        movie.setStock(movieDTO.getStock());
        movie.setGenres(movieDTO.getGenres());

        return movie;
    }
}

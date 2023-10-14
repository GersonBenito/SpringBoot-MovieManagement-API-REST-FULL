package com.movie.management.controller;

import com.movie.management.controller.DTO.StockDTO;
import com.movie.management.entity.Stock;
import com.movie.management.service.IStockService;

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
@RequestMapping("/api/v1/stock")
public class StockController {

    @Autowired
    private IStockService stockService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>>findAll(){
        Map<String, Object> response = new HashMap<>();
        List<StockDTO> stockList = stockService.findAll()
                .stream().map(stock -> StockDTO.builder()
                        .id(stock.getId())
                        .amount(stock.getAmount())
                        .build()
                ).toList();

        response.put("Data", stockList);
        response.put("message", "Datos obtenidos");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>findById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("message", "id no proporcionado");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Stock> stockOptional = stockService.findById(id);
        if(stockOptional.isPresent()){
            Stock stock = stockOptional.get();
            StockDTO stockDTO = StockDTO.builder()
                    .id(stock.getId())
                    .amount(stock.getAmount())
                    .build();

            response.put("Data", stockDTO);
            response.put("message", "Registro obtenido");

            return ResponseEntity.ok(response);
        }

        response.put("message", "Stock con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>save(@RequestBody StockDTO stockDTO){
        Map<String, Object> response = new HashMap<>();
        try {
            if(stockDTO.getAmount() == null){
                response.put("message", "Uno o varios campos estan vacios");
                return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
            }

            stockService.save(
                    Stock.builder()
                            .amount(stockDTO.getAmount())
                            .build()
            );

            response.put("message", "Registro creado correctamente");
            response.put("url", new URI("/api/v1/stock"));

            return ResponseEntity.ok(response);
        }catch (URISyntaxException e){
            response.put("message", "Ocurrio un error interno");
            response.put("error", e.getMessage());

            return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("message", "id no proporcionado");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Stock> stockOptional = stockService.findById(id);
        if(stockOptional.isPresent()){
            response.put("message", "Registro eliminado");
            stockService.deleteById(id);
            return ResponseEntity.ok(response);
        }

        response.put("message", "Stock con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>updateStock(@RequestBody StockDTO stockDTO, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        if(id == null){
            response.put("message", "Id no proporcionado");
            return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Stock> stockOptional = stockService.findById(id);
        if(stockOptional.isPresent()){
            Stock stock = stockOptional.get();
            stock.setAmount(stockDTO.getAmount());

            stockService.save(stock);

            response.put("message", "Stock actualizado correctamente");

            return ResponseEntity.ok(response);
        }

        response.put("message", "Stock con id "+ id +" no encontrado");
        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }
}

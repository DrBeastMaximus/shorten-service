package com.topsquad.shortenservice.controller;

import com.topsquad.shortenservice.entity.ShortURL;
import com.topsquad.shortenservice.model.PreviousShortenResponse;
import com.topsquad.shortenservice.model.ShortenRequest;
import com.topsquad.shortenservice.service.ShortenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/url")
public class ShortenController {
    @Autowired
    private ShortenService shortenService;

    @PostMapping
    public ResponseEntity<String> shorten(@RequestBody ShortenRequest shortenRequest){
        try {
            String code = shortenService.shortenUrl(shortenRequest.getUrl(), shortenRequest.getToken());
            return new ResponseEntity<>(code, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/s/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        try {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .header("Location", shortenService.lookUpUrl(code))
                    .build();
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUrl(@PathVariable String id){
        try {
            shortenService.removeUrl(Integer.parseInt(id));
            return new ResponseEntity<>("URL Has been removed from server!", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUrlOfId(@RequestParam String token){
        try {
            List<PreviousShortenResponse> listData = shortenService.listPrevious(token);
            return new ResponseEntity<>(listData, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

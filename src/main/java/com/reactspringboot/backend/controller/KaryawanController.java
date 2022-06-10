package com.reactspringboot.backend.controller;


import com.reactspringboot.backend.exception.ResourceNotFoundException;
import com.reactspringboot.backend.model.Karyawan;
import com.reactspringboot.backend.repository.KaryawanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class KaryawanController {

    @Autowired
    private KaryawanRepository karyawanRepository;

    // get all karyawan
    @GetMapping("/karyawans")
    public List<Karyawan> getAllKaryawans(){
        return karyawanRepository.findAll();
    }

    // create karyawan rest api
    @PostMapping("/karyawans")
    public Karyawan createKaryawan(@RequestBody Karyawan karyawan) {
        return karyawanRepository.save(karyawan);
    }

    // get karyawan by id rest api
    @GetMapping("/karyawans/{id}")
    public ResponseEntity<Karyawan> getKaryawanById(@PathVariable Long id) {
        Karyawan karyawan = karyawanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID Karyawan ini tidak cocok :" + id));
        return ResponseEntity.ok(karyawan);
    }

    // update karyawan rest api
    @PutMapping("/karyawans/{id}")
    public ResponseEntity<Karyawan> updateKaryawan(@PathVariable Long id, @RequestBody Karyawan detailKaryawan){
        Karyawan karyawan = karyawanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID Karyawan ini tidak cocok :" + id));

        karyawan.setFirstName(detailKaryawan.getFirstName());
        karyawan.setLastName(detailKaryawan.getLastName());
        karyawan.setEmailId(detailKaryawan.getEmailId());

        Karyawan updateKaryawans = karyawanRepository.save(karyawan);
        return ResponseEntity.ok(updateKaryawans);
    }

    // delete karyawan rest api
    @DeleteMapping("/karyawans/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteKaryawan(@PathVariable Long id){
        Karyawan karyawan = karyawanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID Karyawan ini tidak cocok :" + id));

        karyawanRepository.delete(karyawan);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

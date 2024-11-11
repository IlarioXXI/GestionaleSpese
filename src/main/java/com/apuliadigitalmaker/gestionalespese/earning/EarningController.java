package com.apuliadigitalmaker.gestionalespese.earning;

import com.apuliadigitalmaker.gestionalespese.common.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/earning")
public class EarningController {
    @Autowired
    private EarningService earningService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllEarning() {
        try {
            List<Earning> earnings = earningService.findAllEarnings();

            if (earnings.isEmpty()) {
                return ResponseBuilder.notFound("Earning not found");
            } else {
                return ResponseBuilder.success(earnings);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addEarning(@RequestBody Earning earning) {
        try {
            Earning newEarning = earningService.saveEarning(earning);
            return ResponseBuilder.success(newEarning);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error();
        }
    }

}

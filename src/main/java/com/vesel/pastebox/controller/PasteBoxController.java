package com.vesel.pastebox.controller;


import com.vesel.pastebox.api.request.PasteBoxRequest;
import com.vesel.pastebox.api.response.PasteBoxResponse;
import com.vesel.pastebox.api.response.PasteBoxUrlResponse;
import com.vesel.pastebox.service.PasteBoxServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasteBoxController {
    private final PasteBoxServiceImpl pasteBoxService;

    @GetMapping("/{hash}")
    public PasteBoxResponse getByHash(@PathVariable String hash) {
        return  pasteBoxService.getByHash(hash);
    }

    @GetMapping("/")
    public List<PasteBoxResponse> getPublicPasteList() {

        return pasteBoxService.getFirstPublicPastebox();
    }

    @PostMapping("/")
    public PasteBoxUrlResponse add(@RequestBody PasteBoxRequest request) {
        return pasteBoxService.create(request);
    }

}

package com.vesel.pastebox.service;

import com.vesel.pastebox.api.request.PasteBoxRequest;
import com.vesel.pastebox.api.response.PasteBoxResponse;
import com.vesel.pastebox.api.response.PasteBoxUrlResponse;

import java.util.List;

public interface PasteBoxService {
    PasteBoxResponse getByHash (String hash);

    List<PasteBoxResponse> getFirstPublicPastebox();

    PasteBoxUrlResponse create(PasteBoxRequest request);

}

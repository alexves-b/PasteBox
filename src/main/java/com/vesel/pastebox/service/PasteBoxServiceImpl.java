package com.vesel.pastebox.service;

import com.vesel.pastebox.api.request.PasteBoxRequest;
import com.vesel.pastebox.api.request.PublicStatus;
import com.vesel.pastebox.api.response.PasteBoxResponse;
import com.vesel.pastebox.api.response.PasteBoxUrlResponse;
import com.vesel.pastebox.repository.PasteBoxEntity;
import com.vesel.pastebox.repository.PasteBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
public class PasteBoxServiceImpl implements PasteBoxService {

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPublicListSize() {
        return publicListSize;
    }

    public void setPublicListSize(int publicListSize) {
        this.publicListSize = publicListSize;
    }

    private String host;
    private int publicListSize;
    private final PasteBoxRepository pasteBoxRepository;
    private AtomicInteger idGenerator = new AtomicInteger(0);
    @Override
    public PasteBoxResponse getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = pasteBoxRepository.getByHash(hash);
        return new PasteBoxResponse(pasteBoxEntity.getData(),pasteBoxEntity.isPublic());
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPastebox() {

        List <PasteBoxEntity> list = pasteBoxRepository.getListOfPuslicAndAlive(publicListSize);
        return list.stream().map(pasteBoxEntity ->

            new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic()
        )).collect(Collectors.toList());


    }

    @Override
    public PasteBoxUrlResponse create(PasteBoxRequest request) {
        int hash = generateId();
        PasteBoxEntity pasteBoxEntity = new PasteBoxEntity();
        pasteBoxEntity.setData(request.getData());
        pasteBoxEntity.setId(hash);
        pasteBoxEntity.setHash(Integer.toHexString(hash));
        pasteBoxEntity.setPublic(request.getPublicStatus()== PublicStatus.PUBLIC);
        pasteBoxEntity.setLifeTime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        pasteBoxRepository.add(pasteBoxEntity);
       return new PasteBoxUrlResponse(host + "/" + pasteBoxEntity.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}

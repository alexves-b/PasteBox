package com.vesel.pastebox.repository;

import com.vesel.pastebox.exception.NotFoundEntityException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class PasteBoxRepositoryMap implements  PasteBoxRepository{

    private final Map<String, PasteBoxEntity> valut = new ConcurrentHashMap<>();
    @Override
    public PasteBoxEntity getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = valut.get(hash);

        if(pasteBoxEntity ==null) {
            throw  new NotFoundEntityException("PasteBox not found with hash=" + hash);
        }
        return pasteBoxEntity;
    }

    @Override
    public List<PasteBoxEntity> getListOfPuslicAndAlive(int amount) {
        LocalDateTime now = LocalDateTime.now();

        //фильтруем 10  результатов, прося вывести время жизни, сравниваем, что время лайфтайма после сейчас
        //Сортируя по id в обратном порядке и все это выводя в лист.

        return valut.values().stream()
                .filter(PasteBoxEntity::isPublic)
                .filter(pasteBoxEntity -> pasteBoxEntity.getLifeTime()
                .isAfter(now)).sorted(Comparator.comparing(PasteBoxEntity::getId).reversed())
                .limit(amount).collect(Collectors.toList());
    }

    @Override
    public void add(PasteBoxEntity pasteBoxEntity) {
        valut.put(pasteBoxEntity.getHash(), pasteBoxEntity);
    }
}

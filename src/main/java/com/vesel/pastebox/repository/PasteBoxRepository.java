package com.vesel.pastebox.repository;

import java.util.List;

public interface PasteBoxRepository {
PasteBoxEntity getByHash(String hash);
List<PasteBoxEntity> getListOfPuslicAndAlive(int amount);

void add(PasteBoxEntity pasteBoxEntity);
}

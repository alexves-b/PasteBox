package com.vesel.pastebox;

import com.vesel.pastebox.api.response.PasteBoxResponse;
import com.vesel.pastebox.exception.NotFoundEntityException;
import com.vesel.pastebox.repository.PasteBoxEntity;
import com.vesel.pastebox.repository.PasteBoxRepository;
import com.vesel.pastebox.service.PasteBoxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PasteBoxServiceTest {

    @Autowired
    private PasteBoxService pasteBoxService;

    @MockBean
    private PasteBoxRepository pasteBoxRepository;
    @Test
    public void notExistHash() {
        when(pasteBoxRepository.getByHash(anyString())).thenThrow(NotFoundEntityException.class);
        assertThrows(NotFoundEntityException.class, ( ) -> pasteBoxService.getByHash("fef43g4"));
    }


    @Test
    public void getExistHash(){
        PasteBoxEntity entity = new PasteBoxEntity();
        entity.setHash("1");
        entity.setData("f34f43");
        entity.setPublic(true);
        when(pasteBoxRepository.getByHash("1")).thenReturn(entity);
        PasteBoxResponse expected = new PasteBoxResponse("f34f43",true);
        PasteBoxResponse actual = pasteBoxService.getByHash("1");

        assertEquals(actual,expected);
    }
}

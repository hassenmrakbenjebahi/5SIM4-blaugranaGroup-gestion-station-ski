package tn.esprit.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.controllers.PisteRestController;
import tn.esprit.spring.entities.Color;
import tn.esprit.spring.entities.Piste;
import tn.esprit.spring.services.IPisteServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PisteRestControllerMockTest {

    @Mock
    IPisteServices pisteServices;

    @InjectMocks
    PisteRestController pisteController;

    // Sample data
    Piste piste1 = new Piste(1L, "Easy Track", Color.GREEN, 500, 15, null);
    Piste piste2 = new Piste(2L, "Challenging Track", Color.RED, 800, 30, null);
    List<Piste> listPistes = new ArrayList<Piste>() {{
        add(piste1);
        add(piste2);
    }};

    @Test
    public void testGetAllPistes() {
        Mockito.when(pisteServices.retrieveAllPistes()).thenReturn(listPistes);

        List<Piste> pistes = pisteController.getAllPistes();

        Assertions.assertNotNull(pistes);
        Assertions.assertEquals(2, pistes.size());
        Assertions.assertEquals("Easy Track", pistes.get(0).getNamePiste());
    }

    @Test
    public void testGetById() {
        Mockito.when(pisteServices.retrievePiste(1L)).thenReturn(piste1);

        Piste retrievedPiste = pisteController.getById(1L);

        Assertions.assertNotNull(retrievedPiste);
        Assertions.assertEquals(1L, retrievedPiste.getNumPiste());
        Assertions.assertEquals(Color.GREEN, retrievedPiste.getColor());
    }

    @Test
    public void testAddPiste() {
        Mockito.when(pisteServices.addPiste(Mockito.any(Piste.class))).thenReturn(piste1);

        Piste addedPiste = pisteController.addPiste(piste1);

        Assertions.assertNotNull(addedPiste);
        Assertions.assertEquals("Easy Track", addedPiste.getNamePiste());
        Assertions.assertEquals(Color.GREEN, addedPiste.getColor());
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(pisteServices).removePiste(1L);

        pisteController.deleteById(1L);

        Mockito.verify(pisteServices, Mockito.times(1)).removePiste(1L);
    }
}
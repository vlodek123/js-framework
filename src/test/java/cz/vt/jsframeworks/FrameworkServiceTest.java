package cz.vt.jsframeworks;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import cz.vt.jsframeworks.entity.Framework;
import cz.vt.jsframeworks.exception.FrameworkNotFoundException;
import cz.vt.jsframeworks.repository.FrameworkRepository;
import cz.vt.jsframeworks.service.FrameworkServiceImpl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class FrameworkServiceTest {
    
    @Mock
    private FrameworkRepository frameworkRepository;

    @InjectMocks
    private FrameworkServiceImpl frameworkService;


    @Test
    public void getFrameworksTest() {
        when(frameworkRepository.findAll()).thenReturn(Arrays.asList(
            new Framework("Angular", "4", 10L, LocalDate.parse("2025-07-31")),
			new Framework("React", "18.2.0", 8L, LocalDate.parse("2024-07-31")),
			new Framework("React", "17.0.1", 7L, LocalDate.parse("2024-07-31")),
			new Framework("React", "18.0.0", 6L, LocalDate.parse("2024-07-31")),
			new Framework("Meteor", "2.7.3", 5L, LocalDate.parse("2024-07-31")),
			new Framework("Mithril", "2.1.0", 4L, LocalDate.parse("2024-07-31")) 
        ));

        List<Framework> frameworks = frameworkService.getFrameworks();

        assertEquals("Angular", frameworks.get(0).getFrameworkName());
        assertEquals(LocalDate.parse("2024-07-31"), frameworks.get(5).getDeprecationDate());

    }

    @Test
    public void getFrameworkTest() {
        when(frameworkRepository.findById(1L)).thenReturn(Optional.of(
            new Framework("Angular", "4", 10L, LocalDate.parse("2025-07-31"))			
        ));

        assertEquals("4", frameworkService.getFramework(1L).getVersion());
    }

    @Test
    public void saveFrameworkTest() {
        
        Framework newFramework = new Framework("Mithril", "2.1.0", 4L, LocalDate.parse("2024-07-31"));
        frameworkService.saveFramework(newFramework);
        verify(frameworkRepository, times(1)).save(newFramework);
    }

    @Test
    public void updateFrameworkTest() {
        Framework framework = new Framework("Angular", "4", 10L, LocalDate.parse("2025-07-31"));
        Optional<Framework> optionalFramework =  Optional.of(new Framework("Angular", "4", 10L, LocalDate.parse("2025-07-31")));        
       
        when(frameworkRepository.findById(1L)).thenReturn(optionalFramework);

        framework.setHypeLevel(1L);
        frameworkService.updateFramework(framework, 1L);
        verify(frameworkRepository, times(1)).save(framework);
    }

    @Test
    public void updateHypeLevelTest() {
        Framework framework = new Framework("Angular", "4", 10L, LocalDate.parse("2025-07-31"));
        framework.setId(1L);
        Optional<Framework> optionalFramework =  Optional.of(framework);        
       
        when(frameworkRepository.findById(1L)).thenReturn(optionalFramework);
               
        frameworkService.updateHypeLevel(1L, 1L);
        
        verify(frameworkRepository, atLeastOnce()).save(framework);

    }

    @Test
    public void updateDeprecationDateTest() {
        Framework framework = new Framework("Angular", "4", 10L, LocalDate.parse("2025-07-31"));
        framework.setId(1L);
        Optional<Framework> optionalFramework =  Optional.of(framework);        

        when(frameworkRepository.findById(1L)).thenReturn(optionalFramework);
               
        frameworkService.updateDeprecationDate(1L, LocalDate.parse("2030-07-31"));
        
        verify(frameworkRepository, times(1)).save(framework);

    }

    @Test
    public void updateHypeLevelAndDeprecationDateTest() {
        Framework framework = new Framework("Angular", "4", 10L, LocalDate.parse("2025-07-31"));
        framework.setId(1L);
        Optional<Framework> optionalFramework =  Optional.of(framework);        
       
        when(frameworkRepository.findById(1L)).thenReturn(optionalFramework);
               
        frameworkService.updateHypeLevelAndDeprecationDate(1L, 1L, LocalDate.parse("2030-07-31"));
        
        verify(frameworkRepository, times(1)).save(framework);
    }

    @Test
    public void unwrapFrameworkSuccessTest() {
        Framework framework = new Framework("Angular", "4", 10L, LocalDate.parse("2025-07-31"));
        framework.setId(1L);
        Optional<Framework> optionalFramework =  Optional.of(framework);     

        assertEquals(1L, frameworkService.unwrapFramework(optionalFramework, 1L).getId());        
    }

    @Test
    public void unwrapFrameworkFailTest() {
        Optional<Framework> optionalFramework = Optional.ofNullable(null);
        
        assertThrows(FrameworkNotFoundException.class, () -> frameworkService.unwrapFramework(optionalFramework, 1L));
    }

    @Test
    public void getFrameworkByStringTest() {
        when(frameworkRepository.findByFrameworkNameContaining("ea")).thenReturn(Arrays.asList(            
			new Framework("React", "18.2.0", 8L, LocalDate.parse("2024-07-31")),
			new Framework("React", "17.0.1", 7L, LocalDate.parse("2024-07-31")),
			new Framework("React", "18.0.0", 6L, LocalDate.parse("2024-07-31"))			
        ));

        List<Framework> frameworks = frameworkService.getFrameworkByString("ea");

        assertEquals(3, frameworks.size());
        assertEquals(7L, frameworks.get(1).getHypeLevel());
    }


    @Test
    public void deleteFrameworkTest() {
        
        doNothing().when(frameworkRepository).deleteById(1L);

        frameworkService.deleteFramework(1L);

        verify(frameworkRepository, times(1)).deleteById(1L);        
        
    }

    @Test
    public void getByHypeLevelGreaterThanEqualTest() {
        when(frameworkRepository.findByHypeLevelGreaterThanEqual(6L)).thenReturn(Arrays.asList(            
			new Framework("React", "18.2.0", 8L, LocalDate.parse("2024-07-31")),
			new Framework("React", "17.0.1", 7L, LocalDate.parse("2024-07-31")),
			new Framework("React", "18.0.0", 6L, LocalDate.parse("2024-07-31"))			
        ));

        List<Framework> frameworks = frameworkService.getByHypeLevelGreaterThanEqual(6L);

        assertEquals(3, frameworks.size());
    }


    @Test
    public void getByHypeLevelLessThanEqualTest() {
        when(frameworkRepository.findByHypeLevelLessThanEqual(3L)).thenReturn(Arrays.asList(            
			new Framework("React", "18.2.0", 1L, LocalDate.parse("2024-07-31")),
			new Framework("React", "17.0.1", 2L, LocalDate.parse("2024-07-31"))			
        ));

        List<Framework> frameworks = frameworkService.getByHypeLevelLessThanEqual(3L);

        assertEquals(2, frameworks.size());
    }

}

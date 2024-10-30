package tn.esprit.spring.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.RegistrationServicesImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RegistrationServicesImplTest {

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);  // Initialise les objets Mock
    }

    @Test
    void testAddRegistrationAndAssignToSkierAndCourse_Success() {
        // Arrange
        Long skierId = 1L;
        Long courseId = 1L;
        Registration registration = new Registration();
        registration.setNumWeek(1);

        Skier skier = new Skier();
        skier.setNumSkier(skierId);
        skier.setDateOfBirth(LocalDate.of(2000, 1, 1));  // Skier âgé de 24 ans

        Course course = new Course();
        course.setNumCourse(courseId);
        course.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);

        // Simule les réponses des méthodes des repositories
        when(skierRepository.findById(skierId)).thenReturn(Optional.of(skier));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(
                anyInt(), anyLong(), anyLong())).thenReturn(0L);
        when(registrationRepository.countByCourseAndNumWeek(course, registration.getNumWeek())).thenReturn(2L);
        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, skierId, courseId);

        // Assert
        assertNotNull(result);
        assertEquals(result.getSkier(), skier);
        assertEquals(result.getCourse(), course);
        verify(registrationRepository, times(1)).save(registration);
    }

    @Test
    void testAddRegistrationAndAssignToSkierAndCourse_FullCourse() {
        // Arrange
        Long skierId = 1L;
        Long courseId = 1L;
        Registration registration = new Registration();
        registration.setNumWeek(1);

        Skier skier = new Skier();
        skier.setNumSkier(skierId);
        skier.setDateOfBirth(LocalDate.of(2000, 1, 1));  // Skier âgé de 24 ans

        Course course = new Course();
        course.setNumCourse(courseId);
        course.setTypeCourse(TypeCourse.COLLECTIVE_ADULT);

        // Simule les réponses des méthodes des repositories
        when(skierRepository.findById(skierId)).thenReturn(Optional.of(skier));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(registrationRepository.countDistinctByNumWeekAndSkier_NumSkierAndCourse_NumCourse(
                anyInt(), anyLong(), anyLong())).thenReturn(0L);
        when(registrationRepository.countByCourseAndNumWeek(course, registration.getNumWeek())).thenReturn(6L); // Course full

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, skierId, courseId);

        // Assert
        assertNull(result);  // La méthode doit renvoyer null si le cours est plein
        verify(registrationRepository, times(0)).save(any(Registration.class)); // Aucune sauvegarde ne doit se produire
    }

    @Test
    void testAddRegistrationAndAssignToSkierAndCourse_SkierOrCourseNull() {
        // Arrange
        Long skierId = 1L;
        Long courseId = 1L;
        Registration registration = new Registration();
        registration.setNumWeek(1);

        // Simule que le skieur n'existe pas
        when(skierRepository.findById(skierId)).thenReturn(Optional.empty());

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, skierId, courseId);

        // Assert
        assertNull(result); // La méthode doit renvoyer null si le skieur n'existe pas
        verify(registrationRepository, times(0)).save(any(Registration.class)); // Aucune sauvegarde ne doit se produire
    }
}

package tn.esprit.spring.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
public class InstructorServicesImplTest {
    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInstructor() {
        Instructor instructor = new Instructor(1L, "hassen", "mrak", LocalDate.now(), new HashSet<>());

        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorServices.addInstructor(instructor);
        assertNotNull(savedInstructor);
        assertEquals("hassen", savedInstructor.getFirstName());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testRetrieveAllInstructors() {
        instructorServices.retrieveAllInstructors();
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void testUpdateInstructor() {
        Instructor instructor = new Instructor(1L, "hassen", "mrak", LocalDate.now(), new HashSet<>());

        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);
        assertNotNull(updatedInstructor);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testRetrieveInstructor() {
        Long instructorId = 1L;
        Instructor instructor = new Instructor(instructorId, "hassen", "mrak", LocalDate.now(), new HashSet<>());

        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));

        Instructor retrievedInstructor = instructorServices.retrieveInstructor(instructorId);
        assertNotNull(retrievedInstructor);
        assertEquals(instructorId, retrievedInstructor.getNumInstructor());
        verify(instructorRepository, times(1)).findById(instructorId);
    }

    @Test
    void testAddInstructorAndAssignToCourse() {
        Long courseId = 1L;
        Course course = new Course();
        course.setNumCourse(courseId);

        Instructor instructor = new Instructor(1L, "hassen", "mrak", LocalDate.now(), new HashSet<>());

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, courseId);

        assertNotNull(savedInstructor);
        assertTrue(savedInstructor.getCourses().contains(course));
        verify(courseRepository, times(1)).findById(courseId);
        verify(instructorRepository, times(1)).save(instructor);
    }
}

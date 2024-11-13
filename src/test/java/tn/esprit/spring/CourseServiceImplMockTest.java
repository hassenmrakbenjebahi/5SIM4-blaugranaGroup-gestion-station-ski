package tn.esprit.spring;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.TypeCourse;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplMockTest {

    @Mock
    ICourseRepository courseRepository;

    @InjectMocks
    CourseServicesImpl courseService;

    // Sample data
    Course course1 = new Course(1L, 1, TypeCourse.COLLECTIVE_CHILDREN, null, 150.0f, 2, null);
    Course course2 = new Course(2L, 2, TypeCourse.INDIVIDUAL, null, 200.0f, 3, null);
    List<Course> listCourses = new ArrayList<Course>() {{
        add(course1);
        add(course2);
    }};

    @Test
    public void testRetrieveAllCourses() {
        Mockito.when(courseRepository.findAll()).thenReturn(listCourses);

        List<Course> courses = courseService.retrieveAllCourses();

        Assertions.assertNotNull(courses);
        Assertions.assertEquals(2, courses.size());
        Assertions.assertEquals(1L, courses.get(0).getNumCourse());
    }

    @Test
    public void testRetrieveCourse() {
        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course1));

        Course retrievedCourse = courseService.retrieveCourse(1L);

        Assertions.assertNotNull(retrievedCourse);
        Assertions.assertEquals(1L, retrievedCourse.getNumCourse());
    }

    @Test
    public void testAddCourse() {
        Mockito.when(courseRepository.save(Mockito.any(Course.class))).thenReturn(course1);

        Course addedCourse = courseService.addCourse(course1);

        Assertions.assertNotNull(addedCourse);
        Assertions.assertEquals(1L, addedCourse.getNumCourse());
    }

    @Test
    public void testUpdateCourse() {
        Mockito.when(courseRepository.save(Mockito.any(Course.class))).thenReturn(course1);

        course1.setLevel(3);
        Course updatedCourse = courseService.updateCourse(course1);

        Assertions.assertNotNull(updatedCourse);
        Assertions.assertEquals(3, updatedCourse.getLevel());
    }
}
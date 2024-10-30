package ro.siit.HRS.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ro.siit.HRS.dto.create.ManagerCreateDto;
import ro.siit.HRS.dto.rturn.ManagerReturnDto;
import ro.siit.HRS.model.Manager;
import ro.siit.HRS.model.User;
import ro.siit.HRS.repository.ManagerRepository;
import ro.siit.HRS.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ManagerRepository managerRepository;
    @InjectMocks
    private ManagerService managerService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createManager() {

        ManagerCreateDto managerCreateDto = new ManagerCreateDto();
        managerCreateDto.setName("Tudor Dumars");
        managerCreateDto.setEmail("tudor_d@yahoo.com");
        managerCreateDto.setCity("Bucuresti");
        managerCreateDto.setPhoneNumber("0733222111");
        managerCreateDto.setNationalId("112211");
        managerCreateDto.setGender("male");
        managerCreateDto.setAddress("Str. Domnica");
        managerCreateDto.setStartDate(LocalDate.of(2023, 12, 3));
        managerCreateDto.setEndDate(LocalDate.of(2027, 11, 11));

        ManagerReturnDto expectedManagerReturnDto = new ManagerReturnDto();
        expectedManagerReturnDto.setAddress("Str. Domnica");
        expectedManagerReturnDto.setEmail("tudor_d@yahoo.com");
        expectedManagerReturnDto.setCity("Bucuresti");
        expectedManagerReturnDto.setGender("male");
        expectedManagerReturnDto.setName("Tudor Dumars");
        expectedManagerReturnDto.setStartDate(LocalDate.of(2023, 12, 3));
        expectedManagerReturnDto.setEndDate(LocalDate.of(2027, 11, 11));
        expectedManagerReturnDto.setNationalId("112211");
        expectedManagerReturnDto.setPhoneNumber("0733222111");

        Manager manager = new Manager();
        manager.setAddress("Str. Domnica");
        manager.setEmail("tudor_d@yahoo.com");
        manager.setCity("Bucuresti");
        manager.setGender("male");
        manager.setName("Tudor Dumars");
        manager.setStartDate(LocalDate.of(2023, 12, 3));
        manager.setEndDate(LocalDate.of(2027, 11, 11));
        manager.setNationalId("112211");
        manager.setPhoneNumber("0733222111");

        Mockito.when(managerRepository.save(any())).thenReturn(manager);

        ManagerReturnDto resultedManagerReturnDto = managerService.createManager(managerCreateDto);
        assertEquals(expectedManagerReturnDto, resultedManagerReturnDto);


    }

    @Test
    void updateManager() {

    }

    @Test
    void deleteManager() {

    }

    @Test
    void getManagerEmployees() {

    }

    @Test
    void getManagerPendingLeaveRequests() {

    }

    @Test
    void getManagerRemainingDays() {

        String username = "maria_u@yahoo.com";
        Integer expectedManagerRemainingDays = 21;

        User user = new User();

        Manager manager = new Manager();
        manager.setAnnualLeaveDays(21);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(managerRepository.findByUser(user)).thenReturn(Optional.of(manager));

        Integer resultedManagerRemainingDays = managerService.getManagerRemainingDays(username);

        assertEquals(expectedManagerRemainingDays, resultedManagerRemainingDays);
    }
}
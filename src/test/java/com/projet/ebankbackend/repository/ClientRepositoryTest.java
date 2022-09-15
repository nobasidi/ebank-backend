package com.projet.ebankbackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.projet.ebankbackend.entities.Client;

//@DataJpaTest
@SpringBootTest
//@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ClientRepositoryTest {


    @Autowired
    private ClientRepository cr;

    @Test
    void testFindByCode() 
    {
        Client c=cr.findByCode("2a581a25-f08c-430c-bba6-a743eb6af5e4");
        assertNotNull(c);
    }

        

    @Test
    void add()
    {
        int rs=Operation.add(10, 20);
        assertEquals(rs, 30);
    }

    static class Operation
    {
        public static int add(int a, int b)
        {
            return a+b;
        }
    }
}

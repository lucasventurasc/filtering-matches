package com.sparknetworks.loveos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class FilteringMatchesApplicationTest {

    private ByteArrayOutputStream fakeSysOut = new ByteArrayOutputStream();
    private PrintStream realSysOut;

    @BeforeEach
    void beforeEach() {
        realSysOut = System.out;
        System.setOut(new PrintStream(fakeSysOut));
    }

    @AfterEach
    void afterEach() {
        System.setOut(realSysOut);
    }

    @Test
    void spring_context_smoke_test() throws IOException {
        int randomAvailablePort = getRandomAvailablePort();

        FilterMatchesApplication.main(new String[] {"--spring.profiles.active=test", "--server.port=" + randomAvailablePort});

        realSysOut.println(fakeSysOut.toString());
        assertThat(fakeSysOut.toString()).contains("The following profiles are active: test");
        assertThat(fakeSysOut.toString()).contains(format("Tomcat started on port(s): %s (http)", randomAvailablePort));
        assertThat(fakeSysOut.toString()).contains("Started FilterMatchesApplication in");
    }

    private int getRandomAvailablePort() throws IOException {
        return new ServerSocket(0).getLocalPort();
    }
}

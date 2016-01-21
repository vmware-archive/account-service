package org.springframework.nanotrader.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@WebAppConfiguration
public class AccountMVCTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAccount() throws Exception {
        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId", is(1)))
                .andExpect(jsonPath("$.openBalance", is(100000.0)));
    }

    @Test
    public void testGetAccountByProfileId() throws Exception {
        mockMvc.perform(get("/accounts/profile/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProfile() throws Exception {
        mockMvc.perform(get("/profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountProfileId", is(1)))
                .andExpect(jsonPath("$.userId", is("test")));
    }

    @Test
    public void testSaveProfileAndAccount() throws Exception {
        AccountProfile p = new AccountProfile();
        p.setUserId("foo" + System.currentTimeMillis());

        RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/profiles/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(p));

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertNotNull(result);

        AccountProfile p2 = (AccountProfile) toObject(result.getResponse().getContentAsString(), AccountProfile.class);
        long pid = p2.getaccountProfileId();

        mockMvc.perform(get("/profiles/" + pid))
                .andExpect(status().isOk());

        Account a = new Account();
        a.setBalance(123f);
        a.setAccountProfile(p2);
        p2.addAccount(a);

        reqBuilder = MockMvcRequestBuilders.post("/profiles/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(p2));

        result = mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertNotNull(result);

        AccountProfile p3 = (AccountProfile) toObject(result.getResponse().getContentAsString(), AccountProfile.class);
        long p3id = p3.getaccountProfileId();

        MvcResult r2 = mockMvc.perform(get("/profiles/" + p3id))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(r2);
        AccountProfile p4 = (AccountProfile) toObject(r2.getResponse().getContentAsString(), AccountProfile.class);
        assertNotNull(p4);
        Account a3 = p4.getAccounts().get(0);
        assertNotNull(a3);
        assertEquals("123.0", "" + a3.getBalance());
    }

    @Test
    public void testSaveProfileAndAccount2() throws Exception {
        AccountProfile p = new AccountProfile();
        p.setUserId("foo");

        Account a = new Account();
        a.setBalance(123f);
        a.setAccountProfile(p);
        p.setAccounts(new ArrayList<Account>());
        p.getAccounts().add(a);

        RequestBuilder reqBuilder = MockMvcRequestBuilders.post("/profiles/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(p));

        MvcResult result = mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertNotNull(result);
        AccountProfile p2 = (AccountProfile) toObject(result.getResponse().getContentAsString(), AccountProfile.class);
        assertNotNull(p2);
        Account a2 = p2.getAccounts().get(0);
        assertNotNull(a2);
        assertEquals("123.0", "" + a2.getBalance());
    }

    private String toJson(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }

    private Object toObject(String s, Class c) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s, c);
    }
}


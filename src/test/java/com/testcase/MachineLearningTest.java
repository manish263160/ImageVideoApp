/*package com.testcase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.imagevideoapp.exception.GenericException;
import com.imagevideoapp.service.UserService;
import com.imagevideoapp.serviceImpl.UserServiceImpl;
import com.imagevideoapp.testconfig.AppConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class MachineLearningTest {

	@Autowired
    @Qualifier("usrsrvc")
    UserService usrsrvc;
	
	@Test
	public void getUserLogin_true() throws GenericException {

        //assert correct type/impl
        assertThat(usrsrvc, instanceOf(UserServiceImpl.class));

        //assert true
        assertThat(usrsrvc.isValid(), is(true));

    }
}
*/
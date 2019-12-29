package controller;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleActionTest {
    @Test
    public void getNameTest()
    {
        ExampleAction exampleAction = new ExampleAction();
        String name = exampleAction.getName();

        Assert.assertEquals("Example Action", name);
    }
}
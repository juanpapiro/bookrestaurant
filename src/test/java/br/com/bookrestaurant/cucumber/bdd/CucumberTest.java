package br.com.bookrestaurant.cucumber.bdd;

import org.junit.Test;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("cucumber/features")
public class CucumberTest {

}

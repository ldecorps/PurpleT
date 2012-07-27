package com.decorps.purple.purpleT;

import org.junit.Test;
import org.junit.runner.RunWith;

import fitnesse.junit.FitNesseSuite;
import fitnesse.junit.FitNesseSuite.DebugMode;
import fitnesse.junit.FitNesseSuite.FitnesseDir;
import fitnesse.junit.FitNesseSuite.Name;
import fitnesse.junit.FitNesseSuite.OutputDir;

@RunWith(FitNesseSuite.class)
@Name("PurpleT")
@FitnesseDir("src/test/resources")
@OutputDir(value = "log/fitnesse")
@DebugMode(true)
public class MidiConnectorFactoryDriverTest {

	@Test
	public void dummy() {

	}
}

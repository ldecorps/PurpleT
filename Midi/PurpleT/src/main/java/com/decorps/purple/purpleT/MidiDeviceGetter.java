package com.decorps.purple.purpleT;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class MidiDeviceGetter {

	public MidiDeviceGetter() {
	}

	public static void listTransmitterDevices() throws MidiUnavailableException {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < infos.length; i++) {
			MidiDevice device = MidiSystem.getMidiDevice(infos[i]);
			if (device.getMaxTransmitters() != 0)
				System.out.println(device.getDeviceInfo().getName().toString()
						+ " has transmitters");
		}
	}

	// should get me my USB MIDI Interface. There are two of them but only one
	// has Transmitters so the if statement should get me the one i want
	public static MidiDevice getInputDevice() throws MidiUnavailableException {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < infos.length; i++) {
			MidiDevice device = MidiSystem.getMidiDevice(infos[i]);
			System.out.println(device.getDeviceInfo().getName());
			if ("Real Time Sequencer" != device.getDeviceInfo().getName()
					&& "Java Sound Synthesizer" != device.getDeviceInfo().getName()
					&& device.getMaxTransmitters() != 0
					) {
				System.out.println(device.getDeviceInfo().getName().toString()
						+ " was chosen");
				return device;
			}
		}
		return null;
	}
}
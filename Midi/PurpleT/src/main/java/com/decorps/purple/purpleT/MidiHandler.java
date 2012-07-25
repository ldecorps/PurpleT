package com.decorps.purple.purpleT;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class MidiHandler {

	public MidiHandler() {

		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (final MidiDevice.Info info : infos) {
			try {
				final MidiDevice device = MidiSystem.getMidiDevice(info);
				final String deviceName = device.getDeviceInfo().toString();
				if (!"KBD/KNOB".equals(deviceName)) {
					System.out.println("Skipping " + deviceName);
					continue;
				}
				device.getTransmitter()
						.setReceiver(
								new DumpReceiver(System.out));
				device.open();
				System.out.println(device.getDeviceInfo() + " Was Opened");
			} catch (MidiUnavailableException e) {
			}
		}
		System.out.println("Exiting from the for");
	}
}
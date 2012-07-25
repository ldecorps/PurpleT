package com.decorps.purple.purpleT;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

public class MidiInputReceiver implements Receiver {
	public String name;

	public MidiInputReceiver(String name) {
		this.name = name;
	}

	public void send(MidiMessage message, long timeStamp) {
		System.out.println(name + " "
				+ DumpReceiver.decodeMessage((ShortMessage) message));
	}

	public void close() {
	}
}

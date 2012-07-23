package com.decorps.purple.purpleT;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

public class MidiConnectorFactoryDriver {

	public static int NO_TIMESTAMP = -1;

	Transmitter transmitter = null;
	Synthesizer synthesizer = null;
	Receiver receiver = null;
	MidiDevice MIDI_IN = null;
	MidiDevice MIDI_OUT = null;

	public void sendAOnChannelForNoteWithVelocity(final String command,
			final int channel, final int note, final int velocity)
			throws InvalidMidiDataException {
		ShortMessage myMsg = new ShortMessage();
		myMsg.setMessage(ShortMessage.NOTE_ON, 1 + channel, note, velocity);
		receiver.send(myMsg, NO_TIMESTAMP);
	}

	public void registerMidiDevice(String midiDeviceName)
			throws MidiUnavailableException {
		if (midiDeviceName.contains("IN")) {
			MIDI_IN = getByName(midiDeviceName);
			MIDI_IN.open();
		}
		if (midiDeviceName.contains("OUT")) {
			MIDI_OUT = getByName(midiDeviceName);
			MIDI_OUT.open();
		}
	}

	private MidiDevice getByName(final String midiDeviceName)
			throws MidiUnavailableException {
		for (final MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			if (midiDeviceName.equals(info.getName())) {
				return MidiSystem.getMidiDevice(info);
			}
		}
		throw new MidiUnavailableException("Cannot find midi device "
				+ midiDeviceName);
	}

	public List<String> listMidiDevices() {
		final List<String> synthInfos = new ArrayList<String>();

		MidiDevice device = null;
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		synthInfos.add("found " + infos.length + " midi devices");
		for (int i = 0; i < infos.length; i++) {
			try {
				device = MidiSystem.getMidiDevice(infos[i]);
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}
			if (device instanceof Synthesizer) {
				synthInfos.add("Synthesizer: " + infos[i].getName());
			}
			if (device instanceof Sequencer) {
				synthInfos.add("Sequencer: " + infos[i].getName());
			} else {
				synthInfos.add(infos[i].getName());
			}
		}
		return synthInfos;
	}

	public void linkTransmitterToLocalSynthReceiver()
			throws MidiUnavailableException {
		synthesizer = MidiSystem.getSynthesizer();
		synthesizer.open();
		receiver = synthesizer.getReceiver();
		transmitter = MIDI_IN.getTransmitter();
		transmitter.setReceiver(receiver);
	}

	public void close() {
		MIDI_IN.close();
		MIDI_OUT.close();
		transmitter.close();
		receiver.close();
		synthesizer.close();
	}
}

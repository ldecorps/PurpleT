package com.decorps.purple.purpleT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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

	Transmitter remoteTransmitter = null;
	Receiver localReceiver = null;
	Transmitter localTransmitter = null;
	Receiver remoteReceiver = null;
	Synthesizer synthesizer = null;
	MidiDevice OUT = null;
	MidiDevice IN = null;
	ByteArrayOutputStream pipeOut = new ByteArrayOutputStream();
	PrintStream new_out = new PrintStream(pipeOut);
	PrintStream old_out = System.out;

	public MidiConnectorFactoryDriver() {

	}

	public void sendAOnChannelForNoteWithVelocity(final String command,
			final int channel, final int note, final int velocity)
			throws InvalidMidiDataException, InterruptedException {
		ShortMessage myMsg = new ShortMessage();
		MidiNote.main(new String[]{OUT.getDeviceInfo().getName(), Integer.toString(note), Integer.toString(velocity), "1000"});
		myMsg.setMessage(ShortMessage.NOTE_ON, channel - 1, note, velocity);
		remoteReceiver.send(myMsg, NO_TIMESTAMP);
		Thread.sleep(1500);
	}

	public void registerIN(String midiDeviceName)
			throws MidiUnavailableException {
		IN = getByName(midiDeviceName);
	}

	public void registerOUT(String midiDeviceName)
			throws MidiUnavailableException {
		OUT = getByName(midiDeviceName);
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

	public List<String> listMidiDevices() throws MidiUnavailableException {
		final List<String> synthInfos = new ArrayList<String>();

		for (MidiDevice.Info info : MidiSystem.getMidiDeviceInfo()) {
			final MidiDevice device = MidiSystem.getMidiDevice(info);
			if (device instanceof Synthesizer) {
				synthInfos.add("Synthesizer: " + info.getName());
			}
			if (device instanceof Sequencer) {
				synthInfos.add("Sequencer: " + info.getName());
			} else {
				synthInfos.add(info.getName() + "(" + info.getDescription()
						+ ")");
			}
		}
		return synthInfos;
	}

	public void linkRemoteTransmitterToLocalReceiver()
			throws MidiUnavailableException {

		remoteTransmitter = selectTransmitterForDevice(IN.getDeviceInfo()
				.getName());
		localReceiver = new DumpReceiver(new_out);
		remoteTransmitter.setReceiver(localReceiver);
	}

	public void linkLocalTransmitterToRemoteReceiver()
			throws MidiUnavailableException {

		localTransmitter = selectTransmitterForDevice("Real Time Sequencer");
		// OUT.open();
		remoteReceiver = selectReceiverForDevice(OUT.getDeviceInfo().getName());
		// remoteReceiver = new DumpReceiver(System.out);
		localTransmitter.setReceiver(remoteReceiver);
	}

	public String listenForSomeMessages() throws MidiUnavailableException,
			InterruptedException {
		IN.open();
		Thread.sleep(100);
		return new String(pipeOut.toByteArray());
	}

	public void close() {
		close(OUT, IN);
	}

	public void close(final MidiDevice... midiDevices) {
		for (final MidiDevice midiDevice : midiDevices) {
			if (null == midiDevice)
				continue;
			if (!midiDevice.isOpen())
				continue;
			midiDevice.close();
		}
	}

	public void tryMidiDeviceGetter() throws IOException,
			MidiUnavailableException {
		MidiDeviceGetter.listTransmitterDevices();
		MidiDevice inputDevice;

		// MidiDeviceGetter.listTransmitterDevices();
		inputDevice = MidiDeviceGetter.getInputDevice();

		// just to make sure that i got the right one
		System.out.println(inputDevice.getDeviceInfo().getName().toString());
		System.out.println(inputDevice.getMaxTransmitters());

		// opening the device
		System.out.println("open inputDevice: "
				+ inputDevice.getDeviceInfo().toString());
		inputDevice.open();
		System.out.println("connect Transmitter to Receiver");

		// Creating a DumpReceiver and setting up the Midi wiring
		Receiver r = new DumpReceiver(System.out);
		Transmitter t = inputDevice.getTransmitter();
		t.setReceiver(r);
		final List<Transmitter> transmitters = inputDevice.getTransmitters();
		for (final Transmitter currentTransmitter : transmitters) {
			currentTransmitter.setReceiver(new DumpReceiver(System.out));
		}
		System.out.println("connected.");
		System.out.println("reading...");
		final int read = System.in.read();
		// at this point the console should print out at least something, as the
		// send method of the receiver should be called when i hit a key on my
		// keyboard
		System.out.println("has read " + read);
		System.out.println("close inputDevice: "
				+ inputDevice.getDeviceInfo().toString());
		inputDevice.close();
		System.out
				.println(("Received " + DumpReceiver.seCount
						+ " sysex messages with a total of "
						+ DumpReceiver.seByteCount + " bytes"));
		System.out
				.println(("Received " + DumpReceiver.smCount
						+ " short messages with a total of "
						+ DumpReceiver.smByteCount + " bytes"));
		System.out
				.println(("Received a total of "
						+ (DumpReceiver.smByteCount + DumpReceiver.seByteCount) + " bytes"));
	}

	public Transmitter selectTransmitterForDevice(final String deviceName)
			throws MidiUnavailableException {
		for (final MidiDevice midiDevice : getDevices()) {
			if (!deviceName.equals(midiDevice.getDeviceInfo().toString())) {
				continue;
			}
			return midiDevice.getTransmitter();
		}
		throw new MidiUnavailableException("Could not find a transmitter for "
				+ deviceName);
	}

	public Receiver selectReceiverForDevice(String deviceName)
			throws MidiUnavailableException {
		for (final MidiDevice midiDevice : getDevices()) {
			if (!deviceName.equals(midiDevice.getDeviceInfo().getName())) {
				continue;
			}
			// for (final Receiver receiver : midiDevice.getReceivers()) {
			// return receiver;
			// }
			System.out.println(midiDevice.getReceiver() + " was chosen");
			return midiDevice.getReceiver();
		}
		throw new MidiUnavailableException("Could not find a receiver for "
				+ deviceName);
	}

	public void doIt() {
		List<MidiDevice> outputDevices = MidiCommon.listDevices(false, true,
				true);

		for (final MidiDevice device : outputDevices) {
			if ("Java Sound Synthesizer".equals(device.getDeviceInfo()
					.getName())
					|| "Real Time Sequencer".equals(device.getDeviceInfo()
							.getName())
					|| "MIDI OUT".equals(device.getDeviceInfo().getName())) {
				continue;
			}
			System.out.println("do it " + device.getDeviceInfo().getName());
			MidiNote.main(new String[] { device.getDeviceInfo().getName(),
					"60", "93", "1000" });
		}
	}

	public List<MidiDevice> getDevices() throws MidiUnavailableException {
		final List<MidiDevice> midiDevices = new ArrayList<MidiDevice>();
		final MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (final MidiDevice.Info info : infos) {
			midiDevices.add(MidiSystem.getMidiDevice(info));
		}
		return midiDevices;
	}

	public void midiHandler() {
		new MidiHandler();

	}
}

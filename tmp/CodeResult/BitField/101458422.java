package torrent.download.peer;

import java.util.ArrayList;

import org.johnnei.utils.JMath;

import torrent.protocol.IMessage;
import torrent.protocol.messages.MessageBitfield;
import torrent.protocol.messages.MessageHave;

public class Bitfield {

	private byte[] bitfield;

	public Bitfield() {
		this(0);
	}

	public Bitfield(int size) {
		bitfield = new byte[size];
	}

	/**
	 * Increases or decreased the bitfield size but it will preserve the old data
	 * 
	 * @param size The new size to grow/shrink to
	 */
	public void setBitfieldSize(int size) {
		if (size != bitfield.length) {
			byte[] newBitfield = new byte[size];
			int maxSize = JMath.min(size, bitfield.length);
			for (int i = 0; i < maxSize; i++) {
				newBitfield[i] = bitfield[i];
			}
			bitfield = newBitfield;
		}
	}

	/**
	 * Override bitfield with a given one
	 * 
	 * @param bitfield The new bitfield
	 */
	public void setBitfield(byte[] bitfield) {
		this.bitfield = bitfield;
	}

	/**
	 * Checks the bitfield if we have the given piece
	 * 
	 * @param pieceIndex the piece to check
	 * @return True if we verified the hash of that piece, else false
	 */
	public boolean hasPiece(int pieceIndex) {
		int byteIndex = pieceIndex / 8;
		int bit = pieceIndex % 8;
		if (byteIndex < bitfield.length) {
			int bitVal = (0x80 >> bit);
			return (bitfield[byteIndex] & bitVal) > 0;
		} else {
			return false;
		}
	}

	/**
	 * Notify that we have the given piece<br/>
	 * This will update the bitfield to bitwise OR the bit to 1
	 * 
	 * @param pieceIndex The piece to add
	 */
	public void havePiece(int pieceIndex) {
		havePiece(pieceIndex, false);
	}

	/**
	 * Notify that we have the given piece<br/>
	 * This will update the bitfield to bitwise OR the bit to 1
	 * 
	 * @param pieceIndex The piece to add
	 * @param mayExpand If the bitfield may grow to fit the new have data
	 */
	public void havePiece(int pieceIndex, boolean mayExpand) {
		int byteIndex = pieceIndex / 8;
		int bit = pieceIndex % 8;
		if (bitfield.length < byteIndex) {
			if (mayExpand) {
				byte[] newBitfield = new byte[byteIndex + 1];
				for (int i = 0; i < bitfield.length; i++) {
					newBitfield[i] = bitfield[i];
				}
				this.bitfield = newBitfield;
			} else {
				return; // Prevent IndexOutOfRange
			}
		}
		bitfield[byteIndex] |= (0x80 >> bit);
	}

	/**
	 * Goes through the bitfield and checks how many pieces the client has
	 * 
	 * @return The amount of pieces the client has
	 */
	public int hasPieceCount() {
		int pieces = bitfield.length * 8;
		int have = 0;
		for (int pieceIndex = 0; pieceIndex < pieces; pieceIndex++) {
			if (hasPiece(pieceIndex)) {
				have++;
			}
		}
		return have;
	}

	/**
	 * Generates the most efficient way to send the peer which pieces we have.<br/>
	 * This comes down to:<Br/>
	 * ((1 + bitfield.length) < (5 * pieceHaveCount)) ? sendBitfield : sendHaveMessage(s)
	 * 
	 * @return A list of messages to send to the client as bitfield
	 */
	public ArrayList<IMessage> getBitfieldMessage() {
		ArrayList<IMessage> messages = new ArrayList<>();
		if (hasPieceCount() > 0) {
			if (bitfield.length + 1 < 5 * hasPieceCount()) {
				messages.add(new MessageBitfield(bitfield));
			} else {
				int pieceIndex = 0;
				for (int i = 0; i < bitfield.length; i++) {
					for (int j = 0; j < 8; j++) {
						if (hasPiece(pieceIndex)) {
							messages.add(new MessageHave(pieceIndex));
						}
						++pieceIndex;
					}
				}
			}
		}
		return messages;
	}

}

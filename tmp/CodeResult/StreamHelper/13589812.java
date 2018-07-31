/**
 *  Copyright 2009 QArks.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 **/

package com.qarks.util.stream;

import java.io.*;

public class StreamHelper {

	private InputStream mInputStream = null;
	private OutputStream mOutputStream = null;
	private int mBufferSize = -1;
	private byte[] mByteBuffer = null;
	private static final int DEFAULT_BUFFER_SIZE = 50*1024;

	public StreamHelper() {
		setBufferSize(DEFAULT_BUFFER_SIZE);
	}

	public int getBufferSize() {
		return mBufferSize;
	}

	public void setBufferSize(int bufferSize) {

		// update he value only if it has effectively changed with respect to the previous value
		if (mBufferSize != bufferSize) {
			mBufferSize = bufferSize;
			mByteBuffer = new byte[mBufferSize];
		}
	}

	public void setCopyStreams(InputStream inputStream, OutputStream outputStream) {
		mInputStream  = inputStream;
		mOutputStream = outputStream;
	}

	public void doCopy(File file, InputStream inputStream, OutputStream outputStream,StreamCopyListener listener) throws IOException {

		setCopyStreams(inputStream, outputStream);
		long totalLength = file.length();

		int length = 0;
		try {
			int nbReadBytes = 0;
			while (nbReadBytes>-1) {
				nbReadBytes = mInputStream.read(mByteBuffer,0,mBufferSize);
				if (nbReadBytes>0) {
					if (length==totalLength && listener!=null){
						listener.onFileCopyEnding(file);
					}
					mOutputStream.write(mByteBuffer, 0, nbReadBytes);
					length+=nbReadBytes;
					if (listener!=null){
						listener.onLengthChanged(nbReadBytes);
					}					
				}
				else if (nbReadBytes==0){
					Thread.sleep(50);
				}
			}
			if (listener!=null){
				listener.onFileCopyEnded(file);
			}
		}
		catch (IOException e) {
			throw e;    // rethrow the exception, cannot do anything at this level
		}
		catch(Exception ex){
			throw new IOException(ex.getMessage());
		}
	}

}


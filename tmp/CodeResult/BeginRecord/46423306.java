/*
 * Copyright (c) 2012 Rob Eden.
 * All Rights Reserved.
 */

package com.starlight.io.hprof;

/**
 * See href="http://java.net/downloads/heap-snapshot/hprof-binary-format.html or
 * the HPROF manual (demo/jvmti/hprof/src/manual.html) delivered with the JDK.
 */
public interface HPROFParseHandler {
	/**
	 * @param format_name       The format name (or version) of the dump file.
	 * @param identifier_size   Size of identifiers in the file.
	 * @param time              Time at which the file was started.
	 */
	public void header( String format_name, int identifier_size, long time );

	/**
	 * Called when the end of file is reached.
	 */
	public void end();


	/**
	 * Called when we are beginning to parse a new record. The order of operations
	 * will be: this method, a more specific tag method (such as {@link #recordHeapDump}),
	 * then {@link #endRecord}.
	 *
	 * @param tag_type              The record type.
	 * @param micros_since_header   Number of microseconds since the initial header.
	 * @param length                Length of the tag data.
	 *
	 * @see #endRecord(byte)
	 */
	public void beginRecord( byte tag_type, long micros_since_header, long length );


	/**
	 * Called when we a finished parsing a record. See {@link #beginRecord} for more
	 * information.
	 *
	 * @param tag_type              The record type.
	 *
	 * @see #beginRecord(byte, long, long)
	 */
	public void endRecord( byte tag_type );


	public void recordStringInUTF8( ID id, String string );

	public void recordLoadClass( int class_serial_number, ID object_id,
		int stack_trace_serial, ID class_name_string_id );

	public void recordUnloadClass( int class_serial_number );

	public void recordStackFrame( ID frame_id, ID method_name_id, ID method_signature_id,
		ID source_file_name_id, int class_serial_number, int line );

	public void recordStackTrace( int trace_serial_number, int thread_serial_number,
		ID[] frame_ids );

	public void recordCPUSamples( long total_number_of_samples, int[] number_of_samples,
		int[] stack_trace_serial_number );

	public void recordControlSettings( int bit_mask_flags, int stack_trace_depth );

	public void recordHeapDump();

	public void recordHeapDumpSegment();

	public void recordHeapDumpEnd();


	public void heapDumpRootUnknown( ID object_id );

	public void heapDumpRootJNIGlobal( ID object_id, ID jni_global_ref_id );

	public void heapDumpRootJNILocal( ID object_id, int thread_serial_number,
		int frame_number_in_trace );

	public void heapDumpRootJavaFrame( ID object_id, int thread_serial_number,
		int frame_number_in_trace );

	public void heapDumpRootNativeStack( ID object_id, int thread_serial_number );

	public void heapDumpRootStickyClass( ID object_id );

	public void heapDumpRootThreadBlock( ID object_id, int thread_serial_number );

	public void heapDumpRootMonitorUsed( ID object_id );

	public void heapDumpRootThreadObject( ID object_id, int thread_serial_number,
		int stack_trace_serial_number );

	public void heapDumpClassDump( ID object_id, int stack_trace_serial_number,
		ID super_class_id, ID class_loader_id, ID signers_object_id,
		ID protection_domain_object_id, long instance_size, int[] constant_pool_indexes,
		BasicType[] constant_pool_types, Object[] constant_pool_values,
		ID[] static_field_names, BasicType[] static_field_types,
		Object[] static_field_values, ID[] instance_field_names,
		BasicType[] instance_field_types );

	public void heapDumpInstanceDump( ID object_id, int stack_trace_serial_number,
		ID class_object_id, long data_length /* data array? */ );

	public void heapDumpArrayDump( ID object_id, int stack_trace_serial_number,
		int number_of_elements, ID array_class_object_id );

	public void headDumpPrimitiveArrayDump( ID object_id, int stack_trace_serial_number,
		int number_of_elements, byte entry_type );
}

package com.mtoolkit.dbf;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

class DbfHeader {

    static final byte SIG_DBASE_III = (byte) 0x03;
    /* DBF structure start here */
    byte signature;              /* 0 */

    byte year;                   /* 1 */

    byte month;                  /* 2 */

    byte day;                    /* 3 */

    int numberOfRecords;         /* 4-7 */

    short headerLength;          /* 8-9 */

    short recordLength;          /* 10-11 */

    short reserv1;               /* 12-13 */

    byte incompleteTransaction;  /* 14 */

    byte encryptionFlag;         /* 15 */

    int freeRecordThread;        /* 16-19 */

    int reserv2;                 /* 20-23 */

    int reserv3;                 /* 24-27 */

    byte mdxFlag;                /* 28 */

    byte languageDriver;         /* 29 */

    short reserv4;               /* 30-31 */

    DbfField[] fieldArray;       /* each 32 bytes */

    byte terminator1;            /* n+1 */

    //byte[] databaseContainer; /* 263 bytes */
	/* DBF structure ends here */

    DbfHeader() {

        this.signature = SIG_DBASE_III;
        this.terminator1 = 0x0D;
    }

    void read(DataInput dataInput) throws IOException {

        signature = dataInput.readByte(); /* 0 */
        year = dataInput.readByte();      /* 1 */
        month = dataInput.readByte();     /* 2 */
        day = dataInput.readByte();       /* 3 */
        numberOfRecords = DbfUtils.readLittleEndianInt(dataInput); /* 4-7 */

        headerLength = DbfUtils.readLittleEndianShort(dataInput); /* 8-9 */
        recordLength = DbfUtils.readLittleEndianShort(dataInput); /* 10-11 */

        reserv1 = DbfUtils.readLittleEndianShort(dataInput);      /* 12-13 */
        incompleteTransaction = dataInput.readByte();           /* 14 */
        encryptionFlag = dataInput.readByte();                  /* 15 */
        freeRecordThread = DbfUtils.readLittleEndianInt(dataInput); /* 16-19 */
        reserv2 = dataInput.readInt();                            /* 20-23 */
        reserv3 = dataInput.readInt();                            /* 24-27 */
        mdxFlag = dataInput.readByte();                           /* 28 */
        languageDriver = dataInput.readByte();                    /* 29 */
        reserv4 = DbfUtils.readLittleEndianShort(dataInput);        /* 30-31 */

        Vector<DbfField> v_fields = new Vector<DbfField>();

        DbfField field = DbfField.createField(dataInput); /* 32 each */
        while (field != null) {

            v_fields.addElement(field);
            field = DbfField.createField(dataInput);
        }

        fieldArray = new DbfField[v_fields.size()];

        for (int i = 0; i < fieldArray.length; i++) {

            fieldArray[i] = v_fields.elementAt(i);
        }
    //System.out.println( "Number of fields: " + fieldArray.length);

    }

    void write(DataOutput dataOutput) throws IOException {

        dataOutput.writeByte(signature);                       /* 0 */

        GregorianCalendar calendar = new GregorianCalendar();
        year = (byte) (calendar.get(Calendar.YEAR) - 1900);
        month = (byte) (calendar.get(Calendar.MONTH) + 1);
        day = (byte) (calendar.get(Calendar.DAY_OF_MONTH));

        dataOutput.writeByte(year);  /* 1 */
        dataOutput.writeByte(month); /* 2 */
        dataOutput.writeByte(day);   /* 3 */

        //System.out.println( "Number of records in O/S: " + numberOfRecords);
        numberOfRecords = DbfUtils.littleEndian(numberOfRecords);
        dataOutput.writeInt(numberOfRecords); /* 4-7 */

        headerLength = findHeaderLength();
        dataOutput.writeShort(DbfUtils.littleEndian(headerLength)); /* 8-9 */

        recordLength = findRecordLength();
        dataOutput.writeShort(DbfUtils.littleEndian(recordLength)); /* 10-11 */

        dataOutput.writeShort(DbfUtils.littleEndian(reserv1)); /* 12-13 */
        dataOutput.writeByte(incompleteTransaction); /* 14 */
        dataOutput.writeByte(encryptionFlag); /* 15 */
        dataOutput.writeInt(DbfUtils.littleEndian(freeRecordThread));/* 16-19 */
        dataOutput.writeInt(DbfUtils.littleEndian(reserv2)); /* 20-23 */
        dataOutput.writeInt(DbfUtils.littleEndian(reserv3)); /* 24-27 */

        dataOutput.writeByte(mdxFlag); /* 28 */
        dataOutput.writeByte(languageDriver); /* 29 */
        dataOutput.writeShort(DbfUtils.littleEndian(reserv4)); /* 30-31 */

        for (int i = 0; i < fieldArray.length; i++) {

            //System.out.println( "Length: " + fieldArray[i].getFieldLength());
            fieldArray[i].write(dataOutput);
        }

        dataOutput.writeByte(terminator1); /* n+1 */
    }

    private short findHeaderLength() {

        return (short) (1 +
                3 +
                4 +
                2 +
                2 +
                2 +
                1 +
                1 +
                4 +
                4 +
                4 +
                1 +
                1 +
                2 +
                (32 * fieldArray.length) +
                1);
    }

    private short findRecordLength() {

        int recordLengthVar = 0;
        for (int i = 0; i < fieldArray.length; i++) {

            recordLengthVar += fieldArray[i].getFieldLength();
        }

        return (short) (recordLengthVar + 1);
    }
}

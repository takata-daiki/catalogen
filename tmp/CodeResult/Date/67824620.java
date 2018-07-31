// Date class to be used in project 2 COP 3503

// Students have to implement the compareTo(..) and clone() methods



public class Date implements Comparable<Date>, Cloneable {

    /*******************

     * PROPERTIES

     *******************/

    private int day, month, year;



    /*******************

     * CONSTRUCTOR(S)

     *******************/

    public Date() {

        // empty constructor

    }



    public Date(int month, int day, int year) {

        this.day = day;

        this.month = month;

        this.year = year;

    }



    // Implementing Comparable

    // Reverse order - if "this" date is smaller return 1, else -1 or 0

    public int compareTo(Date date) {



        /** YOUR CODE GOES HERE ***/



    }



    // Implementing Cloneable

    // Should return a deep copy

    public Object clone() throws CloneNotSupportedException {



        /** YOUR CODE GOES HERE ***/



    }



    /*******************

     * Get/set Methods

     *******************/

    public int getDay() {

        return day;

    }



    public void setDay(int day) {

        this.day = day;

    }



    public int getMonth() {

        return month;

    }



    public void setMonth(int month) {

        this.month = month;

    }



    public int getYear() {

        return year;

    }



    public void setYear(int year) {

        this.year = year;

    }



    /*******************

     * toString

     *******************/

    public String toString() {

        return month + "/" + day + "/" + year;

    }



}

package scp.logica;

public class Vector {

    private double x;
    private double y;

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double magnitud() {
        return Math.sqrt(x * x + y * y);
    }

    public double direccion() {
        double dir = Math.atan2(-y, x);
        if (dir < 0) {
            dir += 2 * Math.PI;
        }
        return dir;
    }

    public static Vector add(Vector v1, Vector v2) {
        return new Vector((v1.getX() + v2.getX()), (v1.getY() + v2.getY()));
    }

    public static Vector sub(Vector v1, Vector v2) {
        return new Vector((v1.getX() - v2.getX()), (v1.getY() - v2.getY()));
    }

    public static Vector div(Vector v1, double A) {
        return new Vector(v1.getX() / A, v1.getY() / A);
    }

    public static Vector mult(Vector v1, double A) {
        return new Vector(v1.getX() * A, v1.getY() * A);
    }
}

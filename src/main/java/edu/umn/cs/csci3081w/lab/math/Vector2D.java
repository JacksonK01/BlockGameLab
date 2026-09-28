package edu.umn.cs.csci3081w.lab.math;

//TODO fill out
public class Vector2D {
    private final double x;
    private final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    //Source: https://stackoverflow.com/questions/48287927/2d-vector-library

    public Vector2D add(Vector2D vec) {
        double newX = this.x + vec.x;
        double newY = this.y + vec.y;
        return new Vector2D(newX, newY);
    }

    public Vector2D subtract(Vector2D vec) {
        double newX = this.x - vec.x;
        double newY = this.y - vec.y;
        return new Vector2D(newX, newY);
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public double magnitude() {
        double d;
        double magnitude;

        d = (Math.pow((this.x), 2) + Math.pow((this.y), 2));

        magnitude = Math.sqrt(d);

        return magnitude;
    }

    public double dotProduct(Vector2D vec) {
        return (this.x * vec.getX()) + (this.y * vec.getY());
    }

    public Vector2D unitNormal2D(){
        double mag = this.magnitude();

        return new Vector2D(x / mag, y / mag);
    }

    public Vector2D rotation(double angle) {
        double newX = (Math.cos(angle) * this.x) - (Math.sin(angle) * this.y);
        double newY = (Math.sin(angle) * this.x) + (Math.cos(angle) * this.y);

        return new Vector2D(newX, newY);
    }

    public Vector2D velocityVector(double speed, double launchAngle) {
        Vector2D vec8 = new Vector2D(Math.cos(launchAngle), Math.sin(launchAngle));

        double vMag = vec8.magnitude();
        double velocity = speed / vMag;

        double newX = vec8.getX() * velocity;
        double newY = vec8.getY() * velocity;

        return new Vector2D(newX, newY);
    }

    public Vector2D flip() {
        return new Vector2D(-x, -y);
    }
}
